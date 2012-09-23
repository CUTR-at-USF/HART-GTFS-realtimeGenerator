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

package edu.usf.cutr.realtime.hart;

import java.util.Set;

import org.onebusaway.guice.jsr250.JSR250Module;
import org.onebusway.gtfs_realtime.exporter.GtfsRealtimeExporterModule;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

import edu.usf.cutr.realtime.hart.services.HartToGtfsRealtimeServiceV2;

/**
 * 
 * @author Khoa Tran
 *
 */

public class HartToGtfsRealtimeModule extends AbstractModule{

	public static void addModuleAndDependencies(Set<Module> modules) {
		modules.add(new HartToGtfsRealtimeModule());
		GtfsRealtimeExporterModule.addModuleAndDependencies(modules);
		JSR250Module.addModuleAndDependencies(modules);
	}

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
//		bind(HartToGtfsRealtimeServiceV1.class);
	  bind(HartToGtfsRealtimeServiceV2.class);
	}

}
