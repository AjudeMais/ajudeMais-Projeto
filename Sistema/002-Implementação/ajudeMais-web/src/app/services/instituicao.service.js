/**
 * @ngdoc Service
 * @name instituicaoService
 *
 * @description requisições à endpoints de instituição de caridade
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp").factory("instituicaoService", function ($http, Api) {

        var service = {
            getInstituicoes: _getInstituicoes
        };
        return service;

        /**
         *
         * @param callback
         * @private
         */
        function _getInstituicoes(callback) {
            $http.get(Api + "/instituicao").then(function (response) {
                callback(response.data);
            });
        };
    });
})();
