package com.example.juan.chatnube.Main;



import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.juan.chatnube.DataBase.ContactDataSource;
import com.example.juan.chatnube.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class ChatActivity extends ListActivity {

    public String ID_REMITENTE="";
    public String ID_DESTINATARIO="";
    public String MESSAGE="";
    TextView tv;
    String nombre_remitente="";
    ArrayList<String>usuarios=new ArrayList();
    EditText texto;
    ArrayList<String> men=new ArrayList();
    ArrayList<String> mensajes=new ArrayList();
    public static final String TAG = "ChatFragment";
    ArrayAdapter adapter;
    ParseUser mCurrentUser=ParseUser.getCurrentUser();
    List<ParseObject> mMessages;
    GregorianCalendar date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        texto=(EditText)findViewById(R.id.editText2);

        Bundle extras = getIntent().getExtras();
        ID_DESTINATARIO = extras.getString("id_destinatario");
        nombre_remitente=extras.getString("nombre_remitente");
        mCurrentUser=ParseUser.getCurrentUser();
        ID_REMITENTE=mCurrentUser.getObjectId();
        date=new GregorianCalendar();

        tv = (TextView)findViewById(android.R.id.text1);


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

    @Override
    public void onResume() {
        super.onResume();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mensajes);
        setListAdapter(adapter);
        if (ParseUser.getCurrentUser() != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
            query.whereEqualTo("id_destinatario", mCurrentUser.getObjectId());
            query.addDescendingOrder("createdAt");
            final ContactDataSource dataSource = new ContactDataSource(this);


            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    mMessages = parseObjects;
                    if (e == null) {
                        for (ParseObject message : mMessages) {
                            if (message.get("nombre_remitente").equals(nombre_remitente)) {
                                dataSource.addMessages(nombre_remitente, message.getString("mensaje").toString(), getdate(),nombre_remitente);
                                message.deleteInBackground();
                            }


                        }

                        men = dataSource.readMessages(nombre_remitente);
                        usuarios=dataSource.readUser(nombre_remitente);
                        if(!adapter.isEmpty()){
                            for(int i=0;i<mensajes.size();i++) {
                                adapter.remove(mensajes.get(i));
                            }
                        }
                        for (int i = 0; i < men.size(); i++) {

                            if(usuarios.get(i).equals(nombre_remitente)){
                                adapter.add(nombre_remitente+": "+men.get(i).toString());

                            }else{
                                adapter.add(mCurrentUser.getUsername().toString()+": "+men.get(i).toString());
                            }


                        }

                    }

                }
            });


        }
    }

    public void mandar(View v){
        MESSAGE=texto.getText().toString();
        ContactDataSource dataSource=new ContactDataSource(this.getApplicationContext());
        dataSource.addMessages(nombre_remitente, MESSAGE, getdate(), mCurrentUser.getUsername().toString());
        ParseObject message=createMessage();
        if(message!=null){
            send(message);
            adapter.add(mCurrentUser.getUsername().toString() + ": " + MESSAGE);
            texto.setText("");
        }
    }

    public ParseObject createMessage(){
        ParseObject message =new ParseObject("message");
        message.put("id_destinatario",ID_DESTINATARIO);
        message.put("nombre_remitente",ParseUser.getCurrentUser().getUsername());
        message.put("id_remitente",ID_REMITENTE);
        message.put("mensaje", MESSAGE);



        return message;

    }

    public void send(ParseObject message){

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String getdate() {
        int d = date.get(GregorianCalendar.DAY_OF_MONTH);
        int m = date.get(GregorianCalendar.MONTH);
        int y = date.get(GregorianCalendar.YEAR);
        String s = d + "/" + m + "/" + y;
        return s;
    }
}
