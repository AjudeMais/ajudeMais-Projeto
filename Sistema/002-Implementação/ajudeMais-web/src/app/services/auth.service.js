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

        /**
         *
         * @param account
         * @param callback
         * @param callbackError
         * @private
         */
        var _doLogin = function (account, callback, callbackError) {
            $http.post(amValue.apiUri + "/auth/login", account).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response.data);
            });
        };

        /**
         *
         * @param callback
         * @private
         */
        var _doLogout = function (callback) {
            delete $localStorage.authToken;
            delete $sessionStorage.authToken;
            $sessionStorage.sessionUser = undefined;
            callback();
        }

        /**
         *
         * @param callback
         * @private
         */
        var _getUserLogged = function (callback) {
            $http.get(amValue.apiUri + "/auth/user").then(function (response) {
                $sessionStorage.sessionUser = response.data;
                callback();
            });
        };

        /**
         *
         * @param callback
         * @private
         */
        var _logged = function (callback) {
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
         * @private
         */
        var _isAuthorized = function (authorizedRoles) {
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
         * @private
         */
        var _storageToken = function (jwt) {
            $sessionStorage.authToken = jwt;
        }

        return {
            doLogin: _doLogin,
            doLogout: _doLogout,
            getUserLogged: _getUserLogged,
            logged: _logged,
            isAuthorized: _isAuthorized,
            storageToken: _storageToken
        };
    });
})();