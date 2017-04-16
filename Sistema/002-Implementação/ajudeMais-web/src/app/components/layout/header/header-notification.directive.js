(function () {
    angular.module('layout').directive('headerNotification', function () {
        return {
            templateUrl: 'app/components/layout/header/header-notification.directive.html',
            restrict: 'E',
            replace: true,
            controller: function ($rootScope, $state, authenticationService) {
                var vm = this;
                vm.usuario = $rootScope.sessionUser;
                /**
                 *
                 */
                vm.logout = function () {
                    authenticationService.doLogout(function () {
                        $state.go("login");
                    });
                }

            },
            controllerAs: 'headerNotificationCtrl'
        }
    });
})();
