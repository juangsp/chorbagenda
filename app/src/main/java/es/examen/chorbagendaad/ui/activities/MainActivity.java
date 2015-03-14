package es.examen.chorbagendaad.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import es.examen.chorbagendaad.R;
import es.examen.chorbagendaad.ui.fragments.ContactListFragment;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ContactListFragment())
                    .commit();
        }
    }




}
