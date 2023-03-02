package servlets;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletTelefoneController")
public class ServletTelefoneController extends ServletGenericUtil {
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Define o tipo de conteúdo e a codificação de caracteres
        request.setCharacterEncoding("UTF-8"); // Define a codificação de caracteres

        try {
            String idUsuario = request.getParameter("idUsuario");

            if (idUsuario != null && !idUsuario.isEmpty()) {
                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(Long.parseLong(idUsuario));
                request.setAttribute("modelLogin", modelLogin);
                request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

            } else {
                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));

                request.setAttribute("modelLoginView", modelLoginView);
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
