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
                    <span>菜单地址：</span>
                    <p>
                        <input type="text" name="menuName" value="菜单名称" id="menuName">
                        <span class="menu-name-info">字数不超过4个汉字或8个字母（注：请勿输入空格）</span>
                    </p>
                    <input type="button" value="保存" class="save">
                </div>
                <span>点击左侧菜单进行编辑</span>
            </div>
        </div>
        </div>
         <!--初始化数据存放 -->
        <!--最外层大括号必须 ， 键值对下没有另外的键值对那么该键值对后不能加逗号,字键值对不能为空，没有就不传-->
        <div id="initData">
            {
                "button":[
                            {
                                "name":"微门户",
                                "sub_button":[
                                                {   
                                                    "name":"社区简介"    
                                                },
                                                {
                                                    "name":"社区新闻"      
                                                }
                                            ]
                            },
                            {
                                "name":"微互动",
                                "sub_button":[
                                                
                                            ]
                            }
                        ]
            }
        </div> 
    </div>
    
 <script>
   $(function(){
        var initData = JSON.parse($('#initData').text());
        menu.init(initData);
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
