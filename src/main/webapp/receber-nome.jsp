<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
  String nome = request.getParameter("nome");
  out.println("Nome: " + nome);

  String idade = request.getParameter("idade");
  out.println("Idade: " + idade);
%>

</body>
</html>
