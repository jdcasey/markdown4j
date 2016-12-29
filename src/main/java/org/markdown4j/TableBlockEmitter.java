package org.markdown4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.rjeschke.txtmark.BlockEmitter;

/**
 * @author https://github.com/piergiuseppe82
 *
 */
public class TableBlockEmitter implements BlockEmitter{

	@Override
	public void emitBlock(StringBuilder out, List<String> lines, String meta) {
		Map<Integer,String> alignment = new HashMap<Integer, String>();  
		StringBuilder myst = new StringBuilder();
		myst.append("<table width=\"100%\">");
		boolean first = true;
		for (String string : lines) {
			myst.append("<tr>");
			String[] split = string.split("\\|");
			Integer count = 0;
			for (String string2 : split) {
				if(string2.isEmpty()) continue;
				count ++;
				if(string2.trim().matches("\\:.*\\:")){
					alignment.put(count, "center"); continue;
				}else if(string2.trim().matches(".*\\:")){
					alignment.put(count, "right"); continue;
				}else if(string2.trim().matches("\\:.*")){
					alignment.put(count, "left"); continue;
				}				
				myst.append(first?"<th calm=\""+count+"\">":"<td calm=\""+count+"\">");
				myst.append(string2);
				myst.append(first?"</th>":"</td>");
				
			}
			myst.append("</tr>");
			first = false;
		}
		myst.append("</table>");
		String string = myst.toString();
		Set<Integer> keySet = alignment.keySet();
		for (Integer integer : keySet) {
			string = string.replaceAll("calm=\""+integer+"\"", "style=\"text-align:"+alignment.get(integer)+"\"");
		}
		out.append(string);
		
	}

}
