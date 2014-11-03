package com.example.juanpc.laboratoriomoviles;

/**
 * Created by JuanPC on 23/10/2014.
 */
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
public class DetailAdapter extends ParseQueryAdapter<ParseObject> {

    public DetailAdapter(Context context, final String id){

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
       public ParseQuery create(){
            ParseQuery query = new ParseQuery("Food");
            query.whereEqualTo("Name", id);
           return query;
        }
    });
   }


    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent){
        if(v==null){
            v = View.inflate(getContext(), R.layout.detailitem, null);
        }
        super.getItemView(object,v,parent);
        ParseImageView foodImage  = (ParseImageView)   v.findViewById(R.id.Dicon);
        ParseFile imageFile  = object.getParseFile("Image");
        if(imageFile!=null){
            foodImage.setParseFile(imageFile);
            foodImage.loadInBackground();
        }

        TextView nameTextView = (TextView) v.findViewById(R.id.Dname);
        nameTextView.setText(object.getString("Name"));
        TextView typeView = (TextView) v.findViewById(R.id.Dtype);
        typeView.setText(object.getString("Type"));
        TextView descView = (TextView) v.findViewById(R.id.Ddesc);
        descView.setText(object.getString("Description"));

        return v;
    }


}