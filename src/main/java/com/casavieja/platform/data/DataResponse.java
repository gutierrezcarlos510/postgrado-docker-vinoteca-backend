/**
 * 
 */
package com.casavieja.platform.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * @author CARLOS
 *
 */
public class DataResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msg;
	private T data;
	public DataResponse() {
	}

	public DataResponse(String msg) {
		this.msg = msg;
	}
	public DataResponse(T data,String msg) {
		this.msg = msg;
		this.data = data;
	}
	public ResponseEntity<DataResponse> getResult(HttpStatus status){
		return new ResponseEntity<DataResponse>(this, status);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
