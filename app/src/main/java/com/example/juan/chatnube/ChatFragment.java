package com.example.juan.chatnube;


import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends ListFragment{
    String nombre_remitente;
    ArrayList<String> men=new ArrayList();
    ArrayList<String> mensajes=new ArrayList();
    public static final String TAG = "ChatFragment";
    ArrayAdapter adapter;
    ParseUser mCurrentUser=ParseUser.getCurrentUser();
    List<ParseObject> mMessages;
    TextView tv;
//




    public static ChatFragment newInstance(Bundle arguments){
        ChatFragment f = new ChatFragment();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }
    public ChatFragment( ) {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.chat_fragment, null);
        tv = (TextView)getActivity().findViewById(android.R.id.text1);

        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mensajes);
        setListAdapter(adapter);
        Bundle arguments=getArguments();
        nombre_remitente=arguments.getString("nombre_remitente");



        return rootView;
    }




    @Override
    public void onResume() {
        super.onResume();
        if (ParseUser.getCurrentUser() != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
            query.whereEqualTo("id_destinatario", mCurrentUser.getObjectId());
            final ContactDataSource dataSource = new ContactDataSource(getActivity());


            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    mMessages = parseObjects;
                    if (e == null) {
                        for (ParseObject message : mMessages) {
                            if (message.get("nombre_remitente").equals(nombre_remitente)) {
                                dataSource.addMessages(nombre_remitente, message.getString("mensaje").toString(), "12/11/2012");
                                message.deleteInBackground();
                            }


                        }

                        men = dataSource.readMessages(nombre_remitente);

                        for (int i = 0; i < men.size(); i++) {
                            tv.setText(men.get(i).toString());
                            adapter.add(tv);
                        }

                    }

                }
            });


        }
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }
}
