/**
 * 
 */
package com.casavieja.exceptions.models;

import com.casavieja.platform.data.DataResponse;

/**
 * @author CARLOS
 *
 */
public class ResponseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private DataResponse response ;
	public ResponseException(DataResponse response) {
		this.response = response;
	}
	public DataResponse getResponse() {
		return response;
	}
	public void setResponse(DataResponse response) {
		this.response = response;
	}
}
