package filter;

import connection.SingleConnection;
import dao.DAOVersionadorBanco;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

@WebFilter (urlPatterns = {"/principal/*"}) // Intercepta todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {

    private Connection connection;

    public void init(FilterConfig config) throws ServletException { // Inicia os processos ou recursos quando o servidor inicia
        try {
            connection = SingleConnection.getConnection();
            DAOVersionadorBanco daoVersionadorBanco = new DAOVersionadorBanco();
            String caminhoPastaSQL = config.getServletContext().getRealPath("versionadorBancoSQL") + File.separator;
            File[] filesSql = new File(caminhoPastaSQL).listFiles();
            System.out.println("rsrs");

            for (File file : filesSql) {
                boolean arquivoJaExecutou = daoVersionadorBanco.arquivoSqlExecutado(file.getName());

                if (!arquivoJaExecutou) {
                    FileInputStream entradaArquivo = new FileInputStream(file);
                    Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
                    StringBuilder sql = new StringBuilder();

                    while (lerArquivo.hasNext()) {
                        sql.append(lerArquivo.nextLine());
                        sql.append("\n");
                    }

                    connection.prepareStatement(sql.toString()).execute();
                    daoVersionadorBanco.salvarArquivoSqlExecutado(file.getName());
                    connection.commit();
                    lerArquivo.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void destroy() { // Encerra os processos quando o servidor é parado
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException { // Intercepta as requisições e as respostas no sistema (tudo no sistema passa por esse método)
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession session = httpServletRequest.getSession();
            String usuarioLogado = (String) session.getAttribute("usuario");
            String urlAutenticar = httpServletRequest.getServletPath(); // Url que está sendo acessada

            // Validar se está logado, se não, redireciona para a tela de login
            if (usuarioLogado == null && !urlAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // Usuário não está logado
                RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlAutenticar);
                request.setAttribute("msg", "Por favor realize o login!");
                redireciona.forward(request, response);
                return; // Para a execução e redireciona para o login

            } else {
                chain.doFilter(request, response);
            }

            connection.commit(); // Comita as alterações no banco

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e.printStackTrace();
            }
        }
    }
}
