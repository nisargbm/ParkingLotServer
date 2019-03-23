package com.example.nisar.ubookit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String result,resultAfterFetch,uid,uidUser,slotnumber;
    private TextView details,detailsAfterFetch;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private static final String FIREBASE_URL="https://taxi-booking-69de8.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        try {
            result = bundle.getString("result");
        }catch (Exception e){
            result = "0";
        }


        details =(TextView)findViewById(R.id.details);
        detailsAfterFetch =(TextView)findViewById(R.id.details_after_fetch);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, Scanner.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                        //progressBar.setVisibility(View.GONE);
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//
//                            Toast.makeText(Main.this, "Authentication success" ,
//                                    Toast.LENGTH_SHORT).show();
//                            String uid=auth.getCurrentUser().getUid();
//                            mDatabase.child("users").child(uid).child("email").setValue(email);
//                            mDatabase.child("users").child(uid).child("password").setValue(password);
//                            mDatabase.child("users").child(uid).child("name").setValue(name);
//                            mDatabase.child("users").child(uid).child("mobile").setValue(mobile);
//                            mDatabase.child("users").child(uid).child("address").setValue(address);
//                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//                            finish();
//                    }
//                });




    }




    class BackgroundTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public BackgroundTask(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Data being fetched!!");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (result.equals("0")) {
                    resultAfterFetch="0";
                }
                else{
                    resultAfterFetch = getDetails(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private void getDetails(String result) {
        if (auth.getCurrentUser() != null) {
            uid = auth.getCurrentUser().getUid();
            System.out.println(uid);
            mDatabase.child("slots").runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    System.out.println(uid);
                    boolean temp=false;
                   for(MutableData data:mutableData.getChildren()){
                       if(data.getValue()=="false"){
                           slotnumber=data.getKey();
                           data.setValue("true");
                           temp=true;
                       }
                   }
                   if(temp==false)slotnumber="0";
                    //name = dataSnapshot.child("name").getValue().toString();
//                    mobile = dataSnapshot.child("mobile").getValue().toString();
//                    email = dataSnapshot.child("email").getValue().toString();
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });
            mDatabase.child("users").child(uidUser).runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    System.out.println(uid);
                    for(MutableData data:mutableData.getChildren()){
                        if(data.getKey()=="Slot"){
                            data.setValue(slotnumber);
                        }
                    }
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });
        }
    }

//                    BackgroundTask task = new BackgroundTask(getActivity());
//            task.execute();
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_signout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch(id){
            case R.id.nav_gallery:
                fragment = new QRCodeActivity();
                break;
            case R.id.nav_camera:
                fragment = new AddVehicle();
                break;
            case R.id.nav_slideshow:
                fragment = new DesertPlaceHolder();
                break;
//            case R.id.nav_manage:
//                fragment = new FoldingCellsTrial();
//                break;
            default:break;
        }
        if(fragment!=null){
            FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//              Intent intent = new Intent(this, ActivityFoldingCellsTrial.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
