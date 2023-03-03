<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
<!-- Pre-loader start -->
<jsp:include page="theme-loader.jsp"></jsp:include>

<!-- Pre-loader end -->
<div id="pcoded" class="pcoded">
    <div class="pcoded-overlay-box"></div>
    <div class="pcoded-container navbar-wrapper">

        <jsp:include page="navbar.jsp"></jsp:include>

        <div class="pcoded-main-container">
            <div class="pcoded-wrapper">

                <jsp:include page="navbarmainmenu.jsp"></jsp:include>

                <div class="pcoded-content">
                    <!-- Page-header start -->
                    <jsp:include page="page-header.jsp"></jsp:include>
                    <!-- Page-header end -->
                    <div class="pcoded-inner-content">
                        <!-- Main-body start -->
                        <div class="main-body">
                            <div class="page-wrapper">
                                <!-- Page-body start -->
                                <div class="page-body">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <!-- Basic Form Inputs card start -->
                                            <div class="card">
                                                <div class="card-block">
                                                    <h4 class="sub-title">Cadastro de Telefone</h4>
                                                    <form class="form-material" action="<%=request.getContextPath() %>/ServletTelefoneController" method="post" id="formTelefone">
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" id="id" class="form-control" autocomplete="off" readonly="readonly" value="${modelLogin.id}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">ID Usuário:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input readonly="readonly" type="text" name="nome" id="nome" class="form-control" required="required" autocomplete="off" value="${modelLogin.nome}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Nome do Usuário:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="numero" id="numero" class="form-control" required="required" autocomplete="off">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Número:</label>
                                                        </div>
                                                        <button type="submit" class="btn btn-success waves-effect waves-light">Salvar Telefone</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <span id="msg">${msg}</span>
                                    <div style="height: 300px; overflow: scroll;">
                                        <table class="table" id="tabelaResultadoUsuarioView">
                                            <thead class="thead-dark">
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Número</th>
                                                <th scope="col">Excluir</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${modelTelefones}" var="mt">
                                                <tr>
                                                    <td><c:out value="${mt.id}"></c:out></td>
                                                    <td><c:out value="${mt.numero}"></c:out></td>
                                                    <td><a class="btn btn-warning" href="<%=request.getContextPath()%>/ServletTelefoneController?acao=deletarTelefone&id=${mt.id}&idPai=${modelLogin.id}">Excluir</a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!-- Page-body end -->
                            </div>
                            <div id="styleSelector"> </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="javascriptfile.jsp"></jsp:include>

<script type="text/javascript">

    $("#numero").keypress(function (event) {
        return /\d/.test(String.fromCharCode(event.keyCode));
    });

</script>
</body>
</html>

