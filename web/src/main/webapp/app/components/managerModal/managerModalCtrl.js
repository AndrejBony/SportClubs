"use strict";
angular.module("sportsClub").controller('managerModalCtrl',function($scope, $uibModalInstance, $http, data) {
    if(data == null){
        $scope.data = {
            "id":null,
            "firstName":null,
            "lastName":null,
            "email":null,
            "password":null,
            "mobile":null,
            "clubName":null,
            "role":"manager"
            }
    }
    else{
        $scope.data = angular.copy(data);
    }

    $scope.close = function(updatedData){
        $uibModalInstance.close(updatedData);
    }

    $scope.save = function(){
        if(!$scope.managerForm.$valid){
            return;
        }
        if(!validMobile()){
            return;
        }
        if($scope.data.id != null){
            updateManager($scope.data);
            return;
        }
        if(!validPassword()){
            return;
        }
        createNewManager($scope.data);
    }

    var createNewManager = function(managerData){
        $http.post(restInterface + '/manager',managerData).then(
            function(response){
                alert("Manager created");
                $scope.close({"new":true,"data":response.data});
            },
            function(err){
            	$scope.handleErrors(err);
            }
        );
    }

    var updateManager = function(managerData){
        $http.put(restInterface + '/manager',managerData).then(
            function(response){
                alert("Manager updated");
                $scope.close({"new":false,"data":response.data});
            },
            function(err){
            	$scope.handleErrors(err);
            }
        );
    }

    var validPassword = function(){
        if($scope.data.password.length < 6){
            alert("password must contain at least 6 characters");
            return false;
        }
        return true;
    }

    var validMobile = function(){
        if(!(/^(\+|00)?\d+$/.test($scope.data.mobile))){
            alert("invalid mobile phone format");
            return false;
        }
        return true;
    }
});