/**
 * @ngdoc Service
 * @name instituicaoService
 *
 * @description requisições à endpoints de instituição de caridade
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("instituicaoService", instituicaoService);

    instituicaoService.$inject = ['$http', 'Api'];

    function instituicaoService($http, Api) {

        var service = {
            save: _save,
            update: _update,
            getInstituicoes: _getInstituicoes,
            getInstituicoesAtivas: _getInstituicoesAtivas
        };
        return service;

        /**
         *
         * @param instituicao
         * @param callback
         * @private
         */
        function _save(instituicao, callback, callbackError) {
            $http.post(Api + "/instituicao", instituicao).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        }

        /**
         *
         * @param instituicao
         * @param callback
         * @private
         */
        function _update(instituicao, callback, callbackError) {
            $http.put(Api + "/instituicao", instituicao).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        }

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

        function _getInstituicoesAtivas() {
            return $http.get(Api + "/instituicao/ativas");
        }
    };
})();
