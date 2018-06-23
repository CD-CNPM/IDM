package com.idm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadManager {
	// Instance for singleton pattern
	private static DownloadManager instance = null;
	//use to set output fofolder for file downloaded
	private String outputFolder = "";
	//number of connection the thread will create
	private static final int DEFAULT_NUM_OF_CONNECTIONS = 5;
	//list download
	private ArrayList<Download> downloadsList = new ArrayList<>();
	
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
//		if (!fileURL.toLowerCase().startsWith("http://"))
//			return null;
		URL verifiedUrl = null;
		// nếu url hợp lệ, tạo một đối tượng URL
		try {
			verifiedUrl = new URL(fileURL);
		} catch (MalformedURLException e) {
			// MalformedURLException throw nếu protocol không được support hoặc url sai cú pháp
			System.err.println("ERROR: Bad URL");
			return null;
		}
		// kiểm tra url có request đến một file trên server không
		System.out.println(verifiedUrl.getFile());
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

	public Download createDownload(URL url, String outputFolder) {
		Download downloader = new HttpDownloader(url, outputFolder, DEFAULT_NUM_OF_CONNECTIONS);
		downloadsList.add(downloader);
		return downloader;
	}

}
