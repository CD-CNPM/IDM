package com.idm;

import java.net.URL;

public class HttpDownloadThread extends DownloadThread{
	public HttpDownloadThread(int tThreadID, URL tURL, String tOutputFile, long tStartByte, long tEndByte) {
		super(tThreadID, tURL, tOutputFile, tStartByte, tEndByte);
	}

	@Override
	public void run() {
	}

}
