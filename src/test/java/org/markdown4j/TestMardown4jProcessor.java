package org.markdown4j;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.github.rjeschke.txtmark.test.MarkupFileTester;

public class TestMardown4jProcessor {
	
	@Test
	public void test() throws IOException {
		Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
		markdown4jProcessor = markdown4jProcessor.registerPlugins(new TablePlugin());
		URL fileUrl = MarkupFileTester.class.getResource( "/table.txt" );
        FileReader file = new FileReader( fileUrl.getFile() );
		String process = markdown4jProcessor.process(file);
		System.out.println(process);

	}

}
