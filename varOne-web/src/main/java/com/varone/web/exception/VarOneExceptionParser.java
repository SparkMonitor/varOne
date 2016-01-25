package com.varone.web.exception;

public class VarOneExceptionParser {
	
	public String parse(Exception e){
		StackTraceElement[] stackTraceElement = e.getStackTrace();
		String message = e.getMessage() + ":" + e + " ";
		for(StackTraceElement stackTrace : stackTraceElement){
			message = message + "\t" + stackTrace.getClassName() + "(" + stackTrace.getFileName() + ":" + stackTrace.getLineNumber() + ")\n";	
		}
		return message;
	}

}
