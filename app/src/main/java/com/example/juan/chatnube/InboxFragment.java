package com.example.juan.chatnube;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

;

/**
 * Created by Juan on 17/04/2015.
 */
public class InboxFragment extends ListFragment {
    ProgressBar spinner;

    public InboxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);
        spinner=(ProgressBar)rootView.findViewById(R.id.progressBar);

        spinner.setVisibility(View.GONE);

        return rootView;
    }


}
