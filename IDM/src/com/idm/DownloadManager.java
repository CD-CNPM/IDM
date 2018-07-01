package com.idm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.idm.Download;

public class DownloadManager {

	private int connectionNumber;
	// Instance for singleton pattern
	private static DownloadManager instance = null;
	// link output folder chứa file download xuống
	private String outputFolder = new File("").getAbsolutePath();
	// số lượng Thread download đồng thời
	private static final int DEFAULT_NUM_OF_CONNECTIONS = 8;
	// list download
	private ArrayList<Download> downloadList = new ArrayList<>();

	// tạo cùng 1 đối tượng để get khi cần không new đối tượng mới (singleton
	// pattern)
	// list download
	private ArrayList<Download> downloadsList = new ArrayList<>();
	// tạo cùng 1 đối tượng để get khi cần không new đối tượng mới (singleton
	// pattern)

	private DownloadManager() {
		connectionNumber = DEFAULT_NUM_OF_CONNECTIONS;
		downloadList = new ArrayList<Download>();
	}

	public static DownloadManager getInstance() {
		if (instance == null)
			instance = new DownloadManager();

		return instance;
	}

	/**
	 * Verify URL: kiểm tra hệ thống có hỗ trợ url nhập vào không
	 * params: fileURL: url cần check
	 */
	public static URL verifyURL(String fileURL) {
		// nếu không phải là chuẩn http thì báo lỗi
		// if (!fileURL.toLowerCase().startsWith("http://"))
		// return null;
		URL verifiedUrl = null;
		// nếu url hợp lệ, tạo một đối tượng URL
		try {
			verifiedUrl = new URL(fileURL);
		} catch (MalformedURLException e) {
			// MalformedURLException throw nếu protocol không được support
			// hoặc url sai cú pháp
			System.err.println("ERROR: Bad URL");
			return null;
		}
		// kiểm tra url có request đến một file trên server không
		if (verifiedUrl.getFile().length() < 2)
			return null;

		return verifiedUrl;
	}

	/**
	 * Set output folder
	 */
	public void setOutputFolder(String value) {
		outputFolder = value;
	}

	/**
	 * Get output folder
	 */
	public String getOutputFolder() {
		return outputFolder;
	}

	public Download getDownload(int index) {
		return downloadList.get(index);
	}

	/**
	 * Get the downloader object in the list
	 */
	public void removeDownload(int index) {
		downloadList.remove(index);
	}

	public int getConnectionNumber() {
		return connectionNumber;
	}

	/**
	 * Set connection number
	 */
	public void setConnectionNumber(int value) {
		connectionNumber = value;
	}

	public ArrayList<Download> getDownloadList() {
		return downloadList;
	}

	// tạo download khi URL thỏa điều kiện
	public Download createDownload(URL url, String outputFolder) {
		// tạo đối tượng httpdownload(url,outputFolder,số Thread muốn tạo)
		Download download = new HttpDownload(url, outputFolder, DEFAULT_NUM_OF_CONNECTIONS);
		// add đối tượng mới vô ds
		downloadList.add(download);
		// trả về đối tượng Downloader để giao diện lấy thông tin file
		return download;
	}

}
