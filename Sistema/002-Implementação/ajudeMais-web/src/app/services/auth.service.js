/**
 * @ngdoc Service
 * @name authenticationService
 *
 * @description Services para controle de autenticação e autorização
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').factory('authenticationService', function ($http, amValue, $sessionStorage, $localStorage) {
        var service = {};

        /**
         *
         * @param account
         * @param callback
         * @param callbackError
         */
        service.doLogin = function (account, callback, callbackError) {
            $http.post(amValue.apiUri + "/auth/login", account).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response.data);
            });
        };

        /**
         *
         * @param callback
         */
        service.doLogout = function (callback) {
            delete $localStorage.authToken;
            delete $sessionStorage.authToken;
            $sessionStorage.sessionUser = undefined;
            callback();
        }

        /**
         *
         * @param callback
         */
        service.getUserLogged = function (callback) {
            $http.get(amValue.apiUri + "/auth/user").then(function (response) {
                $sessionStorage.sessionUser = response.data;
                callback();
            });
        };

        /**
         *
         * @param callback
         */
        service.logged = function (callback) {
            if ($sessionStorage.authToken === undefined) {
                callback(false);
            } else if ($sessionStorage.sessionUser === undefined) {
                $http.get(amValue.apiUri + "/auth/user").then(function (response) {
                    $sessionStorage.sessionUser = response.data;
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
        service.isAuthorized = function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!$sessionStorage.sessionUser && $sessionStorage.sessionUser.grupos.indexOf(authorizedRole) !== -1);
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
        service.storageToken = function (jwt) {
            $sessionStorage.authToken = jwt;
        }

        return service;
    });
})();