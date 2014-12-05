package com.example.pigout;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	private Object ClickonListView;

	//Button find_recipe; //declaring the go button
	//ListView ingredients;
	 private EditText InputIngredient;
	 private ListView Appliances_List_View;
	 private ListView Ingredients_List_View;
	 private Button addIngredientButton;
	 
	 private List<String> myIngredients = new ArrayList<String>();
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Ingredients_List_View = (ListView) findViewById(R.id.ingredients); //Declaring the ListViewIngredient
		Appliances_List_View = (ListView) findViewById(R.id.appliances);
		InputIngredient = (EditText) findViewById(R.id.UserInputIngredient);
		addIngredientButton = (Button) findViewById(R.id.AddIngredients);
		
		
		
		populateListView_Appliances();
		//populateListView_Ingredients(); //This function will create the elements in the ListView

		addIngredientButton.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {
			
				//prompt the user to enter a string using a dialog
				
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Enter Ingredient");
				
				// Set up the input
				final EditText input = new EditText(MainActivity.this);
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				//input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("Add to list!", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	myIngredients.add(input.getText().toString()); //adds the String input to the ArrayList
				    	populateListView_Ingredients(); //This function will create the elements in the ListView
				    }
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});

				builder.show();
			}
			
		
		});
		
	}
	

	private void populateListView_Ingredients() {

		//Build the adapter
		ArrayAdapter<String> adapter_ingredientView= new ArrayAdapter<String>(this, 
				R.layout.listview_ingredients,
				myIngredients); //Constructing the Adapter
				
		//Configure the ListView
		Ingredients_List_View.setAdapter(adapter_ingredientView);
		
	}
	
	
	
	private void populateListView_Appliances() {
		
		//Declaring the Array
		String [] appliances_in_array={"Oven", "Fridge","Microwave", "Mixer","Toaster"};
		
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
