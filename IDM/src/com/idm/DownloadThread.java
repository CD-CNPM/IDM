package com.idm;

import java.net.URL;

public abstract class DownloadThread implements Runnable {
	protected int tThreadID;
	protected URL tURL;
	protected String tOutputFile;
	protected long tStartByte;
	protected long tEndByte;
	protected boolean tIsFinished = false;
	protected Thread tThread;
	protected long tStartTime;
	protected long tDownloaded= 0;
	protected Download download;
	/**
	 * Constructor
	 * 
	 * @param threadID
	 * @param url
	 * @param outputFile
	 * @param startByte
	 * @param endByte
	 */
	public DownloadThread(int tThreadID, URL tURL, String tOutputFile, long tStartByte, long tEndByte, Download download) {
		this.tThreadID = tThreadID;
		this.tURL = tURL;
		this.tOutputFile = tOutputFile;
		this.tStartByte = tStartByte;
		this.tEndByte = tEndByte;
		this.download = download;
		download();
	}

	public boolean isFinished() {
		return tIsFinished;
	}

	/**
	 * Start / resume the download thread
	 */
	public void download() {
		tThread = new Thread(this);
		tThread.start();
		System.out.println(tThread);
	}
    
	public void waitFinish() throws InterruptedException {
		tThread.join();
	}
	
	/**
	 * Get current speed of thread
	 */
	public int getSpeed() {
		//thời gian thực hiện download
		long elapsedTime = System.currentTimeMillis() - tStartTime;

		// Refresh cache every 5 secs & prevent fake speed when resume -- mỗi 5s refresh time lại
		if (elapsedTime > 5000) {
			speedRefresh();
			return 0;
		}

		return Math.round(1000f * tDownloaded / elapsedTime);
	}

	/**
	 * Refresh the speed meter
	 */
	protected void speedRefresh() {
		tStartTime = System.currentTimeMillis();
		tDownloaded = 0;
	}
	
}
