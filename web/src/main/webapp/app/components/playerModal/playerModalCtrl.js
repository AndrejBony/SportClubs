"use strict";
angular.module("sportsClub").controller('playerModalCtrl', function ($scope, $http, $filter ,$uibModalInstance, player, team, managerId, playerTeams) {
    if (player != null) {
        $scope.player = angular.copy(player);
    } else {
        $scope.player = {
            "id": null,
            "firstName": null,
            "lastName": null,
            "dateOfBirth": null,
            "email": null,
            "password": null,
            "mobile": null,
            "weight": null,
            "heigth": null,
        }
    }

    if (team != null) {
        $scope.team = angular.copy(team);
    } else {
        $scope.team = {
            "id": null,
            "category": null,
            "manager": null,
        }
    }

    if (managerId != null) {
        $scope.managerId = angular.copy(managerId);
    }

    if (playerTeams != null) {
        $scope.playerTeams = angular.copy(playerTeams);
    }

    $scope.close = function (updatedData) {
        $uibModalInstance.close(updatedData);
    }
    var validDateOfBirth = function() {
        $scope.player.dateOfBirth = $filter('date')($scope.player.dateOfBirth, "yyyy-MM-dd");
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
        validDateOfBirth();
        if ($scope.player.id != null) {
            updatePlayer($scope.player);
            return;
        }
        if ($scope.team.id != null) {
            createPlayer($scope.player);
            return;
        }
        createFreePlayer($scope.player,managerId);
    }

    var validFirstName = function () {
        if ($scope.player.firstName == null) {
            alert("First name's field is empty");
            return false;
        }
        return true;
    }

    var validLastName = function () {
        if ($scope.player.lastName == null) {
            alert("Last name's field is empty");
            return false;
        }
        return true;
    }

    var validEmail = function () {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!re.test($scope.player.email)) {
            alert("Invalid email format");
            return false;
        }
        return true;
    }

    var validMobile = function () {
        if (!(/^(\+|00)?\d+$/.test($scope.player.mobile))) {
            alert("Invalid mobile phone format");
            return false;
        }
        return true;
    }

    var createPlayer = function (player, teamId, jerseyNumber) {
        $http.post(restInterface + '/playerInfo/' + teamId + '/' + jerseyNumber, player).then(
                function (response) {
                    alert("Player created and assigned to the team");
                    $scope.close({"new": true, "data": response.data});
                },
                function (err) {
                    $scope.handleErrors(err);
                }
        );
    }

    var createFreePlayer = function (player, managerId) {
        $http.post(restInterface + "/player/" + managerId, player).then(
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
                    $scope.close({"edited": true, "data": response.data});
                },
                function (err) {
                    $scope.handleErrors(err);
                }
        );
    }
});


