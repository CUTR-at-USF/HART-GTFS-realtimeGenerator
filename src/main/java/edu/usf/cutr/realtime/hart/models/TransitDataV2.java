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

public class TransitDataV2 {
	private String vehicleId, stopId, tripId, routeId;
	private double vehicleLat, vehicleLon;
	private Timestamp vehicleTime;
	private int vehicleSpeed, delay, vehicleBearing;

	public TransitDataV2(){
	}
	
	public void setCorrectTransitDataProperties(String colName, Object data){
        if (colName.equals("vehicle_id")) {
          this.setVehicleId(data.toString());
        } else if(colName.equals("latitude")){
          this.setVehicleLat((Double)data);
        } else if(colName.equals("longitude")){
          this.setVehicleLon((Double)data);
        } else if(colName.equals("speed")){
          this.setVehicleSpeed((Integer)data);
        } else if(colName.equals("bearing")){
          this.setVehicleBearing((Integer)data);
        } else if(colName.equals("time")){
          this.setVehicleTime((Timestamp)data);
        } else if(colName.equals("route_id")){
            this.setRouteId(data.toString());
        } else if(colName.equals("trip_id")){
            this.setTripId(data.toString());
        } else if(colName.equals("delay")){
            this.setDelay((Integer)data);
        } else if(colName.equals("stop_id")){
            this.setStopId(data.toString());
        } else {
            System.out.println("Cannot map "+colName+" = "+data.toString()+" to any type!");
        }
    }
	
	public String toString(){
		return vehicleId;
	}

  /**
   * @return the vehicleId
   */
  public String getVehicleId() {
    return vehicleId;
  }

  /**
   * @param vehicleId the vehicleId to set
   */
  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  /**
   * @return the stopId
   */
  public String getStopId() {
    return stopId;
  }

  /**
   * @param stopId the stopId to set
   */
  public void setStopId(String stopId) {
    this.stopId = stopId;
  }

  /**
   * @return the tripId
   */
  public String getTripId() {
    return tripId;
  }

  /**
   * @param tripId the tripId to set
   */
  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  /**
   * @return the routeId
   */
  public String getRouteId() {
    return routeId;
  }

  /**
   * @param routeId the routeId to set
   */
  public void setRouteId(String routeId) {
    this.routeId = routeId;
  }

  /**
   * @return the vehicleLat
   */
  public double getVehicleLat() {
    return vehicleLat;
  }

  /**
   * @param vehicleLat the vehicleLat to set
   */
  public void setVehicleLat(double vehicleLat) {
    this.vehicleLat = vehicleLat;
  }

  /**
   * @return the vehicleLon
   */
  public double getVehicleLon() {
    return vehicleLon;
  }

  /**
   * @param vehicleLon the vehicleLon to set
   */
  public void setVehicleLon(double vehicleLon) {
    this.vehicleLon = vehicleLon;
  }

  /**
   * @return the vehicleSpeed
   */
  public int getVehicleSpeed() {
    return vehicleSpeed;
  }

  /**
   * @param vehicleSpeed the vehicleSpeed to set
   */
  public void setVehicleSpeed(int vehicleSpeed) {
    this.vehicleSpeed = vehicleSpeed;
  }

  /**
   * @return the time
   */
  public Timestamp getVehicleTime() {
    return vehicleTime;
  }

  /**
   * @param time the time to set
   */
  public void setVehicleTime(Timestamp vehicleTime) {
    this.vehicleTime = vehicleTime;
  }

  /**
   * @return the bearing
   */
  public int getVehicleBearing() {
    return vehicleBearing;
  }

  /**
   * @param bearing the bearing to set
   */
  public void setVehicleBearing(int bearing) {
    this.vehicleBearing = bearing;
  }

  /**
   * @return the delay
   */
  public int getDelay() {
    return delay;
  }

  /**
   * @param delay the delay to set
   */
  public void setDelay(int delay) {
    this.delay = delay;
  }
}
