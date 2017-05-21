/**
 * @ngdoc Config
 *
 * @description Configurações para requisições Http
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').config(httpConfig);

    httpConfig.$inject = ['$httpProvider'];

    function httpConfig($httpProvider) {

        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        $httpProvider.interceptors.push('authDeniedInterceptor');
        $httpProvider.interceptors.push('errorResolverInterceptor');
    }
})();

