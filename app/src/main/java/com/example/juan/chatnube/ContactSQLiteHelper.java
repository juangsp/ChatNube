package com.example.juan.chatnube;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by carlosfernandez on 08/03/15.
 */
public class ContactSQLiteHelper extends SQLiteOpenHelper {
     String database_name = "";
    static final int DATABASE_VERSION = 1;
    String message_table="";
    static final String CONTACT_TABLE="contactos";
    static final String COLUMN_USUARIO="usuario";
    static final String COLUMN_ID="id";
    static final String COLUMN_MENSAJE="mensaje";
    static final String COLUMN_FECHA="fecha";

    String CREATE_TABLE_CONVERSATION="CREATE TABLE "+message_table+" ( "+
            COLUMN_USUARIO+" TEXT NOT NULL, "+
            COLUMN_MENSAJE+" TEXT NOT NULL,"+
            COLUMN_FECHA+ " TEXT NOT NULL);";

    static final String CREATE_TABLE_CONTACTS="CREATE TABLE "+CONTACT_TABLE+" ( "+
            COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
            COLUMN_USUARIO+" TEXT NOT NULL);";



    public ContactSQLiteHelper(Context context, String database_name) {
        super(context, database_name, null, DATABASE_VERSION);
        this.database_name = database_name;

    }

    public void createTable(SQLiteDatabase db,String usuario) {
        message_table = usuario;
        Log.i("nombre",usuario);
        db.execSQL("CREATE TABLE "+usuario+"("+
                COLUMN_USUARIO+" TEXT NOT NULL, "+
                COLUMN_MENSAJE+" TEXT NOT NULL,"+
                COLUMN_FECHA+ " TEXT NOT NULL);"
        );

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
