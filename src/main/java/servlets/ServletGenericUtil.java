package servlets;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.SQLException;

public class ServletGenericUtil extends HttpServlet implements Serializable {
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public Long getUsuarioLogado(HttpServletRequest request) throws SQLException {
            HttpSession session = request.getSession();
            String usuarioLogado = (String) session.getAttribute("usuario");
            return  daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
    }

    public ModelLogin getUsuarioLogadoObjeto(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        String usuarioLogado = (String) session.getAttribute("usuario");
        return  daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado);
    }
}
