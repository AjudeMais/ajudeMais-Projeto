/**
 * @ngdoc Controller
 * @name InstituicaoEditController
 *
 * @description Controller para página de Edição de Instituição
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("CategoriaEditController", function (categoriaService, toastr, $uibModalInstance, categoria) {

            var vm = this;
            vm.categoria = {};
            vm.loading = false;

            angular.copy(categoria, vm.categoria);

            if (!edited()) {
                vm.categoria.ativo = true;
            }

            /**
             * Editar/Salvar uma categoria
             */
            vm.save = function () {
                vm.loading = true;
                if (!edited()) {
                    categoriaService.save(vm.categoria, function (response) {
                        toastr.success('salvo com sucesso', 'Item');
                        angular.copy(response, categoria);
                        vm.loading = false;
                        $uibModalInstance.close(categoria);

                    }, function (response) {
                        var msgError = response.data.msg;
                        toastr.warning(msgError);
                        vm.loading = false;
                    });

                } else {
                    categoriaService.update(vm.categoria, function (response) {
                        toastr.success('alterado com sucesso', 'Item');
                        angular.copy(response, categoria);
                        vm.loading = false;
                        $uibModalInstance.close(categoria);

                    }, function (response) {
                        var msgError = response.data.msg;
                        toastr.warning(msgError);
                        angular.copy(response, categoria);
                        vm.loading = false;
                        $uibModalInstance.close(categoria);
                    });
                }
            };

            /**
             *
             */
            vm.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

            /**
             *
             */
            function edited() {
                return vm.categoria.id ? true : false;
            };

            /**
             *
             * @returns {*}
             */
            vm.isEdited = function () {
                return edited();
            }
        });
})();