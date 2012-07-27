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

public class RetrieveTransitData {
	private static final Logger _log = LoggerFactory.getLogger(RetrieveTransitData.class);
	
	String query = "";
	
	public RetrieveTransitData(){
		query = "(SELECT" +
				"				v.vehicle_id," +
				"				v.loc_x AS 'latitude'," +
				"				v.loc_y AS 'longitude'," +
				"				v.average_speed AS 'speed'," +
				"				v.heading AS 'heading'," +
				"				v.vehicle_position_date_time," +
				"				cps.direction_code_id as 'route_direction'," +
				"				cps.route_id," +
				"				cps.trip_id," +
				"				its.incident_desc," +
				"				cps.deviation as 'last_stop_deviation'," +
				"				v.predicted_deviation," +
				"		        CAST( (select top 1 b.bs_id from bus_stop_data b" +
				"		        where  b.booking_num = 2 and b.route_id = cps.route_id and" +
				"		               b.direction_code_id = cps.direction_code_id and" +
				"		               b.seq_num = ctp.global_seq_num) as varchar(6)) as 'previous_stop_id'," +
				"		        CAST( (select top 1 b.bs_id from bus_stop_data b" +
				"		        where  b.booking_num = 2 and b.route_id = cps.route_id and" +
				"		               b.direction_code_id = cps.direction_code_id and" +
				"		               b.seq_num = ntp.global_seq_num) as varchar(6)) as 'next_stop_id'," +
				"				(CAST((" +
				"				  CONVERT(VARCHAR(19), cps.incident_date_time, 102) + ' ' +" +
				"				  CONVERT(VARCHAR(19), ntp.eta,108)" +
				"				  ) AS DATETIME)" +
				"				) as 'next_scheduled_stop_time'," +
				"				cps.incident_date_time," +
				"				(CAST((" +
				"				  CONVERT(VARCHAR(19), cps.incident_date_time, 102) + ' ' +" +
				"				  CONVERT(VARCHAR(19), ctp.eta,108)" +
				"				  ) AS DATETIME)" +
				"				) as 'last_scheduled_time'," +
				"				cps.sched_time as 'status_scheduled_time'" +
				"			FROM" +
				"				dbo.current_performance_status cps" +
				"			JOIN dbo.vehicle v" +
				"				ON" +
				"					cps.vehicle_id = v.vehicle_id" +
				"		    JOIN dbo.trip_timepoint ctp" +
				"		      ON" +
				"		        cps.trip_id = ctp.trip_id" +
				"		      AND" +
				"		        cps.tp_id = ctp.tp_id" +
				"		      AND" +
				"				cps.sched_version = ctp.sched_version" +
				"		    JOIN dbo.trip_timepoint ntp" +
				"		          ON" +
				"		        cps.trip_id = ntp.trip_id" +
				"		      AND" +
				"		        cps.next_tp_id = ntp.tp_id" +
				"		      AND" +
				"				cps.sched_version = ntp.sched_version" +
				"			  AND" +
				"		        ntp.global_seq_num = (" +
				"			      select MIN(global_seq_num)" +
				"			      from trip_timepoint" +
				"			      where" +
				"			        trip_timepoint.tp_id = cps.next_tp_id" +
				"			        and" +
				"			        trip_timepoint.trip_id = cps.trip_id" +
				"			        and" +
				"			        trip_timepoint.sched_version = cps.sched_version" +
				"			        and" +
				"			        trip_timepoint.global_seq_num >= ctp.global_seq_num" +
				"			    )" +
				"			JOIN incident_types its" +
				"			  ON" +
				"			    cps.incident_type = its.incident_type" +
				"			WHERE cps.next_tp_id <> 0 and cps.tp_id <> 0" +
				"			and cps.vehicle_id <> 0 and v.logon_state = 1)" +
				"		UNION" +
				"		(SELECT" +
				"				v.vehicle_id," +
				"				v.loc_x AS 'latitude'," +
				"				v.loc_y AS 'longitude'," +
				"				v.average_speed AS 'speed'," +
				"				v.heading AS 'heading'," +
				"				v.vehicle_position_date_time," +
				"				cps.direction_code_id as 'route_direction'," +
				"				cps.route_id," +
				"				cps.trip_id," +
				"				its.incident_desc," +
				"				cps.deviation," +
				"				v.predicted_deviation," +
				"				null as 'previous_stop_id'," +
				"		        CAST( (select top 1 b.bs_id from bus_stop_data b" +
				"		        where  b.booking_num = 2 and b.route_id = cps.route_id and" +
				"		               b.direction_code_id = cps.direction_code_id and" +
				"		               b.seq_num = ntp.global_seq_num) as varchar(6)) as 'next_stop_id'," +
				"				(CAST((" +
				"				  CONVERT(VARCHAR(19), cps.incident_date_time, 102) + ' ' +" +
				"				  CONVERT(VARCHAR(19), ntp.eta,108)" +
				"				  ) AS DATETIME)" +
				"				) as 'next_scheduled_stop_time'," +
				"				cps.incident_date_time," +
				"				null," +
				"				cps.sched_time" +
				"				" +
				"			FROM" +
				"				dbo.current_performance_status cps" +
				"			JOIN dbo.vehicle v" +
				"				ON" +
				"					cps.vehicle_id = v.vehicle_id" +
				"		    JOIN dbo.trip_timepoint ntp" +
				"		      ON" +
				"		        cps.trip_id = ntp.trip_id" +
				"		      AND" +
				"		        cps.next_tp_id = ntp.tp_id" +
				"		      AND" +
				"				cps.sched_version = ntp.sched_version" +
				"			  AND" +
				"			    ntp.global_seq_num = (" +
				"			      select MIN(global_seq_num)" +
				"			      from trip_timepoint" +
				"			      where" +
				"			        trip_timepoint.tp_id = cps.next_tp_id" +
				"			        and" +
				"			        trip_timepoint.trip_id = cps.trip_id" +
				"			        and" +
				"			        trip_timepoint.sched_version = cps.sched_version" +
				"			    )" +
				"			JOIN incident_types its" +
				"			  ON" +
				"			    cps.incident_type = its.incident_type" +
				"			WHERE cps.next_tp_id <> 0 and cps.tp_id = 0" +
				"			and cps.vehicle_id <> 0 and v.logon_state = 1)" +
				"		UNION" +
				"		(SELECT" +
				"				v.vehicle_id," +
				"				v.loc_x AS 'latitude'," +
				"				v.loc_y AS 'longitude'," +
				"				v.average_speed AS 'speed'," +
				"				v.heading AS 'heading'," +
				"				v.vehicle_position_date_time," +
				"				cps.direction_code_id as 'route_direction'," +
				"				cps.route_id," +
				"				cps.trip_id," +
				"				its.incident_desc," +
				"				cps.deviation," +
				"				v.predicted_deviation," +
				"		        CAST( (select top 1 b.bs_id from bus_stop_data b" +
				"		        where  b.booking_num = 2 and b.route_id = cps.route_id and" +
				"		               b.direction_code_id = cps.direction_code_id and" +
				"		               b.seq_num = ctp.global_seq_num) as varchar(6)) as 'previous_stop_id'," +
				"		        null as 'next_stop_id'," +
				"				null as 'next_scheduled_stop_time'," +
				"				cps.incident_date_time," +
				"				(CAST((" +
				"				  CONVERT(VARCHAR(19), cps.incident_date_time, 102) + ' ' +" +
				"				  CONVERT(VARCHAR(19), ctp.eta,108)" +
				"				  ) AS DATETIME)" +
				"				) AS 'last_scheduled_time'," +
				"				cps.sched_time" +
				"			FROM" +
				"				dbo.current_performance_status cps" +
				"			JOIN dbo.vehicle v" +
				"				ON" +
				"					cps.vehicle_id = v.vehicle_id" +
				"		    JOIN dbo.trip_timepoint ctp" +
				"		      ON" +
				"		        cps.trip_id = ctp.trip_id" +
				"		      AND" +
				"		        cps.tp_id = ctp.tp_id" +
				"		      AND" +
				"				cps.sched_version = ctp.sched_version" +
				"			JOIN incident_types its" +
				"			  ON" +
				"			    cps.incident_type = its.incident_type" +
				"			WHERE cps.next_tp_id = 0 and cps.tp_id <> 0" +
				"			and cps.vehicle_id <> 0 and v.logon_state = 1)";
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
