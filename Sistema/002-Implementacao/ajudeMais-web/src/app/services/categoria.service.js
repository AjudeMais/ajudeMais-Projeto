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
            getCategoriasByInstituicao: _getCategoriasByInstituicao,
            remove: _remove,
            findByName: _findByName
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
        function _remove(id, callback, callbackError) {
            $http.delete(Api + "/categoria/" + id).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response.data);
            });
        }

        /**
         *
         * @param nome
         * @private
         */
        function _findByName(name) {
            return $http.get(Api + '/categoria/filter/nome', {params: {"nome": name}});
        };
    };
})();
