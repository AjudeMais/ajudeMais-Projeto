/**
 * @ngdoc Service
 * @name authenticationService
 *
 * @description Services para controle de autenticação e autorização
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').factory('authenticationService', function ($http, $rootScope, amValue, $sessionStorage, $localStorage) {
        var resultado = {};

        /**
         *
         * @param conta
         * @param callback
         * @param callbackError
         */
        resultado.doLogin = function (conta, callback, callbackError) {
            $http.post(amValue.apiUri + "/auth/login", conta).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response.data);
            });
        };

        /**
         *
         * @param callback
         */
        resultado.doLogout = function (callback) {
            delete $localStorage.authToken;
            delete $sessionStorage.authToken;
            callback();
        }

        /**
         *
         * @param callback
         */
        resultado.logado = function (callback) {
            if ($sessionStorage.authToken === undefined) {
                callback(false);
            } else if ($rootScope.sessionUser === undefined) {
                $http.get(amValue.apiUri + "/auth/user").then(function (response) {
                    $rootScope.sessionUser = response.data;
                    callback(true);
                }, function (response) {
                    callback(false);
                });
                callback(true);
            }
        }

        /**
         *
         * @param authorizedRoles
         * @returns {boolean}
         */
        resultado.autorizado = function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!$rootScope.sessionUser && $rootScope.sessionUser.grupos.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        }

        /**
         *
         * @param jwt
         */
        resultado.storageToken = function (jwt) {
            $sessionStorage.authToken = jwt;
        }

        return resultado;
    });
})();