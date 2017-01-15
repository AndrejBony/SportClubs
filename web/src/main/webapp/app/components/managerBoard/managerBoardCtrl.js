"use strict";
angular.module("sportsClub").controller('managerBoardCtrl', function ($scope, $http, $uibModal, $stateParams) {
    $scope.playerInfos;
    $scope.teams;
    $scope.displayingTeam;

    if ($stateParams.managerId != null) {
        $scope.managerId = $stateParams.managerId;
    } else
        $scope.managerId = sessionStorage.getItem('userId');

    var getTeams = function (managerId) {
        $http.get(restInterface + "/manager/" + managerId + "/teams").then(
            function (response) {
                $scope.teams = response.data;
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }
    $scope.getFreePlayersOfClub = function () {
        $http.get(restInterface + '/manager/' + $scope.managerId + '/freePlayers').then(
            function (response) {
                $scope.displayingTeam = null;
                $scope.playerInfos = response.data;
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    $scope.getPlayersOfTeam = function (team) {
        $http.get(restInterface + "/team/" + team.id + '/players').then(
            function (response) {
                $scope.displayingTeam = team;
                $scope.playerInfos = response.data;
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    $scope.deletePlayer = function (playerId) {
        $http.delete(restInterface + "/player/" + playerId).then(
            function () {
                alert("player deleted");
                for (var i = 0; i < $scope.playerInfos.length; i++) {
                    if ($scope.playerInfos[i].id == playerId) {
                        $scope.playerInfos.splice(i, 1);
                        break;
                    }
                }
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    $scope.removePlayerFromRoster = function (playerInfoId) {
        $http.delete(restInterface + "/playerInfo/" + playerInfoId).then(
            function (response) {
                alert("player removed from team");
                for (var i = 0; i < $scope.playerInfos.length; i++) {
                    if ($scope.playerInfos[i].playerInfoId == playerInfoId) {
                        $scope.playerInfos.splice(i, 1);
                        break;
                    }
                }
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    $scope.deleteTeam = function (teamId) {
        $http.delete(restInterface + "/team/" + teamId).then(
            function (response) {
                alert("team deleted");
                for (var i = 0; i < $scope.teams.length; i++) {
                    if ($scope.teams[i].id == teamId) {
                        $scope.teams.splice(i, 1);
                        break;
                    }
                    $scope.playerInfos = [];
                }
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    var openPlayerModalinEditMode = function (playerData, teamData, managerId) {
        $http.get(restInterface + "/player/" + playerData.player.id + "/teams").then(
            function (response) {
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/components/playerModal/playerModal.html',
                    controller: 'playerModalCtrl',
                    resolve: {
                        playerinfo: function () {
                            return playerData;
                        },
                        team: function () {
                            return teamData;
                        },
                        managerId: function () {
                            return managerId;
                        },
                        playerTeams: function () {
                            return response.data;
                        }
                    }
                });
                modalInstance.result.then(function (updatedData) {
                    if (updatedData.edited == true) {
                        if($scope.displayingTeam == null){
                            for (var i = 0; i < $scope.playerInfos.length; i++) {
                                if ($scope.playerInfos[i].id == updatedData.data.id) {
                                    $scope.playerInfos[i] = updatedData.data;
                                }
                            }
                        }
                        else{
                            for (var i = 0; i < $scope.playerInfos.length; i++) {
                                if ($scope.playerInfos[i].player.id == updatedData.data.id) {
                                    $scope.playerInfos[i].player = updatedData.data;
                                }
                            }
                        }
                    }
                }, function (err) {
                    $scope.handleErrors(err);
                });
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }


    var openPlayerModalinCreateMode = function (playerData, teamData, managerId) {
        var modalInstance = $uibModal.open({
            templateUrl: 'app/components/playerModal/playerModal.html',
            controller: 'playerModalCtrl',
            resolve: {
                playerinfo: function () {
                    return playerData;
                },
                team: function () {
                    return teamData;
                },
                managerId: function () {
                    return managerId;
                },
                playerTeams: function () {
                    return null;
                }
            }
        });
        modalInstance.result.then(function (updatedData) {
            if (updatedData.new == true) {
                $scope.playerInfos.push(updatedData.data);
            }
        }, function (err) {
            $scope.handleErrors(err);
        });
    }

    $scope.openPlayerModal = function (playerData, teamData, managerId) {
        if (playerData == null) {
            openPlayerModalinCreateMode(playerData, teamData, managerId);
        }
        else if (teamData == null){
            openPlayerModalinEditMode({"jerseyNumber":null,"player": playerData}, teamData, managerId);
        }
        else{
            openPlayerModalinEditMode(playerData, teamData, managerId);

        }
    }

    $scope.openNewTeamModal = function () {
        $http.get(restInterface + "/manager/" + $scope.managerId + "/freeTeams").then(
            function (response) {
                if (response.data.length == 0) {
                    alert("You have teams for all possible categories");
                    return;
                }
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/components/newTeamModal/newTeamModal.html',
                    controller: 'newTeamModalCtrl',
                    resolve: {
                        notCreatedTeams: function () {
                            return response.data;
                        }
                    }
                });
                modalInstance.result.then(function (updatedData) {
                    if (updatedData.new == true) {
                        $scope.teams.push(updatedData.newTeam);
                    }
                }, function (err) {
                    $scope.handleErrors(err);
                });
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    $scope.openSuitablePlayersModal = function () {
        $http.get(restInterface + "/team/" + $scope.displayingTeam.id + "/suitablePlayers").then(
            function (response) {
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/components/suitablePlayersModal/suitablePlayersModal.html',
                    controller: 'suitablePlayersModalCtrl',
                    resolve: {
                        suitablePlayers: function () {
                            return response.data;
                        },
                        teamId: function () {
                            return $scope.displayingTeam.id;
                        }
                    }
                });
                modalInstance.result.then(function (playerInfo) {
                    if (playerInfo.playerInfoId !== 'undefined')
                        $scope.playerInfos.push(playerInfo);
                }, function () {
                });
            },
            function (err) {
                $scope.handleErrors(err);
            });
    }

    var init = function () {
        getTeams($scope.managerId);
        $scope.getFreePlayersOfClub();
    }

    init();
});