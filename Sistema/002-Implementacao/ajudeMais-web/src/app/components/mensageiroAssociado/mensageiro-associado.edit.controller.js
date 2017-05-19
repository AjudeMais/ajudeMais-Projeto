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

    function MensageiroAssociadoEditController(mensageiroAssociadoService, mensageiroService, growl, $uibModalInstance, mensageiroAss) {

        var vm = this;
        vm.mensageiroAss = {};

        angular.copy(mensageiroAss, vm.mensageiroAss);

        /**
         *
         * @param email
         */
        vm.findMensageiro = function (email) {
            return mensageiroService.findByContaEmail(email).then(function (response) {
                return response.data;
            });
        };

        /**
         *
         * @param imageName
         */
        vm.getImage = function (imageName) {

        };

        vm.removeCurrentMensageiro = function () {
            vm.mensageiroAss.mensageiro = null;
        }

        /**
         * Editar/Salvar uma associação entre mensageiro e instituição
         */
        vm.save = function () {
            vm.loading = true;
            vm.mensageiroAss.status = true;
            vm.mensageiroAss.data = new Date();
            mensageiroAssociadoService.save(vm.mensageiroAss, function (response) {
                growl.success("<b>Mensageiro</b> associado com sucesso");
                angular.copy(response, mensageiroAss);
                vm.loading = false;
                $uibModalInstance.close(mensageiroAss);
            }, function (response) {
                var msgError = response.data.msg;
                growl.warning(msgError);
                vm.loading = false;
            });
        };

        /**
         *
         */
        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    };
})();