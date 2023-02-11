package servlets;

import model.ModelLogin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletLogin", value = "/ServletLogin") // Mapeamento da URL que vem da tela
public class ServletLogin extends HttpServlet {

    @Override // Recebe os dados pela url em parâmetros
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override // Recebe os dados enviados por um formulário
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            ModelLogin modelLogin = new ModelLogin();
            modelLogin.setLogin(login);
            modelLogin.setSenha(login);
        } else {
            RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Informe o login e senha corretamente!");
            redirecionar.forward(request, response);
        }
    }
}
