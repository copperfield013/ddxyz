<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<base href="${basePath }" />
    <meta charset="utf-8"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <meta name="format-detection" content="telephone=no"><!-- 禁止iphone修改数字样式 -->
    <meta name="viewport" content="minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,width=device-width,user-scalable=no">
    <title>点点新意</title>
    <jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/templete.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/common.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/buy.css">
    <script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
    <script src="${basePath }media/weixin/plugins/xcheck/xcheck.js"></script>
    <script src="${basePath }media/weixin/plugins/xnumber/xnumber.js"></script>
    <script>
    $(function() {
        ix.init();
    });
    </script>
</head>
<body>
<form class="validate" action="index.html" method="post">
<main class="form">
    <!-- 配送信息 -->
    <div class="send-info">
        <h4>配送信息</h4>
        <dl class="select">
            <dt>时间档</dt>
            <dd><select id="time" name="time">
                <option value="11">11点</option>
                <option value="12" selected>12点</option>
                <option value="13">13点</option>
                <option value="14">14点</option>
                <option value="15">15点</option>
                <option value="16">16点</option>
            </select></dd>
        </dl>
        <dl class="select">
            <dt>配送地址</dt>
            <dd><select id="address" name="address">
                <option value="" selected>请选择</option>
                <option value="浙大西溪校区">浙大西溪校区</option>
                <option value="文三路马塍路口90号">文三路马塍路口90号</option>
                <option value="文二路古翠路12号">文二路古翠路12号</option>
            </select></dd>
        </dl>
        <dl class="text">
            <dt>联系号码</dt>
            <dd>
                <input id="telphone" type="text" name="telphone" placeholder="请输入">
            </dd>
        </dl>
    </div>
    <!-- 奶茶详情 -->
    <div class="good-info">
        <h4>奶茶详情</h4>
        <dl class="select">
            <dt>奶茶种类</dt>
            <dd><select id="type" name="type">
                <option value="">请选择</option>
                <option value="1">奶茶</option>
                <option value="2">咖啡</option>
                <option value="3">冷饮</option>
            </select></dd>
        </dl>
        <dl class="number">
            <dt>数量</dt>
            <dd>
                <input id="count" type="number" name="count" value="1">
            </dd>
        </dl>
        <div class="box">
            <h5>奶茶详情<pre>每行单选</pre></h5>
            <p>
                <input type="radio" name="type" id="type_1" value="1" checked>
                <label for="type_1">红茶</label>
                <input type="radio" name="type" id="type_2" value="2">
                <label for="type_2">绿茶</label>
                <input type="radio" name="type" id="type_3" value="3">
                <label for="type_3">四季春茶</label>
                <input type="radio" name="type" id="type_4" value="4">
                <label for="type_4">乌龙茶</label>
            </p>
            <p>
                <input type="radio" name="size" id="size_1" value="1" checked>
                <label for="size_1">中杯</label>
                <input type="radio" name="size" id="size_2" value="2">
                <label for="size_2">大杯</label>
            </p>
            <p>
                <input type="radio" name="sweet" id="sweet_1" value="1" checked>
                <label for="sweet_1">3分甜</label>
                <input type="radio" name="sweet" id="sweet_2" value="2">
                <label for="sweet_2">5分甜</label>
                <input type="radio" name="sweet" id="sweet_3" value="3">
                <label for="sweet_3">7分甜</label>
            </p>
            <p>
                <input type="radio" name="temperature" id="temperature_1" value="1" checked>
                <label for="temperature_1">冰</label>
                <input type="radio" name="temperature" id="temperature_2" value="2">
                <label for="temperature_2">常温</label>
                <input type="radio" name="temperature" id="temperature_3" value="3">
                <label for="temperature_3">热</label>
            </p>
        </div>
        <div class="box">
            <h5>我要加料<pre>可多选哦</pre></h5>
            <p>
                <input type="checkbox" name="other" id="other_1" value="1">
                <label for="other_1">珍珠</label>
                <input type="checkbox" name="other" id="other_2" value="2">
                <label for="other_2">波霸</label>
                <input type="checkbox" name="other" id="other_3" value="3">
                <label for="other_3">烧仙草</label>
                <input type="checkbox" name="other" id="other_4" value="4">
                <label for="other_4">椰果</label>
                <input type="checkbox" name="other" id="other_5" value="5">
                <label for="other_5">红豆</label>
            </p>
        </div>
        <div class="box">
            <h5>其他备注</h5>
            <p class="blob">
                <textarea id="remarks" name="remarks" placeholder="输入备注详情"></textarea>
            </p>
        </div>
        <div class="box">
            <a id="addDrink" href="javascript:;" class="btn-add">添加</a>
            <div class="table-detail">
                <ul>
                    <li>名称</li>
                    <li>详情</li>
                    <li>杯数</li>
                    <li>价格</li>
                    <li>操作</li>
                </ul>
                <ul>
                    <li>金吉柠檬</li>
                    <li>
                        <p>绿茶|中杯|3分甜|常温</p>
                        <p>加料：珍珠、波霸、烧仙草、椰果、红豆绿茶</p>
                    </li>
                    <li>1</li>
                    <li>￥12.0</li>
                    <li><a href="javascript:;">删除</a></li>
                </ul>
                <ul>
                    <li>金吉柠檬</li>
                    <li>
                        <p>绿茶|中杯|3分甜|常温</p>
                        <p>加料：珍珠、波霸、烧仙草、椰果、红豆绿茶</p>
                    </li>
                    <li>1</li>
                    <li>￥12.0</li>
                    <li><a href="javascript:;">删除</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="total-price">
        <span class="count">总杯数：4</span>
        <span class="price">总价：<b>￥40.0</b></span>
    </div>
</main>
<footer>
    <a href="order" class="order">订单</a>
    <a id="payment" href="javascript:;" class="main">结账付款</a>
</footer>
</form>
<script>
$(function(){
	$('input[type="radio"],input[type="checkbox"]').xcheck();
	$('input[type="number"]').xnumber();
	seajs.use(['ajax'], function(Ajax){
		var drinks=[];
		function addDrink(){
			var type = $('select#type option:selected').val(); //饮料类型
			var name = $('input[name="type"]:checked').val(); //奶茶名称
			var count = $("#count").val(); //数量
			var size = $('input[name="size"]:checked').val(); //奶茶规格
			var sweet = $('input[name="sweet"]:checked').val(); //奶茶甜度
			var temperature = $('input[name="temperature"]:checked').val(); //奶茶冰度
			var additions = [];
			$('input[name="other"]:checked').each(function(){
				additions.push({
					additionId : $(this).val(),
					additionName : $(this).next().text()
				})
			});
			var data ={
		            	type : type,
		            	name : name,
		            	count : count,
		            	size : size,
		            	sweet : sweet,
		            	temperature : temperature,
		            	additions : additions
			            };
			drinks.push(data);
			console.log(drinks);
			
		}
		function payment(){
			var time = $('select#time option:selected').val(); //时间档
			var address = $('select#address option:selected').val(); //配送地点
			var telphone = $("#telphone").val(); //时间档
			var remarks = $("#remarks").val();//备注
			var data = {
					time: time,
					address:address,
					telphone:telphone,
					remarks:remarks,
					drinks:drinks
					};
			console.log(data);
			Ajax.postJson('weixinBuy/testJson',data,function(json){
				console.log(json);
			});
		}
		$("#addDrink").click(addDrink);
		$("#payment").click(payment);
	})
	
});
</script>
</body>
</html>