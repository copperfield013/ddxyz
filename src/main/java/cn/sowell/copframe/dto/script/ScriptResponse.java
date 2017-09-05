package cn.sowell.copframe.dto.script;


public class ScriptResponse {
	private String content;
	private String src;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	
	public String getHTML(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script type=\"text/javascript\" ");
		if(content!= null){
			buffer.append(content);
		}
		if(this.src != null){
			buffer.append("src=\"" + src + "\" ");
		}
		buffer.append(">");
		buffer.append(content);
		buffer.append("</script>");
		return buffer.toString();
	}
}
