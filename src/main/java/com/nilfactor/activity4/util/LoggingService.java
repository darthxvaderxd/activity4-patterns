package com.nilfactor.activity4.util;

import org.apache.log4j.Logger;

public class LoggingService {
	private static LoggingService instance;
	
	public static LoggingService getInstance() {
		if (instance == null) {
			instance = new LoggingService();
		}
		return instance;
	}
	
	public Logger getLogger(String className) {
		return Logger.getLogger(className);
	}
}
