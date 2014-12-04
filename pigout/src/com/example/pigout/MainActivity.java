package com.example.pigout;

import android.R.string;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	private Object ClickonListView;

	//Button find_recipe; //declaring the go button
	//ListView ingredients;
	 private EditText InputIngredient;
	 private ListView Appliances_List_View;
	 private ListView Ingredients_List_View;
	 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Ingredients_List_View = (ListView) findViewById(R.id.ingredients); //Declaring the ListViewIngredient
		Appliances_List_View = (ListView) findViewById(R.id.appliances);
		InputIngredient = (EditText) findViewById(R.id.UserInputIngredient);
		
		populateListView_Appliances();
		populateListView_Ingredients(); //This function will create the elements in the ListView
	}


	private void populateListView_Ingredients() {
		//Create the list of the elements in the List View
		String[] ingredients_in_array={"It works"};
		
		//Build the adapter
		ArrayAdapter<String> adapter_ingredientView= new ArrayAdapter<String>(this, 
				R.layout.listview_ingredients,
				ingredients_in_array); //Constructing the Adapter
		
				
		//Configure the ListView
		Ingredients_List_View.setAdapter(adapter_ingredientView);
	}
	
	private void populateListView_Appliances() {
		
		//Declaring the Array
		String [] appliances_in_array={"Oven", "Fridge","Microwave", "Mixer"};
		
		//Build the adapter
		ArrayAdapter<String> adapter_appliancesView= new ArrayAdapter<String>(this,
				R.layout.listview_appliances,
				appliances_in_array);
		
		Appliances_List_View.setAdapter(adapter_appliancesView);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
