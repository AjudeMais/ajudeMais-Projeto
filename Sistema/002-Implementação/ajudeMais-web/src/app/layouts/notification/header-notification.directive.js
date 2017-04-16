(function () {
    angular.module('layout').directive('headerNotification', function () {
        return {
            templateUrl: 'app/layouts/notification/header-notification.directive.html',
            restrict: 'E',
            replace: true,
            controller: function ($sessionStorage, $state, authenticationService) {
                var vm = this;
                vm.usuario = $sessionStorage.sessionUser;
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
