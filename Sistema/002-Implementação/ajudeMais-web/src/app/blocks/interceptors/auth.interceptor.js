/**
 * @ngdoc Service
 * @name authInterceptor
 *
 * @description
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function () {
    angular.module('amApp').factory("authInterceptor", authInterceptor);

    authInterceptor.$inject = ['$rootScope', '$q', '$sessionStorage'];

    function authInterceptor($rootScope, $q, $sessionStorage) {
        var service = {
            request: request
        };

        return service;

        function request(config) {
            config.headers = config.headers || {};
            var token = $sessionStorage.authToken;
            if (token) {
                config.headers.Authorization = token;
            }
            return config;
        }
    }
})();







