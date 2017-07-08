/**
 * @ngdoc Service
 * @name contaService
 *
 * @description requisições à endpoints de conta
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("accountService", accountService);

    accountService.$inject = ['$http', 'Api'];

    function accountService($http, Api) {

        var service = {
            changePassword: _changePassword,
            findByGrupoCurrentUser:_findByGrupoCurrentUser,
        };
        return service;

        /**
         *
         * @param categoria
         * @param callback
         * @private
         */
        function _changePassword(password, callback, callbackError) {
            $http.post(Api + "/conta/changePassword/init", password).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response);

            });
        }

        /**
         *
         * @private
         */
        function _findByGrupoCurrentUser() {
            return $http.get(Api + "/conta/find/grupo");
        }
    };
})();
