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
                                                    <h4 class="sub-title">Cadastro de usuário</h4>
                                                    <form class="form-material" action="<%=request.getContextPath()%>/ServletUsuarioController" method="post" id="formUsuario">

                                                        <input type="hidden" name="acao" id="acao" value="">

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" id="id" class="form-control" autocomplete="off" readonly="readonly" value="${modelLogin.id}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">ID:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="nome" id="nome" class="form-control" required="required" autocomplete="off" value="${modelLogin.nome}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Nome completo:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Email: (exemplo@gmail.com)</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                        <select class="form-control" aria-label="Default select example" name="perfil">
                                                            <option disabled>[Selecione o perfil do usuário]</option>
                                                            <option value="admin">Admin</option>
                                                            <option value="secretario">Secretário</option>
                                                            <option value="auxiliar">Auxiliar</option>
                                                        </select>
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Perfil:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Login:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Senha:</label>
                                                        </div>
                                                        <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                        <button type="submit" class="btn btn-success waves-effect waves-light">Salvar</button>
                                                        <button type="button" class="btn btn-danger waves-effect waves-light" onclick="deletarUsuarioAjax();">Excluir</button>
                                                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modalUsuario">Pesquisar Usuário</button>
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
                                                <th scope="col">Nome</th>
                                                <th scope="col">Ver/Editar</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${modelLoginView}" var="mlv">
                                                <tr>
                                                    <td><c:out value="${mlv.id}"></c:out></td>
                                                    <td><c:out value="${mlv.nome}"></c:out></td>
                                                    <td><a class="btn btn-warning" href="<%=request.getContextPath()%>/ServletUsuarioController?acao=verEditarUsuario&id=${mlv.id}">Ver/Editar Usuário</a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
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

    <div class="modal fade" id="modalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Lista de usuários</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Digite o nome do usuário" aria-label="nome" id="nomeUsuario" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-info" type="button" onclick="buscarUsuarioAjax();">Buscar</button>
                        </div>
                    </div>
                    <div style="height: 300px; overflow: scroll;">
                        <table class="table" id="tabelaResultadoUsuario">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Ver/Editar</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <span id="tabelaQuantidadeUsuario"></span>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>

<script type="text/javascript">

    function verEditarUsuario(id) {
        var urlAction = document.getElementById('formUsuario').action;
        window.location.href = urlAction + '?acao=verEditarUsuario&id='+id;
    }

    function buscarUsuarioAjax() {
        var nomeUsuario = document.getElementById('nomeUsuario').value;
        var urlAction = document.getElementById('formUsuario').action;

        if (nomeUsuario != null && nomeUsuario != '' && nomeUsuario.trim() != '') { // Validando que tem que ter valor para buscar no banco
            $.ajax({
                method: 'get',
                url: urlAction,
                data: 'nomeUsuario=' + nomeUsuario + '&acao=buscarUsuarioAjax',
                success: function (response) {
                    var json = JSON.parse(response);

                    $('#tabelaResultadoUsuario > tbody > tr').remove();

                    for (var i = 0; i < json.length; i++) {
                        $('#tabelaResultadoUsuario > tbody').append('<tr> ' +
                            '<td>' + json[i].id + ' </td>' +
                            '<td>' + json[i].nome + ' </td>' +
                            '<td><button onclick="verEditarUsuario('+json[i].id+')" type="button" class="btn btn-warning">Editar usuário</button></td> </tr>');
                    }
                    document.getElementById('tabelaQuantidadeUsuario').textContent = 'Usuários encontrados: ' + json.length;
                }

            }).fail(function (xhr, status, errorThrown){
                alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
            });
        }
    }

    function limparForm() {
        var elementos = document.getElementById('formUsuario').elements; // Retorna os elementos html dentro do form
        for (i = 0; i < elementos.length; i++) {
            elementos[i].value = '';
        }
    }

    function deletarUsuarioAjax() {
        if (confirm('Deseja realmente excluir os dados?')) {
            var urlAction = document.getElementById('formUsuario').action;
            var idUsuario = document.getElementById('id').value;

            $.ajax({
                method: 'get',
                url: urlAction,
                data: 'id=' + idUsuario + '&acao=deletarUsuarioAjax',
                success: function (response) {
                    limparForm();
                    document.getElementById('msg').textContent = response;
                }

            }).fail(function (xhr, status, errorThrown){
               alert('Erro ao deletar usuário por id: ' + xhr.responseText);
            });
        }
    }

    function deletarUsuario() {
        if (confirm('Deseja realmente excluir os dados?')) {
            document.getElementById('formUsuario').method = 'get';
            document.getElementById('acao').value = 'deletarUsuario';
            document.getElementById('formUsuario').submit();
        }
    }

</script>
</body>
</html>
