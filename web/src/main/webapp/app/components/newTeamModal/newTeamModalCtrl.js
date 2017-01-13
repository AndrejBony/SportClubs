"use strict";
angular.module("sportsClub").controller('newTeamModalCtrl',function($scope, $uibModalInstance, $http, notCreatedTeams) {
    $scope.notCreatedTeams = angular.copy(notCreatedTeams);

    $scope.close = function(updatedData){
        $uibModalInstance.close(updatedData);
    }

    $scope.createNewTeam = function(team){
        $http.post(restInterface + '/team',team).then(
            function(response){
                alert("Team created");
                $scope.close({"new":true,"newTeam":response.data});
            },
            function(err){
            	$scope.handleErrors(err);
            }
        );
    }
});