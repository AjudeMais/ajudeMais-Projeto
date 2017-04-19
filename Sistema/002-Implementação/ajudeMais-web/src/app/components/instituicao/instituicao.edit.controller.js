/**
 * @ngdoc Controller
 * @name InstituicaoEditController
 *
 * @description Controller para página de edição de instituição
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("InstituicaoEditController", function (instituicaoService, growl, $stateParams, $state) {
            var vm = this;

            vm.instituicao = {};

            if ($stateParams.instituicaoEdit) {
                vm.instituicao = $stateParams.instituicaoEdit;
            }

            /**
             * Editar/Salvar um instituição
             */
            vm.save = function () {
                if (!vm.isEdited()) {
                    instituicaoService.save(vm.instituicao, function (response) {
                        growl.success("<b>Instituição</b> criada com sucesso");
                        $state.go('home.instituicao');
                    });

                } else {
                    instituicaoService.update(vm.instituicao, function (response) {
                        growl.success("<b>Instituição</b> alterada com sucesso");
                        $state.go('home.instituicao');
                    });
                }
            };

            /**
             *
             */
            vm.cancelar = function () {
                $state.go('home.instituicao');
            };

            /**
             *
             */
            vm.isEdited = function () {
                return vm.instituicao.id;
            };
        });
})();