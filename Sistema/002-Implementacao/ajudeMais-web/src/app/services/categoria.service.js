/**
 * @ngdoc Service
 * @name categoriaService
 *
 * @description requisições à endpoints de categoria
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("categoriaService", categoriaService);

    categoriaService.$inject = ['$http', 'Api'];

    function categoriaService($http, Api) {

        var service = {
            save: _save,
            update: _update,
            getCategorias: _getCategorias,
            remove: _remove
        };
        return service;

        /**
         *
         * @param categoria
         * @param callback
         * @private
         */
        function _save(categoria, callback, callbackError) {
            $http.post(Api + "/categoria", categoria).then(function (response) {
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
