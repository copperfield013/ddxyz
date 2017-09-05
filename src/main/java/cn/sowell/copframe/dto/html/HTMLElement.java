package cn.sowell.copframe.dto.html;

public interface HTMLElement {
	/**
	 * 
	 * @param attrName
	 * @return
	 */
	String attr(String attrName);
	/**
	 * 
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	HTMLElement attr(String attrName, String attrValue);
	/**
	 * 
	 * @return
	 */
	String text();
	/**
	 * 
	 * @param text
	 * @return
	 */
	HTMLElement text(String text);
	
	/**
	 * 
	 * @param express
	 * @return
	 */
	HTMLElementGroup find(String express);
	
}
