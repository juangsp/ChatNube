package com.example.juan.chatnube.Fragments;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.juan.chatnube.Main.ChatActivity;
import com.example.juan.chatnube.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;


public class FriendsFragment extends ListFragment {
        ProgressBar spinner;
        List<ParseUser> mUser;
        ArrayList<String> usernames;
        ArrayList<String> nombres=new ArrayList();
        ArrayAdapter<String> adapter;
        ParseUser mCurrentUser;
        ParseRelation<ParseUser> friendRelation;

        public FriendsFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_friends_fragment, container, false);
            spinner=(ProgressBar)rootView.findViewById(R.id.progressBar2);

            spinner.setVisibility(View.GONE);

            return rootView;
        }


        @Override
        public void onResume() {
            super.onResume();


            usernames = new ArrayList();
            adapter = new ArrayAdapter<String>(this.getActivity(), simple_list_item_1, usernames);
            setListAdapter(adapter);
            if (ParseUser.getCurrentUser() != null) {
                ParseQuery query = ParseUser.getQuery();
                mCurrentUser = ParseUser.getCurrentUser();
                friendRelation = mCurrentUser.getRelation("friendsRelation");
                query = friendRelation.getQuery();

                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {

                        if (e == null) {
                            mUser = list;

                            for (ParseUser user : mUser) {
                                nombres.add(user.getUsername().toString());
                                adapter.add(user.getUsername());
                            }

                        } else {

                        }

                    }


                });
            }
        }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("id_destinatario",mUser.get(position).getObjectId());
        intent.putExtra("nombre_remitente",mUser.get(position).getUsername());
        startActivity(intent);
    }


    }


