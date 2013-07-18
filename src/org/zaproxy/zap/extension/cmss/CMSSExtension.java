package org.zaproxy.zap.extension.cmss;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;


public class CMSSExtension {
  /**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main (String[] args) throws Exception{
		//CMSSBddUtils.pkl2xml();
		//CMSFingerprinter.checkUrlContentChecksoms(new URL("https://weblogs.java.net/blog/kohsuke/archive/2007/04/how_to_convert.html"));
		//CMSFingerprinter.FingerprintFileBE(new URL("http://www.joomla.fr/"));
	    WebAppGuesser.guessApps(new URL("https://weblogs.java.net/blog/kohsuke/archive/2007/04/how_to_convert.html"));
		//Wappalyzer.essaie();
	//--->Wappalyzer.analyse(new URL("http://www.buinzoo.cl/"));
		//Wappalyzer.bddUpdate();
		
	//--->WebPage wp = new WebPage(new URL("https://weblogs.java.net/blog/kohsuke/archive/2007/04/how_to_convert.html"));
		//System.out.println(""+wp.getHeaders());
		//System.out.println(""+wp.getDocument());
		//System.out.println(""+wp.getMetaNodes());
		//System.out.println(""+wp.getScriptNodes());
		//System.out.println(""+wp.getURL());
		//tt marche hmd
	
	   
	}
}
