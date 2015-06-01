package com.example.juan.chatnube.Menu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.juan.chatnube.DataBase.ContactDataSource;
import com.example.juan.chatnube.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


public class PetitionsActivity extends ListActivity {

    ArrayAdapter adapter;
    ArrayList<String> peticiones;
    ArrayList<String> friends;
    List<ParseObject>mPeticiones;
    ParseQuery query;
    ParseRelation<ParseUser> friendRelation;
    List<ParseUser> mList;
    ParseUser mCurrentUser;
    ParseUser friend;
    ArrayList<ParseObject> petitions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petitions);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_petitions, menu);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        peticiones=new ArrayList<>();
        petitions=new ArrayList<>();
        friends=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,peticiones);
        setListAdapter(adapter);
        ParseQuery<ParseObject> petition = ParseQuery.getQuery("friend_petition");
        petition.whereEqualTo("id_destinatario", ParseUser.getCurrentUser().getObjectId());
        petition.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mPeticiones = list;
                if (e == null && list.size() > 0) {

                    for (ParseObject petition : mPeticiones) {
                        petitions.add(petition);
                        friends.add(petition.get("nombre_remitente").toString());
                        adapter.add(petition.get("nombre_remitente") + " te ha enviado una peticion para que le agregues");
                    }


                }

            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        query= ParseUser.getQuery();
        query.whereEqualTo("username",friends.get(position).toString());
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> list, ParseException e) {

                if (e == null) {
                    mList = list;

                    agregarContacto(mList);
                   petitions.get(position).deleteInBackground();
                }

            }
        });



    }
    public  void agregarContacto(List<ParseUser>list) {
        for (ParseUser user : list) {
            friend = user;
            mCurrentUser = ParseUser.getCurrentUser();
            friendRelation = mCurrentUser.getRelation("friendsRelation");
            friendRelation.add(friend);
            ContactDataSource dataSource = new ContactDataSource(this);
            dataSource.addContact(friend.getUsername());
            dataSource.createTables(friend.getUsername());


            mCurrentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });



        }
    }
}
