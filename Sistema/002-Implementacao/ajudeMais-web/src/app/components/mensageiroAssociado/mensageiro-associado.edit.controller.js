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
        .controller("MensageiroAssociadoEditController", MensageiroAssociadoEditController);

    MensageiroAssociadoEditController.$inject = ['mensageiroAssociadoService', 'growl', '$stateParams', '$state'];

    function MensageiroAssociadoEditController(mensageiroAssociadoService, growl, $stateParams, $state) {

        var vm = this;
        vm.mensageiroAss = {};
        vm.email = "";
        vm.mensageiro  = {};
        if ($stateParams.mensageiroAssEdit) {
            vm.mensageiroAss = $stateParams.mensageiroAssEdit;
        }

        vm.findMensageiro = function () {
            mensageiroAssociadoService.findByContaEmail(function (response) {
                vm.mensageiro = response.data;
            })
        };

        /**
         * Editar/Salvar uma associação entre mensageiro e instituição
         */
        vm.save = function () {
            if (!vm.isEdited()) {
                mensageiroAssociadoService.save(vm.mensageiroAss, function (response) {
                    growl.success("<b>Mensageiro</b> associado com sucesso");
                    $state.go('home.mensageirosAss');
                }, function (response) {
                    var msgError = response.data.msg;
                    growl.warning(msgError);
                });

            } else {
                mensageiroAssociadoService.update(vm.mensageiroAss, function (response) {
                    growl.success("<b>Associação</b> alterada com sucesso");
                    $state.go('home.mensageirosAss');
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
            $state.go('home.mensageirosAss');
        };

        /**
         *
         */
        vm.isEdited = function () {
            return vm.mensageiroAss.id;
        };

    };
})();