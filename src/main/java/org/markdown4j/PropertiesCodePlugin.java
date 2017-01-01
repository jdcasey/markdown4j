package org.markdown4j;

import java.util.List;
import java.util.Map;

public class PropertiesCodePlugin extends Plugin {

	public PropertiesCodePlugin() {
		super("properties");
	}
	
	@Override
	public void emit(StringBuilder out, List<String> lines,
			Map<String, String> params) {
		if(lines == null) return;
		StringBuilder myst = new StringBuilder();
		myst.append("<div style=\"width=100%; font-family:monospace; background-color: #cccccc; color:black;\">");
		for (String string : lines) {
			if(string.startsWith("#")){
				myst.append("<p style=\"color:green;margin:0;padding:0;\">"+string+"</p>");
			}else if(string.contains("=")){
				int indexOf = string.indexOf("=");
				indexOf++;
				myst.append("<p style=\"margin:0;padding:0;\">"+string.substring(0, indexOf)+"<span style=\"color:blue;\">"+string.substring(indexOf)+"</span></p>");
			}else if(string.trim().isEmpty()){
				myst.append("<br/>");
			}else{
				myst.append("<p style=\"margin:0;padding:0;\">"+string+"</p>");
			}
		}
		myst.append("</div>\n");
		out.append(myst.toString());
	}

}
