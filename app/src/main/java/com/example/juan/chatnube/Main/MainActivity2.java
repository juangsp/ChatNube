package com.example.juan.chatnube.Main;

import java.util.List;
import java.util.Locale;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.juan.chatnube.Fragments.FriendsFragment;
import com.example.juan.chatnube.Fragments.InboxFragment;
import com.example.juan.chatnube.LogIn.LoginActivity;
import com.example.juan.chatnube.Menu.DeleteFriendsActivity;
import com.example.juan.chatnube.Menu.EditFriendsActivity;
import com.example.juan.chatnube.Menu.PetitionsActivity;
import com.example.juan.chatnube.Notifications.Notificaciones;
import com.example.juan.chatnube.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
public class MainActivity2 extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static String s= Context.NOTIFICATION_SERVICE;
    NotificationManager mNotificationManager;
    Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    long[] pattern = new long[]{1000,500,1000};
    MenuItem mItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        mItem=(MenuItem)findViewById(R.id.action_add);



        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                }
            });

            // For each of the sections in the app, add a tab to the action bar.
            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }


        } else {
            Intent i=new Intent(this,LoginActivity.class);
            i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();

            Intent i=new Intent(this,LoginActivity.class);
            //i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("contador", 1);
            setResult(RESULT_OK, i);
            finish();


            return true;
        }

        if(id==R.id.action_find){
            Intent i=new Intent(getApplicationContext(),EditFriendsActivity.class);
            startActivity(i);

        }
        if(id==R.id.action_delete){
            Intent i=new Intent(getApplicationContext(),DeleteFriendsActivity.class);
            startActivity(i);
        }
        if(id==R.id.action_add){
            Intent i=new Intent(getApplicationContext(),PetitionsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
   protected void onResume() {
        super.onResume();
        if (ParseUser.getCurrentUser() != null) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
            query.whereEqualTo("id_destinatario", ParseUser.getCurrentUser().getObjectId());
            query.addDescendingOrder("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    final int mensajes;

                    if (e == null && parseObjects.size() > 0) {
                        mensajes = parseObjects.size();
                       /* NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(MainActivity2.this)
                                        .setSmallIcon(R.drawable.simbolo_infinito)
                                        .setLargeIcon((((BitmapDrawable) getResources()
                                                .getDrawable(R.drawable.simbolo_infinito)).getBitmap()))
                                        .setContentTitle("Wayta")
                                        .setContentInfo("Tienes " + mensajes + " mensaje(s) nuevo(s)")
                                        .setSound(defaultSound)
                                        .setVibrate(pattern)
                                        .setLights(Color.BLUE, 1, 0)
                                        .setAutoCancel(true);


                        Intent notIntent =
                                new Intent(MainActivity2.this, MainActivity2.class);
                        PendingIntent contIntent =
                                PendingIntent.getActivity(
                                        MainActivity2.this, 0, notIntent, 0);
                        mBuilder.setContentIntent(contIntent);
                        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(1, mBuilder.build());*/

                        Toast.makeText(MainActivity2.this, "Tienes "+mensajes+"mensajes nuevos", Toast.LENGTH_LONG).show();

                    }
                }
            });


            ParseQuery<ParseObject> petition = ParseQuery.getQuery("friend_petition");
            petition.whereEqualTo("id_destinatario", ParseUser.getCurrentUser().getObjectId());
            petition.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null && list.size() > 0) {

                        Toast.makeText(MainActivity2.this, "Tienes nuevas peticiones de amistad ", Toast.LENGTH_LONG).show();


                    }

                }
            });


        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        String s2= Context.NOTIFICATION_SERVICE;
        if(ParseUser.getCurrentUser()!=null) {
            Notificaciones s = new Notificaciones(this, s2);
            s.run();
        }
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final int NUMBER_OF_TABS = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){

                case 0:
                    return new InboxFragment();
                case 1:
                    return new FriendsFragment();

            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUMBER_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Chats".toUpperCase(l);
                case 1:
                    return "Contactos".toUpperCase(l);

            }
            return null;
        }
    }




}
