package com.example.japancar;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;

public class DataFromFile {
	private Context context;
	private String TOWNS_FILE = "towns.txt";

	public DataFromFile(Context c) {
		context = c;

	}

	public ArrayList<String> townsData() {

		InputStream stream = null;

		ArrayList<String> list = new ArrayList<String>();
		try {
			AssetManager am = context.getAssets();
			stream = am.open(TOWNS_FILE);
			stream = context.getAssets().open(TOWNS_FILE);
		} catch (IOException e) {

		}

		DataInputStream dataStream = new DataInputStream(stream);
		String data = "";
		try {
			while ((data = dataStream.readLine()) != null) {
				list.add(data);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

}