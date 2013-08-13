package org.zaproxy.zap.extension.CMSS;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FastFingerprinter {
	
	
	// basé sur ODZ et similaires 
	
			// je pense qu'on doit mettre cela aussi modulaire , c la meilleur solution 
			public static void JoomlaFastFingerprint(URL url){
				// on se base sur ODZscanner
				WebPage wp = null;
				try {
					 wp = new WebPage(url);
					 org.jsoup.nodes.Document doc = wp.getDocument();
					 wp = new WebPage(new URL(url.toString()+"/index.php?option=com_esi"));
					 org.jsoup.nodes.Document doc2 = wp.getDocument();
					 wp = new WebPage(new URL(url.toString()+"/README.txt"));
					 org.jsoup.nodes.Document rdm = wp.getDocument();
					 wp = new WebPage(new URL(url.toString()+"/htaccess.txt"));
					 org.jsoup.nodes.Document htacc = wp.getDocument();
					 wp = new WebPage(new URL(url.toString()+"/configuration.php-dist"));
					 org.jsoup.nodes.Document dist = wp.getDocument();
					 
					 Pattern p = null, p2 = null;
	 				 p = Pattern.compile("<\\/html> <!-- \\d{1,30} -->");
	 				 p2 = Pattern.compile("The page you are trying to access does not exist");
	 				 Matcher m = p.matcher(doc.toString()), 
	 						 m2 = p.matcher(doc2.toString());
	 				 
	 				 if (m.find() || m2.find() 
	 				 		|| WebAppGuesser.checkIfExist(new URL(url.toString()+"/language/english.xml"))
	 				 		|| WebAppGuesser.checkIfExist(new URL(url.toString()+"/administrator/templates/joomla_admin/images/security.png")))
	 				 	System.out.println("1.0.x");
	 				 
	 				 p = Pattern.compile(" Joomla! 1.5 - Open Source Content Management");
	 				 p2 = Pattern.compile("404- Component not found");
	 				 
	 				 m = p.matcher(doc.toString());
	 				 m2 = p.matcher(doc2.toString());
	 			   	 if (m.find() || m2.find() 
	 				 		|| WebAppGuesser.checkIfExist(new URL(url.toString()+"/administrator/templates/khepri/images/j_login_lock.jpg"))
	 				 		|| WebAppGuesser.checkIfExist(new URL(url.toString()+"/administrator/templates/khepri/images/j_button1_next.png")))
	 				 	System.out.println("1.5.x");
	 			   	 
	 			   	 
	 			   	 p = Pattern.compile("package to version 3.0.x");
	 			   	 m = p.matcher(doc.toString());
	 			   	if (m.find() || WebAppGuesser.checkIfExist(new URL(url.toString()+
	 			   			"/administrator/templates/isis/img/glyphicons-halflings.png")))
	 			   	    System.out.println("3.0.x");
	 			   	
	 			   	 if(searchByRegex("47 2005-09-15 02:55:27Z rhuk", htacc.toString()))
	 			   		 System.out.println("htaccess.txt revealed [1.0.0 - 1.0.2]");
	 			   	 
	 			   	if(searchByRegex("423 2005-10-09 18:23:50Z stingrey", htacc.toString()))
				   		 System.out.println("htaccess.txt revealed 1.0.3");
	 			   	
	 			   	if(searchByRegex("1005 2005-11-13 17:33:59Z stingrey", htacc.toString()))
				   		 System.out.println("htaccess.txt revealed [1.0.4 - 1.0.5]");
	 			   
	 			   	if(searchByRegex("1570 2005-12-29 05:53:33Z eddieajau", htacc.toString()))
				   		 System.out.println("htaccess.txt revealed [1.0.6 - 1.0.7]");
	 			  
	 			   	if(searchByRegex("2368 2006-02-14 17:40:02Z stingrey", htacc.toString()))
				   		 System.out.println("htaccess.txt revealed [1.0.8 - 1.0.9]");
	 			 
	 			   	if(searchByRegex("44085 2006-06-21 16:03:54Z stingrey7 2005-09-15 02:55:27Z rhuk", htacc.toString()))
			   		 System.out.println("htaccess.txt revealed 1.0.10");
	 			
	 				if(searchByRegex("4756 2006-08-25 16:07:11Z stingrey", htacc.toString()))
			   		 System.out.println("htaccess.txt revealed 1.0.11");
	 			
	 				if(searchByRegex("5973 2006-12-11 01:26:33Z robs", htacc.toString()))
	 					System.out.println("htaccess.txt revealed 1.0.12");
	 			
	 				if(searchByRegex("5975 2006-12-11 01:26:33Z robs", htacc.toString()))
	 					System.out.println("htaccess.txt revealed [1.0.13 - 1.0.15]");
	 			
	 				if(searchByRegex("47 2005-09-15 02:55:27Z rhuk", dist.toString()))
	 					System.out.println("configuration.php-dist revealed 1.0.0");
	 			
	 				if(searchByRegex("217 2005-09-21 15:15:58Z stingrey", dist.toString()))
	 					System.out.println("configuration.php-dist revealed [1.0.1 - 1.0.2]");
	 			
	 				if(searchByRegex("506 2005-10-13 05:49:24Z stingrey", dist.toString()))
	 					System.out.println("configuration.php-dist revealed [1.0.3 - 1.0.7]");
	 			   	 
	 				if(searchByRegex("2622 2006-02-26 04:16:09Z stingrey", dist.toString()))
	 					System.out.println("configuration.php-dist revealed 1.0.8");
	 			
	 				if(searchByRegex("3754 2006-05-31 12:08:37Z stingrey", dist.toString()))
	 					System.out.println("configuration.php-dist revealed [1.0.9 - 1.0.10]");
	 			   	 
				} catch (IOException | PatternSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		
			public static boolean searchByRegex(String regex, String str){
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(str);
				if (m.find()) return true;
				return false;
			}
			

	// cette methode prend les resultats des deux methodes : wapalyzer et guessWebApp.fastguess et
	// combine et affiche le resultat 
	public static ArrayList<String> filterResults(ArrayList<String> whatToFingerPrint,int POrAOption) throws Exception{
		
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> wapGessed = new ArrayList<String>();
		ArrayList<String> blindGuessed = new ArrayList<String>();
		if(POrAOption == 1 || POrAOption==3){
			wapGessed = Wappalyzer.analyse(new URL("http://www.joomla.org/"),whatToFingerPrint);
			for (String app : wapGessed){
				result.add(app);
			}
			if(POrAOption==3){
				blindGuessed = WebAppGuesser.guessApps(new URL("http://www.joomla.org/"));
				for (String app : blindGuessed){
					result.add(app);
				}
			}
		}else{
			blindGuessed = WebAppGuesser.guessApps(new URL("http://www.joomla.org/"));
			for (String app : blindGuessed){
				result.add(app);
			}	
		}
		
		// pour le moment on fait que concatener les resultats
		System.out.println("fin");
		for (String app : wapGessed){
			result.add(app);
		}
		return result;
	}
	
	
}
