/**
 * @ngdoc controller
 * @name DoadorController
 *
 * @description Controller para consulta de doadores.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {

    angular.module('amApp')
        .controller('DoadorController', function (doadorService, DTOptionsBuilder, DTColumnDefBuilder) {

            var vm = this;
            vm.doadores = [];

            vm.dtOptions = DTOptionsBuilder.newOptions()
                .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

            /**
             *
             */
            vm.getDoadores = function () {
                doadorService.getDoadores(function (response) {
                    vm.doadores = response;
                });
            }

            vm.getDoadores();

        });
})();
