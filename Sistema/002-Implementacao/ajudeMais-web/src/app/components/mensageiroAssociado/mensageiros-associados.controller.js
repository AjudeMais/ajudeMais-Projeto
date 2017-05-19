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

    MensageiroAssociadoController.$inject = ['mensageiroAssociadoService', 'DTOptionsBuilder', '$uibModal', 'growl'];

    function MensageiroAssociadoController(mensageiroAssociadoService, DTOptionsBuilder, $uibModal, growl) {

        var vm = this;
        vm.associados = [];

        vm.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

        /**
         *
         */
        vm.getMensageirosAssociados = function () {
            mensageiroAssociadoService.getByInstituicao(function (response) {
                vm.associados = response;
                console.log(response);
            });
        }
        vm.getMensageirosAssociados();


        /**
         *
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
        vm.editAssociacao = function (categoria) {
            openModal(categoria);
        }

        /**
         *
         * @param categoria
         */
        vm.remove = function (categoria) {
            var modalInstance = $uibModal
                .open({
                    animation: true,
                    templateUrl: "app/components/partials/dialog.confirm.html",
                    size: 'sm',
                    backdrop: false,
                    controller: function ($uibModalInstance, $scope) {
                        $scope.sim = function () {
                            $uibModalInstance.close(true);
                        }

                        $scope.cancelar = function () {
                            $uibModalInstance.dismiss('cancel');
                        }
                    }
                });

            modalInstance.result.then(function (result) {
                if (result) {
                    categoriaService.remove(categoria.id, function () {
                        growl.success("<b>Item</b> removido com sucesso");
                        var index = vm.categorias.indexOf(categoria);
                        vm.categorias.splice(index, 1);
                    });
                }
            });
        };
    }
})();
