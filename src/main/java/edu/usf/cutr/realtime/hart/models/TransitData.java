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

package edu.usf.cutr.realtime.hart.models;

import java.sql.Timestamp;

/**
 * 
 * @author Khoa Tran
 *
 */

public class TransitData {
	private Vehicle vehicle;
	private TripTimePoint ctp, ntp;
	private CurrentPerformanceStatus cps;

	public TransitData(){
		vehicle = new Vehicle();
		ctp = new TripTimePoint();
		ntp = new TripTimePoint();
		cps = new CurrentPerformanceStatus();
	}
	
	public Vehicle getVehicle(){
		return vehicle;
	}
	
	public TripTimePoint getPreviousTripTimePoint(){
		return ctp;
	}
	
	public TripTimePoint getNextTripTimePoint(){
		return ntp;
	}
	
	public CurrentPerformanceStatus getCurrentPerformanceStatus(){
		return cps;
	}
	
	public void setCorrectTransitDataProperties(String colName, Object data){
        if (colName.equals("vehicle_id")) {
            vehicle.setId((Integer)data);
        } else if(colName.equals("latitude")){
            vehicle.setLat((Double)data);
        } else if(colName.equals("longitude")){
            vehicle.setLon((Double)data);
        } else if(colName.equals("speed")){
            vehicle.setAverageSpeed((Integer)data);
        } else if(colName.equals("heading")){
            vehicle.setHeading((Integer)data);
        } else if(colName.equals("vehicle_position_date_time")){
            vehicle.setPositionDateTime((Timestamp)data);
        } else if(colName.equals("route_direction")){
            cps.setRouteDirection((Integer)data);
        } else if(colName.equals("route_id")){
            cps.setRouteId((Integer)data);
        } else if(colName.equals("trip_id")){
            cps.setTripId((Integer)data);
        } else if(colName.equals("incident_desc")){
            cps.setIncidentDesc((String)data);
        } else if(colName.equals("last_stop_deviation")){
            cps.setLastStopDeviation((Integer)data);
        } else if(colName.equals("predicted_deviation")){
            vehicle.setPredictedDeviation((Integer)data);
        } else if(colName.equals("previous_stop_id")){
            ctp.setId((String)data);
        } else if(colName.equals("next_stop_id")){
        	ntp.setId((String)data);
        } else if(colName.equals("next_scheduled_stop_time")){
            ntp.setScheduledTime((Timestamp)data);
        } else if(colName.equals("incident_date_time")){
            cps.setIncidentDateTime((Timestamp)data);
        } else if(colName.equals("last_scheduled_time")){
            ctp.setScheduledTime((Timestamp)data);
        } else if(colName.equals("status_scheduled_time")){
            cps.setStatusScheduledTime((Timestamp)data);
        } else if(colName.equals("previous_sequence")){
            ctp.setGlobalSequenceNumber((Integer)data);
        } else if(colName.equals("next_sequence")){
        	ntp.setGlobalSequenceNumber((Integer)data);
        } else {
            System.out.println("Cannot map "+colName+" = "+data.toString()+" to any type!");
        }
    }
	
	public String toString(){
		return ((Integer)vehicle.getId()).toString();
	}
}
