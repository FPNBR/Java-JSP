package servlets;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletTelefoneController")
public class ServletTelefoneController extends ServletGenericUtil {
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
    private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Define o tipo de conteúdo e a codificação de caracteres
        request.setCharacterEncoding("UTF-8"); // Define a codificação de caracteres

        try {
            String acao = request.getParameter("acao");

            if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarTelefone")) {
                String idTelefone = request.getParameter("id");
                daoTelefoneRepository.deletarTelefone(Long.parseLong(idTelefone));
                String idPai = request.getParameter("idPai");

                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(Long.parseLong(idPai));
                List<ModelTelefone> modelTelefones = daoTelefoneRepository.gerarTabelaTelefone(modelLogin.getId());

                request.setAttribute("msg", "Telefone exclúido!");
                request.setAttribute("modelTelefones", modelTelefones);
                request.setAttribute("modelLogin", modelLogin);
                request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

                return;
            }

            String idUsuario = request.getParameter("idUsuario");

            if (idUsuario != null && !idUsuario.isEmpty()) {
                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(Long.parseLong(idUsuario));
                List<ModelTelefone> modelTelefones = daoTelefoneRepository.gerarTabelaTelefone(modelLogin.getId());

                request.setAttribute("modelTelefones", modelTelefones);
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
        response.setContentType("text/html;charset=UTF-8"); // Define o tipo de conteúdo e a codificação de caracteres
        request.setCharacterEncoding("UTF-8"); // Define a codificação de caracteres

        try {
            String usuarioPaiId = request.getParameter("id");
            String numero = request.getParameter("numero");

            if (!daoTelefoneRepository.telefoneExiste(numero, Long.valueOf(usuarioPaiId))) {

                ModelTelefone modelTelefone = new ModelTelefone();
                modelTelefone.setNumero(numero);
                modelTelefone.setUsuarioPaiId(daoUsuarioRepository.consultarUsuarioPorId(Long.parseLong(usuarioPaiId)));
                modelTelefone.setUsuarioCadastroId(super.getUsuarioLogadoObjeto(request));
                daoTelefoneRepository.salvarTelefone(modelTelefone);

                request.setAttribute("msg", "Telefone salvo com sucesso!");

            }else {
                request.setAttribute("msg", "Telefone já existe!");
            }

            ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(Long.parseLong(usuarioPaiId));
            List<ModelTelefone> modelTelefones = daoTelefoneRepository.gerarTabelaTelefone(Long.parseLong(usuarioPaiId));

            request.setAttribute("modelLogin", modelLogin);
            request.setAttribute("modelTelefones", modelTelefones);
            request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }
}
