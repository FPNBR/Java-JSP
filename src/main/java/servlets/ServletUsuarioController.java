package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");

            if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarUsuario")) {
                String idUsuario = request.getParameter("id");
                daoUsuarioRepository.deletarUsuario(idUsuario);

                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.consultarUsuarioView(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);

                request.setAttribute("msg", "Excluído com sucesso!");
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }
            
            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarUsuarioAjax")) {
                    String idUsuario = request.getParameter("id");
                    daoUsuarioRepository.deletarUsuario(idUsuario);
                    response.getWriter().write("Excluído com sucesso!");
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioAjax")) {
                String nomeUsuario = request.getParameter("nomeUsuario");
                List<ModelLogin> dadosUsuarios = daoUsuarioRepository.consultarUsuarioList(nomeUsuario, super.getUsuarioLogado(request));
                ObjectMapper objectMapper = new ObjectMapper();
                String dadosUsuariosJSON = objectMapper.writeValueAsString(dadosUsuarios);
                response.getWriter().write(dadosUsuariosJSON);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("verEditarUsuario")) {
                String idUsuario = request.getParameter("id");
                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(idUsuario, super.getUsuarioLogado(request));

                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.consultarUsuarioView(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);

                request.setAttribute("msg", "Dados do usuário resgatados!");
                request.setAttribute("modelLogin", modelLogin);
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsuario")) {// Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.consultarUsuarioView(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);
                request.setAttribute("msg", "Usuários carregados!");
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

            else {
                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.consultarUsuarioView(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

        }catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String msg = "Operação realizada com sucesso";
            String id = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            String perfil = request.getParameter("perfil");

            ModelLogin modelLogin = new ModelLogin();
            modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            modelLogin.setNome(nome);
            modelLogin.setEmail(email);
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
            modelLogin.setPerfil(perfil);

            if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
                msg = "Já existe um usuário com o mesmo login!";
            }else {
                if (modelLogin.idExiste()) {
                    msg = "Cadastrado com sucesso!";
                }else {
                    msg = "Atualizado com sucesso!";
                }
                modelLogin = daoUsuarioRepository.salvarUsuario(modelLogin, super.getUsuarioLogado(request));
            }

            // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
            List<ModelLogin> modelLoginView = daoUsuarioRepository.consultarUsuarioView(super.getUsuarioLogado(request));
            request.setAttribute("modelLoginView", modelLoginView);

            request.setAttribute("msg", msg);
            request.setAttribute("modelLogin", modelLogin);
            request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }
}
