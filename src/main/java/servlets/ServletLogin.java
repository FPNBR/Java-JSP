package servlets;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (urlPatterns = {"/principal/ServletLogin" , "/ServletLogin"}) // Mapeamento da URL que vem da tela
public class ServletLogin extends HttpServlet {
    private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Recebe os dados pela url em parâmetros
        String acao = request.getParameter("acao");

        if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
            request.getSession().invalidate(); // invalida a sessão
            RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
            redirecionar.forward(request, response);
        } else {
            doPost(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Recebe os dados enviados por um formulário
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String url = request.getParameter("url");

        try {

            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setLogin(login);
                modelLogin.setSenha(login);

                if (daoLoginRepository.validarAutenticacao(modelLogin)) {
                    modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
                    request.getSession().setAttribute("usuario", modelLogin.getLogin());
                    request.getSession().setAttribute("perfil", modelLogin.getPerfil());
                    request.getSession().setAttribute("nome", modelLogin.getNome());
                    request.getSession().setAttribute("fotoUsuario", modelLogin.getFotoUsuario());

                    if (url == null || url.equals("null")) {
                        url = "principal/principal.jsp";
                    }

                    RequestDispatcher redirecionar = request.getRequestDispatcher(url);
                    redirecionar.forward(request, response);

                } else {
                    RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
                    request.setAttribute("msg", "Informe o login e senha corretamente!");
                    redirecionar.forward(request, response);
                }

            } else {
                RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
                request.setAttribute("msg", "Informe o login e senha corretamente!");
                redirecionar.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }
}
