package com.idm;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

import javax.net.ssl.HttpsURLConnection;

public abstract class Download extends Observable implements Runnable {
	// url lấy ra đối tượng để download (lấy data từ địa chỉ web để download -
	// mở kết nối)
	protected URL dURL;
	protected String dOutputFolder;
	// số lượng Thread được tạo ra
	protected int dConnections;
	protected String dFileName;
	protected long dFileSize;
	protected DownloadState dState = DownloadState.DOWNLOADING;
	protected int dDownloaded = 0;
	protected File outputFolder;
	// state of download
	protected ArrayList<DownloadThread> dDownloadThreadList = new ArrayList<>();

	// MIN_DOWNLOAD_SIZE = 4096*100 nghĩa là file size trên 400KB mới chia Thread để download 
	protected static final int BLOCK_SIZE = 4096;
	protected static final int BUFFER_SIZE = 4096;
	protected static final int MIN_DOWNLOAD_SIZE = BLOCK_SIZE * 100;

	public Download(URL uRL, String outputFolder, int connections) {
		dURL = uRL;
		this.dOutputFolder = outputFolder;
		this.dConnections = connections;
		// filesize = -1 là file lúc chưa khởi tạo
		dFileSize = -1;
		dFileName = FileUtil.getFileNameFromURL(dURL);
		validateFile();
		// System debug
		System.out.println("Filename: " + dFileName);
	}
	public String getdFileName() {
		return dFileName;
	}

	public void setdFileName(String dFileName) {
		this.dFileName = dFileName;
	}

	public long getdFileSize() {
		return dFileSize;
	}

	public void setdFileSize(long dFileSize) {
		this.dFileSize = dFileSize;
	}

	public DownloadState getdState() {
		return dState;
	}

	public void setdState(DownloadState dState) {
		this.dState = dState;
	}

	public float getProgress() {
		return ((float) dDownloaded / dFileSize);
	}

	/** set state for the download */
	public void setDownloadState(DownloadState downloadState) {
		this.dState = downloadState;
	}

	/**
	 * Start / resume download
	 */
	protected void download() {
		setDownloadState(dState);
		Thread t = new Thread(this);
		t.start();
	}

	protected void error() {
		setDownloadState(DownloadState.ERROR);
	}

	protected void stateChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if the server accept resume or not, because some server does not
	 * support multi-download
	 */
	protected boolean validateServerResume() {
		HttpURLConnection connection = null;
		boolean isSupported = false;
		try {
			// open connection
			connection = (HttpURLConnection) dURL.openConnection();
			// nếu quá 10000 giây không kết nối được, thì nghỉ chơi luôn
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("Range", "bytes=10-20");
			connection.connect();

			// HTTP/206: Partial Content: Support multiple simultaneous streams
			if (connection.getResponseCode() == 206) {
				isSupported = true;

				// System debugs
				System.out.println("Server support resume");
			}

		} catch (IOException e) {
			error();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return isSupported;
	}
	/**
	 * Get the current speed of the download
	 */
	public int getSpeed() {
		int currentSpeed = 0;
		for (DownloadThread thread : dDownloadThreadList) {
			currentSpeed += thread.getSpeed();
		}
		return currentSpeed;
	}
	@Override
	public String toString() {
		return "Download [dURL=" + dURL + ", dOutputFolder=" + dOutputFolder + ", dConnections=" + dConnections
				+ ", dFileName=" + dFileName + ", dFileSize=" + dFileSize + ", dState=" + dState + ", dDownloaded="
				+ dDownloaded + ", dDownloadThreadList=" + dDownloadThreadList + "]";
	}
	/**
	 * Increase the downloaded size
	 */
	protected synchronized void downloaded(long value) {
		dDownloaded += value;
		stateChanged();
	}
	protected void validateFile() {
		File f = new File(FileUtil.joinPath(dOutputFolder, dFileName));
		if (f.exists() && !f.isDirectory()) {
			dFileName = "Copy of " + dFileName;
			validateFile();
		}
	}
	
}
