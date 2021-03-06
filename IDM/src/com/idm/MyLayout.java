package com.idm;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.idm.DownloadTableModel;
import net.miginfocom.swing.MigLayout;

public class MyLayout extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
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

	// Download
	private Download selectedDownloader;
	private boolean isClearing;
	
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

		// Update lại trạng thái các nút
		updateControlButtons();

		// Hiển thị thanh tiến trình theo dạng %
		initialize();

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
				downloadResume(evt);
			}
		});
		// menuitem - Pause
		jmiDownloadPauseItem = new JMenuItem("Pause", KeyEvent.VK_P);
		jmiDownloadPauseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Pause");
				downloadPause(evt);
			}
		});
		// menuitem - Cancel
		jmiDownloadCancelItem = new JMenuItem("Cancel", KeyEvent.VK_C);
		jmiDownloadCancelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Cancel");
				downloadCancel(evt);
			}
		});
		// menuitem - Remove
		jmiDownloadRemoveItem = new JMenuItem("Remove", KeyEvent.VK_M);
		jmiDownloadRemoveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Debug
				System.out.println("Event: Downloads - Remove");
				downloadRemove(evt);
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
		//gán sự kiện ẩn nút
		jbnMainCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				downloadCancel(evt);
					}
				});

		// Resume button
		jbnMainResume = new JButton("Resume");
		jbnMainResume.setIcon(idmResumeBtn);
		// vị trí dọc của chữ trong button
		jbnMainResume.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainResume.setHorizontalTextPosition(SwingConstants.CENTER);
		//gán sự kiện ẩn nút
		jbnMainResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				downloadResume(evt);
					}
				});

		// Pause button
		jbnMainPause = new JButton("Pause");
		jbnMainPause.setIcon(idmPauseBtn);
		// vị trí dọc của chữ trong button
		jbnMainPause.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainPause.setHorizontalTextPosition(SwingConstants.CENTER);
		//gán sự kiện ẩn nút
		jbnMainPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				downloadPause(evt);
			}
		});
		
		// Remove button
		jbnMainRemove = new JButton("Remove");
		jbnMainRemove.setIcon(idmRemoveBtn);
		// vị trí dọc của chữ trong button
		jbnMainRemove.setVerticalTextPosition(SwingConstants.BOTTOM);
		// vị trí ngang của chữ trong button
		jbnMainRemove.setHorizontalTextPosition(SwingConstants.CENTER);
		//gán sự kiện ẩn nút
		jbnMainRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				downloadRemove(evt);
					}
				});
		
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

	// Để thanh tiến trình hiển thị %
	private void initialize() {
		// Set up JTable
		jtbMainDownloadList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				tableChangeDownloader();
			}
		});

		// Only 1 row is selected at a time
		jtbMainDownloadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Init the progress bar
		RendererProgressBar progressBar = new RendererProgressBar(1, 100);
		progressBar.setStringPainted(true);
		jtbMainDownloadList.setDefaultRenderer(JProgressBar.class, progressBar);

	}

	// Exit (tắt IDM)
	private void taskExit(ActionEvent evt) {
		dispose();
	}
	
	//sự kiện Pause
	private void downloadPause(ActionEvent evt) {
		selectedDownloader.pause();
		updateControlButtons();
	}
	//sự kiện Resume
	private void downloadResume(ActionEvent evt) {
		selectedDownloader.resume();
		updateControlButtons();
	}
	//sự kiện Cancel
	private void downloadCancel(ActionEvent evt) {
		selectedDownloader.cancel();
		updateControlButtons();
	}
	//sự kiện Remove
	private void downloadRemove(ActionEvent evt) {
		isClearing = true;
		int index = jtbMainDownloadList.getSelectedRow();
		DownloadManager.getInstance().removeDownload(index);
		tableModel.clearDownload(index);
		isClearing = false;
		selectedDownloader = null;
		updateControlButtons();
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
		if (url != null) {
			// Link hợp lệ thì gọi đối tượng để tạo download
			Download download = DownloadManager.getInstance().createDownload(url,
					DownloadManager.getInstance().getOutputFolder());
			System.out.println(download);
			// một đối tượng download bao gồm các thông tin "File Name", "Size",
			// "Progress",
			// "Transfer rate", "Status" for tableModel
			tableModel.addNewDownload(download);
			jtxTaskAddURL.setText(""); // reset text field
			jDialog.dispose(); // close dialog

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
		jcbOptionConnections.setSelectedItem(DownloadManager.getInstance().getConnectionNumber());
		//
		// Khai báo save location và button browse
		jlbOptionOutputFolder = new JLabel("Save location:");
		jtxOptionOutputFolder = new JTextField(
				new File(DownloadManager.getInstance().getOutputFolder()).getAbsolutePath(), 25);
		jtxOptionOutputFolder.setEditable(false);

		// jtxOptionOutputFolder = new JTextField(25);
		jbnOptionOutputFolderChoose = new JButton("Browse");
		jbnOptionOutputFolderChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				downloadOptionSelectFolder(a);
			}
		});

		// Khai báo button Save
		jbnOptionSave = new JButton("Save");
		jbnOptionSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				downloadOptionSave(evt);
				jDialog.dispose(); // close dialog
			}
		});

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

	private void downloadOptionSave(ActionEvent evt) {
		// Save info to DownloadManager
		try {
			DownloadManager.getInstance().setConnectionNumber((int) jcbOptionConnections.getSelectedItem());
			DownloadManager.getInstance()
					.setOutputFolder(jfcOptionOutputFolderChoose.getSelectedFile().getAbsolutePath());

		} catch (Exception e) {
			// Do nothing when this button hits java.lang.NullPointerException
			// when
			// jfcOptionOutputFolderChoose's path is null

			// System debugs
			System.err.println("WARNING: No Folder was chosen. OutputFolder left as default");
		}

		// System logs
		System.out.println("Saved: connections=" + DownloadManager.getInstance().getConnectionNumber()
				+ " | outputFolder=" + DownloadManager.getInstance().getOutputFolder());

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

	//Theo dõi JTable có gì thay đổi không để update lại JTable
	private void tableChangeDownloader() {
		// Unregister old downloader
		if (selectedDownloader != null)
			selectedDownloader.deleteObserver(MyLayout.this);

		// If downloader is not in delete progress, register this class to be
		// its
		// observer
		if (!isClearing) {
			int index = jtbMainDownloadList.getSelectedRow();
			if (index != -1) {
				selectedDownloader = DownloadManager.getInstance().getDownload(jtbMainDownloadList.getSelectedRow());
				selectedDownloader.addObserver(MyLayout.this);
			} else {
				selectedDownloader = null;
			}
			updateControlButtons();
		}
	}

	// Update lại trạng thái  các dowload và button khi có thay đổi
	@Override
	public void update(Observable arg0, Object arg1) {
		if (selectedDownloader != null && selectedDownloader.equals(arg0))
			updateControlButtons();

	}

	// Trạng thái các button
	private void updateControlButtons() {
		if (selectedDownloader != null) {
			DownloadState state = selectedDownloader.getdState();
			switch (state) {
			case DOWNLOADING:
				System.out.println("Downloading");
				jbnMainResume.setEnabled(false);
				jbnMainPause.setEnabled(true);
				jbnMainCancel.setEnabled(true);
				jbnMainRemove.setEnabled(false);
				jmiDownloadResumeItem.setEnabled(false);
				jmiDownloadPauseItem.setEnabled(true);
				jmiDownloadCancelItem.setEnabled(true);
				jmiDownloadRemoveItem.setEnabled(false);
				break;
			case PAUSE:
				jbnMainResume.setEnabled(true);
				jbnMainPause.setEnabled(false);
				jbnMainCancel.setEnabled(true);
				jbnMainRemove.setEnabled(false);
				jmiDownloadResumeItem.setEnabled(true);
				jmiDownloadPauseItem.setEnabled(false);
				jmiDownloadCancelItem.setEnabled(true);
				jmiDownloadRemoveItem.setEnabled(false);
				break;
			case ERROR:
				jbnMainResume.setEnabled(true);
				jbnMainPause.setEnabled(false);
				jbnMainCancel.setEnabled(false);
				jbnMainRemove.setEnabled(true);
				jmiDownloadResumeItem.setEnabled(true);
				jmiDownloadPauseItem.setEnabled(false);
				jmiDownloadCancelItem.setEnabled(false);
				jmiDownloadRemoveItem.setEnabled(true);
				break;
			default:
				// CANCELLED & COMPLETED
				jbnMainResume.setEnabled(false);
				jbnMainPause.setEnabled(false);
				jbnMainCancel.setEnabled(false);
				jbnMainRemove.setEnabled(true);
				jmiDownloadResumeItem.setEnabled(false);
				jmiDownloadPauseItem.setEnabled(false);
				jmiDownloadCancelItem.setEnabled(false);
				jmiDownloadRemoveItem.setEnabled(true);
			}
		} else {
			// Không có dòng nào trong Jtable được chọn
			jbnMainResume.setEnabled(false);
			jbnMainPause.setEnabled(false);
			jbnMainCancel.setEnabled(false);
			jbnMainRemove.setEnabled(false);
			jmiDownloadResumeItem.setEnabled(false);
			jmiDownloadPauseItem.setEnabled(false);
			jmiDownloadCancelItem.setEnabled(false);
			jmiDownloadRemoveItem.setEnabled(false);
		}
	}
		

	public static void main(String[] args) {
		new MyLayout();
	}
	
	
}
