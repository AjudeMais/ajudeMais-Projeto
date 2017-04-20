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
        .controller("InstituicaoEditController", InstituicaoEditController);

    InstituicaoEditController.$inject = ['instituicaoService', 'growl', '$stateParams', '$state'];

    function InstituicaoEditController(instituicaoService, growl, $stateParams, $state) {
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
                _setDefaultAccount(vm.instituicao);
                instituicaoService.save(vm.instituicao, function (response) {
                    growl.success("<b>Instituição</b> criada com sucesso");
                    $state.go('home.instituicao');
                }, function (validationErrors) {
                    var msgErrors = "";
                    console.log(validationErrors);
                    validationErrors.forEach(function (error) {
                        msgErrors += error + "<br>";
                    })
                    growl.warning(msgErrors);
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

        /**
         *
         * @param instituicao
         */
        function _setDefaultAccount(instituicao) {
            vm.instituicao.conta.username = instituicao.documento;
            vm.instituicao.conta.senha = instituicao.documento;
            vm.instituicao.conta.grupos = ['ROLE_INSTITUICAO'];
            vm.instituicao.conta.ativo = true;
        }
    };
})();