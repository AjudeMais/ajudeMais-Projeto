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

    DashboardAdminController.$inject = ['dashboardAdminService'];

    function DashboardAdminController(dashboardAdminService) {

        var vm = this;
        vm.countInstituicoes = 0;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;

        vm.labelsDoacoesPeriod = [];
        vm.dataDoacoesPeriod = [];
        vm.seriesDoacoesPeriod = ['Doações em Tramite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];

        vm.options = {
            legend: {
                display: true
            }
        };

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
            dashboardAdminService.getDoacoesByPeriodo(30, "ENTREGUE").then(function (response) {
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
        var getDoacoesByPeriodoNaoAceitos= function () {
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
