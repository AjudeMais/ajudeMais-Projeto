(function() {
    angular.module('amApp')
        .factory('authExpiredInterceptor', authExpiredInterceptor);

    authExpiredInterceptor.$inject = ['$q', '$location', '$sessionStorage'];

    function authExpiredInterceptor($q, $location, $sessionStorage) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(rejection) {
            if (rejection.status === 401) {
                delete $sessionStorage.at;
                $sessionStorage.sessionUser
                $location.path('/login');
            }
            return $q.reject(rejection);
        }
    }
})();