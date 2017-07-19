/**
 * @ngdoc Module
 * @name amApp
 *
 * @description módulo principal da aplicação
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp', ['amRoute', 'layout', 'ui.bootstrap', /*'templates',*/
        'ngAnimate', 'datatables', 'datatables.bootstrap', 'ajudeMais.utils', 'ngCookies', 'ngCapsLock',
        'ngStorage', 'idf.br-filters', 'ui.utils.masks', 'angular.viacep', 'frapontillo.bootstrap-switch',
        'chart.js'
    ]);

    angular.module('amApp').run([
        '$rootScope',
        '$location',
        '$http',
        'authenticationService',
        function ($rootScope, $location, $http, authenticationService) {

            $rootScope.$on('$locationChangeStart', function (event, next, current) {

                authenticationService.logged(function (logged) {
                    if (logged) {
                        if ($location.path() === '/login') {
                            $location.path('/home');
                        }

                    } else {
                        if ($location.path() !== '/login') {
                            $location.path('/login');
                        }
                    }
                });




            });
        }
    ]);

})();
