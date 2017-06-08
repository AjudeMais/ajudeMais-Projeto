/**
 * @ngdoc controller
 * @name mensageiroAssociadoService
 *
 * @description Controller para consulta de mensageiros associados
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('MensageiroAssociadoController', MensageiroAssociadoController);

    MensageiroAssociadoController.$inject = ['mensageiroAssociadoService', 'DTOptionsBuilder', '$uibModal'];

    function MensageiroAssociadoController(mensageiroAssociadoService, DTOptionsBuilder, $uibModal) {

        var vm = this;
        vm.associados = [];

        vm.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

        /**
         * Busca todos os mensageiros associados a instituição logada.
         */
        vm.getMensageirosAssociados = function () {
            mensageiroAssociadoService.getByInstituicao(function (response) {
                vm.associados = response;
            });
        }
        vm.getMensageirosAssociados();


        /**
         * Abre modal, configurando template e controller
         * @param categoria
         */
        function openModal(mensageiroAss) {
            return $uibModal.open({
                templateUrl: 'app/components/mensageiroAssociado/mensageiro-associado.edit.html',
                controller: 'MensageiroAssociadoEditController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    mensageiroAss: function () {
                        return mensageiroAss;
                    }
                }
            });
        }

        /**
         *
         */
        vm.addAssociacao = function () {
            var modal = openModal({});

            modal.result.then(function (mensageiroAss) {
                vm.associados.push(mensageiroAss);
            });
        }

        /**
         *
         * @param categoria
         */
        vm.editAssociacao = function (mensageiroAss) {
            openModal(mensageiroAss);
        }
    }
})();
