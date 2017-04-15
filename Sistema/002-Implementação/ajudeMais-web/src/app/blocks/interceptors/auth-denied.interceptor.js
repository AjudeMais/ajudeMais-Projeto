(function() {
    'use strict';

    angular
        .module('amApp')
        .factory('authDeniedInterceptor', authExpiredInterceptor);

    authExpiredInterceptor.$inject = ['$q', '$injector', '$location'];

    function authExpiredInterceptor($q, $injector, $location) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(rejection) {
            if (rejection.status === 403) {
                $location.path('home/403');
            }
            return $q.reject(rejection);
        }
    }
})();