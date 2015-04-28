package com.example.juan.chatnube;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

;import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 17/04/2015.
 */
public class InboxFragment extends ListFragment {
    ProgressBar spinner;
    List<ParseObject> mMessages;
    ArrayList<String> messages;
    ArrayList<String> mensajes=new ArrayList();
    ArrayList<String> nombres=new ArrayList();
    ArrayAdapter adapter;
    String nombre="";
    String mensaje="";

    public InboxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);
        spinner=(ProgressBar)rootView.findViewById(R.id.progressBar);

        spinner.setVisibility(View.GONE);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        messages=new ArrayList();

        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,messages);
        setListAdapter(adapter);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("message");
        query.whereEqualTo("id_destinatario",ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                mMessages=parseObjects;

                for(ParseObject message:mMessages){


                    nombres.add(message.getString("nombre_remitente"));
                    adapter.add(message.getString("nombre_remitente")+ Html.fromHtml("<br />")+message.getString("mensaje"));
                }

                spinner.setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        //intent.putStringArrayListExtra("mensajes", mensajes);
        //intent.putExtra("mensajes", mMessages.get(position).get("mensaje").toString());
        intent.putExtra("id_destinatario",mMessages.get(position).getObjectId());
        intent.putExtra("nombre_remitente",nombres.get(position).toString());
        startActivity(intent);
    }
}
