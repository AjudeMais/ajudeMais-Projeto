/**
 * @ngdoc Directive
 * @name updateTitle
 *
 * @description directiva para verificar a existência de novas notificações
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
(function () {
    angular.module("ajudeMais.utils").directive('noticationDonativo', function ($rootScope, $timeout, donativoService, $localStorage, $state) {
        return {
            link: function (scope, element) {

                var listener = function (event) {
                    donativoService.getFilterDonativoByEstadoAndInstituicao("NAOACEITO").then(function (response) {
                        var listaDonativos = response.data;

                        $rootScope.openDetailsDonativo = function (donativo) {
                            var itemNotification = {};
                            itemNotification.id = donativo.id;
                            itemNotification.clicked = true;
                            if ($localStorage.listaClickedDonativo === undefined) {
                                $localStorage.listaClickedDonativo = [];
                            }
                            $localStorage.listaClickedDonativo.push(itemNotification);
                            $rootScope.donativosNtlist.splice(listaDonativos.indexOf(donativo), 1);
                            $state.go("home.donativo.detail", {donativoDetail: JSON.stringify(donativo)});

                        };

                        if ($localStorage.listaClickedDonativo !== undefined) {
                            $localStorage.listaClickedDonativo.forEach(function (dc) {

                                listaDonativos.forEach(function (d) {

                                    if (d.id === dc.id && dc.clicked) {
                                        listaDonativos.splice(listaDonativos.indexOf(d), 1);
                                    }

                                });
                            });

                        }

                        listaDonativos.forEach(function (d) {
                            var estadoAtivo;
                            d.estadosDaDoacao.forEach(function (estado) {
                                if (estado.ativo) {
                                    estadoAtivo = estado;
                                }
                            });
                            d.estadoAtivo = estadoAtivo;


                        });


                        $rootScope.donativosNtlist = listaDonativos;
                    });

                };

                $rootScope.$on('$stateChangeSuccess', listener);
            }
        };

    });
})();