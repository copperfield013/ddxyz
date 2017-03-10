package cn.sowell.copframe.weixin.pay.prepay;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * <p>Title: OrderDetail</p>
 * <p>Description: </p><p>
 * 微信支付统一接口的detail参数
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月9日 下午9:16:56
 */
public class OrderDetail {
	//可选。商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的支付金额。
	//当订单原价与支付金额不相等则被判定为拆单，无法享受优惠。
	@JSONField(name="cost_price")
	private Integer costPrice;
	
	//可选。商家小票ID
	@JSONField(name="receipt_id")
	private String receiptId;
	
	//必填。商品
	@JSONField(name="goods_detail")
	private List<GoodsDetail> goodsDetail;
	
	
	public Integer getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public List<GoodsDetail> getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(List<GoodsDetail> goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
