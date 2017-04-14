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
        vm.conta = {};

        /**
         *
         */
        vm.doLogin = function () {
            authenticationService.doLogin(vm.conta,
                function (response) {
                    authenticationService.storageToken(response.token);
                    $state.go("home");
                }, function (response) {
                    vm.error = "Nome de Usuário ou Senha Inválida";
                });
        };
    });

})();
