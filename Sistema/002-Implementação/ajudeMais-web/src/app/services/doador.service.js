/**
 * @ngdoc Service
 * @name doadorService
 *
 * @description requisições à endpoints de doador
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp").factory("doadorService", function ($http, Api) {

        /**
         *
         * @param callback
         * @private
         */
        var _getDoadores = function (callback) {
            $http.get(Api + "/doador").then(function (response) {
                callback(response.data);
            });
        };

        /**
         *
         */
        return {
            getDoadores: _getDoadores,
        };

    });

})();
