package org.zaproxy.zap.extension.cmss;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class WebPage {
  
	private URL url ;
	private Document HTMLDoc ;
	
	private Elements scripts = new Elements();
	private Map<String, List<String>> headers;
	private Map<String, String> metas = new HashMap<String, String>(); 
	
	public URL getURL(){
		return this.url;
	}
	public Document getDocument() throws IOException{
		getHTML(this.url);
		return this.HTMLDoc;
	}
	public Elements getScriptNodes() throws IOException{
		getScriptNodes(this.url);
		return this.scripts;
	}
	
	public Map<String, List<String>> getHeaders() throws IOException{
		getHTTPHeaders();
		return this.headers;
	}
	public Map<String, String> getMetaNodes() throws IOException{
		getMetaNodes(url);
		return this.metas;
	}
	
	
	public WebPage(URL url) throws IOException{
		this.HTMLDoc = getHTML(url);
		this.url = url;
	}
	
	
	private static Document getHTML(URL url) throws IOException{
		
		return Jsoup.connect(url.toString()).get();
		
	}
	
	private  void getHTTPHeaders() throws IOException{
		URLConnection conn = this.url.openConnection();
	 
		//get all headers
		headers = conn.getHeaderFields();
		/*for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + 
	                 " ,Value : " + entry.getValue());
		}*/
		
		/*//get header by 'key'
		String server = conn.getHeaderField("Server");*/
		
	}
	
	private void getScriptNodes(URL url) throws IOException{
		
		Document doc = getHTML(url);
    
        Elements scripts = doc.select("script");
        
        
        for (int i=0;i<scripts.size();i++){

        	Element script = scripts.get(i);
        	
        	if(script.hasAttr("src")){
        		//System.out.println("script = "+scripts.get(i)+"");
        		this.scripts.add(script);
        	}
            //System.out.println("-----------------------");
        }
        
	}
	
	
	
	
	@SuppressWarnings("null")
	private void getMetaNodes(URL url) throws IOException{
		
		Document doc = getHTML(url);
        Elements metas = doc.select("meta");      

        
        
        for (int i=0;i<metas.size();i++){
        	Element meta = metas.get(i);

        	if(meta.hasAttr("name") && meta.hasAttr("content")){
        		System.out.println("meta = "+metas.get(i)+"");
        		
        		
        		this.metas.
        		put(meta.
        		attr("name"), 
        		meta.attr("content"));
               
        	}
        	
            //System.out.println("-----------------------");
        }
        
	}
	
}
