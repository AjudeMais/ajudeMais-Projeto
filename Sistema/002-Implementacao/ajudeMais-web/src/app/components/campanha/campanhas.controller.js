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
        vm.openDetails = function (campanha) {
            $state.go("home.campanha.detail", {campanhaDetail: JSON.stringify(campanha)});
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


        vm.getDifferentDatesInDays = function (date1) {
            console.log(date1);
            var ONE_DAY = 1000 * 60 * 60 * 24
            var date1_ms = date1;
            var date2_ms = new Date().getTime();
            var difference_ms = Math.abs(date1_ms - date2_ms);
            return Math.round(difference_ms / ONE_DAY);

        };

    }
})();
