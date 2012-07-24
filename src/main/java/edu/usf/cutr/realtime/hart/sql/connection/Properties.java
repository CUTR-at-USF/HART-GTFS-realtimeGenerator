/**
 * Copyright 2012 University of South Florida
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package edu.usf.cutr.realtime.hart.sql.connection;

/**
 * 
 * @author Khoa Tran
 *
 */

@SuppressWarnings("serial")
public class Properties extends java.util.Properties{
	
	public void setHost(String host) {
		setProperty("host", host);
	}

	public void setDatabaseName(String databaseName) {
		setProperty("databaseName", databaseName);
	}

	public void setPortNumber(int portNumber) {
		setProperty("portNumber", "" + portNumber);
	}

	public void setUser(String user) {
		setProperty("user", user);
	}

	public void setPassword(String password) {
		setProperty("password", password);
	}

	public String getHost() {
		return getProperty("host");
	}

	public String getDatabaseName() {
		return getProperty("databaseName");
	}

	public Integer getPortNumber() {
		return Integer.parseInt(getProperty("portNumber"));
	}

	public String getUser() {
		return getProperty("user");
	}

	public String getPassword() {
		return getProperty("password");
	}
}
