/**
 * @ngdoc Controller
 *
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').controller('PasswordController', PasswordController);

    PasswordController.$inject = ['authenticationService', '$state'];

    function PasswordController(authenticationService, $state) {
        var vm = this;
        vm.account = {};

        /**
         *
         */
        vm.doLogin = function () {
            authenticationService.doLogin(vm.account, function (response) {
                authenticationService.storageToken(response.token);
                authenticationService.getUserLogged(function () {
                    redirect();
                });
            }, function (response) {
                if (response.status == 401) {
                    vm.error = "Nome de usuário ou senha inválido";
                }
                else {
                    vm.error = response.msg;
                }
            });
        };

        vm.toHome = function () {
            $state.go("home");
        }

        function redirect() {
            if(isFirstAccess()) {
                $state.go("home");
            }else {
                $state.go("firstAccess");
            }
        }

        /**
         *
         */
        function isFirstAccess() {
            var user = sessionStorage.sessionUser;
            if(user && user.ultimoAcesso) {
                return false;
            }
            else {
                return true;
            }
        }
    };
})();
