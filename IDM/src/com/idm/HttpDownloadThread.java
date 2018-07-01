package com.idm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.net.ssl.HttpsURLConnection;

//con của HttpDownload
public class HttpDownloadThread extends DownloadThread {
	public HttpDownloadThread(int tThreadID, URL tURL, String tOutputFile, long tStartByte, long tEndByte, Download download) {
		super(tThreadID, tURL, tOutputFile, tStartByte, tEndByte, download);
	}

	@Override
	public void run() {
		BufferedInputStream bis = null;
		RandomAccessFile raf = null;
		try {
			// open connection
			HttpURLConnection connection = (HttpURLConnection) tURL.openConnection();

			// request the range of byte to download
			String byteRange = tStartByte + "-" + tEndByte;
			connection.setRequestProperty("Range", "bytes=" + byteRange);

			// reset speed metering
			speedRefresh();

			// Connect to server
			connection.connect();

			// Make sure the response code is in the 2xx range / success
			if (connection.getResponseCode() / 100 != 2) {
				// System debugs
				System.err.println(
						"ERROR: (HTTPThread " + tThreadID + ") Server response code: " + connection.getResponseCode());
			}

			// System debug
			System.out.println("HTTP Thread " + tThreadID + " start at byte " + tStartByte);
			System.out.println("- Byte: " + byteRange);

			// Get the input stream
			bis = new BufferedInputStream(connection.getInputStream());

			// Open and write file
			raf = new RandomAccessFile(tOutputFile, "rw");
			raf.seek(tStartByte);

			byte data[] = new byte[HttpDownload.BUFFER_SIZE];
			int numRead;
			while (download.dState == DownloadState.DOWNLOADING	&& ((numRead = bis.read(data, 0, HttpDownload.BUFFER_SIZE)) != -1)) {
				// Write
				raf.write(data, 0, numRead);

				// Increase startbyte for resuming
				tStartByte += numRead;

				// Increase the downloaded size
				download.downloaded(numRead);

				// Count the byte read for speed metering
				tDownloaded += numRead;

			}
			if (download.dState == DownloadState.DOWNLOADING) {
				tIsFinished = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}

			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}
		// System debug
		System.out.println("HTTP Thread " + tThreadID + " end.");
	}

}
