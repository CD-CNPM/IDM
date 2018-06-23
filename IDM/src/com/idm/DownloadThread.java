package com.idm;

import java.net.URL;

public abstract class DownloadThread implements Runnable{
	protected int tThreadID;
	protected URL tURL;
	protected String tOutputFile;
	protected long tStartByte;
	protected long tEndByte;
	private boolean tIsFinished = false;
	protected Thread tThread;
	/**
	 * Constructor
	 * @param threadID
	 * @param url
	 * @param outputFile
	 * @param startByte
	 * @param endByte
	 */
	public DownloadThread(int tThreadID, URL tURL, String tOutputFile, long tStartByte, long tEndByte) {
		this.tThreadID = tThreadID;
		this.tURL = tURL;
		this.tOutputFile = tOutputFile;
		this.tStartByte = tStartByte;
		this.tEndByte = tEndByte;
	}
	public boolean isFinished() {
		return tIsFinished ;
	}
	/**
	 * Start / resume the download thread
	 */
	public void download() {
		tThread = new Thread(this);
		tThread.start();
	}
	public void waitFinish() throws InterruptedException {
		tThread.join();
	}
}
