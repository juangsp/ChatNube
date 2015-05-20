package com.example.juan.chatnube;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class EditFriendsActivity extends ActionBarActivity {
    EditText recuadro;
    TextView nombre;
    Button buscar;
    Button agregar;
    ParseQuery query;
    ParseRelation<ParseUser> friendRelation;
    ParseUser mCurrentUser;
    ParseUser friend;
    List<ParseUser> mList;
    String usuario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        recuadro=(EditText)findViewById(R.id.editText);
        nombre=(TextView)findViewById(R.id.textView6);
        buscar=(Button)findViewById(R.id.button);
        agregar=(Button)findViewById(R.id.button2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
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
    public void buscar(View v) {
        query= ParseUser.getQuery();
        query.whereEqualTo("email",recuadro.getText().toString());
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> list, ParseException e) {

                if(e==null){
                    mList=list;

                    for(ParseUser user:mList){
                        usuario=user.getUsername().toString();
                        nombre.setText(usuario);
                        friend=user;
                    }
                }else{

                    Toast.makeText(EditFriendsActivity.this, "El contacto no existe", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void agregar(View v){
        mCurrentUser=ParseUser.getCurrentUser();
        friendRelation=mCurrentUser.getRelation("friendsRelation");
        friendRelation.add(friend);
        ContactDataSource dataSource=new ContactDataSource(this.getApplicationContext());
        dataSource.addContact( nombre.getText().toString());
        dataSource.createTables(nombre.getText().toString());


        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Toast.makeText(EditFriendsActivity.this, "Ahora es tu amigo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditFriendsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public ParseObject createPetition(){
        ParseObject message =new ParseObject("friend_petition");
        message.put("id_destinatario",friend.getObjectId());
        message.put("nombre_remitente", ParseUser.getCurrentUser().getUsername());

        return message;

    }
}
