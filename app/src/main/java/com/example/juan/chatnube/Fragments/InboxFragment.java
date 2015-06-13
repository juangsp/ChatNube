package com.example.juan.chatnube.Fragments;



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
;import com.example.juan.chatnube.Main.ChatActivity;
import com.example.juan.chatnube.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 17/04/2015.
 */
public class InboxFragment extends ListFragment  {
    ProgressBar spinner;
    List<ParseObject> mMessages;
    ArrayList<String> messages=new ArrayList();
    ArrayList<String> nombres=new ArrayList();
    ArrayList<String> nom=new ArrayList();
    ArrayList<Integer> cont=new ArrayList();
    ArrayAdapter adapter;




    public InboxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        //messages=new ArrayList();

        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,messages);
        setListAdapter(adapter);
        if(ParseUser.getCurrentUser()!=null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
            query.whereEqualTo("id_destinatario", ParseUser.getCurrentUser().getObjectId());
            query.addDescendingOrder("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    mMessages = parseObjects;

                    if (parseObjects.size()>0) {
                        for (ParseObject message : mMessages) {
                            nombres.add(message.getString("nombre_remitente"));
                           // adapter.add(message.getString("nombre_remitente") + Html.fromHtml("<br />") + message.getString("mensaje"));

                        }

                           for (int i = 0; i < nombres.size(); i++) {
                            int contador = 1;
                            String nombre = nombres.get(i).toString();
                            for (int j = i + 1; j < nombres.size(); j++) {
                                if (nombre.equals(nombres.get(j).toString())) {
                                    contador++;
                                    nombres.remove(j);

                                }
                            }
                            nom.add(nombre);
                            cont.add(contador);
                            //adapter.add(nombre + Html.fromHtml("<br />") + "Tienes" + contador + "mensajes nuevos");

                        }
                        for(int i=0;i<nom.size();i++){

                            adapter.add(nom.get(i).toString() + Html.fromHtml("<br />") + "Tienes" + cont.get(i).intValue() + "mensajes nuevos");
                        }
                    }

                }
            });
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        adapter.remove(messages.get(position));
        adapter.notifyDataSetChanged();

        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("id_destinatario",mMessages.get(position).getObjectId());
        intent.putExtra("nombre_remitente", nombres.get(position).toString());
        startActivity(intent);







    }
}
