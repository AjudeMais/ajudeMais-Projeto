/**
 * @ngdoc Controller
 *
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').controller('PasswordController', PasswordController);

    PasswordController.$inject = ['accountService', '$state'];

    function PasswordController(accountService, $state) {
        var vm = this;
        vm.sSenha;
        vm.rSenha;
        vm.msgError;

        /**
         *
         */
        vm.change = function () {
            if (isEquals(vm.nSenha, vm.rSenha)) {
                accountService.changePassword(vm.nSenha, function (response) {
                    $state.go("home");
                })
            } else {
                vm.error = "Senhas não conferem";
            }
        };

        /**
         *
         */
        function isEquals(nSenha, rSenha) {
            if (angular.equals(nSenha, rSenha)) {
                return true;
            }
            else {
                return false;
            }
        }
    };
})();
