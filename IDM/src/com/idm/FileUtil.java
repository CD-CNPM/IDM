package com.idm;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;

public class FileUtil {
	//get fileName từ URL
	public static String getFileNameFromURL(URL url) {
		String filename = "" ;
		String fileURL = url.getFile();
		
		try {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1, fileURL.lastIndexOf('?'));
		} catch (Exception e) {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		}
		return filename.replaceAll("%20", " ");
	}
	//nối 2 chuỗi string lại: folderPath + fileName
	public static String joinPath(String folderPath, String fileName) {

		return new File(folderPath, fileName).toString();
	}
	
	/**
	 * Convert file size from byte to KB, MB, GB, TB unit if possible
	 * 
	 * @param size
	 * @return String present in human readable value
	 */
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0 byte";
	    if(size == 1) return "1 byte";
	    final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.###").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
}
