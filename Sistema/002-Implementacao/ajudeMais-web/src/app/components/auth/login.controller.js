/**
 * @ngdoc Controller
 * @name Login Controller
 *
 * @description Controller para pagina de login
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').controller('LoginController', LoginController);

    LoginController.$inject = ['authenticationService', '$state'];

    function LoginController(authenticationService, $state) {
        var vm = this;
        vm.account = {};

        /**
         * Remove informações do usuário salvas no browser.
         */
        authenticationService.doLogout(function (response) {})

        /**
         *
         */
        vm.doLogin = function () {
            authenticationService.doLogin(vm.account, function (response) {
                authenticationService.storageToken(response.token);
                authenticationService.getUserLogged(function (response) {
                    redirect(response);
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

        function redirect(user) {
            if (isFirstAccess(user)) {
                $state.go("passwdChange");

            } else {
                $state.go("home");
            }
        }

        /**
         *
         */
        function isFirstAccess(user) {
            if (user && user.resetSenha) {
                return false;
            }
            else {
                return true;
            }
        }
    };
})();
