package org.markdown4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlAttributes {
  private Map<String, Map<String, String>> attsByTag = new HashMap<String, Map<String, String>>();
  
  
  public HtmlAttributes put(String tagName, String attName, String attValue) {
	  Map<String, String> atts = attsByTag.get(tagName);
	  if(atts == null) {
		  atts = new LinkedHashMap<String, String>();
		  attsByTag.put(tagName, atts);
	  }
	  atts.put(attName, attValue);
	  return this;
  }
  
  public Map<String, String> get(String tagName) {
	  return attsByTag.get(tagName);
  }
}
