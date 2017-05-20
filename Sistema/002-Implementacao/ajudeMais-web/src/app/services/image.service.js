/**
 * @ngdoc Service
 * @name imageService
 *
 * @description service referente a endpoints de upload de imagens.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("imageService", imageService);

    imageService.$inject = ['$http', 'Api'];

    function imageService($http, Api) {

        var service = {
            getImage: _getImage
        };
        return service;

        /**
         * Busca imagem pelo nome
         * @param callback
         * @private
         */
        function _getImage(imageName) {
            return $http.get(Api + '/upload/imagem/'+ imageName, {responseType: "arraybuffer"});
        };
    };
})();
