package org.zaproxy.zap.extension.CMSS;




public class CMSSThread extends Thread {

	public CMSSThread(){
    		
	}
	
	@Override
	public void run() {
		CMSSFrame fra = new CMSSFrame();
        fra.setLocationRelativeTo(null);
 	    fra.setVisible(true);
	}
}
