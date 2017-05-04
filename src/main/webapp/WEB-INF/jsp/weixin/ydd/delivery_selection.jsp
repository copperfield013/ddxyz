<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="http://localhost:8080/ddxyz/media/weixin/main/css/delivery-selection.css?${RES_STAMP}">
<meta name="viewport" content="minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,width=device-width,user-scalable=no">
<script src="http://localhost:8080/ddxyz/media/weixin/main/js/jquery-3.1.1.min.js"></script>
<script>
    $(function(){
//        点击选择地址
        $(".dialog-content").on("click",".dialog-distribution-address",function(){
            $(".dialog-distribution-address").removeClass("active");
            $(this).addClass("active");
        });
        getAddress();
        cancel();
    });
//    确认按钮获取选择中的地址的外层div
    function getAddress(){
        $('.dialog-button-sure').on("click",function(){
        	console.log($(".dialog-distribution-address").filter('.active')[0]);
            return ($(".dialog-distribution-address").filter('.active')[0]);
        });
    }
//    取消按钮
    function cancel(){
        $('.dialog-button-cancel').on("click",function(){
            return {};
        })
    }
</script>
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
            </li>
            <li>
                <div class="dialog-distribution-time">配送时间档：
                    <i class="dialog-distribution-time">13点档</i>
                </div>
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
            </li>
            <li>
                <div class="dialog-distribution-time">配送时间档：
                    <i class="dialog-distribution-time">13点档</i>
                </div>
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
            </li>
        </ul>
        <div class="dialog-button">
            <a href="javascript:;" class="dialog-button-cancel">取消</a>
            <a href="javascript:;" class="dialog-button-sure">确认</a>
        </div>
    </div>
</div>