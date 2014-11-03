package com.example.juanpc.laboratoriomoviles;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.parse.Parse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import android.content.pm.Signature;

public class FoodMenu extends FragmentActivity {


    private TextView lblEmail;
    private UiLifecycleHelper uiHelper;

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i("log_tag", "Logged in...");
        } else if (state.isClosed()) {
            Log.i("log_tag", "Logged out...");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fragment fragment;
        Parse.initialize(this, "SeCU4zHj1PbXG1VPj7xXUaPqO5SS3VfufMFjAfUH", "FktMWxHocRPmqQJqyeEpol4cTTt1jUpkRSb1TAQ0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        lblEmail = (TextView) findViewById(R.id.lblEmail);
        printHashKey();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Master master = new Master();
        master.setId("");
        transaction.add(R.id.placeholder, master).commit();

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
        authButton.setOnErrorListener(new LoginButton.OnErrorListener() {

            @Override
            public void onError(FacebookException error) {
                Log.i("log_tag", "Error " + error.getMessage());
            }
        });



        authButton.setReadPermissions(Arrays.asList("email"));
        authButton.setSessionStatusCallback(new Session.StatusCallback() {



            @Override
            public void call(Session session, SessionState state, Exception exception) {
                Log.i("log_tag", "Accesssss Token");
                if (session.isOpened()) {
                    Log.i("log_tag", "Access Token" + session.getAccessToken());
                    Request.executeMeRequestAsync(session,
                            new Request.GraphUserCallback() {
                                @Override
                                public void onCompleted(GraphUser user, Response response) {
                                    if (user != null) {
                                        Log.i("log_tag", "User ID " + user.getId());
                                        Log.i("log_tag", "Email " + user.asMap().get("email"));
                                        lblEmail.setText(user.asMap().get("email").toString());
                                    }
                                }
                            });
                } else
                    Log.i("log_tag", "Nopes Token");
            }
        });


    }

    public void printHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.juanpc.laboratoriomoviles",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("log_tag", "Accesssss Token");
                Log.i("log_tag",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);
        Log.i("log_tag","hola");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
               // Log.i("log_tag",newText);

                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                Log.i("log_tag",query);

                change(query);



                return true;

            }
        };


        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);




    }

    public void jumpToPage(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), AddFood.class));
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("log_tag",query);
            Toast.makeText(this, query, Toast.LENGTH_LONG);
        }
    }


    public void change(String query){



        Master master = new Master();
        master.setId(query);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.placeholder, master);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }




}
