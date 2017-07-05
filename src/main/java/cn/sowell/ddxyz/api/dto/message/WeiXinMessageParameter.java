package cn.sowell.ddxyz.api.dto.message;

/**
 * 
 * <p>Title: WeiXinMessageParameter</p>
 * <p>Description: </p><p>
 * 微信消息推送到指定url的参数，不包括body
 * </p>
 * @author Copperfield Zhang
 * @date 2017年7月4日 下午5:10:58
 */
public class WeiXinMessageParameter {
	private String signature;
	private String timestamp;
	private String nonce;
	private String openid;
	private String encrypt_type;
	private String msg_signature;
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getEncrypt_type() {
		return encrypt_type;
	}
	public void setEncrypt_type(String encrypt_type) {
		this.encrypt_type = encrypt_type;
	}
	public String getMsg_signature() {
		return msg_signature;
	}
	public void setMsg_signature(String msg_signature) {
		this.msg_signature = msg_signature;
	}
}
