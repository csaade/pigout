package com.example.pigout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
	
	private Object ClickonListView;


	 private EditText InputIngredient;
	 private ListView Ingredients_List_View;
	 private Button addIngredientButton;
	 private ImageButton goButton;
	 public HttpResponse response;
	 private Button removeIngredientButton;
	 
	 //private List<String> myIngredients = new ArrayList<String>();
	private List<NameValuePair> myIngredients = new ArrayList<NameValuePair>();
	public static  List<String> RecipeNameList = new ArrayList<String>();
	public static List<String> LinkToDirections = new ArrayList<String>();
	public static List<String> IngredientsOfRecipe = new ArrayList<String>();
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.ThreadPolicy policy = new
		StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		

		
		Ingredients_List_View = (ListView) findViewById(R.id.ingredients); //Declaring the ListViewIngredient
		InputIngredient = (EditText) findViewById(R.id.UserInputIngredient);
		addIngredientButton = (Button) findViewById(R.id.AddIngredients);
		goButton = (ImageButton) findViewById(R.id.find_recipe);
		removeIngredientButton = (Button) findViewById(R.id.RemoveIngredients);
		
		
		
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
		
		removeIngredientButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
			
				//prompt the user to enter a string using a dialog
				
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Delete last ingredient?");
				
				// Set up the input
				
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				//input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

				// Set up the buttons
				builder.setPositiveButton("Remove!", new DialogInterface.OnClickListener() { 
				    private boolean add;

					@Override
				    public void onClick(DialogInterface dialog, int which) {
				    	//myIngredients.add(input.getText().toString()); //adds the String input to the ArrayList
						if(myIngredients.size() > 0)
						{
							myIngredients.remove((myIngredients.size()-1));
							populateListView_Ingredients(); //This function will create the elements in the ListView
						}
						
				    		
				 
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
		
		
		goButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				
				//Creating HttpClient and HttpPost to POST the requests to API
				final HttpClient client = new DefaultHttpClient();
				String strUrl = "http://www.recipepuppy.com/api/?i=";
				String inStr = ArrayToString(myIngredients, ",");
				String format_return = "&format=xml";
				String finalStr = strUrl + inStr ;//+ format_return;
				final HttpPost post = new HttpPost(finalStr);
				
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
				
				//Printing the HttpResponse
				try {
					String responseContent;
					responseContent = EntityUtils.toString(response.getEntity());
					Log.d("Response", responseContent);
					try {
						JSONObject jsonObject = new JSONObject(responseContent);
						JSONArray jsonArray = jsonObject.optJSONArray("results");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject recipe = jsonArray.getJSONObject(i);
							String title = recipe.optString("title");
							RecipeNameList.add(title);
							String href = recipe.optString("href");
							LinkToDirections.add(href);
							String ingredient = recipe.optString("ingredients");
							IngredientsOfRecipe.add(ingredient);
							
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					//Printing elements in ArrayLists
					//for (int i = 0; i < RecipeNameList.size(); i++) {
			    	  //  String value = RecipeNameList.get(i);
			    	   // System.out.println(value);
			    	//}
					
					Intent i = new Intent(getApplicationContext(),RecipeView.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}
			
		});
		

		
	}
	
	//ArrayList to String seperated by commas METHOD:
	public static String ArrayToString( List<NameValuePair> myList, String delim)
			{
				StringBuilder sb = new StringBuilder();
				String LoopDelim = "";
				for(NameValuePair s : myList) {
					sb.append(LoopDelim);
					sb.append(s);
					
					LoopDelim = delim;
				}
				return sb.toString();
			};

			

			
	private void populateListView_Ingredients() {

		//Build the adapter
		ArrayAdapter<NameValuePair> adapter_ingredientView= new ArrayAdapter<NameValuePair>(this, 
				R.layout.listview_ingredients,
				myIngredients); //Constructing the Adapter
				
		//Configure the ListView
		Ingredients_List_View.setAdapter(adapter_ingredientView);
		
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
		if (id == R.id.Search) {
			//We display the search view (Main_Activity_View)
			Intent i = new Intent(getApplicationContext(),MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			return true;
		}
		if(id ==R.id.AboutUs) {
			//We display the AboutUs View
			Intent i = new Intent(getApplicationContext(),aboutUs.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
