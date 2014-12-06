package com.example.pigout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
	 private Button goButton;
	 public HttpResponse response;
	 
	 //private List<String> myIngredients = new ArrayList<String>();
	 List<NameValuePair> myIngredients = new ArrayList<NameValuePair>();
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Ingredients_List_View = (ListView) findViewById(R.id.ingredients); //Declaring the ListViewIngredient
		Appliances_List_View = (ListView) findViewById(R.id.appliances);
		InputIngredient = (EditText) findViewById(R.id.UserInputIngredient);
		addIngredientButton = (Button) findViewById(R.id.AddIngredients);
		goButton = (Button) findViewById(R.id.find_recipe);
		
		//Creating HttpClient and HttpPost to POST the requests to API
		final HttpClient client = new DefaultHttpClient();
		final HttpPost post = new HttpPost("http://www.recipepuppy.com/api/");
		
		
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
				    private boolean add;

					@Override
				    public void onClick(DialogInterface dialog, int which) {
				    	//myIngredients.add(input.getText().toString()); //adds the String input to the ArrayList
				    	myIngredients.add(new BasicNameValuePair(input.getText().toString(), null));
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
		
		try {
			
			post.setEntity(new UrlEncodedFormEntity(myIngredients));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//OnClick of Button
		
		goButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				
				//Excecuting the HTTPPOST request
				
				try {
					response = client.execute(post);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
	}
	

	private void populateListView_Ingredients() {

		//Build the adapter
		ArrayAdapter<NameValuePair> adapter_ingredientView= new ArrayAdapter<NameValuePair>(this, 
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
