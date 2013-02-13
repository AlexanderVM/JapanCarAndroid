package com.example.japancar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.alexd.jsonrpc.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JsonApiParser {
	int count;
	int pages;
	String parsedData;
	ArrayList<String> list = new ArrayList<String>();
	JSONRPCClient client = JSONRPCClient.create(
			"http://www.wiweb.ru/jcgate/jcapi/V2/json-rpc/",
			JSONRPCParams.Versions.VERSION_2);
	JsonApiParser(){
		
	}
	public ArrayList<String> JtownParse() {
		JSONObject resInt = null;
		JSONArray towns = null;
		String town = null;
		JSONObject t = null;
		
	   try {
			resInt = client.callJSONObject("towns.list",
					"petya", 1, 100);
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	  
		  try {
			count = resInt.getInt("count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  pages = count / 100;
		  
		  if (count % 100 != 0) {
			  pages++;
		}

// read all pages
		for (int k = 1; k <= pages; k++) {

			try {
				towns = resInt.getJSONArray("items");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    for (int i = 0; i < towns.length(); i++) {
				
				try {
					t = towns.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					town = t.getString("name");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add(town);
                }
			
			try {
				resInt = client.callJSONObject("towns.list", "petya",
						k + 1, 100);
			} catch (JSONRPCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	    return list;
	}
	
	
	
	public LinkedHashMap<String, String> JCompaniesParse(){
		LinkedHashMap < String, String> list = new LinkedHashMap< String, String>();
		
			JSONArray resInt = null;
			try {
			resInt = client.callJSONArray("auto.firms",
						"petya");
			
			} catch (JSONRPCException e) {
				// TODO Auto-generated catch block
			
			}
			 try {

//				 
			for (int i = 1; i < resInt.length(); i++){	 

                list.put(String.valueOf(resInt.getJSONObject(i).get("id")),
                		 String.valueOf(resInt.getJSONObject(i).get("name")));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
	}
	          
		
			 return list;
}
	
	public ArrayList<String> JParseSpareParts(){
	     
		String spare_part = "";
		JSONArray partsArray;
		
			JSONObject resInt = null;
			try {
			resInt = client.callJSONObject("autoparts.types",
					"petya", 1, 100);
				 count = resInt.getInt("count");
				 pages = count / 100;
				 if (count % 100 != 0) {
					  pages++;
				    }
				 for (int k = 1; k <= pages; k++) {
					 
					  partsArray = resInt.getJSONArray("items");
					  for (int i = 0; i < partsArray.length() ; i++) {
						   spare_part = partsArray.getJSONObject(i).getString("name");
					      		list.add(spare_part);
			                }
					 resInt = client.callJSONObject("autoparts.types",
									"petya", k + 1, 100);
					 }
				 
				  } catch (JSONRPCException e) { e.printStackTrace();
				  } catch (JSONException e){ e.printStackTrace();
			}
			
			 return list;
		
	}
	
	
	public ArrayList<String> JparseModels(String comp_id){

		
			JSONArray resInt = null;
			list.clear();
			try {
			resInt = client.callJSONArray("auto.models",
					"petya", comp_id);
			
			for (int i = 1; i < resInt.length(); i++){	 

                list.add(resInt.getJSONObject(i).getString("name"));
				}
			
			

			} catch (JSONRPCException e){
				e.toString();
			} catch (JSONException e){
				e.toString();
			}		
		       
		
		return  list;
	}
	public JSONObject ParamsParse(HashMap<String, String> resultsMap){
		
		JSONObject params = new JSONObject();
		try{
		   if (!(resultsMap.get("town")).equals("Все города")){ params.put("town",(resultsMap.get("town")));}
		   if (!(resultsMap.get("types")).equals( "Все запчасти")){ params.put("type",resultsMap.get("types"));}
		   if (!(resultsMap.get("marka")).equals("Все компании")){ params.put("marka",resultsMap.get("marka"));}
		   if (!(resultsMap.get("model")).equals("Все модели")){ params.put("model",resultsMap.get("model"));}
		   if (!(resultsMap.get("condition")).equals("Любая")){
			   if ((resultsMap.get("condition")).equals("Контрактная")){ params.put("condition","old");}
			   if ((resultsMap.get("condition")).equals("Новая")){params.put("condition","new");}
		   }
		   if (!(resultsMap.get("pos_fr")).equals("Любая" )){
			   if ((resultsMap.get("pos_fr")).equals("Передняя")){params.put("pos_fr", "F");}
			   if ((resultsMap.get("pos_fr")).equals("Задняя")){params.put("pos_fr", "R");}
		   }
		   if (!(resultsMap.get("pos_rl")).equals("Любая")){
			   if ((resultsMap.get("pos_rl")).equals("Правая")){params.put("pos_rl", "R");}
			   if ((resultsMap.get("pos_rl")).equals("Левая")){params.put("pos_rl","L");}
		   }
		   if (!(resultsMap.get("pos_ud")).equals("Любая")){
			   if ((resultsMap.get("pos_ud")).equals("Верхняя")){params.put("pos_ud","U");}
			   if ((resultsMap.get("pos_ud")).equals("Нижняя")){params.put("pos_ud","D");}
		   }
		   if (!(resultsMap.get("engine")).equals("")){params.put("engine", resultsMap.get("engine"));}
		   if (!(resultsMap.get("body")).equals("")){params.put("body", resultsMap.get("body"));}
	       params.put("with_photos", Boolean.valueOf(resultsMap.get("with_photos")));
	       params.put("with_price", Boolean.valueOf(resultsMap.get("with_price")));
	       params.put("main_photo_size", "48");
		   }catch(JSONException e){
			   e.printStackTrace();
		   }
		return params;
	}
	
	public JSONObject AutoParamsParse(HashMap<String, String> resultsMap){
		JSONObject params = new JSONObject();
		
		try{
		 if (!(resultsMap.get("town")).equals("Все города")){ params.put("town",(resultsMap.get("town")));}
		 if (!(resultsMap.get("marka")).equals("Все компании")){ 
			params.put("firm",resultsMap.get("marka"));
			params.put("model",resultsMap.get("model"));
			}
		  
		if (! resultsMap.get("year_from").equals("")){ params.put("year_from", resultsMap.get("year_from"));}
        if (!resultsMap.get("year_to").equals("")){ params.put("year_to", resultsMap.get("year_to"));}
        if (!resultsMap.get("price_from").equals("")){ params.put("price_from", resultsMap.get("price_from"));}
        if (!resultsMap.get("price_to").equals("")){ params.put("price_to", resultsMap.get("price_to"));}
        params.put("with_photos", Boolean.valueOf(resultsMap.get("with_photos")));
	    params.put("with_price", Boolean.valueOf(resultsMap.get("with_price")));
	    params.put("main_photo_size", "48");
		
		}catch(JSONException e){ e.printStackTrace();}
		return params;
	}
	public ArrayList<ItemsForList> JparsePartsResults(JSONObject params){
		list.clear();
		JSONObject resInt = null;
		JSONArray partsArray = null;
	    ItemsForList itemsObj = new ItemsForList();	
		ArrayList <ItemsForList> results = new ArrayList<ItemsForList>();
		
			try {
				resInt = client.callJSONObject("autoparts.list",
						"petya", 1, 30, params);
				
				count = resInt.getInt("count");
				 pages = count / 100;
				 if (count % 100 != 0) {
					  pages++;
				    }
				      partsArray = resInt.getJSONArray("items");
				      
				      
					  for (int i = 0;i < 30 ; i++){
					  itemsObj = new ItemsForList();
					  itemsObj.setCompany(partsArray.getJSONObject(i).getString("mark"));  
                      itemsObj.setModel(partsArray.getJSONObject(i).getString("model"));
                      itemsObj.setPhoto(partsArray.getJSONObject(i).getString("main_photo"));
				      itemsObj.setId(partsArray.getJSONObject(i).getString("id"));
				      itemsObj.setPrice(partsArray.getJSONObject(i).getString("price"));
				      itemsObj.setName(partsArray.getJSONObject(i).getString("name"));
				      
				      results.add(itemsObj);
				     
					  }
					  
				  } catch (JSONRPCException e) { e.printStackTrace();
				  } catch (JSONException e){ e.printStackTrace();
			}
		
			return results;
	}
	
	public ArrayList<ItemsForList> JparseAutoResults(JSONObject params){
		JSONObject resInt = null;
		JSONArray partsArray = null;
	    ItemsForList itemsObj = new ItemsForList();	
		ArrayList <ItemsForList> results = new ArrayList<ItemsForList>();
		
			try {
				resInt = client.callJSONObject("auto.list",
						"petya", 1, 30, params);
				
				count = resInt.getInt("count");
				 pages = count / 100;
				 if (count % 100 != 0) {
					  pages++;
				    }
				      partsArray = resInt.getJSONArray("items");
				      
					  for (int i = 0;i < 30 ; i++){
					  itemsObj = new ItemsForList();
					  itemsObj.setCompany(partsArray.getJSONObject(i).getString("mark"));  
                      itemsObj.setModel(partsArray.getJSONObject(i).getString("model"));
				      itemsObj.setId(partsArray.getJSONObject(i).getString("id"));
				      itemsObj.setPrice(partsArray.getJSONObject(i).getString("price"));
				      itemsObj.setYear(partsArray.getJSONObject(i).getString("year"));
				      itemsObj.setTown(partsArray.getJSONObject(i).getString("town"));
				      itemsObj.setPhoto(partsArray.getJSONObject(i).getString("main_photo"));
				      results.add(itemsObj);
				     
					  }
					  
				  } catch (JSONRPCException e) { e.printStackTrace();
				  } catch (JSONException e){ e.printStackTrace();
			}
		
			return results;
	}
	
	public HashMap<String,String> JparsePartsExtended(int id){
		JSONObject resInt = null;
		HashMap<String,String> result = new HashMap <String,String>();
		try {
				resInt = client.callJSONObject("autoparts.view",
						"petya", id);
				try {
					result.put("seller", resInt.getString("seller_name"));
					result.put("frame", resInt.getString("body"));
					result.put("model", resInt.getString("model"));
					result.put("pos_RL", resInt.getString("pos_RL"));
					result.put("contect_email", resInt.getString("contact_email"));
					result.put("condition", resInt.getString("condition"));
					result.put("engine", resInt.getString("engine"));
					result.put("contact_phone", resInt.getString("contact_phone"));
					result.put("date", resInt.getString("date"));
					result.put("currency", resInt.getString("currency"));
					result.put("photos", resInt.getString("photos"));
					result.put("price", resInt.getString("price"));
					result.put("name", resInt.getString("name"));
					result.put("town", resInt.getString("town"));
					result.put("note", resInt.getString("note"));
					result.put("contact_name", resInt.getString("contact_name"));
					
				} catch (JSONException e) {}
			    } catch (JSONRPCException e) {}
			
		return result;
	}
	
}
