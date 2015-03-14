package es.examen.chorbagendaad.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlosfernandez on 08/03/15.
 */
public class ContactDataSource {


    private Context mContext;
    private ContactSQLiteHelper contactSQLiteHelper;

    public ContactDataSource(Context context) {
        mContext = context;
        contactSQLiteHelper=new ContactSQLiteHelper(mContext);



    }

    public void create(Person person){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        ContentValues contactValues=new ContentValues();


        contactValues.put(ContactSQLiteHelper.COLUMN_NOMBRE, person.getName());
        contactValues.put(ContactSQLiteHelper.COLUM_DIRECCION, person.getAddress());
        contactValues.put(ContactSQLiteHelper.COLUM_TELEFONO,person.getTelephone());
        contactValues.put(ContactSQLiteHelper.COLUM_RATIO,person.getRating());
        //contactValues.put(ContactSQLiteHelper.COLUM_EMAIL,person.getEmail());
        long memeID;
        memeID=database.insert(ContactSQLiteHelper.CONTACT_TABLE,null,contactValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }


    public ArrayList<Person> readContact(){
        SQLiteDatabase database=openReadable();
        ArrayList<Person>contacs=new ArrayList();
        Cursor cursor;
        cursor=database.query(ContactSQLiteHelper.CONTACT_TABLE,
                new String[]{ContactSQLiteHelper.COLUMN_ID,ContactSQLiteHelper.COLUMN_NOMBRE,ContactSQLiteHelper.COLUM_DIRECCION,ContactSQLiteHelper.COLUM_TELEFONO,ContactSQLiteHelper.COLUM_RATIO},
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()==true){
            Person person;
            do{
                int id=getIntFromColumnName(cursor,ContactSQLiteHelper.COLUMN_ID);
                String name=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUMN_NOMBRE);
                String direccion=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUM_DIRECCION);
                String telefono=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUM_TELEFONO);
                int ratio=getIntFromColumnName(cursor,ContactSQLiteHelper.COLUM_RATIO);
                //String mail=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUM_EMAIL);
                person=new Person(id,name,direccion,telefono,ratio);
                contacs.add(person);
            }while(cursor.moveToNext()!=false);
        }
        cursor.close();
        close(database);

        return contacs;
    }


    public Person read(int _id) {
        SQLiteDatabase database = openReadable();
        Person person=null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + ContactSQLiteHelper.CONTACT_TABLE + "\n" +
                "WHERE " + ContactSQLiteHelper.COLUMN_ID + "=" + _id, null);

        if (cursor.moveToFirst() == true) {

            do {
                int id = getIntFromColumnName(cursor, ContactSQLiteHelper.COLUMN_ID);
                String name = getStringFromColumnName(cursor, ContactSQLiteHelper.COLUMN_NOMBRE);
                String adress = getStringFromColumnName(cursor, ContactSQLiteHelper.COLUM_DIRECCION);
                String telefono = getStringFromColumnName(cursor, ContactSQLiteHelper.COLUM_TELEFONO);
                int ratio = getIntFromColumnName(cursor, ContactSQLiteHelper.COLUM_RATIO);
               // String mail=getStringFromColumnName(cursor,ContactSQLiteHelper.COLUM_EMAIL);
                person = new Person(id, name, adress, telefono, ratio);
            } while (cursor.moveToNext() != false);
            cursor.close();
            close(database);



        }
        return person;
    }

    public void delete(Person person){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        database.delete(ContactSQLiteHelper.CONTACT_TABLE,String.format("%s=%d", ContactSQLiteHelper.COLUMN_ID, person.getId()),null);
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
