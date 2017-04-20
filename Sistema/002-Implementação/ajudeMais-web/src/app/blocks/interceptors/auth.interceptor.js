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

    /**
     *
     * @param $rootScope
     * @param $q
     * @param $sessionStorage
     * @returns {{request: request, response: response}}
     */
    function authInterceptor($rootScope, $q, $sessionStorage) {
        var service = {
            request: request,
            response: response
        };

        return service;

        /**
         *
         * @param config
         * @returns {*}
         */
        function request(config) {
            config.headers = config.headers || {};
            var token = $sessionStorage.authToken;
            if (token) {
                config.headers.Authorization = token;
            }
            return config;
        }

        /**
         *
         * @param config
         * @returns {*}
         */
        function response(config) {
            var token = config.headers.Authorization;
            console.log = token;
            if (token) {
                $sessionStorage.authToken = token;
            }
            return config;
        }
    }
})();







