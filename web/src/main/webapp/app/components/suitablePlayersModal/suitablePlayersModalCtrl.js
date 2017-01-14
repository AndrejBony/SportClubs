"use strict";
angular.module("sportsClub").controller('suitablePlayersModalCtrl',function($scope, $uibModal, $uibModalInstance, $http, suitablePlayers,teamId) {
	$scope.suitablePlayers = angular.copy(suitablePlayers);

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
                player: function () {
                    return null;
                },
                team: function () {
                    return null;
                },
                managerId: function () {
                    return null;
                }
            }
        });
        modalInstance.result.then(function (updatedData) {
                $scope.playerInfos.player.push(updatedData.data);
        
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