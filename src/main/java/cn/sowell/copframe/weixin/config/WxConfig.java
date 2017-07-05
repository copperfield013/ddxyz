package cn.sowell.copframe.weixin.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

import cn.sowell.copframe.xml.Dom4jNode;
import cn.sowell.copframe.xml.XmlNode;

public class WxConfig {
	private XmlNode node;
	private Map<String, WxApp> appMap = new HashMap<String, WxApp>();
	public WxConfig(Resource file) throws DocumentException, IOException {
		init(file);
	}
	
	private void init(Resource file) throws DocumentException, IOException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(file.getInputStream());
		node = new Dom4jNode(document.getRootElement());
		node.getElements("app").forEach(appTag->{
			WxApp app = new WxApp();
			app.setId(appTag.getStrictAttribute("id"));
			app.setCname(appTag.getAttribute("cname"));
			app.setWxcount(appTag.getStrictFirstElement("wxcount").getText());
			app.setAppid(appTag.getStrictFirstElement("appid").getText());
			app.setSecret(appTag.getStrictFirstElement("secret").getText());
			XmlNode merchantNode = appTag.getFirstElement("merchant");
			if(merchantNode != null){
				app.setMerchantId(merchantNode.getStrictFirstElement("id").getStrictText());
				app.setPayKey(merchantNode.getStrictFirstElement("pay-key").getStrictText());
				app.setPkcs12FilePath(merchantNode.getStrictFirstElement("cert").getStrictFirstElement("pkcs12").getStrictText());
			}
			XmlNode msgNode = appTag.getFirstElement("msg");
			if(msgNode != null){
				app.getMsgConfig().setToken(msgNode.getStrictFirstElement("token").getStrictText());
				app.getMsgConfig().setEncodingAESKey(msgNode.getStrictFirstElement("aesKey").getStrictText());
			}
			appMap.put(app.getId(), app);
		});
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public WxApp getApp(String id){
		return appMap.get(id);
	}
	
}
