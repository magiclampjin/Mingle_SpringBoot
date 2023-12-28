package com.mingle.exceptions;

public class AxiosPageAsyncException extends Exception{
	
	public AxiosPageAsyncException() {
		// TODO Auto-generated constructor stub
	}
	
	public AxiosPageAsyncException(String message) {
		super(message);
	}
	
	
	// 예외를 발생시키는 메서드
    public void throwCustomException() throws AxiosPageAsyncException {
        throw new AxiosPageAsyncException("Page Async Exception occured.");
    }
}
