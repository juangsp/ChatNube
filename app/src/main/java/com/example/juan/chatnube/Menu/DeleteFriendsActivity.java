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
import java.util.ArrayList;
import java.util.List;

public class DeleteFriendsActivity extends ListActivity {

    ArrayAdapter adapter;
    ArrayList<String> peticiones;
    ArrayList<ParseUser> friends;
    List<ParseUser> mUser;
    ParseQuery query;
    ParseRelation<ParseUser> friendRelation;
    List<ParseUser> mList;
    ParseUser mCurrentUser;
    ParseUser friend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_friends);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_friends, menu);
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
        friends=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,peticiones);
        setListAdapter(adapter);

            ParseQuery query = ParseUser.getQuery();
            mCurrentUser = ParseUser.getCurrentUser();
            friendRelation = mCurrentUser.getRelation("friendsRelation");
            query = friendRelation.getQuery();

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> list, ParseException e) {

                    if (e == null) {
                        mUser = list;

                        for (ParseUser user : mUser) {
                            friends.add(user);
                            adapter.add(user.getUsername()+" "+user.get("apellido"));
                        }

                    }

                }


            });


    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        eliminarContacto(friends.get(position));
        adapter.remove(friends.get(position));
        adapter.notifyDataSetChanged();


    }
    public  void eliminarContacto(ParseUser user) {


            mCurrentUser = ParseUser.getCurrentUser();
            friendRelation = mCurrentUser.getRelation("friendsRelation");
            friendRelation.remove(user);
            ContactDataSource dataSource = new ContactDataSource(this);
            //dataSource.deleteContact(user.getUsername().toString());
            dataSource.deleteConversation(user.getUsername().toString());

            mCurrentUser.saveInBackground();



        }

}
