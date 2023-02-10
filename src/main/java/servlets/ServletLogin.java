package servlets;

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
        System.out.println(request.getParameter("nome"));
        System.out.println(request.getParameter("idade"));
    }
}
