/**
 * @ngdoc controller
 * @name CampanhaController
 *
 * @description Controller para consulta de campanhas de doação.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('CampanhaController', CampanhaController);

    CampanhaController.$inject = ['campanhaService', 'DTOptionsBuilder', '$state', 'toastr'];

    function CampanhaController(campanhaService, DTOptionsBuilder, $state, toastr) {

        var vm = this;
        vm.campanhas = [];

        vm.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

        /**
         *
         */
        vm.getCampanhas = function () {
            campanhaService.getCampanhasByInstituicao().then(function (response) {
                vm.campanhas = response.data;
            });
        }
        vm.getCampanhas();

        /**
         *
         */
        vm.addCampanha = function () {
            $state.go("home.campanha.edit", {campanhaEdit: null});
        }

        /**
         *
         * @param campanha
         */
        vm.editCampanha = function (campanha) {
            $state.go("home.campanha.edit", {campanhaEdit: campanha});
        }

        /**
         *
         * @param campanha
         */
        vm.remove = function (campanha) {
            var modalInstance = $uibModal
                .open({
                    animation: true,
                    templateUrl: "app/components/partials/dialog.confirm.html",
                    size: 'sm',
                    backdrop: false,
                    controller: function ($uibModalInstance, $scope) {
                        $scope.sim = function () {
                            $uibModalInstance.close(true);
                        }

                        $scope.cancelar = function () {
                            $uibModalInstance.dismiss('cancel');
                        }
                    }
                });

            modalInstance.result.then(function (result) {
                if (result) {
                    categoriaService.remove(campanha.id, function () {
                        toastr.success("removida com sucesso", "Campanha");
                        var index = vm.campanhas.indexOf(campanha);
                        vm.campanhas.splice(index, 1);
                    });
                }
            });
        };
    }
})();
