(function() {
    'use strict';

    angular
        .module('amApp')
        .factory('authExpiredInterceptor', authExpiredInterceptor);

    authExpiredInterceptor.$inject = ['$rootScope', '$q', '$injector', '$location', '$sessionStorage'];

    function authExpiredInterceptor($rootScope, $q, $injector, $location, $sessionStorage) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(rejection) {
            if (rejection.status === 401 || rejection.status === -1) {
                delete $sessionStorage.authToken;
                $location.path('/login');
            }
            return $q.reject(rejection);
        }
    }
})();