package org.markdown4j;

import java.util.Iterator;
import java.util.Map;

import com.github.rjeschke.txtmark.DefaultDecorator;

public class ExtDecorator extends DefaultDecorator {
	
	private HtmlAttributes attributes = new HtmlAttributes();
	
	public ExtDecorator addHtmlAttribute(String name, String value, String ...tags) {
		for(String tag : tags)
		attributes.put(tag, name, value);
		return this;
	}
	public ExtDecorator addStyleClass(String styleClass, String ...tags) {
		for(String tag : tags)
		attributes.put(tag, "class", styleClass);
		return this;
	}
	public ExtDecorator useCompactStyle() {
		attributes.put("p", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("a", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h1", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h2", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h3", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h4", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h5", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("h6", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("ul", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("ol", "style", "font-size:100%; padding:0px; margin:0px;");
		attributes.put("li", "style", "font-size:100%; padding:0px; margin:0px;");
		return this;
	}
	private boolean open(StringBuilder out, String tagName) {
		return open(out, tagName, true);
	}
	private boolean open(StringBuilder out, String tagName, boolean closed) {
		Map<String, String> atts = attributes.get(tagName);
		if(atts != null) {
			out.append("<");
			out.append(tagName);
			Iterator<String> it = atts.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				String value = atts.get(key);
				out.append(" ");
				out.append(key);
				out.append("=\"");
				out.append(value);
				out.append("\"");
				out.append(" ");
			}
			if(closed) {
				out.append(">");				
			}
			return true;
		}
		return false;
	}

	@Override
	public void openParagraph(StringBuilder out) {
		if(!open(out, "p"))
		super.openParagraph(out);
	}

	@Override
	public void openBlockquote(StringBuilder out) {
		if(!open(out, "blockquote"))
		super.openBlockquote(out);
	}

	@Override
	public void openCodeBlock(StringBuilder out) {
		if(!open(out, "pre"))
		super.openCodeBlock(out);
	}

	@Override
	public void openCodeSpan(StringBuilder out) {
		if(!open(out, "code"))
		super.openCodeSpan(out);
	}

	@Override
	public void openHeadline(StringBuilder out, int level) {
		if(!open(out, "h"+level, false))
		super.openHeadline(out, level);
	}

	@Override
	public void openStrong(StringBuilder out) {
		if(!open(out, "strong"))
		super.openStrong(out);
	}

	@Override
	public void openStrike(StringBuilder out) {
		if(!open(out, "s"))
		super.openStrike(out);
	}

	@Override
	public void openEmphasis(StringBuilder out) {
		if(!open(out, "em"))
		super.openEmphasis(out);
	}

	@Override
	public void openSuper(StringBuilder out) {
		if(!open(out, "super"))
		super.openSuper(out);
	}

	@Override
	public void openOrderedList(StringBuilder out) {
		if(!open(out, "ol"))
		super.openOrderedList(out);
	}

	@Override
	public void openUnorderedList(StringBuilder out) {
		if(!open(out, "ul"))
		super.openUnorderedList(out);
	}

	@Override
	public void openListItem(StringBuilder out) {
		if(!open(out, "li", false))
		super.openListItem(out);
	}

	@Override
	public void horizontalRuler(StringBuilder out) {
		if(open(out, "hr", false)) {
			out.append("/>");
		}
		else {
			super.horizontalRuler(out);
		}
	}

	@Override
	public void openLink(StringBuilder out) {
		if(!open(out, "a", false))
		super.openLink(out);
	}

	@Override
	public void openImage(StringBuilder out) {
		if(!open(out, "img", false))
		super.openImage(out);
	}
	
	

//	@Override
//	public void openLink(StringBuilder out) {
//		out.append("<a target=\"_blank\" ");
//	}
//
//	@Override
//	public void openBlockquote(StringBuilder out) {
//		out.append("<blockquote style=\"color:red;\">");
//	}
//
//	@Override
//	public void openParagraph(StringBuilder out) {
//		if(useCompactStyle) {
//          out.append("<p style=\"font-size:100%; padding:0px; margin:0px;\">");
//		}
//		else {
//			super.openParagraph(out);
//		}
//	}
//
//	@Override
//	public void openHeadline(StringBuilder out, int level) {
//		super.openHeadline(out, level);
//		if(useCompactStyle) {
//	        out.append(" style=\"font-size:100%; padding:0px; margin:0px;\"");			
//		}
//	}
//
//	@Override
//	public void openOrderedList(StringBuilder out) {
//		if(useCompactStyle) {
//	        openParagraph(out);
//		}
//		else {
//			super.openOrderedList(out);
//		}
//	}
//
//	@Override
//	public void openUnorderedList(StringBuilder out) {
//		if(useCompactStyle) {
//	        openParagraph(out);
//		}
//		else {
//			super.openUnorderedList(out);
//		}
//	}
//
//	@Override
//	public void openListItem(StringBuilder out) {
//		if(useCompactStyle) {
//			openParagraph(out);
//			out.append(" * ");
//		}
//		else {
//			super.openListItem(out);			
//		}
//	}
//
//	@Override
//	public void closeOrderedList(StringBuilder out) {
//		if(useCompactStyle) {
//	        closeParagraph(out);
//		}
//		else {
//			super.closeOrderedList(out);
//		}
//	}
//
//	@Override
//	public void closeUnorderedList(StringBuilder out) {
//		if(useCompactStyle) {
//	        closeParagraph(out);
//		}
//		else {
//			super.closeUnorderedList(out);
//		}
//	}
//
//	@Override
//	public void closeListItem(StringBuilder out) {
//		if(useCompactStyle) {
//	        closeParagraph(out);
//		}
//		else {
//			super.closeListItem(out);
//		}
//	}
//
}
