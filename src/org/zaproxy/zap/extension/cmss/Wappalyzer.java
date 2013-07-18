package org.zaproxy.zap.extension.cmss;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Wappalyzer {

    public static void essaie() throws Exception {
      
    	
    	JSONParser parser = new JSONParser();
    	 
        try {
     
            Object obj = parser.parse(new FileReader("fastGuess/apps.json"));
     
            JSONObject jsonObject = (JSONObject) obj;
     
            JSONObject apps =  (JSONObject) jsonObject.get("apps");
            System.out.println(apps.keySet());
     
          
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
     
    	
    }
    
    
    /**
     * download apps.json from wappalyzer GIT repo (that file is updated from here) check wappalyzer wiki 
     * ------>TODO  similar method to update BlinElephant files (this is actually in python)
     * @throws IOException
     */
    public static void bddUpdate() throws IOException{
		URL website = new URL("https://raw.github.com/ElbertF/Wappalyzer/master/share/apps.json");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource")
		FileOutputStream fos = new FileOutputStream("fastGuess/apps.json");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
    
    /*
    public static String[] preparePatterns(JSONObject app){
    	String[] regexes = new String[0];
    	String[] keys1 = {"url","html","script"}, keys2={"headers","meta"};
    	for(String key : keys1){
    		String pattern = (String) app.get(key);
    		if(pattern != null){
    			System.out.println(key+" = "+pattern);
    			regexes = pattern.split("\\\\;");
    			
    			System.out.println(regexes[0]);
    			System.out.println(regexes[1]);
 
    			String chaine = "Test regex Java pour <balise1>Wikibooks</balise1> francophone. Java";
                Pattern p = Pattern.compile("(.*) Java");
                Matcher m = p.matcher(chaine);
                while(m.find())
                        System.out.println(m.group(1)+"  "+m.group(0));

    		}
    	}
    	return regexes;
    }
    */
    
    public static boolean hasApp(JSONObject app, WebPage wp) throws IOException{
    	Document HTML = wp.getDocument();
    	String url = wp.getURL().toString();
    	
    	
    	String[] keys1 = {"url","html","script"}, keys2={"headers","meta"};
    	
    	for(String key : keys1){
    		System.out.println(key);
    		String pattern = (String) app.get(key);
    		if(pattern != null){
    			String[] regexes = pattern.split("\\\\;");
    			for(String regex:regexes){
    				Pattern p = Pattern.compile(regex);
    				if(key.compareTo("url")==0){
    					Matcher m = p.matcher(url);
    					while(m.find()){
    						System.out.println("regex   :   "+regex);
    						System.out.println(key+"   :   "+m.group(0));
    						
    					}
                            
    				}
    				if(key.compareTo("html")==0){
    					Matcher m = p.matcher(HTML.outerHtml());
    					while(m.find()){
    						System.out.println("regex   :   "+regex);
    						System.out.println(key+"   :   "+m.group(0));
    					}
                            
    				}
    				if(key.compareTo("script")==0){
    					for(Element script:wp.getScriptNodes()){
    						Matcher m = p.matcher(""+script.toString());
    						while(m.find()){
    							System.out.println("regex   :   "+regex);
    							System.out.println(key+"   :   "+m.group(0));
    						}
                                
    					}
    				}
    			} 		
    		}
    	}
    	for(String key : keys2){
    		System.out.println(key);
    		JSONObject listHorM = (JSONObject) app.get(key);
    		if(listHorM != null){
    			
    			for(Object name:listHorM.keySet()){
    				String pattern = (String) listHorM.get(name);
    				String[] regexes = pattern.split("\\\\;");
    				for(String regex:regexes){
        				Pattern p = Pattern.compile(regex);
        				if(key.compareTo("headers")==0){
        					System.out.println("name == "+name);
        					if(wp.getHeaders().get(name) != null){
        							String HeaderContent = wp.getHeaders().get(name).toString(); // <---
        							Matcher m = p.matcher(HeaderContent);
                					while(m.find()){
                						System.out.println("regex   :   "+regex);
                						System.out.println(key+"   :   "+m.group(0));
                					}
                                         
        					}
        						
        				}
        				if(key.compareTo("meta")==0){
        					Map<String, String> metaMap = wp.getMetaNodes();
        					Set<String> names = wp.getMetaNodes().keySet();
        					for(String metaName : names){
        						String content = metaMap.get(metaName);
        						Matcher m = p.matcher(content);
        						while(m.find()){
        							System.out.println("regex   :   "+regex);
        							System.out.println(key+"   :   "+m.group(0));
        						} 
        					}
        				}
    				}
    			}  				
    		}
    	}
    	
    	return true;
    }
    
    public static void analyse(URL url) throws IOException{
    	ArrayList<String> detectedApps = new ArrayList<String>() ;
    	WebPage wp = new WebPage(url);
    	ArrayList<String> applist = new ArrayList<String>() ;
    	ArrayList<String> categlist = new ArrayList<String>() ;
    	  
    	JSONParser parser = new JSONParser();
   	 
        try {
     
            Object obj = parser.parse(new FileReader("fastGuess/apps.json"));
     
            JSONObject jsonObject = (JSONObject) obj;
     
            JSONObject apps =  (JSONObject) jsonObject.get("apps");
            /**
             * ici ici 
             */
            hasApp((JSONObject)apps.get("WordPress"),wp);
            applist= new ArrayList<String>() ;
            for (int i = 0 ; i<apps.keySet().size(); i++){
            	applist.add((apps.keySet().toArray())[i].toString());
            	//System.out.println(applist.get(i));
            }
            
            JSONObject categories =  (JSONObject) jsonObject.get("categories");
            categlist = new ArrayList<String>() ;
            for (int i = 0 ; i<categories.keySet().size(); i++){
            	categlist.add((categories.keySet().toArray())[i].toString());
            	//System.out.println(categlist.get(i));
            } 
     
          
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
        for(String app:applist){
        	//if()
        }
    	
    }
    
    
    
}
