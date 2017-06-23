<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet"  href="media/admin/menu/css/add_menu.css"> 
<script src="media/admin/menu/js/add_menu.js"></script>
<div class="ui-page" id="admin-menu-add">
        <div class="ui-body">
        <div class="ui-content">
            <div class="menu-preview-area">
                <div class=menu-preview-title>点点新意</div>
                    <!--底部菜单栏-->
                <ul class=menu-preview-menu>
                    <!--具体菜单部分，需要js动态加载（依据加载数据）-->
                </ul>
            </div>
            <div class="menu-name-area">
                <div class="operation-menuname">
                    <span>菜单名称：</span>
                    <p>
                        <input type="text" name="menuName" value="菜单名称" id="menuName">
                        <span class="menu-name-info">字数不超过4个汉字或8个字母（注：请勿输入空格）</span>
                    </p>
                    <span>链接地址：</span>
                    <p>
                        <input type="text" name="linkUrl" id="linkUrl" placeholder="请输入要跳转的页面URL">
                    </p>
                    <input type="button" value="保存" class="save">
                </div>
                <span>点击左侧菜单进行编辑</span>
            </div>
        </div>
        </div>
    </div>
    
 <script>
   $(function(){
        var initData = $.parseJSON('${menuConteng}');
/*         var initData = JSON.parse($('#initData').text()); */
        console.log("initData:" + initData.menu );
        menu.init(initData.menu);
         //保存
     $('.save').on("click",function(e){
            var saveData = {};
            e.stopPropagation();
            saveData =  menu.save();

            /*
             ***
             ***该数据为保存时的数据,可自由操作；
             ***
            */
            console.log(saveData);  


        })
   })  
</script>
