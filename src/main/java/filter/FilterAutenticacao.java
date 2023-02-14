package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter (urlPatterns = {"/principal/*"}) // Intercepta todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {
    public void init(FilterConfig config) throws ServletException { // Inicia os processos ou recursos quando o servidor inicia
    }

    public void destroy() { // Encerra os processos quando o servidor é parado
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException { // Intercepta as requisições e as respostas no sistema (tudo no sistema passa por esse método)
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String usuarioLogado = (String) session.getAttribute("usuario");
        String urlAutenticar = req.getServletPath(); // Url que está sendo acessada

        // Validar se está logado, se não, redireciona para a tela de login
        if (usuarioLogado == null && !urlAutenticar.equalsIgnoreCase("ServletLogin")) { // Usuário não está logado
            RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlAutenticar);
            request.setAttribute("msg", "Por favor realize o login!");
            redireciona.forward(request, response);

            return; // Para a execução e redireciona para o login

        } else {
            chain.doFilter(request, response);
        }
    }
}
