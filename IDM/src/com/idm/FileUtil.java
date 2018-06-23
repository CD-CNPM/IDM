package com.idm;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;

public class FileUtil {
	
	/**
	 * Get Filename from an URL
	 * Drop queryString like '?' char right after the filename
	 * Clean %20 string as 'space' char in filename
	 * 
	 * @param url
	 * @return String filename
	 */
	public static String getFileNameFromURL(URL url) {
		String filename = "" ;
		String fileURL = url.getFile();
		// lay chuoi con tu filename, bat dau tu ky tu / cuoi cung , ket thuc tai vi tri dau ? cuoi cung trong duong dan file URL
		try {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1, fileURL.lastIndexOf('?'));
		} catch (Exception e) {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		}
		// tra ve filename va thay the %20 thanh khoang trong(hoac bo di %20)
		return filename.replaceAll("%20", " ");
	}
	/**
	 * Join Folder and Filename to make an absolute path
	 * 
	 * @param folderPath
	 * @param fileName
	 * @return
	 */
	public static String joinPath(String folderPath, String fileName) {

		return new File(folderPath, fileName).toString();
	}
	
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0 byte";
	    if(size == 1) return "1 byte";
	    final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.###").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	public static void main(String[] args) {
		FileUtil fu = new FileUtil();
	}
	
}
