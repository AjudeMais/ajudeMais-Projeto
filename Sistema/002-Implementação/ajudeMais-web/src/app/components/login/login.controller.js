/**
 * @ngdoc Controller
 * @name Login Controller
 *
 * @description Controller para pagina de login
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {

    angular.module('amApp').controller('LoginController', function (authenticationService, $state) {
        var vm = this;
        vm.conta = {};

        /**
         *
         */
        vm.doLogin = function () {
            authenticationService.doLogin(vm.conta.username, vm.conta.senha,
                function (response) {
                    console.log(response);
                    $state.go("home");
                }, function (response) {
                    console.log(response);
                    vm.error = "Nome de Usuário ou Senha Inválida";
                });
        };
    });

})();
