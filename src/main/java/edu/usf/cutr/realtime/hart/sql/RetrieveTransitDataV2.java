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

package edu.usf.cutr.realtime.hart.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Khoa Tran
 *
 */

public class RetrieveTransitDataV2 {
	private static final Logger _log = LoggerFactory.getLogger(RetrieveTransitDataV2.class);
	
	String query = "";
	
	public RetrieveTransitDataV2(){
		query = "SELECT " +
				"[vehicle_id], " +
				"[latitude], " +
				"[longitude], " +
				"[time], " +
				"[bearing], " +
				"[speed], " +
				"[route_id], " +
				"[trip_id], " +
				"[delay], " +
				"[stop_id] " +
				"FROM [OrbCAD_III].[dbo].[h_RealTimeData]";
		}	
	
	@SuppressWarnings("finally")
	public ResultSet executeQuery(Connection conn){
		ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            _log.info("Executing SELECT query...");
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            return rs;
        }
	}
}
