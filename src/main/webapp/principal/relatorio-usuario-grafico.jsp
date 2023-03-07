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
                                                    <form class="form-material" action="<%=request.getContextPath()%>/ServletUsuarioController" method="get" id="formUsuario">
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
                                                                <button type="button" onclick="gerarGraficoSalario();" class="btn btn-primary">Gerar Gráfico</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                        <div>
                                                            <canvas id="myChart"></canvas>
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

    var myChart = new Chart(document.getElementById('myChart'));

    function gerarGraficoSalario() {
        var urlAction = document.getElementById('formUsuario').action;
        var dataInicial = document.getElementById('dataInicial').value;
        var dataFinal = document.getElementById('dataFinal').value;

        $.ajax({
            method: 'get',
            url: urlAction,
            data: 'dataInicial=' + dataInicial + '&dataFinal=' + dataFinal + '&acao=gerarGraficoSalario',
            success: function (response) {
                var json = JSON.parse(response);

                myChart.destroy();

                myChart = new Chart(
                    document.getElementById('myChart'),
                    {
                        type: 'bar',
                        data: {
                            labels: json.perfis,
                            datasets: [{
                                label: 'Gráfico de média salarial por tipo de usuário',
                                backgroundColor: '#2196F3',
                                borderColor: '#2196F3',
                                data: json.salarios.map(function(salario) { return salario < 0 ? 0 : salario }),
                            }]
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero: true
                                    }
                                }]
                            }
                        }
                    }
                );
            }

        }).fail(function (xhr, status, errorThrown){
            alert('Erro ao gerar média salarial dos usuarios: ' + xhr.responseText);
        });
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

