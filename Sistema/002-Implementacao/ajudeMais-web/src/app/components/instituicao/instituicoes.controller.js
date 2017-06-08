/**
 * @ngdoc controller
 * @name DoadorController
 *
 * @description Controller para consulta de instituição.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('InstituicaoController', InstituicaoController);

    InstituicaoController.$inject = ['instituicaoService', 'DTOptionsBuilder', '$state'];

    function InstituicaoController(instituicaoService, DTOptionsBuilder, $state) {

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
        vm.addInstituicao = function () {
            $state.go("home.instituicao.edit", {instituicaoEdit: null});
        };

        /**
         *
         */
        vm.editInstituicao = function (instituicao) {
            $state.go("home.instituicao.edit", {instituicaoEdit: instituicao});
        };

        /**
         *
         * @param instituicao
         */
        vm.openDetails = function(instituicao) {
            $state.go("home.instituicao.detail", {instituicaoDetail: JSON.stringify(instituicao)});
        }
    }

})();
