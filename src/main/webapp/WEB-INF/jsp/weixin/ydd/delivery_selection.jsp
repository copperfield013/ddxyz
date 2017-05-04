<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
<link rel="stylesheet" href="${basePath }media/weixin/main/css/delivery-selection.css?${RES_STAMP}">
<div id="dialog">
    <div id="dialog-warp">
        <div class="dialog-title">
            请选择要再次购买的配送
        </div>
        <ul class="dialog-content">
            <li>
                <div class="dialog-distribution-time">配送时间档：
                    <i class="dialog-distribution-time">13点档</i>
                </div>
                <div class="distribution-address-warp">
                    <div class="dialog-distribution-address TH-market">
                        天虹商场
                        <i>
                            <b></b>
                        </i>
                    </div>

                    <div class="dialog-distribution-address HZ-mansion">
                        杭钻大厦
                        <i>
                            <b></b>
                        </i>
                    </div>
                </div>
            </li>
            <li>
                <div class="dialog-distribution-time">配送时间档：
                    <i class="dialog-distribution-time">13点档</i>
                </div>
                <div class="distribution-address-warp">
                    <div class="dialog-distribution-address TH-market">
                        天虹商场
                        <i>
                            <b></b>
                        </i>
                    </div>

                    <div class="dialog-distribution-address HZ-mansion">
                        杭钻大厦
                        <i>
                            <b></b>
                        </i>
                    </div>
                </div>
            </li>
            <li>
                <div class="dialog-distribution-time">配送时间档：
                    <i class="dialog-distribution-time">13点档</i>
                </div>
                <div class="distribution-address-warp">
                    <div class="dialog-distribution-address TH-market">
                        天虹商场
                        <i>
                            <b></b>
                        </i>
                    </div>

                    <div class="dialog-distribution-address HZ-mansion">
                        杭钻大厦
                        <i>
                            <b></b>
                        </i>
                    </div>
                </div>
            </li>
        </ul>
        <div class="dialog-button">
            <a href="javascript:;" class="dialog-button-cancel">取消</a>
            <a href="javascript:;" class="dialog-button-sure">确认</a>
        </div>
    </div>
</div>


<script>
    $(function(){
//		设置第一个地址栏展开    	
    	$(".distribution-address-warp:first").css("display","block");
//		绑定地址栏点击事件    	
        $(".dialog-distribution-time").on("click",function(){
            var address=$(this).next();
            address.slideToggle("fast");
            $('.distribution-address-warp').not(address).slideUp('fast');
        })
//        点击选择地址
        $(".dialog-content").on("click",".dialog-distribution-address",function(){
            $(".dialog-distribution-address").removeClass("active");
            $(this).addClass("active");
        });
//    确认按钮获取选择中的地址的外层div
       $('.dialog-button-sure').on("click",function(){
       		console.log($(".dialog-distribution-address").filter('.active')[0]);
       });
//    取消按钮
       $('.dialog-button-cancel').on("click",function(){
    	   
       })
    });
</script>