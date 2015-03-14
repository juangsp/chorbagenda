package es.examen.chorbagendaad.ui.fragments;

/**
 * Created by carlosfernandez on 04/03/15.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import es.examen.chorbagendaad.R;
import es.examen.chorbagendaad.model.Person;
import es.examen.chorbagendaad.utils.Constants;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private View rootView;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();
        Person person = (Person) intent.getExtras().get(Constants.KEY_PERSON);



        setPerson(person);

        return rootView;


    }

    public void setPerson(Person person){
        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView address = (TextView) rootView.findViewById(R.id.address);
        TextView telephone = (TextView) rootView.findViewById(R.id.telephone);
        RatingBar rating = (RatingBar) rootView.findViewById(R.id.ratingBar);
       // TextView mail=(TextView) rootView.findViewById(R.id.mail);


        name.setText(person.getName());
        address.setText(person.getAddress());
        telephone.setText(person.getTelephone());
        rating.setRating(person.getRating());
        //mail.setText(person.getEmail());
        rating.setEnabled(false);

    }
}
