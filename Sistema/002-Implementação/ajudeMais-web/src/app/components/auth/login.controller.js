/**
 * @ngdoc Controller
 * @name Login Controller
 *
 * @description Controller para pagina de login
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {

    angular.module('amApp').controller('LoginController', function (authenticationService, $state, $rootScope) {
        var vm = this;
        vm.account = {};

        /**
         *
         */
        vm.doLogin = function () {
            authenticationService.doLogin(vm.account, function (response) {
                authenticationService.storageToken(response.token);
                authenticationService.getUserLogged(function () {
                    $state.go("home");
                });
            }, function (response) {
                vm.error = "Nome de Usuário ou Senha Inválido";
            });
        };
    });

})();
