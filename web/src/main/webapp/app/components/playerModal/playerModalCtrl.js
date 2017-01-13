"use strict";
angular.module("sportsClub").controller('playerModalCtrl', function ($scope, $http, $uibModalInstance, data) {
    if (data != null) {
        $scope.data = angular.copy(data);
    } else {
        $scope.data = {
            "id": null,
            "firstName": null,
            "lastName": null,
            "dateOfBirth": null,
            "email": null,
            "password": null,
            "mobile": null,
            "weight": null,
            "heigth": null,
            "manager_id": null
        }
    }

    $scope.close = function (updatedData) {
        $uibModalInstance.close(updatedData);
    }

    $scope.save = function () {
        if (!validFirstName()) {
            return;
        }
        if (!validLastName()) {
            return;
        }
        if (!validEmail()) {
            return;
        }
        if (!validMobile()) {
            return;
        }
        if ($scope.data.id != null) {
            updatePlayer($scope.data);
            return;
        }
        createPlayer($scope.data);
    }

    var validFirstName = function () {
        if ($scope.data.firstName == null) {
            alert("First name's field is empty");
            return false;
        }
        return true;
    }

    var validLastName = function () {
        if ($scope.data.lastName == null) {
            alert("Last name's field is empty");
            return false;
        }
        return true;
    }

    var validEmail = function () {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!re.test($scope.data.email)) {
            alert("Invalid email format");
            return false;
        }
        return true;
    }

    var validMobile = function () {
        if (!(/^(\+|00)?\d+$/.test($scope.data.mobile))) {
            alert("Invalid mobile phone format");
            return false;
        }
        return true;
    }

    var createPlayer = function (player) {
        $http.post(restInterface + '/playerInfo/{teamId}/{jerseyNumber}', player).then(
                function (response) {
                    alert("Player created");
                    $scope.close({"new": true, "data": response.data});
                },
                function (err) {
                    $scope.handleErrors(err);
                }
        );
    }

    var updatePlayer = function (player) {
        $http.put(restInterface + '/player', player).then(
                function (response) {
                    alert("Player updated");
                    $scope.close({"edited": true});
                },
                function (err) {
                    $scope.handleErrors(err);
                }
        );
    }
});


