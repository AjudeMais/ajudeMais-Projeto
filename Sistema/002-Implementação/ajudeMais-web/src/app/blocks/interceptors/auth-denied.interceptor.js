(function () {
    angular.module('amApp')
        .factory('authDeniedInterceptor', authExpiredInterceptor);

    authExpiredInterceptor.$inject = ['$q', '$injector', '$location'];

    /**
     *
     * @param $q
     * @param $injector
     * @param $location
     * @returns {{responseError: responseError}}
     */
    function authExpiredInterceptor($q, $injector, $location) {
        var service = {
            responseError: responseError
        };
        return service;

        /**
         *
         * @param rejection
         */
        function responseError(rejection) {
            if (rejection.status === 403) {
                $location.path('home/403');
            }
            return $q.reject(rejection);
        }
    }
})();