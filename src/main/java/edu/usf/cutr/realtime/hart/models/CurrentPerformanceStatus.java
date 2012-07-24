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

public class CurrentPerformanceStatus {
	private Route route;
	private Trip trip;
	private Incident incident;
	private int lastStopDeviation;
	private Timestamp statusScheduledTime;
	
	public CurrentPerformanceStatus(){
		route = new Route();
		trip = new Trip();
		incident = new Incident();
	}
	
	public void setRouteDirection(Integer dir){
		route.setDirection(dir);
	}
	
	public void setRouteId(int id){
		route.setId(id);
	}
	
	public int getRouteId(){
		return route.getId();
	}
	
	public void setTripId(int id){
		trip.setId(id);
	}
	
	public int getTripId(){
		return trip.getId();
	}
	
	public void setIncidentDesc(String desc){
		incident.setDesc(desc);
	}
	
	public String getIncidentDesc(){
		return incident.getDesc();
	}

	/**
	 * @return the lastStopDeviation
	 */
	public int getLastStopDeviation() {
		return lastStopDeviation;
	}

	/**
	 * @param lastStopDeviation the lastStopDeviation to set
	 */
	public void setLastStopDeviation(int lastStopDeviation) {
		this.lastStopDeviation = lastStopDeviation;
	}
	
	public void setIncidentDateTime(Timestamp dateTime){
		incident.setDateTime(dateTime);
	}

	/**
	 * @return the statusScheduledTime
	 */
	public Timestamp getStatusScheduledTime() {
		return statusScheduledTime;
	}

	/**
	 * @param statusScheduledTime the statusScheduledTime to set
	 */
	public void setStatusScheduledTime(Timestamp statusScheduledTime) {
		this.statusScheduledTime = statusScheduledTime;
	}
}
