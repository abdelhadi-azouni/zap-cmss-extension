package org.zaproxy.zap.extension.CMSS;

import java.util.ArrayList;

public class FingerPrintingThread extends Thread{
	private ArrayList<String> wtfpList = new ArrayList<String>();
	private ArrayList<String> resultList = new ArrayList<String>();
	private int POrAOption;
    
	public FingerPrintingThread(ArrayList<String> wtfpList, int POrAOption){
    		this.wtfpList = wtfpList;
    		this.POrAOption = POrAOption;
	}

	@Override
	public void run() {
		try {
			resultList = FastFingerprinter.filterResults(wtfpList, this.POrAOption);
			this.finalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getFingerPrintingResult(){
		return this.resultList;
	}
}
