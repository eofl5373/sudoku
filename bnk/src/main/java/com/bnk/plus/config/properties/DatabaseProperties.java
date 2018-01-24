package com.bnk.plus.config.properties;

public interface DatabaseProperties {
	
	public String getDatabaseDriver();
	public String getDatabaseUrl();
	public String getDatabaseUsername();
	public String getDatabasePassword();
	public String getDatabaseMod();
	public String getDatabaseUrlLog4();
	public String getDatabaseDriverLog4();
	public int getPartitionCount();
	public int getMinConnectionsPerPartition();
	public int getMaxConnectionsPerPartition();
	
}
