package org.zaproxy.zap.extension.CMSS;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.DecoderException;

public class CMSSFrame extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	private FingerPrintingThread fpThread = null;

	// we save in this list all checkBoses then use them to get web apps category names and then category numbers
	private ArrayList<javax.swing.JCheckBox> checkBoxesList = new ArrayList<javax.swing.JCheckBox>();
	
	private WhatToFingerPrintFrame wtfpFrame = new WhatToFingerPrintFrame();
	
	private int POrAOption = 1;

	/**
	 * Create the frame.
	 */
	public CMSSFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 751, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 725, 251);
		layeredPane.add(tabbedPane);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Details", null, layeredPane_2, null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 720, 223);
		layeredPane_2.add(tabbedPane_1);
		
		JLayeredPane layeredPane_3 = new JLayeredPane();
		tabbedPane_1.addTab("Passive fingerprint", null, layeredPane_3, null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addTab("Agressive fingerprint", null, tabbedPane_2, null);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Fingerprinting options", null, layeredPane_1, null);
		
		
		
		JLabel label = new JLabel("App name:");
		label.setBounds(28, 118, 76, 14);
		layeredPane_1.add(label);
		
		JLabel label_1 = new JLabel("Version:");
		label_1.setBounds(28, 160, 76, 14);
		layeredPane_1.add(label_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(114, 118, 109, 29);
		layeredPane_1.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(114, 153, 109, 29);
		layeredPane_1.add(textField_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 84, 168, 2);
		layeredPane_1.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(196, 11, 1, 201);
		layeredPane_1.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(253, 11, 1, 201);
		layeredPane_1.add(separator_2);
		
		final JCheckBox chckbxGetVersion = new JCheckBox("Get version");
		chckbxGetVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField_1.isEnabled() && !chckbxGetVersion.isSelected()) textField_1.setEnabled(false);
				if(!textField_1.isEnabled() && chckbxGetVersion.isSelected()) textField_1.setEnabled(true);
			}
		});
		chckbxGetVersion.setBounds(28, 11, 195, 23);
		layeredPane_1.add(chckbxGetVersion);
		
		final JCheckBox chckbxPassiveFingerprinting = new JCheckBox("Passive");
		chckbxPassiveFingerprinting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		chckbxPassiveFingerprinting.setBounds(28, 37, 195, 23);
		chckbxPassiveFingerprinting.setSelected(true);//
		layeredPane_1.add(chckbxPassiveFingerprinting);
		
		final JCheckBox chckbxAgressive = new JCheckBox("Agressive");
		chckbxAgressive.setBounds(28, 63, 195, 23);
		layeredPane_1.add(chckbxAgressive);
		
		JLabel lblWhatToFingerprint = new JLabel("What to fingerprint ?");
		lblWhatToFingerprint.setBounds(280, 11, 109, 14);
		layeredPane_1.add(lblWhatToFingerprint);
		
		JCheckBox chckbxCms = new JCheckBox("cms");
		chckbxCms.setBounds(273, 32, 134, 23);
		layeredPane_1.add(chckbxCms);
		
		JCheckBox chckbxMessageboards = new JCheckBox("message-boards");
		chckbxMessageboards.setBounds(273, 58, 134, 23);
		layeredPane_1.add(chckbxMessageboards);
		
		JCheckBox chckbxJavascriptframeworks = new JCheckBox("javascript-frameworks");
		chckbxJavascriptframeworks.setBounds(274, 84, 133, 23);
		layeredPane_1.add(chckbxJavascriptframeworks);
		
		JCheckBox chckbxWebframeworks = new JCheckBox("web-frameworks");
		chckbxWebframeworks.setBounds(274, 108, 133, 23);
		layeredPane_1.add(chckbxWebframeworks);
		
		JCheckBox chckbxWebservers = new JCheckBox("web-servers");
		chckbxWebservers.setBounds(274, 134, 133, 23);
		layeredPane_1.add(chckbxWebservers);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(428, 11, 1, 201);
		layeredPane_1.add(separator_4);
		
		JCheckBox chckbxDatabases = new JCheckBox("databases");
		chckbxDatabases.setBounds(274, 158, 133, 23);
		layeredPane_1.add(chckbxDatabases);
		
		JButton btnMore = new JButton("More");
		btnMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtfpFrame = new WhatToFingerPrintFrame();
				wtfpFrame.setLocationRelativeTo(null);
				wtfpFrame.setVisible(true);
			}
		});
		btnMore.setBounds(284, 191, 123, 23);
		layeredPane_1.add(btnMore);
		
		JLabel lblFingerprintingTimeAnd = new JLabel("Fingerprinting time and occuracy settings:");
		lblFingerprintingTimeAnd.setBounds(483, 11, 210, 14);
		layeredPane_1.add(lblFingerprintingTimeAnd);
		
		
		
		JButton btnFingerprint = new JButton("Fingerprint");
		btnFingerprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!chckbxPassiveFingerprinting.isSelected() && !chckbxAgressive.isSelected()) chckbxPassiveFingerprinting.setSelected(true);
				if(chckbxPassiveFingerprinting.isSelected() && !chckbxAgressive.isSelected()) POrAOption = 1;
				else if(!chckbxPassiveFingerprinting.isSelected() && chckbxAgressive.isSelected()) POrAOption = 2;
				else if(chckbxPassiveFingerprinting.isSelected() && chckbxAgressive.isSelected()) POrAOption = 3;
				
				
				
				System.out.println("POrAOption : "+POrAOption);
				
				
				// we concatenate the two ArrayLists 
				ArrayList<String> wtfpList = getWhatToFingerprint();
				for(String wtfp:wtfpFrame.getWhatToFingerprint()){
					wtfpList.add(wtfp);
				}
				// we call FastFingerprinter.filterResults on the global whatToFingerPrint List
					
				fpThread = new FingerPrintingThread (wtfpList,POrAOption);
				fpThread.start();
                while(fpThread.isAlive()){
                    // waiting;
                   
                }
                ArrayList<String> resultList = fpThread.getFingerPrintingResult();
				for(String app: resultList) {
					textField.setText(textField.getText()+app+" , ");
				}
				
				
				if(chckbxGetVersion.isSelected()){
					System.out.println("wiw");
					ArrayList<String> versions = new ArrayList<String>();
					for(String app: resultList){
						try {
							versions = WebAppGuesser.fingerPrintFile("Joomla");
						} catch ( NoSuchAlgorithmException | IOException
								| DecoderException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					for(String version: versions) {
						textField_1.setText(textField_1.getText()+version+" , ");
					}
				}
			}
		});
		
		btnFingerprint.setBounds(28, 84, 195, 23);
		layeredPane_1.add(btnFingerprint);
		
		JButton btnDetailedView = new JButton("Detailed view ");
		btnDetailedView.setBounds(28, 189, 195, 23);
		layeredPane_1.add(btnDetailedView);
		this.checkBoxesList.add(chckbxCms);
		this.checkBoxesList.add(chckbxJavascriptframeworks);
		this.checkBoxesList.add(chckbxWebframeworks);
		this.checkBoxesList.add(chckbxWebservers);
		this.checkBoxesList.add(chckbxDatabases);
		this.checkBoxesList.add(chckbxMessageboards);
	}
	private ArrayList<String> getWhatToFingerprint(){
		ArrayList<String> WhatToFingerprint = new ArrayList<String>();
		for(JCheckBox checkBox:checkBoxesList){
			if(checkBox.isSelected()){
				System.out.println(checkBox.getText());
				WhatToFingerprint.add(checkBox.getText());
			}
		}
		return WhatToFingerprint;
	}
	
}
