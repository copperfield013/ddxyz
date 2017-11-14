
document.onreadystatechange = function () {
    if (document.readyState === "interactive") {

        var siteDom = document.getElementById("fetchSite");
        var timeDom = document.getElementById("fetchTime");
        var payDom = document.getElementById("payWay");
        
        var locationIdDom = document.getElementById('locationId');
        //实例化pushbutton
        var sitePub = new Pushbutton('#sitebutton', {
            data: [
                { text: '雷峰塔之巅' },
                { text: '断桥之上' },
                { text: '杭州大厦' }
            ],
            // 点击回调 返回true 则不隐藏弹出框
            onClick: function (e) {
            	if(e.data){
            		locationIdDom.value = e.data.text;
            		siteDom.innerHTML = e.data.text;
            	}
            },
            isShow: false   
        });
        var timePub = new Pushbutton('#timebutton', {
            data: [
                { text: '2016-01-01 10:30' },
                { text: '2016-01-08 10:30' },
                { text: '2016-01-01 16:30' },
                { text: '2016-01-01 16:30' },
                { text: '2016-01-01 16:30' }
            ],
            // 点击回调 返回true 则不隐藏弹出框
            onClick: function (e) {
            	if(e.data){
            		if(e.data.text){
                		timeDom.value = e.data.text;
                	}
            	}
            },
            isShow: false
        });
        var payPub = new Pushbutton('#paybutton', {
            data: [
                { text: '当面支付' },
                { text: '断桥之上' },
                { text: '杭州大厦' }
            ],
            // 点击回调 返回true 则不隐藏弹出框
            onClick: function (e) {
            	if(e.data){
            		payDom.value = e.data.text;               
            	}
            },
            isShow: false
        });
        

        siteDom.addEventListener('click', function (e) {
            sitePub.show();
        }, false)

        timeDom.addEventListener('click', function (e) {
            timePub.show();
        }, false)
        payDom.addEventListener('click', function (e) {
            payPub.show();
        }, false)
    }
}
