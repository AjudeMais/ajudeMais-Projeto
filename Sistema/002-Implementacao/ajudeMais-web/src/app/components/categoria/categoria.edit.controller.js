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
        .controller("CategoriaEditController", function (categoriaService, growl, $uibModalInstance, categoria) {

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
                        growl.success("<b>Item</b> criada com sucesso");
                        angular.copy(response, categoria);
                        vm.loading = false;
                        $uibModalInstance.close(categoria);

                    }, function (response) {
                        var msgError = response.data.msg;
                        growl.warning(msgError);
                        vm.loading = false;
                    });

                } else {
                    categoriaService.update(vm.categoria, function (response) {
                        growl.success("<b>Item</b> alterada com sucesso");
                        angular.copy(response, categoria);
                        vm.loading = false;
                        $uibModalInstance.close(categoria);

                    }, function (response) {
                        var msgError = response.data.msg;
                        growl.warning(msgError);
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