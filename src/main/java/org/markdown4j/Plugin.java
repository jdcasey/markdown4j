package org.markdown4j;

import java.util.List;
import java.util.Map;

public abstract class Plugin {
	
	protected String idPlugin;
	
	public Plugin(String idPlugin) {
		this.idPlugin = idPlugin;
	}

	public abstract void emit(final StringBuilder out, final List<String> lines, final Map<String, String> params);

	public String getIdPlugin() {
		return idPlugin;
	}
}
