package com.idm;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.idm.DownloadManager;
import com.idm.DownloadTableModel;
import net.miginfocom.swing.MigLayout;

public class MyLayout extends JFrame {

	// dialog vÃ  panel
	private JPanel mainPanel;
	private JPanel subPanel;
	private JDialog jDialog;
	
	// Menu components
	private JMenuBar jMenuBar;
	private JMenu jmnTaskMenu;
	private JMenu jmnDownloadMenu;
	private JMenu jmnHelpMenu;
	private JMenuItem jmiTaskAddNewDownloadItem;
	private JMenuItem jmiTaskExitItem;
	private JMenuItem jmiDownloadResumeItem;
	private JMenuItem jmiDownloadPauseItem;
	private JMenuItem jmiDownloadCancelItem;
	private JMenuItem jmiDownloadRemoveItem;
	private JMenuItem jmiDownloadOptionItem;
	private JMenuItem jmiHelpAboutItem;

	// component cá»§a JDialogAddNewDownload
	private JLabel jlbTaskAddURL;
	private JTextField jtxTaskAddURL;
	private JButton jbnTaskAddURL;

	// component cá»§a JDialogOptions
	private JLabel jlbOptionConnections;
	private JComboBox<Integer> jcbOptionConnections;
	private JLabel jlbOptionOutputFolder;
	private JTextField jtxOptionOutputFolder;
	private JButton jbnOptionOutputFolderChoose;
	private JFileChooser jfcOptionOutputFolderChoose;
	private JButton jbnOptionSave;
	private final Integer[] connectionsValue = { 2, 4, 8, 16, 32 };

	// component cá»§a JDialogHelp
	private JLabel jlbHelpAboutImg;
	private JLabel jlbHelpAboutVersion;
	private JLabel jlbHelpAboutInfo;
	
	//button cÃ¡c chá»©c nÄƒng
	private JButton jbnMainAdd;
	private JButton jbnMainCancel;
	private JButton jbnMainPause;
	private JButton jbnMainRemove;
	private JButton jbnMainResume;
	private JButton jbnMainOption;
	
	//components cá»§a Download List
	private JScrollPane jspMainDownloadList;
	private JTable jtbMainDownloadList;
	
	// Khai bÃ¡o Ä‘á»‘i tÆ°á»£ng DownloadTableModel gá»“m cÃ¡c thÃ´ng tin "File Name", "Size", "Progress", "Transfer rate", "Status"
	private DownloadTableModel tableModel;
	
	// Icons
	private final ImageIcon idmIcon = new ImageIcon("image/dowload48x.png");
	private final ImageIcon idmAddBtn = new ImageIcon("image/icons8-add-link-40.png");
	private final ImageIcon idmCancelBtn = new ImageIcon("image/icons8-close-window-40.png");
	private final ImageIcon idmPauseBtn = new ImageIcon("image/icons8-pause-button-40.png");
	private final ImageIcon idmRemoveBtn = new ImageIcon("image/icons8-trash-40.png");
	private final ImageIcon idmResumeBtn = new ImageIcon("image/icons8-resume-button-40.png");
	private final ImageIcon idmOptionBtn = new ImageIcon("image/icons8-automatic-40.png");
	
	public MyLayout() {
		setTitle("Internet Download Manager");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 500));
		setIconImage(idmIcon.getImage());

		// code Menu
		buildMenubar();
		buildMainWindow();

		// set vá»‹ trÃ­ vÃ  hiá»ƒn thá»‹ panel
		pack();
		setVisible(true);
		setLocationRelativeTo(null);// Center the window
	}

	private void buildMenubar() {
		jMenuBar = new JMenuBar();

		// Khai bÃ¡o menu Task
		jmnTaskMenu = new JMenu("Tasks");

		// Khai bÃ¡o menuitem trong menu Task
		// menuitem - Add new download
		jmiTaskAddNewDownloadItem = new JMenuItem("Add new download", KeyEvent.VK_N);
		jmiTaskAddNewDownloadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				// Debug
				System.out.println("Event: Tasks - Add new download");
				taskAddNewDownload(a);
			}
		});
		// menuitem - Exit
		jmiTaskExitItem = new JMenuItem("Exit", KeyEvent.VK_X);
		jmiTaskExitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Tasks - Exit");
			}
		});

		jmnTaskMenu.add(jmiTaskAddNewDownloadItem);
		// Ä‘Æ°á»�ng gáº¡ch ngang
		jmnTaskMenu.addSeparator();
		jmnTaskMenu.add(jmiTaskExitItem);

		// Khai bÃ¡o menu Download
		jmnDownloadMenu = new JMenu("Downloads");

		// Khai bÃ¡o menuitem trong menu Download
		// menuitem - Resume
		jmiDownloadResumeItem = new JMenuItem("Resume", KeyEvent.VK_R);
		jmiDownloadResumeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Resume");
			}
		});
		// menuitem - Pause
		jmiDownloadPauseItem = new JMenuItem("Pause", KeyEvent.VK_P);
		jmiDownloadPauseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Pause");
			}
		});
		// menuitem - Cancel
		jmiDownloadCancelItem = new JMenuItem("Cancel", KeyEvent.VK_C);
		jmiDownloadCancelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Cancel");
			}
		});
		// menuitem - Remove
		jmiDownloadRemoveItem = new JMenuItem("Remove", KeyEvent.VK_M);
		jmiDownloadRemoveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Remove");
			}
		});
		// menuitem - Options
		jmiDownloadOptionItem = new JMenuItem("Options", KeyEvent.VK_O);
		jmiDownloadOptionItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				// Debug
				System.out.println("Event: Downloads - Options");
				downloadOption(a);
			}
		});
		// Add menu items vÃ o menu Download
		jmnDownloadMenu.add(jmiDownloadResumeItem);
		jmnDownloadMenu.add(jmiDownloadPauseItem);
		jmnDownloadMenu.add(jmiDownloadCancelItem);
		jmnDownloadMenu.add(jmiDownloadRemoveItem);
		jmnDownloadMenu.addSeparator();
		jmnDownloadMenu.add(jmiDownloadOptionItem);

		// Khai bÃ¡o menu Help
		jmnHelpMenu = new JMenu("Help");
		// Khai bÃ¡o menuitem trong Help menu
		// menuitem - About IDM
		jmiHelpAboutItem = new JMenuItem("About IDM", KeyEvent.VK_B);
		jmiHelpAboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				// Debug
				System.out.println("Event: Help - About IDM");
				helpAbout(a);
			}
		});

		jmnHelpMenu.add(jmiHelpAboutItem);

		// Add menu vÃ o menubar
		jMenuBar.add(jmnTaskMenu);
		jMenuBar.add(jmnDownloadMenu);
		jMenuBar.add(jmnHelpMenu);
		
		setJMenuBar(jMenuBar);

	}

	private void buildMainWindow() {
		// Add button
		jbnMainAdd = new JButton("Add URL");
		jbnMainAdd.setIcon(idmAddBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Cancel button
		jbnMainCancel = new JButton("Cancel");
		jbnMainCancel.setIcon(idmCancelBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainCancel.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainCancel.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Resume button
		jbnMainResume = new JButton("Resume");
		jbnMainResume.setIcon(idmResumeBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainResume.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainResume.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Pause button
		jbnMainPause = new JButton("Pause");
		jbnMainPause.setIcon(idmPauseBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainPause.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainPause.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Remove button
		jbnMainRemove = new JButton("Remove");
		jbnMainRemove.setIcon(idmRemoveBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainRemove.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainRemove.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//Options button
		jbnMainOption = new JButton("Option");
		jbnMainOption.setIcon(idmOptionBtn);
		//vá»‹ trÃ­ dá»�c cá»§a chá»¯ trong button
		jbnMainOption.setVerticalTextPosition(SwingConstants.BOTTOM);
		//vá»‹ trÃ­ ngang cá»§a chá»¯ trong button
		jbnMainOption.setHorizontalTextPosition(SwingConstants.CENTER);
		
		// Khai bÃ¡o Download list lÃ  má»™t JTable cÃ³ Scroll
		jspMainDownloadList = new JScrollPane();
		jtbMainDownloadList = new JTable();
		
		// Map data tá»« Ä‘á»‘i tÆ°á»£ng DownloadTableModel xuá»‘ng JTable
		tableModel = new DownloadTableModel();
		jtbMainDownloadList.setModel(tableModel);
		jspMainDownloadList.setViewportView(jtbMainDownloadList);
		
		// Chá»‰nh mÃ u cho Download List
		jspMainDownloadList.getViewport().setBackground(new Color(255, 255, 255));
		jtbMainDownloadList.setGridColor(new Color(240, 240, 240));
		
		//Khai bÃ¡o main panel dÃ¹ng MigLayout
		mainPanel = new JPanel(new MigLayout("fill"));
		mainPanel.add(jbnMainAdd);
		mainPanel.add(jbnMainResume);
		mainPanel.add(jbnMainPause);
		mainPanel.add(jbnMainCancel);
		mainPanel.add(jbnMainRemove);
		mainPanel.add(jbnMainOption, "wrap");
		mainPanel.add(jspMainDownloadList, "span, width 100%, height 100%");
		
		add(mainPanel);
	}

	private void taskAddNewDownload(ActionEvent a) {

		// Khai bÃ¡o JDialog
		jDialog = new JDialog(this, "Enter new address to download");
		jDialog.setPreferredSize(new Dimension(600, 70));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

		// Khai bÃ¡o ná»™i dung cÃ¡c component trong subpanel
		jlbTaskAddURL = new JLabel("Address:");
		jtxTaskAddURL = new JTextField("");
		jbnTaskAddURL = new JButton("OK");

		// Khai bÃ¡o subpanel dÃ¹ng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbTaskAddURL);
		subPanel.add(jtxTaskAddURL, "width 100%");
		subPanel.add(jbnTaskAddURL);

		jDialog.add(subPanel);

		// set vá»‹ trÃ­ vÃ  hiá»ƒn thá»‹ jDialog Add New URL
//		jDialog.getRootPane().setDefaultButton(jbnTaskAddURL);
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);
	}

	private void downloadOption(ActionEvent evt) {
		// Khai bÃ¡o JDialog
		jDialog = new JDialog(this, "Internet Download Manager Options");
		jDialog.setPreferredSize(new Dimension(400, 100));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

//		// Khai bÃ¡o combobox Max Connections
//		jlbOptionConnections = new JLabel("Max connection:");
//		jcbOptionConnections = new JComboBox<Integer>(connectionsValue);
//		jcbOptionConnections.setSelectedItem(DownloadManager.getInstance().getConnectionNumber());
//
//		// Khai bÃ¡o save location vÃ  button browse
//		jlbOptionOutputFolder = new JLabel("Save location:");
//		jtxOptionOutputFolder = new JTextField(
//				new File(DownloadManager.getInstance().getOutputFolder()).getAbsolutePath(), 25);
//		jbnOptionOutputFolderChoose = new JButton("Browse");
//		jbnOptionOutputFolderChoose.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent a) {
//				downloadOptionSelectFolder(a);
//			}
//		});

		// Khai bÃ¡o button Save
		jbnOptionSave = new JButton("Save");

		// Khai bÃ¡o subpanel dÃ¹ng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbOptionConnections, "align right");
		subPanel.add(jcbOptionConnections);
		subPanel.add(jbnOptionSave, "wrap, growx");
		subPanel.add(jlbOptionOutputFolder, "align right");
		subPanel.add(jtxOptionOutputFolder);
		subPanel.add(jbnOptionOutputFolderChoose, "wrap, growx");

		jDialog.add(subPanel);

		// Set vá»‹ trÃ­ vÃ  hiá»ƒn thá»‹ JDialog Options
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);
	}

	private void downloadOptionSelectFolder(ActionEvent evt) {
		jfcOptionOutputFolderChoose = new JFileChooser();
		jfcOptionOutputFolderChoose.setDialogTitle("Choose Save Location");
		// DIRECTORIES_ONLY chá»‰ hiá»ƒn thá»‹ folder thÃ´i khÃ´ng cÃ³ file
		jfcOptionOutputFolderChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		jfcOptionOutputFolderChoose.setCurrentDirectory(new File(DownloadManager.getInstance().getOutputFolder()));
		jfcOptionOutputFolderChoose.setAcceptAllFileFilterUsed(false);

		// Set temporary info for displaying
		if (jfcOptionOutputFolderChoose.showOpenDialog(jDialog) == JFileChooser.APPROVE_OPTION) {
			jtxOptionOutputFolder.setText(jfcOptionOutputFolderChoose.getSelectedFile().getAbsolutePath());
		}
	}

	private void helpAbout(ActionEvent a) {
		// Khai bÃ¡o JDialog
		jDialog = new JDialog(this, "Help About");
		jDialog.setPreferredSize(new Dimension(420, 200));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

		jlbHelpAboutImg = new JLabel();
		jlbHelpAboutImg.setIcon(new ImageIcon("img/about.png"));

		// Khai bÃ¡o ná»™i dung cÃ¡c component trong subpanel (<br /> xuá»‘ng dÃ²ng)
		jlbHelpAboutVersion = new JLabel("Version 0.1 alpha");
		jlbHelpAboutInfo = new JLabel(
				"<html><div style='text-align:center'>This program was designed and programmed by <b>GroupNine</b>.<br/>"
						+ "Software Engineering: Specialized Project - Spring 2018 - Nong Lam University.<br/>"
						+ "Instructor: <b>Prof. Pham Van Tinh PhD.</b></div></html>");

		// Khai bÃ¡o subpanel dÃ¹ng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbHelpAboutImg, "wrap");
		subPanel.add(jlbHelpAboutVersion, "wrap, align center");
		subPanel.add(jlbHelpAboutInfo, "height 100%, align center");

		jDialog.add(subPanel);

		// set vá»‹ trÃ­ vÃ  hiá»ƒn thá»‹ jDialog Help About
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);
	}

	public static void main(String[] args) {
		new MyLayout();
	}
}
