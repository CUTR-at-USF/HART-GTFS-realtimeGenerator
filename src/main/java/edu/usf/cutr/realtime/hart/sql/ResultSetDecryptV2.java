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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.usf.cutr.realtime.hart.models.TransitDataV2;

/**
 * 
 * @author Khoa Tran
 *
 */

public class ResultSetDecryptV2 {
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private HashMap<String, Integer> columnInfo;

	public ResultSetDecryptV2(ResultSet rs){
		this.rs = rs;
		try {
			this.rsmd = rs.getMetaData();
			columnInfo = new HashMap<String, Integer>();
			columnInfo.putAll(getColumnInfo(rsmd));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public ArrayList<TransitDataV2> decrypt(){
		ArrayList<TransitDataV2> transitData = new ArrayList<TransitDataV2>();
		try {
			while(rs.next()){
				TransitDataV2 td = convertRowToTransitData();
			    if(td!=null)
			        transitData.add(td);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transitData;
	}

	private HashMap<String, Integer> getColumnInfo(ResultSetMetaData rsmd) throws SQLException{
		HashMap<String, Integer> columnInfo = new HashMap<String, Integer>();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnInfo.put(rsmd.getColumnName(i), rsmd.getColumnType(i));
		}
		return columnInfo;
	}
	
	public TransitDataV2 convertRowToTransitData() {
        Iterator<String> columnNames = columnInfo.keySet().iterator();
        
        TransitDataV2 td = null;
        
        while (columnNames.hasNext()) {
                String colName = columnNames.next();
                Object data = getDataWithCorrectDataType( colName, columnInfo.get(colName));
                
                if(data == null) 
                    continue;
                
                if(td == null) {
                    td = new TransitDataV2();
                }
                
                td.setCorrectTransitDataProperties(colName, data);
        }
        return td;
    }
	
	private Object getDataWithCorrectDataType(String colName, int datatype) {
        try {
            switch (datatype) {

                case 2003:
                case -5:
                    return (Long) rs.getLong(colName);
                case -9:
                case -15: // String
                    try {
                        return (String) rs.getString(colName);
                    } catch (NullPointerException npe) {
                        return "";
                    }
                case -2:
                case -7: // Boolean
                    return (Boolean) rs.getBoolean(colName);
                case 2004:
                case 16:
                case 1:
                case 2005:
                case 70:
                case 91:
                case 3:
                case 2001:
                case 8: // Double
                    return (Double) rs.getDouble(colName);
                case 6:
                case 4: // Integer
                    return (Integer) rs.getInt(colName);
                case 2000:
                case -4:
                case -1:
                case 0:
                case 2:
                case 1111:
                case 7:
                case 2006:
                case 5:
                	return (Integer) rs.getInt(colName);
                case 2002:
                case 92:
                case 93: // Timestamp
                    return (Timestamp) rs.getTimestamp(colName);
                case -6:
                	return (Integer) rs.getInt(colName);
                case -3:
                case 12:
                	try {
                        return (String) rs.getString(colName);
                    } catch (NullPointerException npe) {
                        return "";
                    }
                default: {
                    System.out.println("ERROR: unknown sql datatype (" + datatype + ") for column: " + colName);
                    return null;
                }
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("SQL Exception ERROR at " + colName + ": " + ex.getMessage());
        }
        return null;
    }
}
