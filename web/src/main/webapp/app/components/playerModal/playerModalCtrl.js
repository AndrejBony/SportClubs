"use strict";
angular.module("sportsClub").controller('playerModalCtrl', function ($scope, $http, $filter, $uibModalInstance, playerinfo, team, managerId, playerTeams) {
    if (playerinfo != null) {
        $scope.playerinfo = angular.copy(playerinfo);
    } else {
        $scope.playerinfo = {
            "jerseyNumber": null,
            "player": {
                "id": null,
                "firstName": null,
                "lastName": null,
                "dateOfBirth": null,
                "email": null,
                "password": null,
                "mobile": null,
                "weight": null,
                "heigth": null
            }
        }
    }

    if (team != null) {
        $scope.team = angular.copy(team);
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

    var validDateOfBirth = function () {
        $scope.playerinfo.player.dateOfBirth = $filter('date')($scope.playerinfo.player.dateOfBirth, "yyyy-MM-dd");
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
        if ($scope.playerinfo.player.id != null) {
            updatePlayer($scope.playerinfo.player, $scope.team, $scope.playerinfo.jerseyNumber);
            return;
        }
        if ($scope.team != null) {
            createPlayer($scope.playerinfo.player, $scope.team.id, $scope.playerinfo.jerseyNumber);
            return;
        }
        createFreePlayer($scope.playerinfo.player, managerId);
    }

    var validFirstName = function () {
        if ($scope.playerinfo.player.firstName == null) {
            alert("First name's field is empty");
            return false;
        }
        return true;
    }

    var validLastName = function () {
        if ($scope.playerinfo.player.lastName == null) {
            alert("Last name's field is empty");
            return false;
        }
        return true;
    }

    var validEmail = function () {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!re.test($scope.playerinfo.player.email)) {
            alert("Invalid email format");
            return false;
        }
        return true;
    }

    var validMobile = function () {
        if (!(/^(\+|00)?\d+$/.test($scope.playerinfo.player.mobile))) {
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

    var updatePlayer = function (player, team, jerseyNumber) {
        $http.put(restInterface + '/player', player).then(
            function (response) {
                if (team != null) {
                    $http.put(restInterface + '/playerInfo/' + player.id + '/' + team.id + '/' + jerseyNumber).then(
                        function (response) {
                            alert("Player updated");
                            $scope.close({"edited": true, "data": $scope.playerinfo});
                        },
                        function (err) {
                            $scope.handleErrors(err);
                        }
                    );
                }
                else{
                    alert("Player updated");
                    $scope.close({"edited": true, "data": response.data});
                }
            },
            function (err) {
                $scope.handleErrors(err);
            }
        );
    }
});


