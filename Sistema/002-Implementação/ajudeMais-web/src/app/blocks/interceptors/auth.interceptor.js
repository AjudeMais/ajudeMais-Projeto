/**
 * @ngdoc Service
 * @name authInterceptor
 *
 * @description
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function () {
    angular.module('amApp').factory("authInterceptor", function ($q, $location, $rootScope) {

        return {

            'request': function (config) {
                if($rootScope.basicAuth) {
                    config.headers.Authorization = 'Basic ' + $rootScope.basicAuth;
                }

                return config;
            },

            'responseError': function (rejection) {
                if (rejection.status == 401) {
                    $rootScope.sessionUser = {};
                    $rootScope.basicAuth = {};
                    $location.path("/login");
                }
                return rejection;
            }
        };
    });
})();