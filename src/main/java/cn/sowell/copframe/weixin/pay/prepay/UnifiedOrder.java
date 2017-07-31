package cn.sowell.copframe.weixin.pay.prepay;

import cn.sowell.copframe.utils.xml.XMLTag;


/**
 * 
 * <p>Title: UnifiedOrder</p>
 * <p>Description: </p><p>
 * 微信支付统一接口预付款订单请求对象
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月9日 下午9:13:57
 */
public class UnifiedOrder implements SignSetable{
	//$微信支付分配的公众账号ID（企业号corpid即为此appId）
	@XMLTag(required=true, lengthLimit=32)
	private String appid;
	
	//$微信支付分配的商户号
	@XMLTag(tagName="mch_id", required=true, lengthLimit=32)
	private String merchantId;
	
	//自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	@XMLTag(tagName="device_info", lengthLimit=32)
	private String deviceInfo;
	
	//$随机字符串，长度要求在32位以内。推荐随机数生成算法
	@XMLTag(tagName="nonce_str", required=true, lengthLimit=32)
	private String nonceStr;
	
	//$通过签名算法计算得出的签名值，详见签名生成算法
	@XMLTag(required=true, lengthLimit=32)
	private String sign;
	
	//签名类型，默认为MD5，支持HMAC-SHA256和MD5。
	@XMLTag(tagName="sign_type", lengthLimit=32)
	private String signType = "MD5";
	
	//$商品简单描述，该字段请按照规范传递，具体请见参数规定(腾讯充值中心-QQ会员充值)
	@XMLTag(required=true, lengthLimit=128)
	private String body;
	
	//商品详情
	@XMLTag(lengthLimit=6000)
	private OrderDetail detail;
	
	//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
	@XMLTag(lengthLimit=127)
	private String attach;
	
	//商户系统内部订单号，要求32个字符内、且在同一个商户号下唯一。 详见商户订单号
	@XMLTag(tagName="out_trade_no", required=true, lengthLimit=32)
	private String outTradeNo;
	
	//标记币种。符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
	@XMLTag(tagName="fee_type", lengthLimit=16)
	private String feeType = "CNY";
	
	//标价金额。订单总金额，单位为分，详见支付金额
	@XMLTag(tagName="total_fee")
	private Integer totalFee;
	
	//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	@XMLTag(tagName="spbill_create_ip", lengthLimit=16)
	private String spbillCreateIp;
	
	//交易起始时间。订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	@XMLTag(tagName="time_start", lengthLimit=14)
	private String timeStart;
	
	//交易结束时间。订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	@XMLTag(tagName="time_expire", lengthLimit=14)
	private String timeExpire;
	
	//商品标记。商品标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
	@XMLTag(tagName="goods_tag", lengthLimit=32)
	private String goodsTag;
	
	//通知地址。异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	@XMLTag(tagName="notify_url",required=true, lengthLimit=256)
	private String notifyURL;
	
	//取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
	@XMLTag(tagName="trade_type", required=true, lengthLimit=16)
	private TradeType tradeType = TradeType.JSAPI;
	
	//商品ID。trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	@XMLTag(tagName="product_id", lengthLimit=32)
	private String productId;
	
	//指定支付方式。上传此参数no_credit--可限制用户不能使用信用卡支付
	@XMLTag(tagName="limit_pay", lengthLimit=32)
	private String limitPay;
	
	//trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	@XMLTag(lengthLimit=128)
	private String openid;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public OrderDetail getDetail() {
		return detail;
	}

	public void setDetail(OrderDetail detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
