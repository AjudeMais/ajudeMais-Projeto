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
        vm.image;
        vm.mockImage = 'content/img/mock-user.png';

        if (donativoDetailparam) {
            vm.donativo = donativoDetailparam;
            _getImage(vm.donativo.doador.foto);
        }

        /**
         *
         * @param imageName
         */
        function _getImage(image) {
            if (image) {
                imageService.getImage(image.nome).then(function (response) {
                    vm.image = _arrayBufferToBase64(response.data);
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