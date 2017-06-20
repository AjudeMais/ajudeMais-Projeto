/**
 * @ngdoc controller
 * @name CategoriaController
 *
 * @description Controller para consulta de categorias de itens doaveis.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('CategoriaController', CategoriaController);

    CategoriaController.$inject = ['categoriaService', 'DTOptionsBuilder', '$uibModal', 'toastr'];

    function CategoriaController(categoriaService, DTOptionsBuilder, $uibModal, toastr) {

        var vm = this;
        vm.categorias = [];

        vm.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

        /**
         *
         */
        vm.getCategorias = function () {
            categoriaService.getCategoriasByInstituicao(function (response) {
                vm.categorias = response;
            });
        }
        vm.getCategorias();

        /**
         *
         * @param categoria
         */
        function openModal(categoria) {
            return $uibModal.open({
                templateUrl: 'app/components/categoria/categoria.edit.html',
                controller: 'CategoriaEditController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    categoria: function () {
                        return categoria;
                    }
                }
            });
        }

        /**
         *
         */
        vm.addCategoria = function () {
            var modal = openModal({});

            modal.result.then(function (categoria) {
                vm.categorias.push(categoria);
            });
        }

        /**
         *
         * @param categoria
         */
        vm.editCategoria = function (categoria) {
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
                    categoriaService.remove(categoria.id, function (response) {

                        toastr.success("removido com sucesso", "Item");
                        var index = vm.categorias.indexOf(categoria);
                        vm.categorias.splice(index, 1);

                    }, function (response) {
                        var msgError = response.msg;
                        toastr.warning(msgError);

                    });
                }
            });
        };
    }
})();
