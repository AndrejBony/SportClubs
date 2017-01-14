'use strict';

angular.module('sportsClub').controller("homeCtrl",function($scope,$http,$state,$rootScope){
	$scope.data = {email:"",password:"",passwordcheck:""};
	
	$scope.login = function(){
		if(!validPassword()){
			return;
		}
		$http({ 
			url: restInterface + '/manager/login',
			skipAuthorization: true,
			method: 'POST',
			data: {
				email:$scope.data.email,
				password:$scope.data.password
			}
		}).then(function(response){
			if(response.data == ""){
				alert("Wrong email or password");
				$scope.data.password="";
				$scope.data.email="";
				return;
			}
				sessionStorage.setItem("jwt", response.data.token);
				sessionStorage.setItem("userId", response.data.manager.id);
				if(response.data.manager.role=="manager"){
					$state.go("manager",{managerId:response.data.manager.id});
				}
				if(response.data.manager.role=="admin"){
					$state.go("admin");
				}
		},function(response){
			$scope.handleErrors(response);});
	}
	
	var validPassword = function(){
        if($scope.data.password.length < 6){
            alert("password must contain at least 6 characters");
            return false;
        }
        return true;
    }
});	