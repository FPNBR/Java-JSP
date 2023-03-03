package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Define o tipo de conteúdo e a codificação de caracteres
        request.setCharacterEncoding("UTF-8"); // Define a codificação de caracteres

        try {
            String acao = request.getParameter("acao");

            if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarUsuario")) {
                String idUsuario = request.getParameter("id");
                daoUsuarioRepository.deletarUsuario(idUsuario);

                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);

                request.setAttribute("msg", "Excluído com sucesso!");
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }
            
            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarUsuarioAjax")) {
                    String idUsuario = request.getParameter("id");
                    daoUsuarioRepository.deletarUsuario(idUsuario);
                    response.getWriter().write("Excluído com sucesso!");
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioNomeAjax")) {
                String nomeUsuario = request.getParameter("nomeUsuario");
                List<ModelLogin> dadosUsuarios = daoUsuarioRepository.buscarUsuarioNomeAjax(nomeUsuario, super.getUsuarioLogado(request));
                ObjectMapper objectMapper = new ObjectMapper();
                String dadosUsuariosJSON = objectMapper.writeValueAsString(dadosUsuarios);
                response.addHeader("totalPaginas", ""+ daoUsuarioRepository.buscarUsuarioNomeAjaxPaginada(nomeUsuario, super.getUsuarioLogado(request)));
                response.getWriter().write(dadosUsuariosJSON);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioNomeAjaxPaginada")) {
                String nomeUsuario = request.getParameter("nomeUsuario");
                String pagina = request.getParameter("pagina");
                List<ModelLogin> dadosUsuarios = daoUsuarioRepository.buscarUsuarioNomeAjax(nomeUsuario, super.getUsuarioLogado(request), Integer.parseInt(pagina));
                ObjectMapper objectMapper = new ObjectMapper();
                String dadosUsuariosJSON = objectMapper.writeValueAsString(dadosUsuarios);
                response.addHeader("totalPaginas", ""+ daoUsuarioRepository.buscarUsuarioNomeAjaxPaginada(nomeUsuario, super.getUsuarioLogado(request)));
                response.getWriter().write(dadosUsuariosJSON);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("verEditarUsuario")) {
                String idUsuario = request.getParameter("id");
                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(idUsuario, super.getUsuarioLogado(request));

                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);

                request.setAttribute("msg", "Dados do usuário resgatados!");
                request.setAttribute("modelLogin", modelLogin);
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsuario")) {// Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));
                request.setAttribute("modelLoginView", modelLoginView);
                request.setAttribute("msg", "Usuários carregados!");
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFotoUsuario")) {
                String idUsuario = request.getParameter("id");
                ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioPorId(idUsuario, super.getUsuarioLogado(request));
                if (modelLogin.getFotoUsuario() != null && !modelLogin.getFotoUsuario().isEmpty()) {
                    response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaoFotoUsuario());
                    response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUsuario().split("\\,")[1]));
                }
            }

            else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginacao")) {
                Integer offset = Integer.parseInt(request.getParameter("pagina"));
                if (offset < 0) {
                    offset = 0;
                }
                // Mostra os usuários de forma paginada novamente na tabela na página cadastrar/usuários ao fazer outras requisições, obs: que esse método foi sobrecarregado com offset, toda vez que o usuário clicar na segunda página em diante será acionado a ação "paginacao" da servlet, onde no usuario.jsp a lógica da paginação está assim > /ServletUsuarioController?acao=paginacao&pagina="
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(this.getUsuarioLogado(request), offset);

                request.setAttribute("modelLoginView", modelLoginView);
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
            }

            else {
                // Mostra os usuários novamente na tabela na página cadastrar/usuários ao fazer outras requisições
                List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));

                request.setAttribute("modelLoginView", modelLoginView);
                request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
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
        response.setContentType("text/html;charset=UTF-8"); // Define o tipo de conteúdo e a codificação de caracteres
        request.setCharacterEncoding("UTF-8"); // Define a codificação de caracteres

        try {
            String msg = "Operação realizada com sucesso";
            String id = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            String perfil = request.getParameter("perfil");
            String sexo = request.getParameter("sexo");
            String cep = request.getParameter("cep");
            String logradouro = request.getParameter("logradouro");
            String bairro = request.getParameter("bairro");
            String localidade = request.getParameter("localidade");
            String uf = request.getParameter("uf");
            String numeroCasa = request.getParameter("numero_casa");
            String dataNascimento = request.getParameter("data_nascimento");
            String rendaMensal = request.getParameter("renda_mensal");

                rendaMensal = rendaMensal.replaceAll("^R\\$|\\.", "").replace(",", ".");

            ModelLogin modelLogin = new ModelLogin();
            modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            modelLogin.setNome(nome);
            modelLogin.setEmail(email);
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
            modelLogin.setPerfil(perfil);
            modelLogin.setSexo(sexo);
            modelLogin.setCep(cep);
            modelLogin.setLogradouro(logradouro);
            modelLogin.setBairro(bairro);
            modelLogin.setLocalidade(localidade);
            modelLogin.setUf(uf);
            modelLogin.setNumeroCasa(numeroCasa);
            modelLogin.setDataNascimento(new Date(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento).getTime()));
            modelLogin.setRendaMensal(Double.valueOf(rendaMensal));

            if (ServletFileUpload.isMultipartContent(request)) {
                Part part = request.getPart("arquivoFoto"); // Pega a foto da tela
                if (part.getSize() > 0) {
                    byte[] fotoUsuario = IOUtils.toByteArray(part.getInputStream()); // Converte a imagem para byte

                    String fotoBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(fotoUsuario);
                    modelLogin.setFotoUsuario(fotoBase64);
                    modelLogin.setExtensaoFotoUsuario(part.getContentType().split("\\/")[1]);
                }
            }

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
            List<ModelLogin> modelLoginView = daoUsuarioRepository.gerarTabelaUsuario(super.getUsuarioLogado(request));

            request.setAttribute("modelLoginView", modelLoginView);
            request.setAttribute("msg", msg);
            request.setAttribute("modelLogin", modelLogin);
            request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request)));
            request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }
    }
}
