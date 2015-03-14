package es.examen.chorbagendaad.model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carlosfernandez on 08/03/15.
 */
public class ContactSQLiteHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "contactos.db";
    static final int DATABASE_VERSION = 1;

    static final String CONTACT_TABLE="contactos";
    static final String COLUMN_ID="id";
    static final String COLUMN_NOMBRE="name";
    static final String COLUM_DIRECCION="direccion";
    static final String COLUM_TELEFONO="telefono";
    static final String COLUM_RATIO="ratio";
    static final String COLUM_EMAIL="email";


    static final String CREATE_TABLE_CONTACS="CREATE TABLE "+CONTACT_TABLE+" ( "+
            COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE+" TEXT NOT NULL,"+
            COLUM_DIRECCION+ " TEXT NOT NULL,"+
            COLUM_TELEFONO+" TEXT NOT NULL,"+
            COLUM_RATIO+" INTEGER NOT NULL);"
            ;

    static final String CREATE_TABLE_CONTACS2="CREATE TABLE "+CONTACT_TABLE+" ( "+
            COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOMBRE+" TEXT NOT NULL,"+
            COLUM_DIRECCION+ " TEXT NOT NULL,"+
            COLUM_TELEFONO+" TEXT NOT NULL,"+
            COLUM_RATIO+" INTEGER NOT NULL,"+
            COLUM_EMAIL+ "TEXT NOT NULL);"
            ;




    public ContactSQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
