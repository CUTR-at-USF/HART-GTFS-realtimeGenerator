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

public class Vehicle {
	private int id;
	private double lat, lon;
	private int heading, averageSpeed, predictedDeviation;
	private Timestamp positionDateTime;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}
	
	/**
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	/**
	 * @return the averageSpeed
	 */
	public int getAverageSpeed() {
		return averageSpeed;
	}
	
	/**
	 * @param averageSpeed the averageSpeed to set
	 */
	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
	
	/**
	 * @return the predictedDeviation
	 */
	public int getPredictedDeviation() {
		return predictedDeviation;
	}
	
	/**
	 * @param predictedDeviation the predictedDeviation to set
	 */
	public void setPredictedDeviation(int predictedDeviation) {
		this.predictedDeviation = predictedDeviation;
	}
	
	/**
	 * @return the positionDateTime
	 */
	public Timestamp getPositionDateTime() {
		return positionDateTime;
	}
	
	/**
	 * @param positionDateTime the positionDateTime to set
	 */
	public void setPositionDateTime(Timestamp positionDateTime) {
		this.positionDateTime = positionDateTime;
	}

	/**
	 * @return the heading
	 */
	public int getHeading() {
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(int heading) {
		this.heading = heading;
	}
}
