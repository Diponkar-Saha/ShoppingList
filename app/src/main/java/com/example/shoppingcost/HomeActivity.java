package com.example.shoppingcost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private  FirebaseAuth auth;
    private Toolbar toolbar;
    private FloatingActionButton fab_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.home_toolbar);
        fab_btn=findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Shopping List");

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
                Toast.makeText(HomeActivity.this, "fab", Toast.LENGTH_LONG).show();

            }
        });

    }
    private void customDialog(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myview=inflater.inflate(R.layout.input_data,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(myview);
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
