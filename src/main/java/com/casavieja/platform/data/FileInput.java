/**
 * 
 */
package com.casavieja.platform.data;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author CARLOS
 *
 */
public class FileInput implements Serializable {
	
	private static final long serialVersionUID = -5254065295244853000L;
	/**
	 * el fragmento de blob de archivo real dividido según el tamaño del fragmento 
	 */
	private MultipartFile fileBlob;
	
	
	
	public FileInput() {
	}
	
	public MultipartFile getFileBlob() {
		return fileBlob;
	}

	public void setFileBlob(MultipartFile fileBlob) {
		this.fileBlob = fileBlob;
	}

	
	public String getExtension() {
		String ext = "";
		if(fileBlob != null) {
			int ind = fileBlob.getOriginalFilename().lastIndexOf(".");
			if(ind > -1) {
				ext = fileBlob.getOriginalFilename().substring(ind+1);
			}
		}
		return ext;
	}
	
}
