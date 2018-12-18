 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){	
	
	$controller('baseController',{$scope:$scope});//继承
	//点击注册
    $scope.reg=function () {
    	if ($scope.entity.password!=$scope.password){
    		alert("两次输入的密码不一致!");
    		return;
		}
		userService.add($scope.entity,$scope.smscode).success(
			function (response) {
				alert(response.message)
            }
		)
    };
    //发送验证码
    $scope.sendCode=function () {
		if ($scope.entity.phone==null){
			alert("请输入手机号");
			return
		}
		userService.sendCode($scope.entity.phone).success(
			function (response) {
				alert(response.message)
            }
		)
    }


});	
