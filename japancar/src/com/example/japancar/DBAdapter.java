package com.example.japancar;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBAdapter {

	public static final String MYDATABASE_NAME = "JC_BASE";
	public static final String MYDATABASE_TABLE = "MY_TABLE";
	public static final String JAPANCAR_CN_TABLE = "CARCOMP_TABLE";
	public static final String JAPANCAR_MN_TABLE = "MODEL_TABLE";
	public static final String JAPANCAR_SP_TABLE = "SPAR_PARTS_TABLE";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_CONTENT = "Content";
	public static final String COMP_ID = "company_id";
	public static final String COMP_NAME = "company_name";
	public static final String MODEL_NAME = "model_name";
	public static final String PARTS_ID = "p_id";
	public static final String PARTS_NAME = "spare_part";

	// create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_DATABASE = "create table "
			+ MYDATABASE_TABLE  + " ("
			  + KEY_ID + " integer primary key autoincrement, "
			  + KEY_CONTENT + " text not null);";
	private static final String CREATE_JCN_TABLE = "create table "
			+ JAPANCAR_CN_TABLE +" (" 
			  + KEY_ID + " integer primary key autoincrement, "
			  + COMP_ID + " integer not null,"
			  + COMP_NAME +" text not null);";
	private static final String CREATE_JM_TABLE = "create table "
			+ JAPANCAR_MN_TABLE +" (" 
			  + KEY_ID + " integer primary key autoincrement, "
			  + COMP_ID + " integer not null,"
			  + MODEL_NAME +" text not null);"; 
	private static final String CREATE_SPAREPARTS_TABLE = "create table "
	          + JAPANCAR_SP_TABLE  + " ("
			  + PARTS_ID + " integer primary key autoincrement, "
			  + PARTS_NAME + " text not null);";
	
	

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	private String TOWNS_FILE = "towns.txt";

	public DBAdapter(Context c) {
		context = c;
	}

	public DBAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public DBAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public long insert(String content) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_CONTENT, content);

		return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
	}

	public void insertFromFile() {

		InputStream stream = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			stream = context.getAssets().open(TOWNS_FILE);
		} catch (IOException e) {

		}

		DataInputStream dataStream = new DataInputStream(stream);
		String data = "";
		try {
			while ((data = dataStream.readLine()) != null) {
				list.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ContentValues values = new ContentValues();
		for (String dat : list) {
			values.put(KEY_CONTENT, dat);
			sqLiteDatabase.insert(MYDATABASE_TABLE, null, values);
		}

	}
	
	
	public void insertTowns(ArrayList<String> list){
        this.openToWrite();
		String[] towns = list.toArray(new String[list.size()]);
		ContentValues contentValues = new ContentValues();
		
		for (int i=0; i < towns.length; i++){
			contentValues.put(KEY_CONTENT, towns[i]);
			sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
		}
		this.close();
	}
	

// Getter from database, returning list of towns
	
	public ArrayList<String> getTownsList(){
		ArrayList<String> list = new ArrayList<String>();
		this.openToRead();
		Cursor cursor = this.queueAll(new String[]{ KEY_ID, KEY_CONTENT}, MYDATABASE_TABLE);
		cursor.moveToFirst();
		
		for (int i=0; i < cursor.getCount() ; i++){
		   
		   list.add(cursor.getString(1));
		   cursor.moveToNext();
		
		}
		this.close();
		return list;
	}


	
// Insert spare parts from json to database	
	
	
	public void insertSpareParts(ArrayList<String> list){
        this.openToWrite();
		String[] spare_parts = list.toArray(new String[list.size()]);
		ContentValues contentValues = new ContentValues();
		
		for (int i=0; i < spare_parts.length; i++){
			contentValues.put(PARTS_NAME, spare_parts[i]);
			sqLiteDatabase.insert(JAPANCAR_SP_TABLE, null, contentValues);
		}
		this.close();
	}
	
	
	public void insertModels(String compId, ArrayList<String> models_list){
		this.openToWrite();
		ContentValues contentValues = new ContentValues();
        String[] models = models_list.toArray(new String[models_list.size()]);
		
        for (int i=0; i < models.length; i++){
           contentValues.put(COMP_ID, compId);
           contentValues.put(MODEL_NAME, models[i]);
	       sqLiteDatabase.insert(JAPANCAR_MN_TABLE, null, contentValues);
        }
	}
	
	
	
	
	public ArrayList<String> getSparePartsList(){
		ArrayList<String> list = new ArrayList<String>();
		this.openToRead();
		Cursor cursor = this.queueAll(new String[]{ PARTS_ID, PARTS_NAME}, JAPANCAR_SP_TABLE);
		cursor.moveToFirst();
		
		for (int i=0; i < cursor.getCount() ; i++){
		   
		   list.add(cursor.getString(1));
		   cursor.moveToNext();
		
		}
		this.close();
		return list;
	}

	
	
	
	
// Insert data about car companies.	
    public void insertCompanies(LinkedHashMap<String, String> companies){
    	
        this.openToWrite();
		ContentValues contentValues = new ContentValues();
		
		Iterator<String> compid = companies.keySet().iterator();
		Iterator<String> compname = companies.values().iterator();
        while(compid.hasNext()){
        	contentValues.put(COMP_ID, compid.next());
        	contentValues.put(COMP_NAME, compname.next());
        	sqLiteDatabase.insert(JAPANCAR_CN_TABLE, null, contentValues);
       
        }
		
    	
    }
    
    public  ArrayList<String> getCompanies(){
    	this.openToRead();
    	ArrayList<String> list = new ArrayList<String>();
    	Cursor cursor = this.queueAll(new String[]{ KEY_ID, COMP_ID, COMP_NAME, }, JAPANCAR_CN_TABLE);
    	cursor.moveToFirst();
		
		for (int i=0; i < cursor.getCount() ; i++){
		   
		   list.add(cursor.getString(2));
		   cursor.moveToNext();
		
		}
    	return list;
    	
    }
	
// 
/*    public ArrayList<String> getModels(String comp_id){
    	this.openToRead();
    	ArrayList<String> list = new ArrayList<String>();
    	Cursor cursor = this.queueAll(new String[]{ KEY_ID, COMP_ID, COMP_NAME, }, JAPANCAR_CN_TABLE);
    	cursor.moveToFirst();
		
		for (int i=0; i < cursor.getCount() ; i++){
		   
		   list.add(cursor.getString(2));
		   cursor.moveToNext();
		}
    	return list;
    }
    	
*/	
	
// Database available?	
	public boolean checkTownBase() {
	     
		boolean dbexist = false;
		if (this.getTownsList().size() > 100){
			dbexist = true;
		}
		return dbexist;	
	}
	

	public boolean checkCompaniesBase() {
	     
		boolean dbexist = false;
		if (this.getCompanies().size() > 20){
			dbexist = true;
		}
		return dbexist;	
	}
	
	public boolean checkSparePartsBase() {
	     
		boolean dbexist = false;
		if (this.getSparePartsList().size() > 20){
			dbexist = true;
		}
		return dbexist;	
	}
	
	public boolean checkModelsList(String comp_id){
		boolean dbexist = false;
		if (this.getModelsList(comp_id).size() > 0){
			dbexist = true;
		}
		return dbexist;
	}
	
	
	public void deleteBase() {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tableName");
	}

	public int deleteAll() {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tableName");
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}

	public Cursor queueAll(String[] columns_string, String table) {
		String[] columns = columns_string;
		Cursor cursor = sqLiteDatabase.query(table, columns, null,
				null, null, null, null);

		return cursor;
	}


	
	
	public String getCompId(String comp_name){
		this.openToRead();
		Cursor cursor = sqLiteDatabase.query(JAPANCAR_CN_TABLE, new String[]{ KEY_ID, COMP_ID, COMP_NAME, }, COMP_NAME + "= ?", new String[]{comp_name},null , null, null, null);
  	    cursor.moveToFirst();
	    return cursor.getString(1);
	}
	public ArrayList<String> getModelsList(String comp_name){
    	this.openToRead();
    	ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = sqLiteDatabase.query(JAPANCAR_MN_TABLE, new String[]{ KEY_ID, COMP_ID, MODEL_NAME, }, COMP_ID + "= ?", new String[]{comp_name},null , null, null, null);
       cursor.moveToFirst();
		for (int i=0; i < cursor.getCount() ; i++){
		   
		   list.add(cursor.getString(2));
		   cursor.moveToNext();
		
		}
    		return list;
    }

	
	// Getting data about towns

	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SCRIPT_CREATE_DATABASE);
			db.execSQL(CREATE_JCN_TABLE);
			db.execSQL(CREATE_SPAREPARTS_TABLE);
			db.execSQL(CREATE_JM_TABLE);
//			fillData(db);
		}

		
// Get Array list of towns	 from file, right now not using because of encoding problems
		public ArrayList<String> getTownsData() {
			InputStream stream = null;
			ArrayList<String> list = new ArrayList<String>();
			try {
				stream = context.getAssets().open(TOWNS_FILE);
			} catch (IOException e) {

			}

			DataInputStream dataStream = new DataInputStream(stream);
			String data = "";
			try {
				while ((data = dataStream.readLine()) != null) {
					list.add(data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return list;
		}

		
// puts data from file		
/*		private void fillData(SQLiteDatabase db) {
			ArrayList<String> data = getTownsData();
			for (String dt : data)
				// Log.d(Constants.DEBUG_TAG,"item="+dt);

				if (db != null) {
					ContentValues values;

					for (String dat : data) {
						values = new ContentValues();
						values.put("KEY_CONTENT", dat);
						db.insert(MYDATABASE_TABLE, null, values);
					}
				} else {
				}
		}
*/
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}