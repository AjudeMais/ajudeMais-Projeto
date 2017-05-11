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
        .controller("InstituicaoEditController", InstituicaoEditController);

    InstituicaoEditController.$inject = ['instituicaoService', 'growl', '$stateParams', '$state', 'viaCEP'];

    function InstituicaoEditController(instituicaoService, growl, $stateParams, $state, viaCEP) {

        var vm = this;
        vm.instituicao = {};

        if ($stateParams.instituicaoEdit) {
            vm.instituicao = $stateParams.instituicaoEdit;
        } else {
            vm.instituicao.conta = {};
            vm.instituicao.conta.ativo = true;
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
                }, function (response) {
                    var msgError = response.data.msg;
                    growl.warning(msgError);
                });

            } else {
                instituicaoService.update(vm.instituicao, function (response) {
                    growl.success("<b>Instituição</b> alterada com sucesso");
                    $state.go('home.instituicao');
                }, function (response) {
                    var msgError = response.data.msg;
                    growl.warning(msgError);
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
         * obtém localidade pelo CEP
         *
         * @param cep
         */
        vm.getLocation = function (cep) {
            viaCEP.get(cep).then(function (response) {
                vm.instituicao.endereco = response;
                if (response) {
                    vm.instituicao.endereco.cep = formatCep(response.cep);
                }
            });
        }

        /**
         * Cria um conta default para uma instituição.
         * @param instituicao
         */
        function _setDefaultAccount(instituicao) {
            vm.instituicao.conta.username = instituicao.documento;
            vm.instituicao.conta.senha = instituicao.documento;
            vm.instituicao.conta.grupos = ['ROLE_INSTITUICAO'];
        }

        /**
         *
         * @param cep
         * @returns {string}
         */
        function formatCep(cep) {
            if (cep) {
                return cep.replace("-", "");
            } else {
                return "";
            }
        }
    };
})();