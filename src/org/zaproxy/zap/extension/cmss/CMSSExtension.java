package org.zaproxy.zap.extension.cmss;

import java.net.URL;
import java.util.ArrayList;


public class CMSSExtension {
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main (String[] args) throws Exception{
		//CMSSBddUtils.pkl2xml();
		//CMSFingerprinter.checkUrlContentChecksoms(new URL("https://weblogs.java.net/blog/kohsuke/archive/2007/04/how_to_convert.html"));
		//WebPage wp = new WebPage(new URL("http://www.joomla.fr/"));
		
		//CMSFingerprinter.FingerprintFileBE(new URL("http://www.joomla.fr/"));
		/*String hash = CMSFingerprinter.checksum("hadi azounio");
		System.out.println(hash);*/


		//WebAppGuesser.checkIfExist(new URL("http://www.joomla.fr"), "/images/joomla_logo_black.jpg");
		//WebAppGuesser.setUrlToGuess(new URL("http://www.lemelies.net/"));		
		ArrayList<String> list = WebAppGuesser.guessApps(new URL("http://www.joomla.org/"));
		for(String app:list){
			System.out.println(":::"+app);
		}
		//WebAppGuesser.fingerPrintFile("joomla");
		
		
		//Wappalyzer.essaie();
		/*ArrayList<String> list = Wappalyzer.analyse(new URL("http://www.joomla.org/"));
		for(String app:list){
			System.out.println(":::"+app);
		}*/
		//Wappalyzer.bddUpdate();
		//PlunginGuesser.getWordpressThemeDB();
		//PlunginGuesser.wordpressComponentLister(new URL("http://cse-club.org/"),"plugin");

	//--->WebPage wp = new WebPage(new URL("https://weblogs.java.net/blog/kohsuke/archive/2007/04/how_to_convert.html"));
		//System.out.println(""+wp.getHeaders());
		//System.out.println(""+wp.getDocument());
		//System.out.println(""+wp.getMetaNodes());
		//System.out.println(""+wp.getScriptNodes());
		//System.out.println(""+wp.getURL());
		//tt marche hmd

	}
}
