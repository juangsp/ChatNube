package com.example.juan.chatnube.LogIn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juan.chatnube.Main.MainActivity2;
import com.example.juan.chatnube.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {
    private Button enviar;
    private TextView registro;
    private EditText nombre;
    private EditText contrasena;
    private String nom;
    private String password;
    private int contador=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre=(EditText)findViewById(R.id.usernameField);
        contrasena=(EditText)findViewById(R.id.passwordField);
        enviar=(Button)findViewById(R.id.singUpButton);
        registro=(TextView)findViewById(R.id.registrotextView);

    }

    public void enviar(View v){

        nom=nombre.getText().toString();
        nom=nom.trim();
        password=contrasena.getText().toString();
        password=password.trim();

        //String message=String.format(getString(R.string.empty_field_message));
       ;

        ParseUser.logInInBackground(nom,password, new LogInCallback() {
            String title=String.format(getString(R.string.title));
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent=new Intent(LoginActivity.this,MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivityForResult(intent, 1234);
                } else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage( "Ese usuario no existe");
                    builder.setTitle(title);
                    builder.setPositiveButton(android.R.string.ok,null);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    AlertDialog dialogo=builder.create();
                    dialogo.show();
                }
            }
        });

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void SingUpOnClick(View v){
    Intent in=new Intent(this,SingUp_activity.class);
        startActivity(in);
        registro.setVisibility(View.INVISIBLE);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode ==1234 && resultCode==RESULT_OK){

            int res = data.getExtras().getInt("contador");
            contador=res;

          if(contador==1){
              registro.setVisibility(View.INVISIBLE);
          }


        }

    }
}
