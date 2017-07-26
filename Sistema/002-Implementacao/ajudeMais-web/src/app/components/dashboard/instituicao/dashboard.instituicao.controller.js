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

    DashboardInstController.$inject = ['dashboardInstituicaoService', 'imageService', '$state'];

    function DashboardInstController(dashboardInstituicaoService, imageService, $state) {

        var vm = this;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;
        vm.countItens = 0;

        vm.rankingMensageiros = [];
        vm.rankingImagesMensageiros = [];
        vm.statusPanelRanking = true;
        vm.rankingDto = {};

        vm.statusPanelLineChart = true;
        vm.seriesDoacoesPeriod = ['Doações em Trâmite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];
        vm.labelsDoacoesInstPeriod = [];
        vm.dataDoacoesInstPeriod = [];

        vm.statusPanelCampanhas = true;
        vm.campanhasMetasProgres = [];

        vm.donativosTimeline = [];
        vm.statusPanelTimeline = true;

        vm.mockImage = 'content/img/mock-user.png';

        var getDonativosTimeline = function () {
            dashboardInstituicaoService.getDonativosTimeline().then(function (response) {
                vm.donativosTimeline = response.data;
                console.log(vm.donativosTimeline)
            })
        }

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

        var getCampanhasMetas = function () {
            dashboardInstituicaoService.getCampanhasMetasProgres().then(function (response) {
                vm.campanhasMetasProgres = response.data;
            })
        }

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
        var getCountItens = function () {
            dashboardInstituicaoService.getCountItens().then(function (response) {
                vm.countItens = response.data;
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

        var getDoacoesByPeriodoInstituicao = function () {
            dashboardInstituicaoService.getDoacoesByPeriodoInstituicao(30, "RECOLHIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    vm.labelsDoacoesInstPeriod.push(dto.date);
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoCanceladasInstituicao();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoCanceladasInstituicao = function () {
            dashboardInstituicaoService.getDoacoesByPeriodoInstituicao(30, "CANCELADO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoEntreguesInstituicao();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoEntreguesInstituicao = function () {
            dashboardInstituicaoService.getDoacoesByPeriodoInstituicao(30, "RECEBIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoNaoAceitos();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoNaoAceitos = function () {
            dashboardInstituicaoService.getDoacoesByPeriodoInstituicao(30, "NAO_ACEITO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
            });
        }

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
        getCountItens();
        getCountMensageiros();
        getDoacoesByPeriodoInstituicao();
        getCampanhasMetas();
        getDonativosTimeline();


    }
})
();
