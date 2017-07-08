/**
 * @ngdoc controller
 * @name DonativoController
 *
 * @description Controller para consulta de donativos da instituição..
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
        angular.module('amApp')
            .controller('DonativoController', DonativoController);

        DonativoController.$inject = ['donativoService', 'DTOptionsBuilder', '$state', 'toastr'];

        function DonativoController(donativoService, DTOptionsBuilder, $state, toastr) {

            var vm = this;
            vm.donativos = [];

            vm.dtOptions = DTOptionsBuilder.newOptions()
                .withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();

            /**
             *
             */
            vm.getDonativos = function () {
                donativoService.getByInstituicao().then(function (response) {
                    vm.donativos = response.data;
                });
            }

            vm.getDonativos();

            vm.getEtadosDonativo = function (estados) {
                var estadoAtivo;
                estados.forEach(function (estado) {
                    if (estado.ativo) {
                        estadoAtivo = estado;
                    }
                });
                return estadoAtivo.estadoDoacao;
            }

            /**
             *
             * @param campanha
             */
            vm.openDetails = function (donativo) {
                $state.go("home.donativo.detail", {donativoDetail: JSON.stringify(donativo)});
            }
        }
    })
();
