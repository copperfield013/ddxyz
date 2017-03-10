package cn.sowell.copframe.weixin.pay.prepay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * <p>Title: GoodsDetail</p>
 * <p>Description: </p><p>
 * 注意：单品总金额应<=订单总金额total_fee，否则会无法享受优惠。
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月9日 下午7:13:17
 */
public class GoodsDetail {
	//商品的编号
	@JSONField(name="goods_id")
	private String goodsId;
	
	//微信支付定义的统一商品编号
	@JSONField(name="wxpay_goods_id")
	private String wxpayGoodsId;
	
	//商品名称 
	@JSONField(name="goods_name")
	private String goodsName;
	
	//商品数量
	private Integer quantity;
	//商品单价，如果商户有优惠，需传输商户优惠后的单价 
	private Integer price;
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getWxpayGoodsId() {
		return wxpayGoodsId;
	}
	public void setWxpayGoodsId(String wxpayGoodsId) {
		this.wxpayGoodsId = wxpayGoodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
