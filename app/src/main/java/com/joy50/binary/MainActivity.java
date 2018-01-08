package com.joy50.binary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE=1;
    FirebaseListAdapter<clatMassage>adapter;
    RelativeLayout activity_main;
    FloatingActionButton fab;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_sign_out){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main,"You have been signed out..",Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==SIGN_IN_REQUEST_CODE){
            Snackbar.make(activity_main,"Sussesfully Signed in.Welcome!!",Snackbar.LENGTH_SHORT).show();
            displayChatMassage();
        }
        else{
            Snackbar.make(activity_main,"We could not Sign in",Snackbar.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main=(RelativeLayout)findViewById(R.id.activity_main);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText i = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new clatMassage(i.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                i.setText("");
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else{
            Snackbar.make(activity_main,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_LONG).show();
            displayChatMassage();
        }
    }
    private void displayChatMassage(){
        ListView l =(ListView)findViewById(R.id.list_of_massage);
        adapter=new FirebaseListAdapter<clatMassage>(this,clatMassage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, clatMassage model, int position) {
                TextView m1,m2,m3;
                m1=(TextView)v.findViewById(R.id.massage_text);
                m2=(TextView)v.findViewById(R.id.massage_user);
                m3=(TextView)v.findViewById(R.id.massage_time);
                m1.setText(model.getMassageText());
                m2.setText(model.getMassageUser());
                m3.setText(DateFormat.format("dd-MM-YYYY(HH:mm:ss)",model.getMassageTime()));

            }
        };
        l.setAdapter(adapter);
    }
}
