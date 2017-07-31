package cn.sowell.copframe.dto.xml;

import cn.sowell.copframe.utils.xml.XmlNode;

public class XMLRequest {
	private final XmlNode root;
	public XMLRequest(XmlNode root) {
		this.root = root;
	}
	public XmlNode getRoot() {
		return root;
	}
	
	public String getElementValue(String tagName){
		XmlNode node = root.getFirstElement(tagName);
		return node.getText();
	}
	
	@Override
	public String toString() {
		return root.asXML();
	}
	
}
