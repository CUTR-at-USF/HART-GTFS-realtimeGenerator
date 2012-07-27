/**
 * Copyright (C) 2012 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.realtime.hart;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;
import org.onebusaway.cli.CommandLineInterfaceLibrary;
import org.onebusaway.guice.jsr250.LifecycleService;
import org.onebusway.gtfs_realtime.exporter.TripUpdatesFileWriter;
import org.onebusway.gtfs_realtime.exporter.TripUpdatesServlet;
import org.onebusway.gtfs_realtime.exporter.VehiclePositionsFileWriter;
import org.onebusway.gtfs_realtime.exporter.VehiclePositionsServlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import edu.usf.cutr.realtime.hart.services.HartToGtfsRealtimeService;

/**
 * 
 * @author bdferris
 * Modified by Khoa Tran 
 *
 */

public class HartToGtfsRealtimeMain {

	private static final String ARG_TRIP_UPDATES_PATH = "tripUpdatesPath";

	private static final String ARG_TRIP_UPDATES_URL = "tripUpdatesUrl";

	private static final String ARG_VEHICLE_POSITIONS_PATH = "vehiclePositionsPath";

	private static final String ARG_VEHICLE_POSITIONS_URL = "vehiclePositionsUrl";
	
	private static final String ARG_REFRESH_INTERVAL = "refreshInterval";

	public static void main(String[] args) throws Exception {
		HartToGtfsRealtimeMain m = new HartToGtfsRealtimeMain();
		m.run(args);
	}

	private HartToGtfsRealtimeService _provider;

	private LifecycleService _lifecycleService;

	@Inject
	public void setProvider(HartToGtfsRealtimeService provider) {
		_provider = provider;
	}

	@Inject
	public void setLifecycleService(LifecycleService lifecycleService) {
		_lifecycleService = lifecycleService;
	}

	public void run(String[] args) throws Exception {

		if (args.length == 0 || CommandLineInterfaceLibrary.wantsHelp(args)) {
			printUsage();
			System.exit(-1);
		}

		Options options = new Options();
		buildOptions(options);
		Parser parser = new GnuParser();
		CommandLine cli = parser.parse(options, args);

		Set<Module> modules = new HashSet<Module>();
		HartToGtfsRealtimeModule.addModuleAndDependencies(modules);
		
		Injector injector = Guice.createInjector(modules);
		injector.injectMembers(this);

		if (cli.hasOption(ARG_TRIP_UPDATES_URL)) {
			URL url = new URL(cli.getOptionValue(ARG_TRIP_UPDATES_URL));
			TripUpdatesServlet servlet = injector.getInstance(TripUpdatesServlet.class);
			servlet.setUrl(url);
		}
		if (cli.hasOption(ARG_TRIP_UPDATES_PATH)) {
			File path = new File(cli.getOptionValue(ARG_TRIP_UPDATES_PATH));
			TripUpdatesFileWriter writer = injector.getInstance(TripUpdatesFileWriter.class);
			writer.setPath(path);
		}

		if (cli.hasOption(ARG_VEHICLE_POSITIONS_URL)) {
			URL url = new URL(cli.getOptionValue(ARG_VEHICLE_POSITIONS_URL));
			VehiclePositionsServlet servlet = injector.getInstance(VehiclePositionsServlet.class);
			servlet.setUrl(url);
		}
		if (cli.hasOption(ARG_VEHICLE_POSITIONS_PATH)) {
			File path = new File(cli.getOptionValue(ARG_VEHICLE_POSITIONS_PATH));
			VehiclePositionsFileWriter writer = injector.getInstance(VehiclePositionsFileWriter.class);
			writer.setPath(path);
		}
		
		if (cli.hasOption(ARG_REFRESH_INTERVAL)) {
		  _provider.setRefreshInterval(Integer.parseInt(cli.getOptionValue(ARG_REFRESH_INTERVAL)));
    }

		_lifecycleService.start();
	}

	private void printUsage() {
		CommandLineInterfaceLibrary.printUsage(getClass());
	}

	protected void buildOptions(Options options) {
		options.addOption(ARG_TRIP_UPDATES_PATH, true, "trip updates path");
		options.addOption(ARG_TRIP_UPDATES_URL, true, "trip updates url");
		options.addOption(ARG_VEHICLE_POSITIONS_PATH, true,
				"vehicle positions path");
		options.addOption(ARG_VEHICLE_POSITIONS_URL, true, "vehicle positions url");
		options.addOption(ARG_REFRESH_INTERVAL, true, "realtime data refresh interval");

	}
}
