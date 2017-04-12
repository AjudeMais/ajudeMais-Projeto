/**
 * @ngdoc Service
 * @name authenticationService
 *
 * @description Services para controle de autenticação e autorização
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp').factory('authenticationService', function ($http, $rootScope, amValue) {
        var resultado = {};

        resultado.doLogin = function (username, senha, callback, callbackError) {
            var encodedString = btoa(username + ":" + senha);
            $rootScope.basicAuth = encodedString;
            $http.post(amValue.apiUri + '/login', {
                username: username,
                senha: senha
            }).then(function (response) {
                $rootScope.sessionUser = response.data;
                callback(response.data);
            }, function (error) {
                callbackError(error.data);
            });
        };

        resultado.doLogout = function (callback) {
            $rootScope.sessionUser = {};
            $rootScope.basicAuth = {};
            $http.get(amValue.apiUri + '/logout').then(function (response) {
                callback();
            });
        };

        resultado.usuarioLogado = function (callback) {
            if (angular.equals($rootScope.sessionUser, {})) {
                callback(false);
            } else if ($rootScope.sessionUser === undefined) {
                $http.get(amValue.apiUri + '/user').then(
                    function (response) {
                        $rootScope.sessionUser = response.data;
                        if ($rootScope.sessionUser.hasOwnProperty("username")) {
                            console.log("Pegou o usuario no servidor");
                            callback(true);
                        } else {
                            callback(false);
                        }
                    }, function (response) {
                        callback(false);
                    });
            } else {
                callback(true);
            }
        }

        /**
         *
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

        return resultado;
    });

})();