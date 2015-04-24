package com.example.juan.chatnube;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends ActionBarActivity {
    ParseUser mCurrentUser;
    List<ParseObject> mMessages;
    public String ID_REMITENTE="";
    public String ID_DESTINATARIO="";
    public String MESSAGE="";
    String accion="";
    String mensaje="";
    String nombre_remitente="";
    //ArrayList<String> mensajes=new ArrayList();

    EditText texto;
    TextView cuadro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        texto=(EditText)findViewById(R.id.editText2);
        cuadro=(TextView)findViewById(R.id.textView7);
        Bundle extras = getIntent().getExtras();
        ID_DESTINATARIO = extras.getString("id_destinatario");
        nombre_remitente=extras.getString("nombre_remitente");
        mCurrentUser=ParseUser.getCurrentUser();
        ID_REMITENTE=mCurrentUser.getObjectId();


        cuadro.setText(cuadro.getText().toString()+ Html.fromHtml("<br />")+mensaje);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query=ParseQuery.getQuery("message");
        query.whereEqualTo("nombre_remitente",nombre_remitente);
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                mMessages=parseObjects;

                for(ParseObject message:mMessages){
                    cuadro.setText(cuadro.getText().toString()+ Html.fromHtml("<br />")+message.getString("mensaje").toString());

                }

            }
        });
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
            cuadro.setText(cuadro.getText().toString()+ Html.fromHtml("<br />")+MESSAGE);
            texto.setText("");


        }

    }

    public ParseObject createMessage(){
        ParseObject message =new ParseObject("message");
        message.put("id_destinatario",ID_DESTINATARIO);
        message.put("nombre_remitente",ParseUser.getCurrentUser().getUsername());
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
