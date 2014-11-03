package com.example.juanpc.laboratoriomoviles;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by JuanPC on 22/10/2014.
 */
public class Detail extends Fragment {

    ListView listView;
    String _id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = null;
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        DetailAdapter detailAdapter = new  DetailAdapter(getActivity().getApplicationContext(),_id);
        listView = (ListView) view.findViewById(R.id.list_d);
        listView.setAdapter(detailAdapter);
        detailAdapter.loadObjects();


          return view;

    }

    public void set_id(String _id) {
        this._id = _id;
    }


}




