package org.markdown4j;

import java.util.List;
import java.util.Map;

public class YumlPlugin extends Plugin {

	public YumlPlugin() {
		super("yuml");
	}

	@Override
	public void emit(final StringBuilder out, final List<String> lines, final Map<String, String> params) {
		StringBuffer sb2 = new StringBuffer();
		for(String line : lines) {
			line = line.trim();
			if(line.length() > 0) {
				if(sb2.length() > 0) {
					sb2.append(", ");					
				}
				sb2.append(line);					
			}
		}
		
		String type = params.get("type");
		if(type == null) {
			type = "class";			
		}
		String style = params.get("style");
		if(style == null) {
			style = "scruffy";			
		}
		String dir = params.get("dir");
		if(dir != null) {
			style = style+";dir:"+dir+";";			
		}
		String scale = params.get("scale");
		if(scale != null) {
			style = style+";scale:"+scale+";";			
		}
		String format = params.get("format");
				
		out.append("<img src=\"http://yuml.me/diagram/"+style+"/"+type+"/");
		out.append(sb2.toString());
		if(format != null) {
			out.append("."+format);		
		}
		out.append("\"/>");
	}

}
