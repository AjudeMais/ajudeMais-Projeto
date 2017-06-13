/**
 * @ngdoc Controller
 * @name CampanhaEditController
 *
 * @description Controller para página de Edição de Campanha
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>

 */
(function () {
    angular.module("amApp")
        .controller("CampanhaEditController", CampanhaEditController);

    CampanhaEditController.$inject = ['campanhaService', 'categoriaService', 'toastr', '$stateParams', '$state', '$uibModal'];

    function CampanhaEditController(campanhaService, categoriaService, toastr, $stateParams, $state, $uibModal) {

        var vm = this;
        vm.campanha = {};
        vm.dtpk = false;
        vm.meta = {};
        vm.currentDate = new Date();

        vm.isCtgNotEmpty = true;
        vm.isQtdNotEmpty = true;
        vm.isUnMediadeNotEmpty = true;
        vm.isEditMeta = false;

        if ($stateParams.campanhaEdit) {
            vm.campanha = $stateParams.campanhaEdit;
            if (!vm.campanha.status) {
                vm.campanha.dataInicio = undefined;
                vm.campanha.dataFim = undefined;
            }
        } else {
            vm.campanha.metas = [];
            vm.campanha.status = true;
        }

        /**
         * Editar/Salvar uma campanha
         */
        vm.save = function () {

            if (vm.campanha.status) {
                vm.campanha.dataInicio = new Date();
            }

            if (vm.campanha.metas.length > 0) {
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

            } else {
                toastr.warning('A campanha deve possui ao menos uma meta');
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
        vm.setMeta = function (meta) {
            var flag = false;

            validateFieldsMeta(meta);

            if (vm.isCtgNotEmpty && (vm.isUnMediadeNotEmpty && vm.isQtdNotEmpty)) {
                if (!vm.isEditMeta) {
                    vm.campanha.metas.forEach(function (i) {
                        if (meta.categoria.id === i.categoria.id) {
                            flag = true;
                        }
                    })
                    if (flag) {
                        toastr.warning('Item já adicionado a uma meta');
                    } else {
                        vm.campanha.metas.push(meta);
                        vm.meta = {};
                    }

                } else {
                    vm.campanha.metas.forEach(function (i) {
                        if (meta.id === i.id) {
                            i.quantidade = meta.quantidade;
                            vm.isEditMeta = false;
                            vm.meta = {};
                        }
                    })
                }


            }
        };

        /**
         * valida os campos de uma meta;
         * @param meta
         */
        validateFieldsMeta = function (meta) {
            if (meta.categoria == undefined) {
                vm.isCtgNotEmpty = false;
            } else {
                vm.isCtgNotEmpty = true;
            }

            if (meta.unidadeMedida == null) {
                vm.isUnMediadeNotEmpty = false;
            } else {
                vm.isUnMediadeNotEmpty = true;
            }

            if (meta.quantidade == null) {
                vm.isQtdNotEmpty = false;
            } else {
                vm.isQtdNotEmpty = true;
            }
        };

        /**
         *
         * @param meta
         */
        vm.removeMeta = function (meta) {
            var index = vm.campanha.metas.indexOf(meta);
            vm.campanha.metas.splice(index, 1);
        };

        /**
         *
         * @param meta
         */
        vm.editMeta = function (meta) {
            var index = vm.campanha.metas.indexOf(meta);
            vm.meta = vm.campanha.metas[index];
            vm.isEditMeta = true;
        };

        vm.onSelect = function (mensageiro) {
            vm.isCtgNotEmpty = true;
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
                vm.meta.categoria = categoria;
                vm.isCtgNotEmpty = true;
            });
        }
    }
})
();