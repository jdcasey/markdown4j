package org.markdown4j;

import java.util.List;
import java.util.Map;

/**
 * @author https://github.com/piergiuseppe82
 *
 */
public class JSONCodePlugin extends Plugin {
	private static final String separator = " {}[],:";
	private static final String[] keywords = new String[]{
		
		"true",
		"false",
		"null",
		
	}; 
	

	public JSONCodePlugin() {
		super("json");
	}
	
	@Override
	public void emit(StringBuilder out, List<String> lines,
			Map<String, String> params) {
		if(lines == null) return;
		StringBuilder myst = new StringBuilder();
		myst.append("<div style=\"width=100%; font-family:monospace; background-color: #cccccc; color:black;\">");
		for (String string : lines) {
				String newStr = "";
			 		char[] charArray = string.toCharArray();
			 		String word = "";
			 		boolean isString = false;
			 		for (char c : charArray) {
			 			if((c == '\'' || c == '"' )&& !isString){
			 				isString = true;
			 				word = word +c;
			 			}else if((c == '\'' || c == '"' ) && isString){
				 				isString = false;
				 				word = word +c;	
				 				newStr = newStr + "<span style=\"color:magenta;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>";	
				 				word = "";
			 			}else if( !isString && (separator.contains(""+c))){	
			 				for (String keyword : keywords) {
								if(keyword.equalsIgnoreCase(word)){
									//IS KEYWORD
									word = word.replaceAll(keyword, "<span style=\"color:red;margin:0;padding:0;\">"+replaceHtmlSpecial(keyword)+"</span>");
									newStr = newStr + word;
									word = "";
								}																	
							}	
			 				if(word.toUpperCase().matches("\\d+\\.\\d+")){
					 			//IS DECIMAL
								word = word.toUpperCase().replaceAll("\\d+\\.\\d+", "<span style=\\\"color:black;font-style:bold;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>");
								newStr = newStr + word;	
								word = "";
							}
					 		if(word.toUpperCase().matches("\\d+")){
					 			//IS INTEGER
								word = word.toUpperCase().replaceAll("\\d+", "<span style=\"color:blue;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>");
								newStr = newStr + word;	
								word = "";
							}					 		
					 		if(c == '-' && word.equals("-")){
					 			word = word +c;	
					 		}else if(c != '-'){	
					 			if(!word.isEmpty()){
						 			newStr = newStr + word;
						 			word = "";
						 		}
						 		newStr = newStr + (c==' '?"&#160;":c);
					 		}else{
					 			word = word +c;	
					 		}
						}else{
							word = word +c;							
						}
					}
			 		if(!word.isEmpty()){
			 				//IS KEYWORD
			 				for (String keyword : keywords) {
			 					if(keyword.equalsIgnoreCase(word)){
									word = word.replaceAll(keyword, "<span style=\"color:red;font-style:bold;margin:0;padding:0;\">"+replaceHtmlSpecial(keyword)+"</span>");
									newStr = newStr + word;
									word = "";
								}																
							}
			 				if(word.toUpperCase().matches("\\d+\\.\\d+")){
					 			//IS DECIMAL
								word = word.toUpperCase().replaceAll("\\d+\\.\\d+", "<span style=\"color:blue;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>");
								newStr = newStr + word;	
								word = "";
							}
					 		if(word.toUpperCase().matches("\\d+")){
					 			//IS INTEGER
								word = word.toUpperCase().replaceAll("\\d+", "<span style=\"color:blue;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>");
								newStr = newStr + word;	
								word = "";
							}
			 				newStr = newStr + word;
				 			word = "";
			 			
			 		}
							
				myst.append(newStr);
				myst.append("<br/>");
		}
		myst.append("</div>\n");
		out.append(myst.toString());
	}

	private String replaceHtmlSpecial(String string) {
		if(string == null) return string;
		
		return string.replaceAll("<", "&#060;")
				.replaceAll(">", "&#062;");
	}

}
