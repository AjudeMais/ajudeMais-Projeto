/**
 * @ngdoc controller
 * @name DashboardAdminController
 *
 * @description Controller dashboard do administrador.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .controller('DashboardAdminController', DashboardAdminController);

    DashboardAdminController.$inject = ['dashboardAdminService', 'instituicaoService',
        'donativoService', 'campanhaService', 'mensageiroAssociadoService', 'imageService'];

    function DashboardAdminController(dashboardAdminService, instituicaoService,
                                      donativoService, campanhaService, mensageiroAssociadoService, imageService) {

        var vm = this;
        vm.countInstituicoes = 0;
        vm.countDonativos = 0;
        vm.countCampanhas = 0;
        vm.countMensageiros = 0;
        vm.statusPanelLineChart = true;
        vm.instituicaoSelected = {};
        vm.instituicoesAtivas = [];
        vm.rankingMensageiros = [];
        vm.statusPanelRanking = true;
        vm.rankingDto = {};

        vm.countDonativosCampanhaInst = 0;
        vm.countDonativosInst = 0;
        vm.countCampanhasInst = 0;
        vm.campanhasAtivasInt = [];
        vm.countMensageirosInst = 0;
        vm.statusPanelInstDetail = true;

        vm.labelsDoacoesPeriod = [];
        vm.dataDoacoesPeriod = [];
        vm.seriesDoacoesPeriod = ['Doações em Trâmite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];

        vm.labelsDoacoesInstPeriod = [];
        vm.dataDoacoesInstPeriod = [];

        vm.options = {
            legend: {
                display: true
            }
        };

        /**
         *
         */
        var getInstituicoesAtivas = function () {
            instituicaoService.getInstituicoesAtivas().then(function (response) {
                vm.instituicoesAtivas = response.data;
                vm.instituicaoSelected = vm.instituicoesAtivas[0];
                vm.onSelectInstituicao();
            })
        };

        var getMensageirosRanking = function() {
            dashboardAdminService.getRankingMensageiro().then(function (response) {
                response.data.forEach(function (ranking) {
                    vm.rankingDto = {};
                    vm.rankingDto.ranking = ranking;
                    vm.rankingDto.image  = getImage(ranking.mensageiro.mensageiro.foto);
                    vm.rankingMensageiros.push(vm.rankingDto);
                })
            })
        }

        /**
         *
         */
        var getCountInstituicaoCaridade = function () {
            dashboardAdminService.getCountInstituicaoCaridade().then(function (response) {
                vm.countInstituicoes = response.data;
            });
        }

        /**
         *
         */
        var getCountDonativos = function () {
            dashboardAdminService.getCountDonativos().then(function (response) {
                vm.countDonativos = response.data;
            });
        }

        /**
         *
         */
        var getCountCampanhas = function () {
            dashboardAdminService.getCountCampanhas().then(function (response) {
                vm.countCampanhas = response.data;
            });
        }

        /**
         *
         */
        var getCountMensageiros = function () {
            dashboardAdminService.getCountMensageiros().then(function (response) {
                vm.countMensageiros = response.data;
            });
        };

        /**
         *
         */
        var getCountDonativosCampanhaByInstituicao = function (id) {
            donativoService.countDonativosCampanhaByInstituicao(id).then(function (response) {
                vm.countDonativosCampanhaInst = response.data;
            });
        };

        /**
         *
         */
        var getCountDonativosByInstituicao = function (id) {
            donativoService.countDonativosByInstituicao(id).then(function (response) {
                vm.countDonativosInst = response.data;
            });
        };

        /**
         *
         */
        var getCountCampanhasByInstituicao = function (id) {
            campanhaService.getCountCampanhasByInstituicao(id).then(function (response) {
                vm.countCampanhasInst = response.data;
            });
        };

        /**
         *
         */
        var getCountMensageirosByInstituicao = function (id) {
            mensageiroAssociadoService.getCountByInstituicao(id).then(function (response) {
                vm.countMensageirosInst = response.data;
            });
        };

        /**
         *
         */
        var getCampanhasAtivasByInstituicao = function (id) {
            campanhaService.getCampanhasAtivasByInstituicao(id).then(function (response) {
                vm.campanhasAtivasInt = response.data;
            });
        };

        /**
         *
         */
        vm.onSelectInstituicao = function () {
            getCountDonativosCampanhaByInstituicao(vm.instituicaoSelected.id);
            getCountDonativosByInstituicao(vm.instituicaoSelected.id)
            getCountCampanhasByInstituicao(vm.instituicaoSelected.id);
            getCountMensageirosByInstituicao(vm.instituicaoSelected.id);
            getCampanhasAtivasByInstituicao(vm.instituicaoSelected.id);
            getDoacoesByPeriodoInstituicao(vm.instituicaoSelected.id);
        }

        /**
         *
         */
        var getDoacoesByPeriodo = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "RECOLHIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    vm.labelsDoacoesPeriod.push(dto.date);
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoCanceladas();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoInstituicao = function (idInst) {
            vm.seriesDoacoesPeriod = ['Doações em Trâmite', 'Doações Canceladas', 'Doações Recebidas', 'Doações não aceitas'];
            vm.labelsDoacoesInstPeriod = [];
            vm.dataDoacoesInstPeriod = [];
            dashboardAdminService.getDoacoesByPeriodoInstituicao(30, "RECOLHIDO", idInst).then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    vm.labelsDoacoesInstPeriod.push(dto.date);
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoCanceladasInstituicao(idInst);
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoCanceladas = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "CANCELADO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoEntregues();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoCanceladasInstituicao = function (idInst) {
            dashboardAdminService.getDoacoesByPeriodoInstituicao(30, "CANCELADO", idInst).then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoEntreguesInstituicao(idInst);
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoEntregues = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "RECEBIDO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoNaoAceitos();
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoEntreguesInstituicao = function (idInst) {
            dashboardAdminService.getDoacoesByPeriodoInstituicao(30, "RECEBIDO", idInst).then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesInstPeriod.push(dataDoacoesPeriod);
                getDoacoesByPeriodoNaoAceitosInstituicao(idInst);
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoNaoAceitos = function () {
            dashboardAdminService.getDoacoesByPeriodo(30, "NAO_ACEITO").then(function (response) {
                var dataDoacoesPeriod = [];
                response.data.forEach(function (dto) {
                    dataDoacoesPeriod.push(dto.count);
                })
                vm.dataDoacoesPeriod.push(dataDoacoesPeriod);
            });
        }

        /**
         *
         */
        var getDoacoesByPeriodoNaoAceitosInstituicao = function (idInst) {
            dashboardAdminService.getDoacoesByPeriodoInstituicao(30, "NAO_ACEITO", idInst).then(function (response) {
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
        var getImage = function(image) {
            if (image) {
                imageService.getImage(image.nome).then(function (response) {
                    var image =  _arrayBufferToBase64(response.data);
                    return image;
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
        }

        getInstituicoesAtivas();
        getMensageirosRanking();
        getCountCampanhas();
        getCountDonativos();
        getCountInstituicaoCaridade();
        getCountMensageiros();
        getDoacoesByPeriodo();
    }
})
();
