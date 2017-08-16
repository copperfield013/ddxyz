package cn.sowell.copframe.weixin.common.service;

import java.util.Map;

import cn.sowell.copframe.utils.xml.XmlNode;
import cn.sowell.copframe.weixin.config.WxAppReadOnly;

public interface WxConfigService {
	
	/**
	 * 获得公众号的信息数据
	 * @return
	 */
	WxAppReadOnly getWxApp();
	
	/**
	 * 获得公众号的微信号
	 * @return
	 */
	String getAppWxAccount();
	/**
	 * 配置公众号时的唯一标识
	 * @return
	 */
	String getAppKey();
	
	/**
	 * 公众号的名称
	 * @return
	 */
	String getAppName();
	
	/**
	 * 微信平台分配的公众号的id
	 * @return
	 */
	String getAppid();
	
	/**
	 * 获得全局唯一的秘钥
	 * @return
	 */
	String getSecret();
	
	/**
	 * 微信支付秘钥（key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置）
	 * @return
	 */
	String getWxPayKey();
	
	/**
	 * 微信签名生成算法。获得的签名全部是大写
	 * @param key 秘钥
	 * @param parameters 要生成签名的各个参数
	 * @return
	 */
	String getMd5Signature(String key, Map<String, String> parameters);
	/**
	 * 检查xml的签名合法性
	 * 如果xml中传入了sign标签，那么清除，并且以signature参数为准。
	 * 如果参数sinature为null，那么用sign标签的值来验证
	 * <b>注意区分大小写，验证的签名应该全部是大写</b>
	 * @param signature
	 * @param xml
	 * @return
	 */
	boolean checkSignature(String signature, XmlNode xml);
	
	/**
	 * 微信商户号
	 * @return
	 */
	String getMerchantId();
	/**
	 * 微信支付的通知地址
	 * @return
	 */
	String getPayNotifyURL();
	/**
	 * 获得项目所在地址
	 * @return
	 */
	String getProjectURL();

	/**
	 * 获得当前的时间戳，单位是秒
	 * @return
	 */
	long getCurrentTimestamp();
	/**
	 * 计算签名，以sha1方式加密
	 * @param signParam
	 * @return
	 */
	String getSha1Signature(Map<String, String> signParam);
	
	/**
	 * 当前系统是否是处于调试模式
	 * @return
	 */
	boolean isDebug();
	/**
	 * 获得商户的微信支付证书路径
	 * @return
	 */
	String getMerchantPKCS12FilePath();
	
	/**
	 * 获得微信消息的
	 * @return
	 */
	String getMsgToken();
	
	/**
	 * 
	 * @return
	 */
	String getMsgEncodingAESKey();
	/**
	 * 获得当前公众号的默认微信用户id
	 * @return
	 */
	Long getDebugUserId();

}
