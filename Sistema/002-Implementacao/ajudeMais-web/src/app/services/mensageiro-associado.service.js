/**
 * @ngdoc Service
 * @name mensageiroAssociadoService
 *
 * @description service referente aos serviços de associação de mensageiro com instuição.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("mensageiroAssociadoService", mensageiroAssociadoService);

    mensageiroAssociadoService.$inject = ['$http', 'Api'];

    function mensageiroAssociadoService($http, Api) {

        var service = {
            save: _save,
            getByInstituicao: _getByInsituicao,
            update: _update,
            getCountByInstituicao: _getCountByInstituicao
        };
        return service;

        /**
         *
         * @param associacao
         * @param callback
         * @private
         */
        function _save(associacao, callback, callbackError) {
            $http.post(Api + "/associacao", associacao).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        }

        /**
         *
         * @param associacao
         * @param callback
         * @private
         */
        function _update(associacao, callback, callbackError) {
            $http.put(Api + "/associacao", associacao).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        };

        /**
         * Busca mensageiros associados por instituição.
         * @param callback
         * @private
         */
        function _getByInsituicao(callback) {
            $http.get(Api + '/associacao/filter/instituicao').then(function (response) {
                callback(response.data);
            })
        };

        function _getCountByInstituicao(instituicaoId) {
            return $http.get(Api + "/associacao/count/" + instituicaoId);
        }
    };
})();
