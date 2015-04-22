package com.example.juan.chatnube;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ChatActivity extends ActionBarActivity {
    ParseUser mCurrentUser;
    public String ID_REMITENTE="";
    public String ID_DESTINATARIO="";
    public String MESSAGE="";


    EditText texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        texto=(EditText)findViewById(R.id.editText2);
        Bundle extras = getIntent().getExtras();
        ID_DESTINATARIO = extras.getString("id_destinatario");
        mCurrentUser=ParseUser.getCurrentUser();
        ID_REMITENTE=mCurrentUser.getObjectId();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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

    public void mandar(View v){
        MESSAGE=texto.getText().toString();
        ParseObject message=createMessage();

        if(message!=null){
            send(message);

        }



    }

    public ParseObject createMessage(){
        ParseObject message =new ParseObject("message");
        message.put("id_destinatario",ID_DESTINATARIO);
        message.put("id_remitente",ID_REMITENTE);
        message.put("mensaje",MESSAGE);

        return message;


    }

    public void send(ParseObject message){

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
