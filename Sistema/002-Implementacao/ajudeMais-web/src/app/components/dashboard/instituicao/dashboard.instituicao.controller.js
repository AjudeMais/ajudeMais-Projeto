/**
 * @ngdoc controller
 * @name DashboardInstituicaoController
 *
 * @description Controller dashboard da instituição.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>

 */
(function () {
    angular.module('amApp')
        .controller('DashboardInstController', DashboardInstController);

    DashboardInstController.$inject = ['dashboardInstituicaoService', 'imageService'];

    function DashboardInstController(dashboardInstituicaoService, imageService) {

        var vm = this;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;

        vm.rankingMensageiros = [];
        vm.rankingImagesMensageiros = [];
        vm.statusPanelRanking = true;
        vm.rankingDto = {};

        vm.statusPanelLineChart = true;
        vm.seriesDoacoesPeriod = ['Doações em Trâmite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];
        vm.labelsDoacoesInstPeriod = [];
        vm.dataDoacoesInstPeriod = [];

        vm.mockImage = 'content/img/mock-user.png';

        var getMensageirosRanking = function () {
            dashboardInstituicaoService.getRankingMensageiro().then(function (response) {
                response.data.forEach(function (ranking) {
                    getImages(ranking);
                    vm.rankingDto = {};
                    vm.rankingDto.ranking = ranking;
                    vm.rankingMensageiros.push(vm.rankingDto);
                })
            })
        }

        var setImageInRanking = function () {
            vm.rankingImagesMensageiros.forEach(function (ranking) {
                console.log();
                vm.rankingMensageiros.forEach(function (rankingM) {
                    if (ranking.ranking.mensageiro.id === rankingM.ranking.mensageiro.id) {
                        rankingM.image = ranking.image;
                    }
                })
            })
        }

            /**
             *
             */
            var getCountDonativos = function () {
                dashboardInstituicaoService.getCountDonativos().then(function (response) {
                    vm.countDonativos = response.data;
                });
            }

            /**
             *
             */
            var getCountCampanhas = function () {
                dashboardInstituicaoService.getCountCampanhas().then(function (response) {
                    vm.countCampanhas = response.data;
                });
            }

            /**
             *
             */
            var getCountMensageiros = function () {
                dashboardInstituicaoService.getCountMensageiros().then(function (response) {
                    vm.countMensageiros = response.data;
                });
            };

            /**
             *
             * @param imageName
             */
            var getImages = function (ranking) {
                if (ranking.mensageiro.mensageiro.foto) {
                    imageService.getImage(ranking.mensageiro.mensageiro.foto.nome).then(function (response) {
                        vm.rankingDto = {};
                        vm.rankingDto.ranking = ranking;
                        vm.rankingDto.image = _arrayBufferToBase64(response.data)
                        vm.rankingImagesMensageiros.push(vm.rankingDto);
                        setImageInRanking();
                    })
                }
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
            };

            getMensageirosRanking();
            getCountCampanhas();
            getCountDonativos();
            getCountMensageiros();


        }
    })
();
