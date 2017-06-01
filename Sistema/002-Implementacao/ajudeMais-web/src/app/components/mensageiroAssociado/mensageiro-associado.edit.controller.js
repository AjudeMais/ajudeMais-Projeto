/**
 * @ngdoc Controller
 * @name InstituicaoEditController
 *
 * @description Controller para página de Edição de Instituição
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("MensageiroAssociadoEditController", MensageiroAssociadoEditController);

    function MensageiroAssociadoEditController(mensageiroAssociadoService, mensageiroService, imageService, toastr, $uibModalInstance, mensageiroAss) {

        var vm = this;
        vm.mensageiroAss = {};
        vm.image;
        vm.mockImage = 'content/img/mock-user.png';

        angular.copy(mensageiroAss, vm.mensageiroAss);

        if (edited()) {
            _getImage(vm.mensageiroAss.mensageiro.foto);
        } else {
            vm.mensageiroAss.status = true;
            vm.mensageiroAss.data = new Date();
        }

        /**
         *
         * @param email
         */
        vm.findMensageiro = function (email) {
            return mensageiroService.findByContaEmail(email).then(function (response) {
                return response.data;
            });
        };

        vm.onSelect = function (mensageiro) {
            _getImage(mensageiro.foto);
        };

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
         * remove mensageiro buscado.
         */
        vm.removeCurrentMensageiro = function () {
            if (!edited()) {
                vm.mensageiroAss.mensageiro = null;
            }
        }

        /**
         * Editar/Salvar uma associação entre mensageiro e instituição
         */
        vm.save = function () {
            if (vm.mensageiroAss.mensageiro != null) {
                vm.loading = true;
                if (edited()) {
                    mensageiroAssociadoService.update(vm.mensageiroAss, function (response) {
                        toastr.success('atualizado com sucesso', 'Mensageiro');
                        angular.copy(response, mensageiroAss);
                        vm.loading = false;
                        $uibModalInstance.close(mensageiroAss);
                    }, function (response) {
                        var msgError = response.data.msg;
                        toastr.warning(msgError);
                        vm.loading = false;
                    });
                } else {
                    mensageiroAssociadoService.save(vm.mensageiroAss, function (response) {
                        toastr.success('associado com sucesso', 'Mensageiro');
                        angular.copy(response, mensageiroAss);
                        vm.loading = false;
                        $uibModalInstance.close(mensageiroAss);
                    }, function (response) {
                        var msgError = response.data.msg;
                        toastr.warning(msgError);
                        vm.loading = false;
                    });
                }
            }
        };

        /**
         *
         */
        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
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

        /**
         *
         */
        function edited() {
            return vm.mensageiroAss.id ? true : false;
        };

        /**
         *
         * @returns {*}
         */
        vm.isEdited = function () {
            return edited();
        }
    };
})();