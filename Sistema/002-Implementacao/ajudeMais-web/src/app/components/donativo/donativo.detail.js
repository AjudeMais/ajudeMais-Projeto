/**
 * @ngdoc Controller
 * @name DonativoDetailController
 *
 * @description Controller para página de visualização de detalhes de um danativo.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("DonativoDetailController", DonativoDetailController);

    DonativoDetailController.$inject = ['$stateParams', '$state', 'imageService'];

    function DonativoDetailController($stateParams, $state, imageService) {
        var vm = this;
        vm.donativo = {};
        var donativoDetailparam = JSON.parse($stateParams.donativoDetail);
        vm.images = [];

        if (donativoDetailparam) {
            vm.donativo = donativoDetailparam;
            console.log(vm.donativo);
            getImages();
        }

        function getImages() {
            vm.donativo.fotosDonativo.forEach(function (image) {
                _getImage(image);
            })

        }

        /**
         *
         * @param imageName
         */
        function _getImage(image) {
            if (image) {
                imageService.getImage(image.nome).then(function (response) {
                    var imageContent = {};
                    imageContent.contentType = image.contentType;
                    imageContent.content = _arrayBufferToBase64(response.data);
                    vm.images.push(imageContent);
                })
            }
        };

        /**
         *
         */
        vm.previus = function () {
            $state.go('home.donativo.list');
        };

        /**
         * Necessário para converter array de bytes para base64.
         * utilizado para recuperar foto da API.
         * @param buffer
         * @returns {string}
         * @private
         */
        function _arrayBufferToBase64(buffer) {
            var binary = '';
            var bytes = new Uint8Array(buffer);
            var len = bytes.byteLength;
            for (var i = 0; i < len; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            return window.btoa(binary);
        }
    }
})();