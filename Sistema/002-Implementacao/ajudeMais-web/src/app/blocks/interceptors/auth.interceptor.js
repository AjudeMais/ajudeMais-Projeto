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

    authInterceptor.$inject = ['Api', '$sessionStorage'];

    function authInterceptor(Api, $sessionStorage) {
        var service = {
            request: request,
            response: response
        };

        return service;

        function request(config) {
            config.headers = config.headers || {};
            var token = $sessionStorage.at;
            if (token && config.url.startsWith(Api)) {
                config.headers.Authorization = token;
            }
            return config;
        }

        function response(config) {
            var token = config.headers.Authorization;
            if (token) {
                $sessionStorage.at = token;
            }
            return config;
        }
    }
})();







