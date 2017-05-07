/**
 * @ngdoc Service
 * @name categoriaService
 *
 * @description requisições à endpoints de conta
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("accountService", accountService);

    accountService.$inject = ['$http', 'Api'];

    function accountService($http, Api) {

        var service = {
            changePassword: _changePassword
        };
        return service;

        /**
         *
         * @param categoria
         * @param callback
         * @private
         */
        function _changePassword(password, callback, callbackError) {
            $http.post(Api + "/conta/changePassword", password).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        }

        /**
         *
         * @param categoria
         * @param callback
         * @private
         */
        function _update(categoria, callback, callbackError) {
            $http.put(Api + "/categoria", categoria).then(function (response) {
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
        function _getCategorias(callback) {
            $http.get(Api + "/categoria").then(function (response) {
                callback(response.data);
            });
        };

        /**
         *
         * @param callback
         * @private
         */
        function _getCategoriasByInstituicao(callback) {
            $http.get(Api + "/categoria/instituicao").then(function (response) {
                callback(response.data);
            });
        };

        /**
         *
         * @param id
         * @param callback
         * @private
         */
        function _remove(id, callback) {
            $http.delete(Api + "/categoria/" + id).then(function (response) {
                callback(response.data);
            })
        }
    };
})();
