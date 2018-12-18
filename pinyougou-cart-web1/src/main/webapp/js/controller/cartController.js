app.controller("cartController",function ($scope,cartService) {
    //显示购物车列表
    $scope.findCartList=function () {
        cartService.findCartList().success(
            function (response) {
                $scope.cartList=response;
                $scope.totalValue=cartService.sum($scope.cartList);//求合计数
            }
        )
    };
    //添加商品到购物车
    $scope.addGoodsToCartList=function (itemId,num) {
        cartService.addGoodsToCartList(itemId,num).success(
            function (response) {
            if (response.success){
                $scope.findCartList();
            }else {
                alert(response.message)
            }

        })
    }
    //获取当前登陆人的地址列表
    $scope.findAddressList=function () {
        cartService.findAddressList().success(
            function (repsonse) {
           $scope.addressList=repsonse;
           for(var i=0;i<$scope.addressList.length;i++){
               if($scope.addressList[i].isDefault=='1'){
                  $scope.address=$scope.addressList[i];
                  break;
               }
           }
        })
    }
    //选择地址
    $scope.selectAddress=function (address) {
        $scope.address=address;
    }
    //判断是否是当前选中的地址
    $scope.isSelectdAddress=function (address) {
        if(address==$scope.address){
            return true;
        }else {
            return false;
        }
    }
    $scope.order={paymentType:'1'};
    //选择支付方式
    $scope.selectPayType=function (type) {
        $scope.order.paymentType=type;

    }
    //提交订单
    $scope.submitOrder=function () {
        $scope.order.receiverAreaName=$scope.address.address;//地址
        $scope.order.receiverMobile=$scope.address.mobile;//手机
        $scope.order.receiver=$scope.address.contact;//联系人
        cartService.submitOrder($scope.order).success(
            function (repsonse) {
                if(repsonse.success){
                    //页面跳转
                    if ($scope.order.paymentType=='1'){
                        //如果在线支付,跳转到微信支付页面
                        location.href='pay.html';
                    }else {
                        //如果是货到付款,跳转到成功页面
                        location.href="paysuccess.html"
                    }
                }else {
                    alert(repsonse.message);
                }

            }
        )
    }
});