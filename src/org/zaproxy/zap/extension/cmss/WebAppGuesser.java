package org.zaproxy.zap.extension.cmss;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class WebAppGuesser {
  
	private static URL urlToGuess ;
	
	public static ArrayList<String> guessApps(URL url) throws IOException{
		urlToGuess = new URL(url.toString());
		ArrayList<String> possibleApps = new ArrayList<String>();
		Document doc = getFastGuessBDD("fastGuess/fastGuess.xml");  
		Element racine = doc.getRootElement();	
		for(int i=0;i<racine.getChildren().size();i++){
			Element web_app = (Element)racine.getChildren().get(i);
			String app_name = guessApp(web_app.getAttribute("name").getValue(),doc);
			if (app_name.compareTo("")!=0){
				possibleApps.add(app_name);
			}
		}
		return possibleApps;	
	}
	
	
	public static String guessApp(String app_name,Document doc) throws MalformedURLException, IOException{
		Element racine = doc.getRootElement();
		for(int i = 0 ; i<racine.getChildren().size(); i++){
			Element web_app = (Element)racine.getChildren().get(i);
			for(int j=0;j<web_app.getChildren().size();j++){
				String indicFilePath = ((Element)web_app.getChildren().get(j)).getValue();
				fingerPrintFile(indicFilePath, app_name);
			}
		}
		return null;
	}


	
	public static Document getFastGuessBDD (String bddPath){
		Document doc = null;
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(new File(bddPath));
		}
		catch (JDOMException e) {
			e.printStackTrace();	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
	public static void fingerPrintFile(String filePath, String appName) throws MalformedURLException, IOException{
		Document doc = loadFingerPrintingDB(appName2dbPath(appName));
		Element racine = doc.getRootElement();
		for(int i =0;i<racine.getChildren().size();i++){
			Element file = (Element)racine.getChildren().get(i);
			String path = file.getAttributeValue("src");
			if (path.compareTo(filePath)==0){
				System.out.println("path hwo match = "+path);
				
//------->pending TODO here i must introduce accurating 
//options to specify accurating fingerprinting  level
				
				for (int j=0;j<file.getChildren().size();j++){
					Element hashNode = (Element) file.getChildren().get(j);
					String hash = hashNode.getAttributeValue("md5");
					String chksum = 
							CMSFingerprinter.checkUrlContentChecksoms(
									new URL(urlToGuess.toString()+filePath));
					if (hash.compareTo(chksum)==0){
						for(int k= 0 ;k<hashNode.getChildren().size();k++){
							Element versionNode = (Element)hashNode.getChildren().get(k);
							String version= versionNode.getValue();
							System.out.println("		version=="+version);
						}
					}		
				}
			}
			else{
				System.out.println("no");
			}
		}
	}
	
	
	
	public static Document loadFingerPrintingDB(String dbPath){
		Document doc = null;
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(new File(dbPath));
		}
		
		catch (JDOMException e) {
			e.printStackTrace();	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public static String appName2dbPath(String appName){
		/**
		 * here is defined a naming and locating convention for webapps DBs
		 */
		return appName+"/"+appName+".xml";
	}
	
}
