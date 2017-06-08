/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para auth
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'app/components/auth/login.html',
            controller: 'LoginController',
            controllerAs: 'vm',
            data: {
                pageTitle: 'Login'
            }
        })

            .state('passwdChange', {
                url: '/passwdChange',
                templateUrl: 'app/components/auth/password/password.html',
                controller: "PasswordController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Primeiro Acesso'
                }
            })
    }
})();