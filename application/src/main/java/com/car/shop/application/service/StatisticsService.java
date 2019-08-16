package com.car.shop.application.service;

public interface StatisticsService {
	
	long getNumberOfDBConnectionsForCurrentUser();
	
	long getNumberOfAllDBConnections();
	
	void incrementDBConnectionsNumber();
	
	String getCreationDate();

}
