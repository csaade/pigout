package com.example.pigout;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pigout.MainActivity;

public class RecipeView extends ActionBarActivity {
	
	
	private ListView RecipeListView;
	private TextView NumberResults;
	
	private List<String> RecipeList = new ArrayList<String>();
	private ArrayList<String> recipeListNoSpace = new ArrayList<String>();
	private List<String> Links = new ArrayList<String>();
	private List<String> Ingredients = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.recipe_view);
		
		RecipeListView = (ListView) findViewById(R.id.recipeList);
		NumberResults = (TextView) findViewById(R.id.resultsFoundText);
		
		RecipeList = MainActivity.RecipeNameList;
		recipeListNoSpace = trimArray(RecipeList);
		
		Links = MainActivity.LinkToDirections;
		
		Ingredients = MainActivity.IngredientsOfRecipe;
		
		int number_results = RecipeList.size();
		NumberResults.setText(number_results + " Recipes Found");
		populateListView_Recipe();
		
		
		RecipeListView.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                final int position, long id) {

	        	String url = Links.get(position);
	        	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        	startActivity(i);
	          
	        }
	    });
	}
	
	
	private void populateListView_Recipe() {

		//Build the adapter
		ArrayAdapter<String> adapter_recipeView= new ArrayAdapter<String>(this, 
				R.layout.listview_recipes,
				recipeListNoSpace); //Constructing the Adapter
				
		//Configure the ListView
		RecipeListView.setAdapter(adapter_recipeView);
		
	}
	
	private ArrayList<String> trimArray(List<String> array)
	{
		ArrayList<String> trimmedArray = new ArrayList<String>();
		
		for (int i = 0; i < array.size(); i++){
		    String str = array.get(i);
		    String newStr = str.trim();
		    trimmedArray.add(newStr);
		}
		
		return trimmedArray;
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
	
