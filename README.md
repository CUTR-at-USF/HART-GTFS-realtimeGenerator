# HART-GTFS-realtimeGenerator [![Build Status](https://travis-ci.org/CUTR-at-USF/HART-GTFS-realtimeGenerator.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/HART-GTFS-realtimeGenerator)

This project retrieves real-time transit data from [Hillsborough Area Regional Transit (HART)'s](http://www.gohart.org/) OrbCAD Automatic Vehicle Location (AVL) system and formats the data into the [General Transit Feed Specification (GTFS)-realtime format](https://developers.google.com/transit/gtfs-realtime/).  GTFS-realtime feeds can be consumed by applications such as [OneBusAway](http://onebusaway.org/) and [Google Transit](http://www.google.com/transit) to provide real-time transit data updates.

In this project, we serve up two types of GTFS-realtime data for HART: 
* [Trip Updates](https://developers.google.com/transit/gtfs-realtime/trip-updates)
* [Vehicle Positions](https://developers.google.com/transit/gtfs-realtime/vehicle-positions)

We use the [OneBusAway GTFS-realtime Exporter project](https://github.com/OneBusAway/onebusaway-gtfs-realtime-exporter/wiki/) to help produce and serve the GTFS-realtime feed.

Here's the architecture that is being used, with this project ("HART GTFS-realtime Generator") being the module in the middle:

<img src="https://github.com/CUTR-at-USF/HART-GTFS-realtimeGenerator/wiki/HART_OrbCAD_GTFS-realtime_architecture.png" width="690" height="248" align=center title="GTFS-realtime Generator"/>

## Building the project

This project uses [Apache Maven](https://maven.apache.org/), so you'll need to install and configure Maven to run on the command line.  Then, run `mvn package` to create the JAR file.

## Running the application

### Setup
This project expects a file `config.properties`, which contains credential information (e.g., username, password) for the database to be present in the parent directory of the application (which avoids accidentally checking this file into version control).  For example, if the `cutr-gtfs-realtime-hart-1.0.1.jar` file is in the directory `c:\gtfs-realtime-feed`, you would need to put the `config.properties` in the `c:\` directory.

The `config.properties` file should contain the following fields:
~~~
host=123.456.789.001
portNumber=1433
databaseName=OrbCAD_III
user=this_is_my_username
password=this_is_my_password
~~~

...where `host` is the IP address of your database server, `portNumber` is set to the database access port (`1433` is the default for Microsoft SQL Server), `databaseName` is set to the name of the database that contains the real-time transit info you want to translate to GTFS-realtime, `this_is_my_username` is replaced with the username of the user with permissions to execute queries against the database, and `this_is_my_password` is replaced with the password of that user.


### Execution

Below are the 5 arguments that we can feed into the program:

1. **tripUpdatesUrl** - The url tripUpdates feed will be located
2. **vehiclePositionsUrl** - The url vehiclePositions feed will be located 
3. **tripUpdatesPath** - The location on disk where tripUpdates feed will be stored
4. **vehiclePositionsPath** - The location on disk where vehiclePositions feed will be stored
5. **refreshInterval** - The amount of time (in second) between two consecutive queries to update new information. The default time is set to **_30 seconds_**. 

Execute the following via the command line to start the software that retrieves the real-time data from HART and hosts the Trip Updates and Vehicle Positions GTFS-realtime files on an embedded Jetty web server:

    java -jar cutr-gtfs-realtime-hart-1.0.1.jar 
      --tripUpdatesUrl=http://localhost:8088/trip-updates  
      --vehiclePositionsUrl=http://localhost:8088/vehicle-positions 
      --tripUpdatesPath=C:\Test\tripUpdates.pb 
      --vehiclePositionsPath=C:\Test\vehiclePositions.pb 
      --refreshInterval=5

**NOTE:** HART's SQL Server database is **not** open access.  Therefore, you will not be able to execute a local copy of this application and pull back information from HART.  However, you can point this tool at your own database to test by editing the `config.properties` file to point to your own database.  The [SQL query](https://github.com/CUTR-at-USF/HART-GTFS-realtimeGenerator/blob/master/src/main/java/edu/usf/cutr/realtime/hart/sql/RetrieveTransitDataV2.java#L40) that is executed to pull back records is:

~~~
SELECT [vehicle] as vehicle_id,[latitude],[longitude],[time],[delay],[speed],[bearing],[route] as route_id,
[trip] as trip_id,[stop] as stop_id,[sequence] as seq FROM h_BusEvents ORDER BY trip_id, vehicle_id, seq ASC;
~~~

Therefore, if you provide a database table with the name `h_BusEvents` with the same column names in the `[]` and some test data, you should be able to pull back this test data and see it formatted in GTFS-realtime.  Note that your database should return only real-time data for this query - if a route/trip does not have real-time data, that record should not be returned by the query.

The code expects the data types for each field to be the following values (using Microsoft SQL Server data types from the database we originally built this for) - if your data is different, you may need to modify [the code](https://github.com/CUTR-at-USF/HART-GTFS-realtimeGenerator/blob/master/src/main/java/edu/usf/cutr/realtime/hart/services/HartToGtfsRealtimeServiceV2.java#L185) to handle different types (e.g., if your speed values are decimals and you want to keep this precision):

1. `vehicle` – varchar
1. `latitude` – decimal
1. `longitude` – decimal
1. `time` – datetime
1. `delay` – int
1. `speed` – int
1. `bearing` – int
1. `route` – varchar
1. `trip` – varchar
1. `stop` – varchar
1. `sequence` – int

Here's an example dataset, based on actual real-time data for vehicles 1007, 2306, and 2308:

~~~
vehicle,latitude,longitude,time,delay,speed,bearing,route,trip,stop,sequence
1007,-82.458206,27.955198,20:08.0,-300,0,210,2,909728,4648,55
2306,-82.479233,27.981339,20:23.0,-180,13,270,14,910705,7378,10
2308,-82.442924,28.073837,19:00.0,-180,5,165,33,911647,605,13
...
~~~

Full example dataset is in a [CSV file here](https://drive.google.com/file/d/0B8oU647elPShLTdQTF9xV2tkYUE/edit?usp=sharing).  You could try to import this data into a SQL database table to test, or manually create the records yourself with the exact same field names.

Fields have the same definitions as the corresponding fields in GTFS-realtime [TripUpdates](https://developers.google.com/transit/gtfs-realtime/reference/TripUpdate) and [VehiclePositions](https://developers.google.com/transit/gtfs-realtime/reference/VehiclePosition).

## Testing the application

### Trip Updates Feed
While the application is running, we can check the Trip Updates feed by browsing to links in an internet browser *on the machine this application is running on*:
* Check `http://localhost:8088/trip-updates?debug` to see the Trip Updates feed in plain text, for HART debugging purposes only.
* Check `http://localhost:8088/trip-updates` to see the feed in [Protocol Buffer format](https://developers.google.com/protocol-buffers/), which is the default GTFS-realtime format that would be consumed by applications.
 
### Vehicle Positions Feed
While the application is running, we can check the Vehicle Positions feed by browsing to following links in an internet browser *on the machine this application is running on*:
* Check `http://localhost:8088/vehicle-positions?debug` to see the feed in plain text, for HART debugging purposes only.
* Check `http://localhost:8088/vehicle-positions` to see the feed in [Protocol Buffer format](https://developers.google.com/protocol-buffers/), which is the default GTFS-realtime format that would be consumed by applications.



## Deployment of this application

This application is currently serving data to the [OneBusAway Tampa instance](http://tampa.onebusaway.org).  The [OneBusAway Tampa wiki](https://github.com/Hillsborough-Transit-Authority/onebusaway-application-modules/wiki) has a detailed system architecture diagram for this deployment.

Here's example links to view the information in the plain text debugging format:

* *http://api.tampa.onebusaway.org:8088/trip-updates?debug* - For estimate arrival time updates
* *http://api.tampa.onebusaway.org:8088/vehicle-positions?debug* - For vehicle positions

...and Protocol Buffer Format (should be used by apps):

* *http://api.tampa.onebusaway.org:8088/trip-updates* - For estimate arrival time updates
* *http://api.tampa.onebusaway.org:8088/vehicle-positions* - For vehicle positions
