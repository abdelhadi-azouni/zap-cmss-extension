package org.zaproxy.zap.extension.cmss;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class PlunginGuesser {
  
	
	// I will start with specific wabaaps, CMSs first
	// and joomla first
	public static void joomlaComponentLister(URL url, String componentType){
		try{
			InputStream flux = null;
			if(componentType.compareTo("plugin")==0){
				flux=new FileInputStream("pluginEnum/joomla_plugins.txt"); 
			}
			if(componentType.compareTo("theme")==0){
				flux=new FileInputStream("pluginEnum/joomla_themes.txt"); 
			}
			
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String line;
			while ((line=buff.readLine())!=null){
				//System.out.println(line);
				URL completeUrl = new URL((url.toString()+line).replaceAll(" ", ""));
				//System.out.println(completeUrl.toString());
				HttpURLConnection  con = (HttpURLConnection) completeUrl.openConnection();
				con.setRequestMethod("HEAD");
				//System.out.println(con.getResponseCode());
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
					//System.out.println(completeUrl.toString());
					//System.out.println(con.getResponseCode());
					System.out.println(componentType+" : "+line+" exists!!");
					URL rdm = new URL(completeUrl.toString()+"readme.txt");
					//System.out.println(rdm.toString());
					HttpURLConnection  conx = (HttpURLConnection) rdm.openConnection();
					conx.setRequestMethod("HEAD");
					if(conx.getResponseCode() == HttpURLConnection.HTTP_OK){
						System.out.println("------------> readme exists !!");
					}
				}
			}
			buff.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	// on doit dabord decider avec simon si on doit modifier les raw files 
	// en autre format pour l'unification (car ceux de wp existent aussi sont en xml)
	public static void prepareJoomlaPluginDB(){
		//pending
	}
	
	
	// using fuzzdb from googlecode.com 
	// j'ai remarqué que la base de wp scan est plus riche avec une difference de format 
	// celle de fuzz contient le chemin du plugin a partir de l'url : /component/nom_plugin ..
	// celle de wp scan contirnt que le nom
	// donc il faut combiner combiner (-_-)
	
	public static void getJoomlaPluginDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/joomla_plugins.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/joomla_plugins.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	public static void getJoomlaThemeDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/joomla_themes.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/joomla_themes.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
	
	
	
	
	//wp part
	public static void wordpressComponentLister(URL url, String componentType){
		try{
			InputStream flux = null;
			if(componentType.compareTo("plugin")==0){
				flux=new FileInputStream("pluginEnum/wp_plugins.txt"); 
			}
			if(componentType.compareTo("theme")==0){
				flux=new FileInputStream("pluginEnum/wp_themes.txt"); 
			}
			
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String line;
			while ((line=buff.readLine())!=null){
				//System.out.println(line);
				URL completeUrl = new URL((url.toString()+line).replaceAll(" ", ""));
				//System.out.println(completeUrl.toString());
				HttpURLConnection  con = (HttpURLConnection) completeUrl.openConnection();
				con.setRequestMethod("HEAD");
				//System.out.println(con.getResponseCode());
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
					//System.out.println(completeUrl.toString());
					//System.out.println(con.getResponseCode());
					System.out.println(componentType+" : "+line+" exists!!");
					URL rdm = new URL(completeUrl.toString()+"readme.txt");
					//System.out.println(rdm.toString());
					HttpURLConnection  conx = (HttpURLConnection) rdm.openConnection();
					conx.setRequestMethod("HEAD");
					if(conx.getResponseCode() == HttpURLConnection.HTTP_OK){
						System.out.println("------------> readme exists !!");
					}
				}
			}
			buff.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}	
	

	//wp part
	public static void getWordpressPluginDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/wp_plugins.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/wp_plugins.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	public static void getWordpressThemeDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/wp_themes.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/wp_themes.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
	
	public static void drupalComponentLister(URL url, String componentType){
		try{
			InputStream flux = null;
			if(componentType.compareTo("plugin")==0){
				flux=new FileInputStream("pluginEnum/drupal_plugins.txt"); 
			}
			if(componentType.compareTo("theme")==0){
				flux=new FileInputStream("pluginEnum/drupal_themes.txt"); 
			}
			
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String line;
			while ((line=buff.readLine())!=null){
				//System.out.println(line);
				URL completeUrl = new URL((url.toString()+line).replaceAll(" ", ""));
				//System.out.println(completeUrl.toString());
				HttpURLConnection  con = (HttpURLConnection) completeUrl.openConnection();
				con.setRequestMethod("HEAD");
				//System.out.println(con.getResponseCode());
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
					//System.out.println(completeUrl.toString());
					//System.out.println(con.getResponseCode());
					System.out.println(componentType+" : "+line+" exists!!");
					URL rdm = new URL(completeUrl.toString()+"readme.txt");
					//System.out.println(rdm.toString());
					HttpURLConnection  conx = (HttpURLConnection) rdm.openConnection();
					conx.setRequestMethod("HEAD");
					if(conx.getResponseCode() == HttpURLConnection.HTTP_OK){
						System.out.println("------------> readme exists !!");
					}
				}
			}
			buff.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	
	
	// using fuzzdb from googlecode.com 
	// j'ai remarqué que la base de wp scan est plus riche avec une difference de format 
	// celle de fuzz contient le chemin du plugin a partir de l'url : /component/nom_plugin ..
	// celle de wp scan contirnt que le nom
	// donc il faut combiner combiner (-_-)
	
	public static void getDrupalPluginDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/drupal_plugins.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/drupal_plugins.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	public static void getDrupalThemeDB() throws IOException{
		URL website = new URL("https://fuzzdb.googlecode.com/svn/trunk/Discovery/PredictableRes/CMS/drupal_themes.fuzz.txt");
	    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    @SuppressWarnings("resource") 
		FileOutputStream fos = new FileOutputStream("pluginEnum/drupal_themes.txt");
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
}
