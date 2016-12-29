package org.markdown4j;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

import com.github.rjeschke.txtmark.test.MarkupFileTester;
/**
 * @author https://github.com/piergiuseppe82
 *
 */
public class TestMardown4jProcessor {
	
	private static final String expected = 
			"<table width=\"100%\"><tr><th style=\"text-align:left\"> HEADER 1 </th><th style=\"text-align:center\"> HEADER 2 </th><th style=\"text-align:right\"> HEADER 3 </th></tr><tr></tr><tr><td style=\"text-align:left\">content 1 </td><td style=\"text-align:center\"> content 2</td><td style=\"text-align:right\">content 3 </td></tr></table>";

	@Test
	public void testTable() throws IOException {
		Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
		URL fileUrl = MarkupFileTester.class.getResource( "/table.txt" );
        FileReader file = new FileReader( fileUrl.getFile() );
		String process = markdown4jProcessor.process(file);
		System.out.println(process);
		Assert.assertEquals(expected, process);

	}
	
	@Test
	public void testTableAsPlugin() throws IOException {
		Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
		markdown4jProcessor = markdown4jProcessor.registerPlugins(new TablePlugin());
		URL fileUrl = MarkupFileTester.class.getResource( "/tableAsPlugin.txt" );
        FileReader file = new FileReader( fileUrl.getFile() );
		String process = markdown4jProcessor.process(file);
		System.out.println(process);
		Assert.assertEquals(expected, process);
	}

}
