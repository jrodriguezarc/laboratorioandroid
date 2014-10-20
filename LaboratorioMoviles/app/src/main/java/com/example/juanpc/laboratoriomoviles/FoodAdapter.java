package com.example.juanpc.laboratoriomoviles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by JuanPC on 07/10/2014.
 */
public class FoodAdapter extends ParseQueryAdapter<ParseObject> {
    public FoodAdapter(Context context){ super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
        public ParseQuery create(){
            ParseQuery query = new ParseQuery("Food");
            return query;
        }
    });
    }


    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent){
        if(v==null){
            v = View.inflate(getContext(), R.layout.fooditem, null);
        }
        super.getItemView(object,v,parent);
        ParseImageView foodImage  = (ParseImageView)   v.findViewById(R.id.icon);
        ParseFile imageFile  = object.getParseFile("Image");
        if(imageFile!=null){
            foodImage.setParseFile(imageFile);
            foodImage.loadInBackground();
        }

        TextView nameTextView = (TextView) v.findViewById(R.id.name);
        nameTextView.setText(object.getString("Name"));
        TextView typeView = (TextView) v.findViewById(R.id.type);
        typeView.setText(object.getString("Type"));
        TextView descView = (TextView) v.findViewById(R.id.desc);
        descView.setText(object.getString("Description"));
        return v;
    }
}