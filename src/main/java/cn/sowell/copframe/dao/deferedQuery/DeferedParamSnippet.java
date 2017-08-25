package cn.sowell.copframe.dao.deferedQuery;

import java.util.Stack;


public class DeferedParamSnippet {
	private String snippetName;
	private StringBuffer buffer = new StringBuffer();
	private String prependWhenNotEmpty;
	private Stack<String[]> wrapStack = new Stack<String[]>();
	
	DeferedParamSnippet(String snippetName) {
		this.snippetName = snippetName;
	}

	DeferedParamSnippet(String snippetName, String snippet) {
		this.snippetName = snippetName;
		this.buffer.append(snippet);
	}
	
	public String getName(){
		return snippetName;
	}
	
	public String getSnippet(){
		StringBuffer temp = new StringBuffer(buffer.toString());
		@SuppressWarnings("unchecked")
		Stack<String[]> stack = (Stack<String[]>) wrapStack.clone(); 
		while(!stack.isEmpty()){
			String[] wrap = wrapStack.pop();
			if(wrap[0] != null){
				temp.insert(0, wrap[0]);
			}
			if(wrap[1] != null){
				temp.append(wrap[1]);
			}
		}
		return temp.toString();
	}

	public boolean isEmpty(){
		return buffer.length() == 0 && wrapStack.isEmpty();
	}
	
	/**
	 * 添加片段中的某一部分，并且该部分前后自动加上空格
	 * @param part
	 */
	public void append(String part) {
		appendWithoutPadding(" " + part + " ");
	}
	
	/**
	 * 添加片段中的某一部分
	 * @param part
	 * @return
	 */
	public DeferedParamSnippet appendWithoutPadding(String part){
		if(part != null){
			buffer.append(part);
		}
		return this;
	}

	public String getPrependWhenNotEmpty() {
		return prependWhenNotEmpty;
	}

	public void setPrependWhenNotEmpty(String prependWhenNotEmpty) {
		this.prependWhenNotEmpty = prependWhenNotEmpty;
	}
	
	public void trimBefore(String trimStr){
		if(trimStr != null && !trimStr.isEmpty()){
			int index = buffer.indexOf(trimStr);
			if(index == 0){
				buffer.substring(trimStr.length());
			}
		}
	}
	
	/**
	 * 语句外围包裹一层
	 * @param prefix
	 * @param suffix
	 */
	public void pushWrap(String prefix, String suffix){
		String[] wrap = new String[2];
		wrap[0] = prefix;
		wrap[1] = suffix;
		wrapStack.push(wrap);
	}
	
	/**
	 * 移除最后pushWrap
	 * @return
	 */
	public String[] popWrap(){
		return wrapStack.pop();
	}
	
}
