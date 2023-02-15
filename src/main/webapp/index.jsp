<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Página inicial</title>
</head>
<body>

    <div class="container">
        <div class="row justify-content-center row align-items-center vh-100">
            <div class="col-3">
                <form action="ServletLogin" method="post">
                    <input type="hidden" value="<%= request.getParameter("url") %>" name="url">
                    <div class="row">
                        <div class="col">
                            <h2>Entrar no sistema</h2>
                            <label class="form-label" for="login">Login</label>
                            <input class="form-control" name="login" type="text" id="login">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col mb-2">
                            <label class="form-label" for="senha">Senha</label>
                            <input class="form-control" name="senha" type="password" id="senha">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <input type="submit" value="Entrar" class="btn btn-primary w-100">
                            <h4>${msg}</h4>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
