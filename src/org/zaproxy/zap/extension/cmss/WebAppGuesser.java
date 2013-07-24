package org.zaproxy.zap.extension.cmss;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class WebAppGuesser {
	
	
	
	
	/**
	 *************** TODO method checkIfExist ---> to support : url+/blabla/+lien
	 */
	
	

	private static URL urlToGuess ;
	
	
	/**
	 * path to the fast guessing used file
	 * the fast guessing consists on use a number of detector files (those of BlinElephant)
	 * to check the name of the webapp, and not the version 
	 */
	private static String fastAppGuessBD = "fastGuess/fastGuess.xml"; 
	
	
	
	public static void setUrlToGuess(URL url){
		urlToGuess = url;
	}
		
		
	/**
	* this function return a list of possible version according to the presence or not 
	* of indicator files 
	* 
	* TODO : implement analyze of HTTP response and compare with 404 model file
	* 
	* 
	* @param urlToGuess
	* @return
	* @throws MalformedURLException
	* @throws IOException
	*/
	public static ArrayList<String> guessApps(URL urlToGuess) throws MalformedURLException, IOException{
			ArrayList<String> guessedApps = new ArrayList<String>();
			Document doc = getFastGuessBDD(fastAppGuessBD);  
			Element racine = doc.getRootElement();	
			for(int i=0;i<racine.getChildren().size();i++){
				Element app = (Element)racine.getChildren().get(i);
				String appName = app.getAttributeValue("name");
				//System.out.println(appName);
				for(int j=0;j<app.getChildren().size();j++){
					String indicFilePath = ((Element)app.getChildren().get(j)).getValue();
					//System.out.println(indicFilePath);
					if(checkIfExist(urlToGuess, indicFilePath)){
						
						// ici soit on retourne le resultat ou on passe au fingerprinting
						System.out.println(appName);
						
						// ********************************************
						
						// here we can change this to : pass urlToGuess 
						// to fingerprintFile as an argument or ...
						setUrlToGuess(urlToGuess);
						// TODO the following call must return a set of versions
						fingerPrintFile(appName);
						guessedApps.add(appName);
						break;
					}		
				}
			}
			return guessedApps;	
		}
			
	
	
	
		// c'est pour le cas ou un path existe dans plusieurs apps (path non unique)
		// donc on retourne une liste puis on effectue une recheche plus precise 
		// en comparant avec wappalyzer ou encre en applicant guessVersion 
		public static List<String> guessApps(){
			
			return null;
		}
	
		
	public static void fingerPrintFile(String appName) throws MalformedURLException, IOException{
		boolean stop = false;
		Document doc = loadFingerPrintingDB((appName2dbPath(appName)));
		Element racine = doc.getRootElement();
		for(int i =0;i<racine.getChildren().size();i++){
			Element file = (Element)racine.getChildren().get(i);
			String path = file.getAttributeValue("path");
			if(checkIfExist(urlToGuess, path)){
				//System.out.println("path that match = "+path);
				
				//-------------------------------------------------
				//TODO here i must introduce accuracy
				//options to specify accuracy fingerprinting level
				//--------------------------------------------------
				
				for (int j=0;j<file.getChildren().size();j++){
					Element hashNode = (Element) file.getChildren().get(j);
					String hash = hashNode.getAttributeValue("md5");
						String chksum = 
								CMSFingerprinter.checkUrlContentChecksoms(
										new URL(urlToGuess.toString()+path));
						System.out.println("hash = "+hash);
						System.out.println("chksum = "+chksum);
						if (hash.compareTo(chksum)==0){
							stop=true;
							System.out.println("hhhhhhhh");
							for(int k= 0 ;k<hashNode.getChildren().size();k++){
								Element versionNode = (Element)hashNode.getChildren().get(k);
								String version= versionNode.getValue();
								System.out.println("		version=="+version);
							}
							break; // parceque un fichier sur le net n'a pas deux hashes
						}	
				}
				if (stop)/* break*/; //  should analyze all files 
			}
			else /*System.out.println("dont exist !!")*/;
		}
	}
	
	
	/**
	 * open the xml file of the given path and return a DOM document of this file
	 * @param dbPath:path to the db file 
	 * @return
	 */
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
	
	
	/**
	 * from an app name return the correspondent xml file 
	 * eg: appName = joomla => this function return : db/joomla/joomla.xml
	 * @param appName
	 * @return
	 */
	public static String appName2dbPath(String appName){
		/**
		 * here is defined a naming and locating convention for webapps DBs
		 */
		return "db/"+appName+"/"+appName+".xml";
	}
	
	
	/**
	 * TODO: extend it to check url+/blabla/+filepath, that extended call this not extended one
	 * like this : checkIfExist(url) with one argument
	 * 
	 * Answer if a given file exists in a given webapp
	 * @return true if the file exists, false else
	 * @param appUrl
	 * @param filePath
	 * @throws IOException 
	 * 
	 */
	public static boolean checkIfExist(URL appUrl, String filePath) throws IOException{
		URL completeUrl = new URL(appUrl.toString()+filePath);
		//System.out.println("-->"+completeUrl.toString());
		HttpURLConnection  con = (HttpURLConnection) completeUrl.openConnection();
		con.setRequestMethod("HEAD");
		//System.out.println(con.getResponseCode());
		if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
			//System.out.println("yes");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 ***************** pending
	 * 
	 * 
	 * 
	 * Answer if a given url exists (server code 200)
	 * @return true if the file exists, false else
	 * @param appUrl
	 * @param filePath
	 * @throws IOException 
	 * 
	 */
	public static boolean checkIfExist(URL url) throws IOException{
		
		//System.out.println("-->"+completeUrl.toString());
		HttpURLConnection  con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("HEAD");
		//System.out.println(con.getResponseCode());
		if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
			//System.out.println("yes");
			return true;
		}
		return false;
	}
	
	
	/**
	 * open the file in bddPath (xml file) and return a DOM document of this file 
	 * 
	 * @param bddPath
	 * @return
	 */
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
}
