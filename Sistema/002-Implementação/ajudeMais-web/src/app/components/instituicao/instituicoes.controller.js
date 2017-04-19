/**
 * @ngdoc controller
 * @name DoadorController
 *
 * @description Controller para consulta de instituições.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {

    angular.module('amApp')
        .controller('InstituicaoController', function (instituicaoService, DTOptionsBuilder, $state) {

            var vm = this;
            vm.instituicoes = [];

            vm.dtOptions = DTOptionsBuilder.newOptions()
                .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

            /**
             *
             */
            vm.getInstituicoes = function () {
                instituicaoService.getInstituicoes(function (response) {
                    vm.instituicoes = response;
                });
            }
            vm.getInstituicoes();

            /**
             *
             */
            vm.adicionarInstituicao = function () {
                $state.go("home.instituicaoEdit", {instituicaoEdit: null});
            };

            /**
             *
             */
            vm.editarInstituicao = function (instituicao) {
                $state.go("home.instituicaoEdit", {instituicaoEdit: instituicao});
            };

        });
})();
