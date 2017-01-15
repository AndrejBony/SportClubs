"use strict";
angular.module("sportsClub").controller('suitablePlayersModalCtrl',function($scope, $uibModal, $uibModalInstance, $http, suitablePlayers, team) {
	$scope.suitablePlayers = angular.copy(suitablePlayers);
    $scope.team = angular.copy(team);


    $scope.close = function(updatedData){
        $uibModalInstance.close(updatedData);
    }

    $scope.addPlayerToRoster = function(playerId,jerseyNumber){
    	if(!$scope.assignationForm.$valid){
            return;
        }
    	if(jerseyNumber==null){
    		alert("You must enter jersey number.");
    		return;
    	}
        $http.post(restInterface + '/playerInfo/' + teamId + '/' + playerId + '/' + jerseyNumber).then(
            function(response){
                alert("Player added in the roster");
                $scope.close(response.data);
            },
            function(err){
            	$scope.handleErrors(err);
            }
        );
    }

    $scope.newPlayerModal = function(){
        var modalInstance = $uibModal.open({
            templateUrl: 'app/components/playerModal/playerModal.html',
            controller: 'playerModalCtrl',
            resolve: {
                playerinfo: function () {
                    return null;
                },
                team: function () {
                    return $scope.team;
                },
                managerId: function () {
                    return null;
                },
                playerTeams: function () {
                    return null;
                }
            }
        });
        modalInstance.result.then(function (updatedData) {
            $scope.close(updatedData.data);
        
            }, function () {
        });
    }
    
    var init = function(){
    	//add jeresey number fields to objects
    	var players = angular.copy(suitablePlayers);
    	for(var i =0; i<players.length;i++){
    		players[i].jerseyNumber = null;
    	}
    	$scope.suitablePlayers = players;
    }
    init();
});