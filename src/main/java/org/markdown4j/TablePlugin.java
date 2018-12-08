package org.markdown4j;

import java.util.List;
import java.util.Map;

import com.github.rjeschke.txtmark.BlockEmitter;

public class TablePlugin extends Plugin {

	public TablePlugin() {
		super("table");
	}
	
	@Override
	public void emit(StringBuilder out, List<String> lines,
			Map<String, String> params) {
		BlockEmitter blockemitter = new TableBlockEmitter();
		blockemitter.emitBlock(out, lines, null);
	}

}
