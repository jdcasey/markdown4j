package org.markdown4j;

import java.util.List;
import java.util.Map;

/**
 * @author https://github.com/piergiuseppe82
 *
 */
public class SqlCodePlugin extends Plugin {
	private static final String separator = " =;(),+-/\\><!%*";
	private static final String[] keywords = new String[]{
		//FIXME KEYWORDS LIST IS NOT COMPLETE!!!!!!
		"TIMESTAMP",
		"VARCHAR2",
		"VARCHAR",
		"PRIMARY",
		"COMMENT",
		"COLUMN",
		"CREATE",
		"TABLE",
		"INTO",
		"ON",
		"KEY",
		"NOT",
		"NULL",
		"CONSTRAINT",
		"BYTE",
		"NUMBER",
		"NUMERIC",
		"FLOAT",
		"DECIMAL",
		"SMALLINT",
		"SMALLINT",
		"BLOB",
		"CLOB",
		"DATE",
		"FOREIGN",
		"BOOLEAN",
		"LONG",
		"INTEGER",
		"INT",
		"VARCHAR",
		"SELECT",
		"INSERT",
		"UPDATE",
		"DELETE",
		"SET",
		"ALTER",
		"JOIN",
		"LEFT",
		"RIGHT",
		"INNER",
		"OUTHER",
		"USING",
		"REFRENCE",
		"FROM",
		"ORDER",
		"BY",
		"DISTINCT",
		"SELECT",
		"EXIST",
		"GROUP",
		"IS",
		"WHERE",
		"AND",		
		"VALUES",
		"CHAR",
		"SMALL",
		"UNION",
		"DROP",
		"OR",
		"REPLACE",
		"SYNONYM",
		"FOR",
		"IF",
		"SYSDATE",
		"GRANT",
		"INDEX",
		"TO",
		"LIKE",
		"BEETWIN"		
	}; 
	

	public SqlCodePlugin() {
		super("sql");
	}
	
	@Override
	public void emit(StringBuilder out, List<String> lines,
			Map<String, String> params) {
		if(lines == null) return;
		StringBuilder myst = new StringBuilder();
		myst.append("<div style=\"width=100%; font-family:monospace; background-color: #cccccc; color:black;\">");
		boolean comment = false;
		for (String string : lines) {
				String newStr = "";
			 	if(string.trim().startsWith("--") || string.trim().startsWith("#")){
			 		//IS COMMENT
			 		newStr = "<span style=\"color:green;margin:0;padding:0;\">"+replaceHtmlSpecial(string)+"</span>";
			 	}else if(string.trim().startsWith("/*")){
			 		//IS COMMENT
			 		comment = true;
			 		newStr = "<span style=\"color:green;margin:0;padding:0;\">"+replaceHtmlSpecial(string)+"</span>";
			 	}else if(string.trim().endsWith("*/")){
			 		//IS COMMENT
			 		comment = false;
			 		newStr = "<span style=\"color:green;margin:0;padding:0;\">"+replaceHtmlSpecial(string)+"</span>";
			 	}else if(comment){
			 		//IS COMMENT
			 		newStr = "<span style=\"color:green;margin:0;padding:0;\">"+replaceHtmlSpecial(string)+"</span>";
			 	}else{
			 		char[] charArray = string.toCharArray();
			 		String word = "";
			 		boolean isString = false;
			 		boolean isInlineComment = false;
			 		for (char c : charArray) {
			 			if(c == '\'' && !isString && !isInlineComment){
			 				isString = true;
			 				word = word +c;
			 			}else if(c == '\'' && isString && !isInlineComment){
				 				isString = false;
				 				word = word +c;	
				 				newStr = newStr + "<span style=\"color:magenta;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>";	
				 				word = "";
			 			}else if(!isInlineComment && !isString && (separator.contains(""+c))){	
			 				for (String keyword : keywords) {
								if(word.toUpperCase().matches(keyword)){
									//IS KEYWORD
									word = word.toUpperCase().replaceAll(keyword, "<span style=\"color:red;margin:0;padding:0;\">"+replaceHtmlSpecial(keyword)+"</span>");
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
					 		if(c == '-' && word.equals("-")){
					 			isInlineComment = true;
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
							if(!isInlineComment && !isString && (word.startsWith("--") || word.startsWith("#"))){
								isInlineComment = true;
							}
						}
					}
			 		//END OF LINE
			 		if(!word.isEmpty()){
			 			if(isInlineComment){
			 				//IS COMMENT
			 				newStr = newStr + "<span style=\"color:green;margin:0;padding:0;\">"+replaceHtmlSpecial(word)+"</span>";
			 				word = "";
			 			}else{
			 				//IS KEYWORD
			 				for (String keyword : keywords) {
								if(word.toUpperCase().matches(keyword)){
									word = word.toUpperCase().replaceAll(keyword, "<span style=\"color:red;margin:0;padding:0;\">"+replaceHtmlSpecial(keyword)+"</span>");
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
			 			
			 		}
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
