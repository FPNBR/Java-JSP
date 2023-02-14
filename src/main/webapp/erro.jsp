<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tela de erros</title>
</head>
<body>

<h1>Mensagem de Erro, entre em contato com a equipe de suporte do sistema</h1>
<%
    out.print(request.getAttribute("msg"));
%>>

</body>
</html>
