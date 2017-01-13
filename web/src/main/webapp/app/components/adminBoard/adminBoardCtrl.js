"use strict";
angular.module("sportsClub").controller('adminBoardCtrl',function($scope, $uibModal, $http, $location) {
    $scope.managers;

    $scope.deleteManager = function(managerId){
        $http.delete(restInterface +'/manager/'+managerId).then(
            function(){
                for(var i=0; i < $scope.managers.length; i++){
                    if($scope.managers[i].id == managerId){
                        $scope.managers.splice(i,1);
                        break;
                    }
                }
            },
            function(err){
            	$scope.handleErrors(err);
            }
        );
    }

    $scope.openManagerModal = function(managerData){
        var modalInstance = $uibModal.open({
            templateUrl : 'app/components/managerModal/managerModal.html',
            controller : 'managerModalCtrl',
            resolve : {
                data : function() {
                    return managerData;
                }
            }
        });
        modalInstance.result.then(function(updatedData) {
            if(updatedData.new==true){
                $scope.managers.push(updatedData.data);
            }
            else{
                for(var i =0;i<$scope.managers.length;i++){
                    if($scope.managers[i].id==updatedData.data.id){
                        $scope.managers[i] = updatedData.data;
                    }
                }
            }
        }, function () {
        });
    }

    var loadManagers = function(){
        $http.get(restInterface + '/manager').then(
            function(response) {
                $scope.managers = response.data;
            },
            function(err) {
            	$scope.handleErrors(err);
            }
        );
    }

    var init = function(){
        loadManagers();
    }
    init();
});