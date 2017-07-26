/**
 * @ngdoc controller
 * @name DashboardInstituicaoController
 *
 * @description Controller dashboard da instituição.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>

 */
(function () {
    angular.module('amApp')
        .controller('DashboardInstController', DashboardInstController);

    DashboardInstController.$inject = ['dashboardInstituicaoService'];

    function DashboardInstController(dashboardInstituicaoService) {

        var vm = this;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;

        /**
         *
         */
        var getCountDonativos = function () {
            dashboardInstituicaoService.getCountDonativos().then(function (response) {
                vm.countDonativos = response.data;
            });
        }

        /**
         *
         */
        var getCountCampanhas = function () {
            dashboardInstituicaoService.getCountCampanhas().then(function (response) {
                vm.countCampanhas = response.data;
            });
        }

        /**
         *
         */
        var getCountMensageiros = function () {
            dashboardInstituicaoService.getCountMensageiros().then(function (response) {
                vm.countMensageiros = response.data;
            });
        };

        getCountCampanhas();
        getCountDonativos();
        getCountMensageiros();

    }
})
();
