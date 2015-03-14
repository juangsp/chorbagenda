package es.examen.chorbagendaad.ui.fragments;

/**
 * Created by carlosfernandez on 04/03/15.
 */

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import es.examen.chorbagendaad.R;
import es.examen.chorbagendaad.model.ContactDataSource;
import es.examen.chorbagendaad.model.Person;
import es.examen.chorbagendaad.ui.activities.AddPersonActivity;
import es.examen.chorbagendaad.ui.activities.DetailActivity;
import es.examen.chorbagendaad.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
  public class ContactListFragment extends ListFragment {

    private ArrayAdapter<Person> aPersonas;
    private ArrayList<Person> mPersonas=new ArrayList<>();
    private int _id=0;
    private ArrayList<Integer>idpersonas=new ArrayList<>();
    private ProgressBar progressBar;

    public ContactListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);


        addSaveButton(rootView);

        setHasOptionsMenu(true);

        return rootView;
    }

    /**
     * Load the listView
     */
    private void loadList() {

        aPersonas = new ArrayAdapter<Person>(getActivity(), android.R.layout.simple_list_item_1,
                mPersonas);
        setListAdapter(aPersonas);

        progressBar.setVisibility(View.INVISIBLE);
    }


    public void addSaveButton(View rootView) {
        Button saveButon = (Button) rootView.findViewById(R.id.add_button);
        saveButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(),AddPersonActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADD_PERSON);
            }
        });


    }

    @Override
    public void onActivityCreated(Bundle icicle) {
        super.onActivityCreated(icicle);
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        String delete = getActivity().getResources().getString(R.string.delete);
        String cancel = getActivity().getResources().getString(R.string.cancel);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        menu.setHeaderTitle(mPersonas.get(position).toString());
        menu.add(0, v.getId(), 0, delete);
        menu.add(0, v.getId(), 0, cancel);
    }

    /**
     *  Delete ContextMenu
    */

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo element = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String delete = getActivity().getResources().getString(R.string.delete);
        String cancel = getActivity().getResources().getString(R.string.cancel);
        if (item.getTitle().equals(delete)) {
            // Delete a Contact
            ContactDataSource data=new ContactDataSource(getActivity());
            data.delete( mPersonas.get(element.position));
            aPersonas.notifyDataSetChanged();


        } else if (item.getTitle().equals(cancel)) {
            // Do nothing
        }
        return super.onContextItemSelected(item);

    }

    /**
     * Long click Listener for deleting
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constants.KEY_PERSON, mPersonas.get(position));
        startActivity(intent);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.settings) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == Constants.REQUEST_CODE_ADD_PERSON) {
            if (resultCode == getActivity().RESULT_OK && data!=null) {
                Person newPerson = (Person)data.getExtras().get(Constants.KEY_PERSON);
                aPersonas.add(newPerson);
                Resources res = getResources();

                Toast.makeText(getActivity(),String.format(res.getString(R.string.contact_added_message), newPerson.getName()),Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ContactDataSource data=new ContactDataSource(getActivity());
        mPersonas=data.readContact();
        //_id=data.readContact().get(0).getId();
        //idpersonas.add(_id);
        loadList();

    }
}


