package com.example.juanpc.laboratoriomoviles;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.Objects;


public class Master extends Fragment implements android.view.View.OnClickListener{

    ListView listView;
    String id;

    @Override public View onCreateView(LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.fragment_master, container, false);



        FoodAdapter foodAdapter = new  FoodAdapter(getActivity(),this.id);
        listView = (ListView) view.findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override


            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Object object = listView.getItemAtPosition(position);
                ParseObject item = (ParseObject) object;
                String name = item.get("Name").toString();

                Detail new_fragment = new Detail();
                new_fragment.set_id(name);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.placeholder, new_fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listView.setAdapter(foodAdapter);
        foodAdapter.loadObjects();

        return view;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "El platillo ha sido almacenado con exito",  Toast.LENGTH_SHORT).show();
    }


    public void setId(String id) {
        this.id = id;
    }
}
