package test.sowell.copframe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sowell.copframe.weixin.common.service.WxConfigService;
import cn.sowell.copframe.weixin.pay.prepay.GoodsDetail;
import cn.sowell.copframe.weixin.pay.prepay.JsApiPrepayParameter;
import cn.sowell.copframe.weixin.pay.prepay.OrderDetail;
import cn.sowell.copframe.weixin.pay.prepay.PrepayResult;
import cn.sowell.copframe.weixin.pay.prepay.TradeType;
import cn.sowell.copframe.weixin.pay.prepay.UnifiedOrder;
import cn.sowell.copframe.weixin.pay.service.WxPayService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config/spring-junit.xml")
public class WxPayServiceTest {
	
	@Resource
	WxConfigService wxConfigService;
	
	@Resource
	WxPayService payService;
	
	@Test
	public void test() throws Exception {
		UnifiedOrder order = new UnifiedOrder();
		OrderDetail detail = new OrderDetail();
		detail.setCostPrice(11);
		detail.setReceiptId("111");
		List<GoodsDetail> gds = new ArrayList<GoodsDetail>();
		GoodsDetail gd = new GoodsDetail();
		gd.setGoodsId("iphone6s_16G");
		gd.setGoodsName("iPhone6s 16G");
		gd.setWxpayGoodsId("1001");
		gd.setQuantity(1);
		gd.setPrice(528800);
		gds.add(gd);
		detail.setGoodsDetail(gds);
		order.setDetail(detail);
		
		
		order.setAppid("wx2421b1c4370ec43b");
		order.setAttach("支付测试");
		order.setBody("JSAPI支付测试");
		order.setMerchantId("10000100");
		order.setNonceStr("1add1a30ac87aa2db72f57a2375d8fec");
		order.setNotifyURL("http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
		order.setOpenid("oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
		order.setOutTradeNo("1415659990");
		order.setSpbillCreateIp("14.23.150.211");
		order.setTotalFee(1);
		order.setTradeType(TradeType.JSAPI);
		
		
		//order.setSign("0CB01533B8C1EF103065174F50BCA001");
		
		PrepayResult result = payService.sendPrepay(order, true);
		System.out.println(result);
		/*XmlNode node = converter.doConvert(order , true, false);
		System.out.println(node);
		XmlNode result = HttpRequestUtils.postXMLAndReturnXML("https://api.mch.weixin.qq.com/pay/unifiedorder", node);
		System.out.println(result);
		System.out.println(result.getFirstElementText("return_code"));*/
	}
	
	@Test
	public void testPay() {
		JsApiPrepayParameter parameter = new JsApiPrepayParameter();
		parameter.setOpenId("ovZxms3dvkaZR2aFpmkWh2SxmCTY");
		parameter.setBody("JSAPI支付测试");
		parameter.setTotalFee(1);
		OrderDetail detail = new OrderDetail();
		detail.setCostPrice(11);
		detail.setReceiptId("111");
		List<GoodsDetail> gds = new ArrayList<GoodsDetail>();
		GoodsDetail gd = new GoodsDetail();
		gd.setGoodsId("iphone6s_16G");
		gd.setGoodsName("iPhone6s 16G");
		gd.setWxpayGoodsId("1001");
		gd.setQuantity(1);
		gd.setPrice(528800);
		gds.add(gd);
		detail.setGoodsDetail(gds);
		parameter.setOrderDetail(detail);
		PrepayResult result = payService.sendPrepay(parameter);
		System.out.println(result);
	}
	
}
