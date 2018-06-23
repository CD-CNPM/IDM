package com.idm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Download extends Observable implements Runnable {
	protected URL dURL;
	protected String dOutputFolder;
	protected int dConnections;
	protected String dFileName;
	protected long dFileSize;
	protected DownloadState dState = DownloadState.DOWNLOADING;

	// state of download
	protected ArrayList<DownloadThread> dDownloadThreadList = new ArrayList<>();

	// Default constant
	protected static final int BLOCK_SIZE = 4096;
	protected static final int BUFFER_SIZE = 4096;
	protected static final int MIN_DOWNLOAD_SIZE = BLOCK_SIZE * 100;

	public Download(URL uRL, String outputFolder, int connections) {
		dURL = uRL;
		this.dOutputFolder = outputFolder;
		this.dConnections = connections;
		dFileSize = -1;
		dFileName = FileUtil.getFileNameFromURL(dURL);
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
		}
		finally {
			if(connection != null){
				connection.disconnect();
			}
		}
		return isSupported;
	}
}
