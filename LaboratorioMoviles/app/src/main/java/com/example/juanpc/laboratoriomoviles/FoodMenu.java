package com.example.juanpc.laboratoriomoviles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;


public class FoodMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "SeCU4zHj1PbXG1VPj7xXUaPqO5SS3VfufMFjAfUH", "FktMWxHocRPmqQJqyeEpol4cTTt1jUpkRSb1TAQ0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        FoodAdapter foodAdapter = new  FoodAdapter(this);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(foodAdapter);
        foodAdapter.loadObjects();

        Log.d("Mi App", "Estoy en el onCreate");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void jumpToPage(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), AddFood.class));
    }


}
