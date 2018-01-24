package com.bnk.plus.config.properties;

public class MasterDatabaseProperties implements DatabaseProperties{
	
	private String databaseDriver = "db.driver";
	private String databaseUrl = "db.url";
	private String databaseUsername = "db.username";
	private String databasePassword = "db.password";
	private String databaseMod = "log4jdbc.use";
	private String databaseUrlLog4 = "db.url.log4jdbc";
	private String databaseDriverLog4 = "db.driver.log4jdbc";
	
	private int partitionCount = 0;
	private int minConnectionsPerPartition = 0;
	private int maxConnectionsPerPartition = 0;
	
	public String getDatabaseDriver() {
		return databaseDriver;
	}
	public String getDatabaseUrl() {
		return databaseUrl;
	}
	public String getDatabaseUsername() {
		return databaseUsername;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public String getDatabaseMod() {
		return databaseMod;
	}
	public String getDatabaseUrlLog4() {
		return databaseUrlLog4;
	}
	public String getDatabaseDriverLog4() {
		return databaseDriverLog4;
	}
	public int getPartitionCount() {
		return partitionCount;
	}
	public int getMinConnectionsPerPartition() {
		return minConnectionsPerPartition;
	}
	public int getMaxConnectionsPerPartition() {
		return maxConnectionsPerPartition;
	}
	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	public void setDatabaseMod(String databaseMod) {
		this.databaseMod = databaseMod;
	}
	public void setDatabaseUrlLog4(String databaseUrlLog4) {
		this.databaseUrlLog4 = databaseUrlLog4;
	}
	public void setDatabaseDriverLog4(String databaseDriverLog4) {
		this.databaseDriverLog4 = databaseDriverLog4;
	}
	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}
	public void setMinConnectionsPerPartition(int minConnectionsPerPartition) {
		this.minConnectionsPerPartition = minConnectionsPerPartition;
	}
	public void setMaxConnectionsPerPartition(int maxConnectionsPerPartition) {
		this.maxConnectionsPerPartition = maxConnectionsPerPartition;
	}
	
}
