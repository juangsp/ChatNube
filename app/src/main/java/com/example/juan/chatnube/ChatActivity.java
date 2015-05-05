package com.example.juan.chatnube;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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


public class ChatActivity extends Activity {
    ParseUser mCurrentUser;
    List<ParseObject> mMessages;
    public String ID_REMITENTE="";
    public String ID_DESTINATARIO="";
    public String MESSAGE="";
    String accion="";
    String mensaje="";
    String nombre_remitente="";
    ArrayList<String>mensajes=new ArrayList();
    ArrayList<String>usuarios=new ArrayList();
    ChatFragment fragment;
    ArrayAdapter adapter;
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
        Bundle arguments = new Bundle();
        arguments.putString("nombre_remitente", nombre_remitente);

        fragment = ChatFragment.newInstance(arguments);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment, "ChatFragment");
        ft.commit();

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
        ContactDataSource dataSource=new ContactDataSource(this.getApplicationContext());
        dataSource.addMessages(nombre_remitente,MESSAGE,"12/10/2012");
        ParseObject message=createMessage();
        if(message!=null){
            send(message);
            adapter=fragment.getAdapter();
            adapter.add(MESSAGE);
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
