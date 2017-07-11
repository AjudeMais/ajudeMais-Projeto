/**
 * @ngdoc Directive
 * @name updateTitle
 *
 * @description directiva para verificar a existência de novas notificações
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

(function () {
    angular.module("ajudeMais.utils").directive('noticationDonativo', function ($rootScope, $timeout, donativoService, accountService, $localStorage, $state) {
        return {
            link: function (scope, element) {
                var listaDonativos;

                /**
                 * Abri detalhes do donativo na notificação
                 * @param donativo
                 */
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

                var notification = function () {
                    donativoService.getFilterDonativoByEstadoAndInstituicao("NAO_ACEITO").then(function (response) {
                        listaDonativos = response.data;

                        if ($localStorage.listaClickedDonativo !== undefined) {
                            $localStorage.listaClickedDonativo.forEach(function (dc) {

                                listaDonativos.forEach(function (d) {
                                    if (d.id === dc.id && dc.clicked) {
                                        listaDonativos.splice(listaDonativos.indexOf(d), 1);
                                    }
                                });

                            });

                        }
                        listaDonativos = addStateActiveInDonativo(listaDonativos);

                        $rootScope.donativosNtlist = listaDonativos;

                    });
                };

                var addStateActiveInDonativo = function (listaDonativos) {
                    listaDonativos.forEach(function (d) {
                        var estadoAtivo;
                        d.estadosDaDoacao.forEach(function (estado) {
                            if (estado.ativo) {
                                estadoAtivo = estado;
                            }
                        });
                        d.estadoAtivo = estadoAtivo;


                    });

                    return listaDonativos;
                };


                var listener = function (event) {
                    if ($rootScope.pageTitle !== 'Login') {
                        accountService.findByGrupoCurrentUser().then(function (response) {
                            if (response.data[0] === 'ROLE_INSTITUICAO') {
                                notification();
                            }
                        });
                    }
                };
                $rootScope.$on('$stateChangeSuccess', listener);


            }
        }
    });
})();