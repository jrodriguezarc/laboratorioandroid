package com.example.juanpc.laboratoriomoviles;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class AddFood extends Activity {

        EditText name;
        EditText description;
        EditText type;
        byte arr[];
        private static final int RESULT_LOAD_IMAGE = 1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_food);

            name = (EditText) this.findViewById(R.id.food_name);
            description = (EditText) this.findViewById(R.id.food_desc);
            type = (EditText) this.findViewById(R.id.food_type);


        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.add_food, menu);
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

        public void addFood(View view) {
            ParseObject food = new ParseObject("Food");
            food.put("Name", name.getText().toString());
            food.put("Description", description.getText().toString());
            food.put("Type", type.getText().toString());
            ParseFile file = new ParseFile("photo.png", arr);
            food.put("Image", file);
            food.saveInBackground();
            Toast.makeText(AddFood.this, "El platillo ha sido almacenado con exito", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                try {
                    File file = new File(picturePath);
                    FileInputStream fileStream = new FileInputStream(file);
                    arr = new byte[(int) file.length()];
                    fileStream.read(arr, 0, arr.length);

                } catch (IOException e) {
                }
            }
        }


        public void searching(View view) {
            Intent i = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }


}
