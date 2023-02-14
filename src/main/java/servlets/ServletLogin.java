package servlets;

import model.ModelLogin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (urlPatterns = {"/principal/ServletLogin" , "/ServletLogin"}) // Mapeamento da URL que vem da tela
public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Recebe os dados pela url em parâmetros
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Recebe os dados enviados por um formulário
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String url = request.getParameter("url");

        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            ModelLogin modelLogin = new ModelLogin();
            modelLogin.setLogin(login);
            modelLogin.setSenha(login);

            if (modelLogin.getLogin().equalsIgnoreCase("admin") && modelLogin.getSenha().equalsIgnoreCase("admin")) { // Simulando login
                request.getSession().setAttribute("usuario", modelLogin.getLogin());

                if (url == null || url.equals("null")) {
                    url = "principal/principal.jsp";
                }

                RequestDispatcher redirecionar = request.getRequestDispatcher(url);
                redirecionar.forward(request, response);

            } else {
                RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
                request.setAttribute("msg", "Informe o login e senha corretamente!");
                redirecionar.forward(request, response);
            }

        } else {
            RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Informe o login e senha corretamente!");
            redirecionar.forward(request, response);
        }
    }
}
