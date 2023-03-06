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
                                                    <h4 class="sub-title">Relatório de Usuário</h4>
                                                    <form class="form-material" action="<%=request.getContextPath()%>/ServletUsuarioController?acao=imprimirRelatorioUsuario" method="get" id="formUsuario">
                                                        <input type="hidden" id="acaoImprimirRelatorioTipo" name="acao" value="imprimirRelatorioUsuario">

                                                        <div class="form-row align-items-center">
                                                            <div class="col-auto">
                                                                <label class="sr-only" for="dataInicial">Data Inicial</label>
                                                                <input value="${dataInicial}" type="text" class="form-control mb-2" id="dataInicial" name="dataInicial" placeholder="Data inicial">
                                                            </div>
                                                            <div class="col-auto">
                                                                <label class="sr-only" for="dataFinal">Data Final</label>
                                                                <div class="input-group mb-2">
                                                                    <input value="${dataFinal}" type="text" class="form-control" id="dataFinal" name="dataFinal" placeholder="Data Final">
                                                                </div>
                                                            </div>
                                                            <div class="col-auto">
                                                                <button type="button" onclick="imprimirHTML();" class="btn btn-primary">Gerar tabela</button>
                                                                <button type="button" onclick="imprimirPDF();" class="btn btn-danger">Imprimir PDF</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                    <div style="height: 300px; overflow: scroll;">
                                                        <table class="table" id="listaRelatorioUsuario">
                                                            <thead class="thead-dark">
                                                            <tr>
                                                                <th scope="col">ID</th>
                                                                <th scope="col">Nome/Números</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach items="${listaRelatorioUsuario}" var="lru">
                                                                <tr>
                                                                    <td><c:out value="${lru.id}"></c:out></td>
                                                                    <td>
                                                                        <h4><span class="badge badge-primary"><c:out value="${lru.nome}"></c:out></span></h4>
                                                                        <c:forEach items="${lru.modelTelefones}" var="telefones">
                                                                            <span class="badge badge-secondary"><c:out value="${telefones.numero}"></c:out></span><br>
                                                                        </c:forEach>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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

    function imprimirHTML() {
        document.getElementById("acaoImprimirRelatorioTipo").value = 'imprimirRelatorioUsuario';
        $("#formUsuario").submit();
    }

    function imprimirPDF() {
        document.getElementById("acaoImprimirRelatorioTipo").value = 'imprimirRelatorioUsuarioPDF';
        $("#formUsuario").submit();
    }

    $( function() {
        $("#dataInicial").datepicker({
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

    $( function() {
        $("#dataFinal").datepicker({
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


</script>
</body>
</html>

