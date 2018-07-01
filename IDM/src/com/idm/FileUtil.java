package com.idm;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;

public class FileUtil {
	//get fileName từ URL
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
		//lấy url mình nhập vào
		String fileURL = url.getFile();
		// lay chuoi con tu filename, bat dau tu ky tu / cuoi cung , ket thuc tai vi tri dau ? cuoi cung trong duong dan file URL
		try {
			//lấy tên file từ sau dấu / cuối cùng trở về sau đến dấu ?
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1, fileURL.lastIndexOf('?'));
		} catch (Exception e) {
			//nếu không có ? thì lấy tên file từ sau dấu / cuối cùng 
			filename = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		}
		// tra ve filename va thay the %20 thanh khoang trong(hoac bo di %20)
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
	//chuyển size của file thành size cho người dùng đọc được
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0 byte";
	    if(size == 1) return "1 byte";
	    final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.###").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
}
