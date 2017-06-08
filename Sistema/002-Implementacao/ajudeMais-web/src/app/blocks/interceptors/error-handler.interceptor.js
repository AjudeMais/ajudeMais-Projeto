/**
 * @ngdoc Service
 * @name errorResolverInterceptor
 *
 * @description Service para interceptação de erros do servidor
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function () {
    angular.module('amApp').factory("errorResolverInterceptor", errorResolverInterceptor);

    errorResolverInterceptor.$inject = ['$q', '$location'];

    function errorResolverInterceptor($q, $location) {

        var errorResolverInterceptor = {
            responseError: function (response) {
                if (response.status >= 500) {
                    $location.path('/500');
                }
                if (response.status <= 0) {
                    $location.path('/refused');
                }
                return $q.reject(response);
            }
        };

        return errorResolverInterceptor;
    };

})();
