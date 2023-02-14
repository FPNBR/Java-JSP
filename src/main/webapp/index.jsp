<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Página inicial</title>
</head>
<body>

<h1>Login teste</h1>

<form action="ServletLogin" method="post">
<input type="hidden" value="<%= request.getParameter("url") %>" name="url">

    <table>
        <tr>
            <td><label>Login</label></td>
            <td><input name="login" type="text"></td>
        </tr>
    </table>

    <table>
        <tr>
            <td><label>Senha</label></td>
            <td><input name="senha" type="password"></td>
        </tr>
    </table>

    <table>
        <tr>
            <td><input type="submit" value="Enviar"></td>
        </tr>
    </table>

    <h4>${msg}</h4>

</form>

</body>
</html>
