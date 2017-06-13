/**
 * @ngdoc Controller
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("CampanhaDetailController", CampanhaDetailController);

    CampanhaDetailController.$inject = ['$stateParams', '$state', 'donativoService'];

    function CampanhaDetailController($stateParams, $state, donativoService) {
        var vm = this;
        vm.campanha = {};
        vm.donativos = [];

        var campanhaDetailparam = JSON.parse($stateParams.campanhaDetail);

        if (campanhaDetailparam) {
            vm.campanha = campanhaDetailparam;
        }

        vm.setPercentage = function () {

            vm.campanha.metas.forEach(function (i) {
                var count = 0;
                for (j=0 ; j<vm.donativos.length; j++){
                    if (i.categoria.id === j.categoria.id) {
                        count++;
                    }
                }
                i.percentage =  parseFloat((count*100)/i.quantidade).toFixed(2)+"%";
            })
        };


        /**
         *
         */
        vm.previus = function () {
            $state.go('home.campanha.list');
        };

        vm.filterDonativoByEstadoAfterAceito = function () {
            donativoService.filterDonativoByEstadoAfterAceito(vm.campanha.id).then(function (response) {
                vm.donativos = response.data;
                vm.setPercentage();

            });
        };

        vm.filterDonativoByEstadoAfterAceito();
    }


})();