(function() {
    angular.module('amApp')
        .factory('authExpiredInterceptor', authExpiredInterceptor);

    authExpiredInterceptor.$inject = ['$q', '$injector', '$location', '$sessionStorage'];

    function authExpiredInterceptor($q, $injector, $location, $sessionStorage) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(rejection) {
            if (rejection.status === 401 || rejection.status === -1) {
                delete $sessionStorage.at;
                $location.path('/login');
            }
            return $q.reject(rejection);
        }
    }
})();