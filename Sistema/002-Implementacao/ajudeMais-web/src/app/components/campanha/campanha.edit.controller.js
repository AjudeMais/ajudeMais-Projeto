/**
 * @ngdoc Controller
 * @name CampanhaEditController
 *
 * @description Controller para página de Edição de Campanha
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("CampanhaEditController", CampanhaEditController);

    CampanhaEditController.$inject = ['campanhaService', 'categoriaService', 'toastr', '$stateParams', '$state', '$uibModal'];

    function CampanhaEditController(campanhaService, categoriaService, toastr, $stateParams, $state, $uibModal) {

        var vm = this;
        vm.campanha = {};
        vm.dtpk = false;
        vm.item;
        vm.currentDate = new Date();

        if ($stateParams.campanhaEdit) {
            vm.campanha = $stateParams.campanhaEdit;
        } else {
            vm.campanha.itensDoaveis = [];
            vm.campanha.status = true;
        }

        /**
         * Editar/Salvar uma campanha
         */
        vm.save = function () {
            if(vm.campanha.status) {
                vm.campanha.dataInicio = new Date();
            }

            if (!vm.isEdited()) {
                campanhaService.save(vm.campanha).then(function (response) {
                    toastr.success('criada com sucesso', 'Campanha');
                    $state.go('home.campanha.list');
                }, function (response) {
                    response.data.forEach(function (data) {
                        toastr.warning(data);
                    });
                });

            } else {
                campanhaService.update(vm.campanha).then(function (response) {
                    toastr.success('atualizada com sucesso', 'Campanha');
                    $state.go('home.campanha.list');
                }, function (response) {
                    response.data.forEach(function (data) {
                        toastr.warning(data);
                    });
                });
            }
        };

        /**
         *
         */
        vm.cancelar = function () {
            $state.go('home.campanha.list');
        };

        /**
         *
         */
        vm.isEdited = function () {
            return vm.campanha.id;
        };

        /**
         *
         * @param datepicker
         */
        vm.openDtpk = function () {
            vm.dtpk = true;
        }

        /**
         *
         * @param name
         */
        vm.findByName = function (name) {
            return categoriaService.findByName(name).then(function (response) {
                return response.data;
            });
        };

        /**
         *
         * @param mensageiro
         */
        vm.setItem = function (item) {
            var flag = false;
            if (item == null) {
                toastr.warning('Selecione um item');
            } else {
                vm.campanha.itensDoaveis.forEach(function (i) {
                    if (item.id == i.id) {
                        flag = true;
                    }
                })
                if (flag) {
                    toastr.warning('Item Já adicionado');
                } else {
                    vm.campanha.itensDoaveis.push(item);
                    vm.item = null;
                }
            }
        };

        /**
         *
         * @param item
         */
        vm.removeItem = function (item) {
            var index = vm.campanha.itensDoaveis.indexOf(item);
            vm.campanha.itensDoaveis.splice(index, 1);
        };

        /**
         *
         * @param categoria
         */
        function openModal(categoria) {
            return $uibModal.open({
                templateUrl: 'app/components/categoria/categoria.edit.html',
                controller: 'CategoriaEditController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    categoria: function () {
                        return categoria;
                    }
                }
            });
        }

        /**
         *
         */
        vm.addItem = function () {
            var modal = openModal({});

            modal.result.then(function (categoria) {
                vm.campanha.itensDoaveis.push(categoria);
            });
        }
    }
})
();