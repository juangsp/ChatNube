package com.example.juan.chatnube;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by carlosfernandez on 08/03/15.
 */
public class ContactDataSource {


    private Context mContext;
    private ContactSQLiteHelper contactSQLiteHelper;
    private String database_name;
    private String name="";


    public ContactDataSource(Context context) {
        mContext = context;
        contactSQLiteHelper=new ContactSQLiteHelper(mContext);

    }

    public void addContact(String name){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        ContentValues contactValues=new ContentValues();

        contactValues.put(ContactSQLiteHelper.COLUMN_USUARIO,name);
        long memeID;
        memeID=database.insert(ContactSQLiteHelper.CONTACT_TABLE,null,contactValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }

    public void createTables(String name){
        SQLiteDatabase database=openReadable();
        database.beginTransaction();
        contactSQLiteHelper.createTable(database,name);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }


    public void addMessages(String usuario,String mensaje,String fecha){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        ContentValues contactValues=new ContentValues();

        contactValues.put(ContactSQLiteHelper.COLUMN_USUARIO, usuario);
        contactValues.put(ContactSQLiteHelper.COLUMN_MENSAJE, mensaje);
        contactValues.put(ContactSQLiteHelper.COLUMN_FECHA, fecha);

        long memeID;
        memeID=database.insert(usuario,null,contactValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }


    public ArrayList<String> readContact(){
        SQLiteDatabase database=openReadable();
        ArrayList<String>contacs=new ArrayList();
        Cursor cursor;
        cursor=database.query(ContactSQLiteHelper.CONTACT_TABLE,
                new String[]{ContactSQLiteHelper.COLUMN_USUARIO},
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()==true){

            do{

                String name=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUMN_USUARIO);


                contacs.add(name);
            }while(cursor.moveToNext()!=false);
        }
        cursor.close();
        close(database);

        return contacs;
    }


    public ArrayList<String> readMessages(String tabla) {
        SQLiteDatabase database = openReadable();
        ArrayList<String> mensajes=new ArrayList();

        Cursor cursor;
        /*cursor = database.rawQuery("SELECT "+ContactSQLiteHelper.COLUMN_MENSAJE+"  FROM " + tabla + "\n" +
                "WHERE " + ContactSQLiteHelper.COLUMN_USUARIO + "=" +usuario+
                "ORDER BY "+ContactSQLiteHelper.COLUMN_FECHA,null);*/
        cursor = database.rawQuery("SELECT "+ContactSQLiteHelper.COLUMN_MENSAJE+"  FROM " + tabla ,null);

        if (cursor.moveToFirst() == true) {

            do {

                String message = getStringFromColumnName(cursor, ContactSQLiteHelper.COLUMN_MENSAJE);
                mensajes.add(message);

            } while (cursor.moveToNext() != false);
            cursor.close();
            close(database);



        }
        return mensajes;
    }

    public void deleteContact(String name){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        database.delete(ContactSQLiteHelper.CONTACT_TABLE,String.format("%s=%d", ContactSQLiteHelper.COLUMN_USUARIO, name),null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }


    public SQLiteDatabase openWriteable() {
        return contactSQLiteHelper.getWritableDatabase();
    }
    public SQLiteDatabase openReadable() {
        return contactSQLiteHelper.getReadableDatabase();
    }
    public void close(SQLiteDatabase database) {
        database.close();
    }

    private int getIntFromColumnName(Cursor cursor,String name){
        int columIndex=cursor.getColumnIndex(name);
        return cursor.getInt(columIndex) ;
    }
    private String getStringFromColumnName(Cursor cursor,String name){
        int columIndex=cursor.getColumnIndex(name);
        return cursor.getString(columIndex) ;
    }


}
