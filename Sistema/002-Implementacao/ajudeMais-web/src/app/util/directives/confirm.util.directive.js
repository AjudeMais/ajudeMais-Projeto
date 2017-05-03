/**
 * @ngdoc Directive
 * @name access/access-view
 *
 * @description directivas para para visualização de compomentes por papel de acesso.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {

    angular.module("ajudeMais.utils").directive('confirm',
        function (authenticationService) {
            return {
                restrict: 'A',
                link: function ($scope, element, attrs) {
                    var roles = attrs.accessView.split(',');
                    if (roles.length > 0) {
                        if (!authenticationService.isAuthorized(roles)) {
                            element.hide();
                        }
                    }


                }
            };
        });
})();