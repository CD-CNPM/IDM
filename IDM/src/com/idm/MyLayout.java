package com.idm;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.idm.DownloadTableModel;
import net.miginfocom.swing.MigLayout;

public class MyLayout extends JFrame{

	// dialog và panel
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

	// component của JDialogAddNewDownload
	private JLabel jlbTaskAddURL;
	private JTextField jtxTaskAddURL;
	private JButton jbnTaskAddURL;

	// component của JDialogOptions
	private JLabel jlbOptionConnections;
	private JComboBox<Integer> jcbOptionConnections;
	private JLabel jlbOptionOutputFolder;
	private JTextField jtxOptionOutputFolder;
	private JButton jbnOptionOutputFolderChoose;
	private JFileChooser jfcOptionOutputFolderChoose;
	private JButton jbnOptionSave;
	private final Integer[] connectionsValue = { 2, 4, 8, 16, 32 };

	// component của JDialogHelp
	private JLabel jlbHelpAboutImg;
	private JLabel jlbHelpAboutVersion;
	private JLabel jlbHelpAboutInfo;

	// button các chức năng
	private JButton jbnMainAdd;
	private JButton jbnMainCancel;
	private JButton jbnMainPause;
	private JButton jbnMainRemove;
	private JButton jbnMainResume;
	private JButton jbnMainOption;

	// components của Download List
	private JScrollPane jspMainDownloadList;
	private JTable jtbMainDownloadList;

	// Khai báo đối tượng DownloadTableModel gồm các thông tin "File Name",
	// "Size", "Progress", "Transfer rate", "Status"
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

		// set vị trí và hiển thị panel
		pack();
		setVisible(true);
		setLocationRelativeTo(null);// Center the window
	}

	private void buildMenubar() {
		jMenuBar = new JMenuBar();

		// Khai báo menu Task
		jmnTaskMenu = new JMenu("Tasks");

		// Khai báo menuitem trong menu Task
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
				taskExit(evt);
			}
		});

		jmnTaskMenu.add(jmiTaskAddNewDownloadItem);
		// đường gạch ngang
		jmnTaskMenu.addSeparator();
		jmnTaskMenu.add(jmiTaskExitItem);

		// Khai báo menu Download
		jmnDownloadMenu = new JMenu("Downloads");

		// Khai báo menuitem trong menu Download
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
		// Add menu items vào menu Download
		jmnDownloadMenu.add(jmiDownloadResumeItem);
		jmnDownloadMenu.add(jmiDownloadPauseItem);
		jmnDownloadMenu.add(jmiDownloadCancelItem);
		jmnDownloadMenu.add(jmiDownloadRemoveItem);
		jmnDownloadMenu.addSeparator();
		jmnDownloadMenu.add(jmiDownloadOptionItem);

		// Khai báo menu Help
		jmnHelpMenu = new JMenu("Help");
		// Khai báo menuitem trong Help menu
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

		// Add menu vào menubar
		jMenuBar.add(jmnTaskMenu);
		jMenuBar.add(jmnDownloadMenu);
		jMenuBar.add(jmnHelpMenu);

		setJMenuBar(jMenuBar);

	}

	private void buildMainWindow() {
		// Add button
		jbnMainAdd = new JButton("Add URL");
		jbnMainAdd.setIcon(idmAddBtn);
		// vị trí dọc của chữ trong button
		jbnMainAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		jbnMainAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				// Debug
				System.out.println("Event: Tasks - Add new download");
				taskAddNewDownload(a);
			}
		});
		// Cancel button
		jbnMainCancel = new JButton("Cancel");
		jbnMainCancel.setIcon(idmCancelBtn);
		// vị trí dọc của chữ trong button
		jbnMainCancel.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainCancel.setHorizontalTextPosition(SwingConstants.CENTER);

		// Resume button
		jbnMainResume = new JButton("Resume");
		jbnMainResume.setIcon(idmResumeBtn);
		// vị trí dọc của chữ trong button
		jbnMainResume.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainResume.setHorizontalTextPosition(SwingConstants.CENTER);

		// Pause button
		jbnMainPause = new JButton("Pause");
		jbnMainPause.setIcon(idmPauseBtn);
		// vị trí dọc của chữ trong button
		jbnMainPause.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainPause.setHorizontalTextPosition(SwingConstants.CENTER);

		// Remove button
		jbnMainRemove = new JButton("Remove");
		jbnMainRemove.setIcon(idmRemoveBtn);
		// vị trí dọc của chữ trong button
		jbnMainRemove.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainRemove.setHorizontalTextPosition(SwingConstants.CENTER);

		// Options button
		jbnMainOption = new JButton("Option");
		jbnMainOption.setIcon(idmOptionBtn);
		// vị trí dọc của chữ trong button
		jbnMainOption.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainOption.setHorizontalTextPosition(SwingConstants.CENTER);
		jbnMainOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO Auto-generated method stub
				downloadOption(a);
			}
		});

		// Khai báo Download list là một JTable có Scroll
		jspMainDownloadList = new JScrollPane();
		jtbMainDownloadList = new JTable();

		// Map data từ đối tượng DownloadTableModel xuống JTable
		tableModel = new DownloadTableModel();
		jtbMainDownloadList.setModel(tableModel);
		jspMainDownloadList.setViewportView(jtbMainDownloadList);

		// Chỉnh màu cho Download List
		jspMainDownloadList.getViewport().setBackground(new Color(255, 255, 255));
		jtbMainDownloadList.setGridColor(new Color(240, 240, 240));

		// Khai báo main panel dùng MigLayout
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

	
	// Exit
	private void taskExit(ActionEvent evt) {
		dispose();
	}

	private void taskAddNewDownload(ActionEvent a) {
		// Khai báo JDialog
		jDialog = new JDialog(this, "Enter new address to download", Dialog.ModalityType.DOCUMENT_MODAL);
		jDialog.setPreferredSize(new Dimension(600, 70));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

		// Khai báo nội dung các component trong subpanel
		jlbTaskAddURL = new JLabel("Address:");
		jtxTaskAddURL = new JTextField("");
		jbnTaskAddURL = new JButton("OK");
		jbnTaskAddURL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				taskAddNewDownloadURL(e);
			}
		});
		// Khai báo subpanel dùng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbTaskAddURL);
		subPanel.add(jtxTaskAddURL, "width 100%");
		subPanel.add(jbnTaskAddURL);

		jDialog.add(subPanel);

		// set vị trí và hiển thị jDialog Add New URL
		// jDialog.getRootPane().setDefaultButton(jbnTaskAddURL);
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);

	}

	void taskAddNewDownloadURL(ActionEvent e) {
		// kiểm tra url có hợp lệ không
		URL url = DownloadManager.verifyURL(jtxTaskAddURL.getText());
		// ok
		if (url != null) {
			// add download to the download list in class download manager, it's
			// like add a url to download list url in data table
			Download download = DownloadManager.getInstance().createDownload(url,
					DownloadManager.getInstance().getOutputFolder());
			// download object return include "File Name", "Size", "Progress",
			// "Transfer rate", "Status" for tableModel

			// after has download object, add it to tableModel
			// tableModel.addNewDownload(download);

			// reset text field addURL to empty
			// jtxTaskAddURL.setText("");

			// close dialog
			// jDialog.dispose();
		}
		// not support
		else {
			JOptionPane.showMessageDialog(this, ErrorMessage.INVALID_URL, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void downloadOption(ActionEvent evt) {
		// Khai báo JDialog
		jDialog = new JDialog(this, "Internet Download Manager Options", Dialog.ModalityType.DOCUMENT_MODAL);
		jDialog.setPreferredSize(new Dimension(400, 100));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

		// Khai báo combobox Max Connections
		jlbOptionConnections = new JLabel("Max connection:");
		jcbOptionConnections = new JComboBox<Integer>(connectionsValue);
		// jcbOptionConnections.setSelectedItem(DownloadManager.getInstance().getConnectionNumber());
		//
		// Khai báo save location và button browse
		jlbOptionOutputFolder = new JLabel("Save location:");
		// jtxOptionOutputFolder = new JTextField(new
		// File(DownloadManager.getInstance().getOutputFolder()).getAbsolutePath(),25);
		jtxOptionOutputFolder = new JTextField(25);
		jbnOptionOutputFolderChoose = new JButton("Browse");
		jbnOptionOutputFolderChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				downloadOptionSelectFolder(a);
			}
		});

		// Khai báo button Save
		jbnOptionSave = new JButton("Save");

		// Khai báo subpanel dùng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbOptionConnections, "align right");
		subPanel.add(jcbOptionConnections);
		subPanel.add(jbnOptionSave, "wrap, growx");
		subPanel.add(jlbOptionOutputFolder, "align right");
		subPanel.add(jtxOptionOutputFolder);
		subPanel.add(jbnOptionOutputFolderChoose, "wrap, growx");

		jDialog.add(subPanel);

		// Set vị trí và hiển thị JDialog Options
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);
	}

	private void downloadOptionSelectFolder(ActionEvent evt) {
		jfcOptionOutputFolderChoose = new JFileChooser();
		jfcOptionOutputFolderChoose.setDialogTitle("Choose Save Location");
		// DIRECTORIES_ONLY chỉ hiển thị folder thôi không có file
		jfcOptionOutputFolderChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfcOptionOutputFolderChoose.setAcceptAllFileFilterUsed(false);

		// Set temporary info for displaying
		if (jfcOptionOutputFolderChoose.showOpenDialog(jDialog) == JFileChooser.APPROVE_OPTION) {
			jtxOptionOutputFolder.setText(jfcOptionOutputFolderChoose.getSelectedFile().getAbsolutePath());
		}
	}

	private void helpAbout(ActionEvent a) {
		// Khai báo JDialog
		jDialog = new JDialog(this, "About IDM", Dialog.ModalityType.DOCUMENT_MODAL);
		jDialog.setPreferredSize(new Dimension(420, 220));
		jDialog.setIconImage(idmIcon.getImage());
		jDialog.setResizable(false);

		jlbHelpAboutImg = new JLabel();
		jlbHelpAboutImg.setIcon(new ImageIcon("image/about.png"));
		jlbHelpAboutImg.setIcon(new ImageIcon("image/idm_icon.png"));

		// Khai báo nội dung các component trong subpanel (<br /> xuống dòng)
		jlbHelpAboutVersion = new JLabel("Version 0.1 alpha");
		jlbHelpAboutInfo = new JLabel(
				"<html><div style='text-align:center'>This program was designed and programmed by <b>GroupNine</b>.<br />"
						+ "Software Engineering: Specialized Project - Spring 2015 - Nong Lam University.<br />"
						+ "Instructor: <b>Prof. Pham Van Tinh PhD.</b></div></html>");
		jlbHelpAboutVersion = new JLabel("Version 1.0");
		jlbHelpAboutInfo = new JLabel(
				"<html><div style='text-align:center'>This program was designed and programmed by <b>Group_KLPM</b>.<br />"
						+ "Students of the Nong Lam University.<br /></div></html>");

		// Khai báo subpanel dùng MigLayout
		subPanel = new JPanel(new MigLayout("fill"));
		subPanel.add(jlbHelpAboutImg, "wrap,align center");
		subPanel.add(jlbHelpAboutVersion, "wrap, align center");
		subPanel.add(jlbHelpAboutInfo, "height 100%, align center");

		jDialog.add(subPanel);

		// set vị trí và hiển thị jDialog Help About
		jDialog.pack();
		jDialog.setLocationRelativeTo(this);
		jDialog.setVisible(true);
	}

	public static void main(String[] args) {
		new MyLayout();
	}
}
