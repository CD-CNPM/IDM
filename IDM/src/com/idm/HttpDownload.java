package com.idm;

import java.io.RandomAccessFile;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//con của class Download
public class HttpDownload extends Download {

	public HttpDownload(URL url, String outputFolder, int defaultNumOfConnections) {
		super(url, outputFolder, defaultNumOfConnections);
		download();
	}

	@Override
	public void run() {
		System.out.println("run download http loader");
		HttpsURLConnection connection = null;
		RandomAccessFile raf = null;
		try {
			connection = (HttpsURLConnection) dURL.openConnection();
			// nếu quá 10000 giây không kết nối được, thì nghỉ chơi luôn
			connection.setConnectTimeout(10000);

			// kiểm tra response code, nếu không phải mã code 2xx thì báo
			// lỗi (mã code 2xx là request thành công)
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}
			// System debugs
			System.err.println("ERROR: Server response code: " + connection.getResponseCode());

			long contentLength = connection.getContentLengthLong();
			if (contentLength < 1) {
				error();

				// System debugs
				System.err.println("ERROR: The URL is not a file (contentLength < 1) ");
			}

			// in initial, dFileSize is set equal -1
			if (dFileSize == -1) {
				dFileSize = contentLength;
				stateChanged();
				// System debug
				System.out.println("File size: " + dFileSize);
			}
			// DOWNLOADING
			if (dState == DownloadState.DOWNLOADING) {

				// If download have no thread, init and download
				if (dDownloadThreadList.size() == 0) {

					// create randowm access file with read/write permission
					raf = new RandomAccessFile(FileUtil.joinPath(dOutputFolder, dFileName), "rw");
					raf.setLength(dFileSize);
					raf.close();
					System.out.println(validateServerResume());
					System.out.println(dFileSize > MIN_DOWNLOAD_SIZE);
					if (dFileSize > MIN_DOWNLOAD_SIZE && validateServerResume()) {
						// Calculate size for each part
						long partSize = (long) Math.ceil((((float) dFileSize / dConnections) / BLOCK_SIZE))
								* BLOCK_SIZE;

						// System debug
						System.out.println("Part size: " + partSize);

						// Calculate start/end byte
						long startByte = 0;
						long endByte = partSize - 1;
						DownloadThread downloadThread;

						// DownloadThread downloadThread = new
						// HttpDownloadThread(1, URL,
						// FileUtil.joinPath(outputFolder, dFileName),
						// startByte, endByte);
						// dDownloadThreadList.add(downloadThread);

						// Add other threads
						for (int i = 1; endByte < dFileSize; i++) {
							startByte = endByte + 1;
							// The last thread is end at the end size of
							// filesize
							if (i == dConnections) {
								endByte = dFileSize;
							} else {
								endByte += partSize;
							}
							downloadThread = new HttpDownloadThread(i, dURL,
									FileUtil.joinPath(dOutputFolder, dFileName), startByte, endByte, this);
							dDownloadThreadList.add(downloadThread);
						}
					}
					// If file size smaller than 400KB or not support resume,
					// use one thread
					else {
						System.out.println("download use one thread");
						HttpDownloadThread downloadThread = new HttpDownloadThread(1, dURL,
								FileUtil.joinPath(dOutputFolder, dFileName), 0, (int) dFileSize, this);
						dDownloadThreadList.add(downloadThread);
					}
				}
				// Resume all threads if download already have thread list
				else {
					System.out.println("resume all thread if download already have thread list");
					for (DownloadThread thread : dDownloadThreadList) {
						if (!thread.isFinished()) {
							System.out.println("wait finish");
							thread.download();
						}
					}
				}

				// Waiting for all threads to complete
				for (DownloadThread thread : dDownloadThreadList) {
					if (!thread.isFinished()){
						thread.waitFinish();
					}
						
				}
				// Mark state as completed
				if (dState == DownloadState.DOWNLOADING) {
					setDownloadState(DownloadState.COMPLETED);
				}
			}
		} catch (Exception e) {
			error();
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}

}
