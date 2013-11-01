package com.github.rjeschke.txtmark.test;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

public class MyTxtMarkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s;
		try {
//			s = Markdown4jProcessor.process("Heading 1\n==================\n\nHeading 2\n---------------------\ntoto\ntata \n~~~\n if(a < 10) { do ; } \n my code 2 \n~~~\n  > my comment\n\ntata <http://www.liberation.fr> [[@ 15 rue de la paix 75010 paris ]] titi\n``` javascript\ntotototo\ntatatata\ntitititi\n```\ntata  \n\n~~~ haskell\ntotototo\ntatatata\ntitititi\n~~~~~\n%%% yuml style=nofunky scale=120 format=svg\ntoto\ntata\ntiti\n%%%\ntiti"); //, Configuration.builder().forceExtentedProfile().convertNewline2Br().setSpecialLinkEmitter(new ExtEmitter(new ExtDecorator())).setDecorator(new ExtDecorator()).setCodeBlockEmitter(new CodeBlockEmitter()).build());
//			System.err.println(s);
			s = new Markdown4jProcessor().addHtmlAttribute("target", "_blank", "a").addHtmlAttribute("style", "color:red", "p", "h1").process("My First Markdown\n=====\n%%% yuml \n toto \n tata \n titi \n%%%\n <http://www.liberation.fr> <@15 rue de la paix 75010 paris>");
			System.err.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
