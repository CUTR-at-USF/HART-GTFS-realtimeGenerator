package edu.usf.cutr.realtime.hart.rt.fake;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;
import edu.usf.cutr.realtime.hart.models.TransitDataV2;

public class StopTimesCsvDecrypt {
  
  private final Integer[] DELAY_VALUES = {0, 60, 120, 180};
  
  public ArrayList<TransitDataV2> decrypt(){
    ArrayList<TransitDataV2> transitData = new ArrayList<TransitDataV2>();

    ArrayList<String> tripIds = new ArrayList<String>();

    CSVReader reader = null;
    try {
      reader = new CSVReader(new InputStreamReader(this.getClass().getResourceAsStream("stop_times.txt")));

      String [] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        String currentTimeStamp = new SimpleDateFormat("HH:mm:").format(Calendar.getInstance().getTime());

        String tripId = nextLine[0];
        String time = nextLine[1];
        if(time.contains(currentTimeStamp)){
          if(!tripIds.contains(tripId)){
            tripIds.add(tripId);
            TransitDataV2 transitDataItem = new TransitDataV2();
            transitDataItem.setTripId(tripId);
            
            Random rand = new Random();
            int pickedItem = rand.nextInt(DELAY_VALUES.length);
            transitDataItem.setDelay(DELAY_VALUES[pickedItem]);
            
            transitDataItem.setSequenceNumber(Integer.parseInt(nextLine[4]));
            transitDataItem.setStopId(nextLine[3]);
            
            transitData.add(transitDataItem);
          }
        }
      } 

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e){
      e.printStackTrace();
    } finally {
      if(reader!=null){
        try {
          reader.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return transitData;
  }
}
