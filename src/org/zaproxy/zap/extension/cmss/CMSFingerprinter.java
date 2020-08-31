package org.zaproxy.zap.extension.cmss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class CMSFingerprinter {
	
	
	/**
	 *  Use BlindElephant DB to fingerprint webapp
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
        // @deepalgo

	public static void FingerprintFileBE(URL url) throws IOException, NoSuchAlgorithmException{
		
		try {
		
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File("joomla.xml"));
			Element racine = doc.getRootElement();
			System.out.println(racine.getChildren().size());
			for (int i=0;i<racine.getChildren().size();i++){
				System.out.println(i);
				Element file = (Element)racine.getChildren().get(i);
				String path = file.getAttributeValue("path");
				URL filePath = new URL(url.toString()+path);
				int len = url.toString().length();
				String urlF = url.toString().substring(0, len-1);
				System.out.println("path == "+urlF+path);
				URLConnection con = filePath.openConnection();
				if (con.getContentLength()!= -1){
					String chksum = checkUrlContentChecksoms(filePath);
					System.out.println(chksum);
					for (int j=0;j<file.getChildren().size();j++){
						Element hashNode = (Element) file.getChildren().get(j);
						String hash = hashNode.getAttributeValue("md5");
						System.out.println(hash);
						if (hash.compareTo(chksum)==0){
							for(int k= 0 ;k<hashNode.getChildren().size();k++){
								Element versionNode = (Element)hashNode.getChildren().get(k);
								String version= versionNode.getValue();
								System.out.println("		version=="+version);
							}
							break; //<----
						}
					}
				}
				//else System.out.println("daznot");
			}
			
		} 
		catch (JDOMException e) {
			e.printStackTrace();	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
		
		
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static InputStream getFileFromUrl(URL url) throws IOException{
		
		InputStream is = null;
		try{
			is= url.openStream();
			File file = new File(url.getPath());
			//System.out.println("filename = "+file.getName());
			FileOutputStream out = new FileOutputStream(file.getName());
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		
		/**
		 * some stuff to do here with 'out'
		 */
	
		return is;
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String checkSumApacheCommons(InputStream is){
        String checksum = null;
        try {  
            checksum = DigestUtils.md5Hex(is);
        } catch (IOException e) {
            //logger.log(Level.SEVERE, null, ex);
        	e.printStackTrace();
        }
        return checksum;
    }
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String checkUrlContentChecksoms(URL url) throws IOException{
		String chksum = checkSumApacheCommons(getFileFromUrl(url));
		//System.out.println(chksum);
		return chksum;
	}
	
	
	// semble qu'elle fonctionne mais j'ai pas bien teste 
	public static String checksum(byte[] octets) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(octets);
		final byte[] resultByte = messageDigest.digest();
		return new String(Hex.encodeHex(resultByte));
	}
	

}
