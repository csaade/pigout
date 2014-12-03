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
	 private ListView Ingredients_List_View;
	 
	 String [] appliances_in_array={"Oven", "Fridge","Microwave", "Mixer"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Ingredients_List_View = (ListView) findViewById(R.id.ingredients); //Declaring the ListViewIngredient
		InputIngredient = (EditText) findViewById(R.id.UserInputIngredient);
		
		populateListView_Appliances();
		populateListView_Ingredients(); //This function will create the elements in the ListView
		ClickonListView_Ingredients(); //This function would get run when the user clicks on an element in the ListView
	}


	private void populateListView_Ingredients() {
		//Create the list of the elements in the List View
		String[] ingredients_in_array={"It works"};
		
		//Build the adapter
		ArrayAdapter<String> adapter_ingredientView= new ArrayAdapter<String>(this, 
				R.layout.listview_ingredients,
				ingredients_in_array); //Constructing the Adapter
		
				
		//Configure the ListView
		//ListView ingredients_view= (ListView) findViewById(R.id.ingredients);
		Ingredients_List_View.setAdapter(adapter_ingredientView);
	}
	
	private void populateListView_Appliances() {
		//Build the adapter
		ArrayAdapter<String> adapter_appliancesView= new ArrayAdapter<String>(this,
				R.layout.listview_appliances,
				appliances_in_array);
		
		ListView appliances_view= (ListView) findViewById(R.id.appliances);
		appliances_view.setAdapter(adapter_appliancesView);
		
	}

	private void ClickonListView_Ingredients() {
		//Configure the ListView
		ListView ingredients_view= (ListView) findViewById(R.id.ingredients);
		
		ingredients_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			//It would implement this method whenever the user clicks on the listView element
			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position,
					long id) {
				
				populateListView_Ingredients();
				//THE THING THAT I AM SUPPOSED TO DO HERE IS TO ADD THE INGREDIENT THAT THE USER ENTERED
				//HOW???
				
			}
		});
		
		
		
		
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