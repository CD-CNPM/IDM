package com.idm;

import java.io.File;
import java.net.URL;

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
		
		try {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1, fileURL.lastIndexOf('?'));
		} catch (Exception e) {
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		}
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
}
