<%@ page import="model.ModelLogin" %>
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
                                                    <form class="form-material" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ServletUsuarioController" method="post" id="formUsuario">
                                                        <input type="hidden" name="acao" id="acao" value="">

                                                        <div class="form-group form-default input-group mb-4">
                                                            <div class="d-flex justify-content-center align-items-center">
                                                                <div class="input-group-prepend">
                                                                    <c:if test="${modelLogin.fotoUsuario != '' && modelLogin.fotoUsuario != null}">
                                                                        <a href="<%= request.getContextPath()%>/ServletUsuarioController?acao=downloadFotoUsuario&id=${modelLogin.id}">
                                                                        <img class="img-70 img-radius" id="fotoBase64" src="${modelLogin.fotoUsuario}" alt="Foto Usuário" style="width: 70px; height: 70px;">
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${modelLogin.fotoUsuario == '' || modelLogin.fotoUsuario == null}">
                                                                        <img class="img-70 img-radius" id="fotoBase64" src="/Java-JSP/assets/images/icon_user.png" alt="User-Profile-Image">
                                                                    </c:if>
                                                                </div>
                                                                <input type="file" id="arquivoFoto" name="arquivoFoto" accept="image/*" onchange="fotoUsuario('fotoBase64', 'arquivoFoto');" class="form-control-file" style="margin-left: 5px;">
                                                            </div>
                                                        </div>
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
                                                            <input type="text" name="data_nascimento" id="data_nascimento" class="form-control" required="required" autocomplete="off" value="${modelLogin.dataNascimento}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Data de nascimento:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="renda_mensal" id="renda_mensal" class="form-control" required="required" autocomplete="off" value="${modelLogin.rendaMensal}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Renda mensal:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Email: (exemplo@gmail.com)</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                        <select class="form-control" aria-label="Default select example" name="perfil">
                                                            <option disabled>[Selecione o perfil do usuário]</option>
                                                            <option value="admin" <%
                                                                ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
                                                                if (modelLogin != null && modelLogin.getPerfil().equals("admin")) {
                                                                out.print(" ");
                                                                    out.print("selected=\"selected\"");
                                                                out.print(" ");
                                                            }%>>Admin</option>
                                                            <option value="secretario" <%
                                                                modelLogin = (ModelLogin) request.getAttribute("modelLogin");
                                                                if (modelLogin != null && modelLogin.getPerfil().equals("secretario")) {
                                                                out.print(" ");
                                                                out.print("selected=\"selected\"");
                                                                out.print(" ");
                                                            }%>>Secretário</option>
                                                            <option value="auxiliar" <%
                                                                modelLogin = (ModelLogin) request.getAttribute("modelLogin");
                                                                if (modelLogin != null && modelLogin.getPerfil().equals("auxiliar")) {
                                                                out.print(" ");
                                                                out.print("selected=\"selected\"");
                                                                out.print(" ");
                                                            }%>>Auxiliar</option>
                                                        </select>
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Perfil:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <select class="form-control" id="sexo" name="sexo">
                                                                <option value="masculino" <% if(modelLogin != null && modelLogin.getSexo().equals("masculino")) { out.print("selected"); } %>>Masculino</option>
                                                                <option value="feminino" <% if(modelLogin != null && modelLogin.getSexo().equals("feminino")) { out.print("selected"); } %>>Feminino</option>
                                                            </select>
                                                            <span class="form-bar"></span>
                                                            <label class="float-label" for="sexo">Sexo:</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input onblur="pesquisaCEP();" type="text" name="cep" id="cep" class="form-control" required="required" autocomplete="off" value="${modelLogin.cep}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">CEP</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="logradouro" id="logradouro" class="form-control" required="required" autocomplete="off" value="${modelLogin.logradouro}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Rua</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="bairro" id="bairro" class="form-control" required="required" autocomplete="off" value="${modelLogin.bairro}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Bairro</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="localidade" id="localidade" class="form-control" required="required" autocomplete="off" value="${modelLogin.localidade}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Cidade</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="uf" id="uf" class="form-control" required="required" autocomplete="off" value="${modelLogin.uf}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Estado</label>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="numero_casa" id="numero_casa" class="form-control" required="required" autocomplete="off" value="${modelLogin.numeroCasa}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Número da Casa</label>
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
                                                        <button type="button" class="btn btn-danger waves-effect waves-light" onclick="deletarUsuario();">Excluir</button>
                                                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modalUsuario">Pesquisar Usuário</button>
                                                        <c:if test="${modelLogin.id > 0}">
                                                            <a href="<%= request.getContextPath() %>/ServletTelefoneController?idUsuario=${modelLogin.id}" class="btn btn-link">Ver Telefones</a>
                                                        </c:if>
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
                                                <c:forEach items="${modelLoginView}" var="mt">
                                                    <tr>
                                                        <td><c:out value="${mt.id}"></c:out></td>
                                                        <td><c:out value="${mt.nome}"></c:out></td>
                                                        <td><a class="btn btn-warning" href="<%=request.getContextPath()%>/ServletUsuarioController?acao=verEditarUsuario&id=${mt.id}">Ver/Editar Usuário</a></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <nav aria-label="Paginação">
                                            <ul class="pagination">
                                                <%
                                                    int totalPaginas = (int) request.getAttribute("totalPaginas");
                                                    for (int i = 0; i < totalPaginas; i++) {
                                                        String url = request.getContextPath() + "/ServletUsuarioController?acao=paginacao&pagina=" + (i * 5);
                                                        out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(i + 1)+"</a></li>");
                                                    }
                                                %>
                                            </ul>
                                        </nav>
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
                            <button class="btn btn-info" type="button" onclick="buscarUsuarioNomeAjax();">Buscar</button>
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
                <nav aria-label="Page navigation example">
                    <ul class="pagination" id="modaltabelaResultadoUsuario">
                    </ul>
                </nav>
                <span id="tabelaQuantidadeUsuario"></span>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>

<script type="text/javascript">

    $("#renda_mensal").maskMoney({showSymbol:true, symbol:"R$", decimal:",", thousands:"."});

    const formatter = new Intl.NumberFormat('pt-BR', {
        currency : 'BRL',
        minimumFractionDigits : 2
    })

    $("#renda_mensal").val(formatter.format($("#renda_mensal").val()))
    $("#renda_mensal").focus();

    var data_nascimento = $("#data_nascimento").val();
    var dateFormat = new Date(data_nascimento);
    $("#data_nascimento").val(dateFormat.toLocaleDateString('pt-BR', {timeZone : 'UTC'}));
    $("#nomeUsuario").focus();

    $( function() {
        $("#data_nascimento").datepicker({
            dateFormat: 'dd/mm/yy',
            dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
            dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
            dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
            monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
            monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
            nextText: 'Próximo',
            prevText: 'Anterior',
            changeYear: true
        });
    });


    $("#numero_casa").keypress(function (event) {
        return /\d/.test(String.fromCharCode(event.keyCode));
    });

    $("#cep").keypress(function (event) {
        return /\d/.test(String.fromCharCode(event.keyCode));
    });

    function pesquisaCEP() {
        var cep = $("#cep").val();

        $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
            if (!("erro" in dados)) {
                $("#cep").val(dados.cep);
                $("#logradouro").val(dados.logradouro);
                $("#bairro").val(dados.bairro);
                $("#localidade").val(dados.localidade);
                $("#uf").val(dados.uf);
            }
        });
    }

    function fotoUsuario(fotoBase64, arquivoFoto) {
        var preview = document.getElementById(fotoBase64); // Campo IMG HTML
        var arquivoUsuario = document.getElementById(arquivoFoto).files[0];
        var reader = new FileReader();

        reader.onloadend = function () {
            preview.src = reader.result; // Carrega a foto na tela
        }

        if (arquivoUsuario) {
            reader.readAsDataURL(arquivoUsuario); // Preview da imagem
            preview.style.width = "70px";
            preview.style.height = "70px";
            }
        else {
            preview.src = '';
        }
    }

    function verEditarUsuario(id) {
        var urlAction = document.getElementById('formUsuario').action;
        window.location.href = urlAction + '?acao=verEditarUsuario&id='+id;
    }

    function buscarUsuarioNomeAjaxPaginada(url) {
        var nomeUsuario = document.getElementById('nomeUsuario').value;
        var urlAction = document.getElementById('formUsuario').action;

        $.ajax({
            method: 'get',
            url: urlAction,
            data: url,
            success: function (response, textStatus, xhr) {
                var json = JSON.parse(response);

                $('#tabelaResultadoUsuario > tbody > tr').remove();
                $('#modaltabelaResultadoUsuario > li').remove();

                for (var i = 0; i < json.length; i++) {
                    $('#tabelaResultadoUsuario > tbody').append('<tr> ' +
                        '<td>' + json[i].id + ' </td>' +
                        '<td>' + json[i].nome + ' </td>' +
                        '<td><button onclick="verEditarUsuario('+json[i].id+')" type="button" class="btn btn-warning">Editar usuário</button></td> </tr>');
                }

                document.getElementById('tabelaQuantidadeUsuario').textContent = 'Usuários encontrados: ' + json.length;
                var totalPaginas = xhr.getResponseHeader('totalPaginas');

                for (var j = 0; j < totalPaginas; j++) {
                    var url = 'nomeUsuario=' + nomeUsuario + '&acao=buscarUsuarioNomeAjaxPaginada&pagina='+ (j * 5);
                    $("#modaltabelaResultadoUsuario").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUsuarioNomeAjaxPaginada(\''+url+'\')">'+ (j + 1) +'</a></li>');                    }
            }

        }).fail(function (xhr, status, errorThrown){
            alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
        });
    }

    function buscarUsuarioNomeAjax() {
        var nomeUsuario = document.getElementById('nomeUsuario').value;

        if (nomeUsuario != null && nomeUsuario != '' && nomeUsuario.trim() != '') { // Validando que tem que ter valor para buscar no banco
            var urlAction = document.getElementById('formUsuario').action;

            $.ajax({
                method: 'get',
                url: urlAction,
                data: 'nomeUsuario=' + nomeUsuario + '&acao=buscarUsuarioNomeAjax',
                success: function (response, textStatus, xhr) {
                    var json = JSON.parse(response);

                    $('#tabelaResultadoUsuario > tbody > tr').remove();
                    $('#modaltabelaResultadoUsuario > li').remove();

                    for (var i = 0; i < json.length; i++) {
                        $('#tabelaResultadoUsuario > tbody').append('<tr> ' +
                            '<td>' + json[i].id + ' </td>' +
                            '<td>' + json[i].nome + ' </td>' +
                            '<td><button onclick="verEditarUsuario('+json[i].id+')" type="button" class="btn btn-warning">Editar usuário</button></td> </tr>');
                    }

                    document.getElementById('tabelaQuantidadeUsuario').textContent = 'Usuários encontrados: ' + json.length;
                    var totalPaginas = xhr.getResponseHeader("totalPaginas");

                    for (var j = 0; j < totalPaginas; j++) {
                        var url = 'nomeUsuario=' + nomeUsuario + '&acao=buscarUsuarioNomeAjaxPaginada&pagina='+ (j * 5);
                        $("#modaltabelaResultadoUsuario").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUsuarioNomeAjaxPaginada(\''+url+'\')">'+ (j + 1) +'</a></li>');                    }
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
