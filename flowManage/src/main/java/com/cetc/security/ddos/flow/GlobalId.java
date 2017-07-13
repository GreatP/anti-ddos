package com.cetc.security.ddos.flow;

public class GlobalId {

	public static int priority = 500;
	public static int idleTimeout = 0;
	public static int hardTimeout = 0;
	public static int tableId = 0;
	public static int ethernetType = 2048;
	public static int maxLength = 256;

	public static int getPriority() {
		return priority;
	}

	public static void setPriority(int priority) {
		GlobalId.priority = priority;
	}

	public static int getIdleTimeout() {
		return idleTimeout;
	}

	public static void setIdleTimeout(int idleTimeout) {
		GlobalId.idleTimeout = idleTimeout;
	}

	public static int getHardTimeout() {
		return hardTimeout;
	}

	public static void setHardTimeout(int hardTimeout) {
		GlobalId.hardTimeout = hardTimeout;
	}

	public static int getTableId() {
		return tableId;
	}

	public static void setTableId(int tableId) {
		GlobalId.tableId = tableId;
	}

	public static int getEthernetType() {
		return ethernetType;
	}

	public static void setEthernetType(int ethernetType) {
		GlobalId.ethernetType = ethernetType;
	}

	public static int getMaxLength() {
		return maxLength;
	}

	public static void setMaxLength(int maxLength) {
		GlobalId.maxLength = maxLength;
	}
	
	
	
	
}
