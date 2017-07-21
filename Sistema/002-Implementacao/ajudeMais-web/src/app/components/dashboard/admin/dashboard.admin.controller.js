/**
 * @ngdoc controller
 * @name DashboardAdminController
 *
 * @description Controller dashboard do administrador.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('DashboardAdminController', DashboardAdminController);

    DashboardAdminController.$inject = ['dashboardAdminService', 'instituicaoService',
        'donativoService', 'campanhaService', 'mensageiroAssociadoService'];

    function DashboardAdminController(dashboardAdminService, instituicaoService,
                                      donativoService, campanhaService, mensageiroAssociadoService) {

        var vm = this;
        vm.countInstituicoes = 0;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;
        vm.countDonativosCampanhaInst = 0;
        vm.countDonativosInst = 0;
        vm.countCampanhasInst = 0;
        vm.countMensageirosInst = 0;
        vm.statusPanelLineChart = true;
        vm.statusPanelInstDetail = true;
        vm.instituicaoSelected = {};
        vm.instituicoesAtivas = [];
        vm.campanhasAtivasInt = [];

        vm.labelsDoacoesPeriod = [];
        vm.dataDoacoesPeriod = [];
        vm.seriesDoacoesPeriod = ['Doações em Trâmite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];

        vm.labelsDoacoesInstPeriod = [];
        vm.dataDoacoesInstPeriod = [];
        vm.seriesDoacoesInstPeriod = ['Doações em Trâmite'];

        vm.options = {
            legend: {
                display: true
            }
        };

        var getInstituicoesAtivas = function () {
            instituicaoService.getInstituicoesAtivas().then(function (response) {
                vm.instituicoesAtivas = response.data;
                vm.instituicaoSelected = vm.instituicoesAtivas[0];
                vm.onSelectInstituicao();
                console.log(vm.instituicaoSelected);
            })
        };
        getInstituicoesAtivas();

        /**
         *
         */
        var getCountInstituicaoCaridade = function () {
            dashboardAdminService.getCountInstituicaoCaridade().then(function (response) {
                vm.countInstituicoes = response.data;
            });
        }

        /**
         *
         */
        var getCountDonativos = function () {
            dashboardAdminService.getCountDonativos().then(function (response) {
                vm.countDonativos = response.data;
            });
        }

        /**
         *
         */
        var getCountCampanhas = function () {
            dashboardAdminService.getCountCampanhas().then(function (response) {
                vm.countCampanhas = response.data;
            });
        }

        /**
         *
         */
        var getCountMensageiros = function () {
            dashboardAdminService.getCountMensageiros().then(function (response) {
                vm.countMensageiros = response.data;
            });
        };

        /**
         *
         */
        var getCountDonativosCampanhaByInstituicao = function (id) {
            donativoService.countDonativosCampanhaByInstituicao(id).then(function (response) {
                vm.countDonativosCampanhaInst = response.data;
            });
        };

        /**
         *
         */
        var getCountDonativosByInstituicao = function (id) {
            donativoService.countDonativosByInstituicao(id).then(function (response) {
                vm.countDonativosInst = response.data;
            });
        };

        /**
         *
         */
        var getCountCampanhasByInstituicao = function (id) {
            campanhaService.getCountCampanhasByInstituicao(id).then(function (response) {
                vm.countCampanhasInst = response.data;
            });
        };

        /**
         *
         */
        var getCountMensageirosByInstituicao = function (id) {
            mensageiroAssociadoService.getCountByInstituicao(id).then(function (response) {
                vm.countMensageirosInst = response.data;
            });
        };

        /**
         *
         */
        var getCampanhasAtivasByInstituicao = function (id) {
            campanhaService.getCampanhasAtivasByInstituicao(id).then(function (response) {
                vm.campanhasAtivasInt = response.data;
                console.log(vm.campanhasAtivasInt);
            });
        };

        /**
         *
         */
        vm.onSelectInstituicao = function () {
            getCountDonativosCampanhaByInstituicao(vm.instituicaoSelected.id);
            getCountDonativosByInstituicao(vm.instituicaoSelected.id)
            getCountCampanhasByInstituicao(vm.instituicaoSelected.id);
            getCountMensageirosByInstituicao(vm.instituicaoSelected.id);
            getCampanhasAtivasByInstituicao(vm.instituicaoSelected.id);
            getDoacoesByPeriodoInstituicao(vm.instituicaoSelected.id);
        }

        /**
         *
         */
        var getDoacoesByPeriodo = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "RECOLHIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    vm.labelsDoacoesPeriod.push(dto.date);
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoCanceladas();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoInstituicao = function (idInst) {
            vm.seriesDoacoesInstPeriod = ['Doações em Trâmite'];
            vm.labelsDoacoesInstPeriod = [];
            vm.dataDoacoesInstPeriod = [];
            dashboardAdminService.getDoacoesByPeriodoInstituicao(30, "RECOLHIDO", idInst).then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    vm.labelsDoacoesInstPeriod.push(dto.date);
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoCanceladas = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "CANCELADO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoEntregues();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoEntregues = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "RECEBIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoNaoAceitos();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoNaoAceitos = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "NAO_ACEITO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
            });
        }

        getCountCampanhas();
        getCountDonativos();
        getCountInstituicaoCaridade();
        getCountMensageiros();
        getDoacoesByPeriodo();
    }
})
();
