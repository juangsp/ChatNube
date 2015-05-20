package com.example.juan.chatnube;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SingUp_activity extends Activity {

    private Button enviar;
    private EditText nombre;
    private EditText contrasena;
    private EditText email;
    private EditText apellido;
    private EditText edad;

    private String nom;
    private String password;
    private String mail;
    private String ape;
    private String ed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_activity);
        nombre=(EditText)findViewById(R.id.username2Field);
        contrasena=(EditText)findViewById(R.id.password2Field);
        email=(EditText)findViewById(R.id.emailField);
        apellido=(EditText)findViewById(R.id.editText3);
        edad=(EditText)findViewById(R.id.editText4);
        enviar=(Button)findViewById(R.id.singUpButton);




    }


    public void enviar(View v){

        nom=nombre.getText().toString();
        nom=nom.trim();
        password=contrasena.getText().toString();
        password=password.trim();
        mail=email.getText().toString();
        mail=mail.trim();
        ape=apellido.getText().toString();
        ape=ape.trim();
        ed=edad.getText().toString();
        ed=ed.trim();
        //String message=String.format(getString(R.string.empty_field_message));
        String title=String.format(getString(R.string.title));

        if(nom.isEmpty()){
            Toast.makeText(this,"Te falta por añadir el nombre",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage( "Es obligatorio un nombre");
            builder.setTitle(title);
            builder.setPositiveButton(android.R.string.ok,null);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog dialogo=builder.create();
            dialogo.show();

        }else{
            ParseUser user=new ParseUser();
            user.setUsername(nom);
            user.setPassword(password);
            user.setEmail(mail);
            user.put("apellido", ape);
            user.put("edad",ed);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(SingUp_activity.this,"Subido Correctamente",Toast.LENGTH_SHORT).show();
                        ContactDataSource dataSource=new ContactDataSource(getApplicationContext());
                        Intent intent=new Intent(SingUp_activity.this,MainActivity2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SingUp_activity.this,"Error al subirlo",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sing_up_activity, menu);
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
}
