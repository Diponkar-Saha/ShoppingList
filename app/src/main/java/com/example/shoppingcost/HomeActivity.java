package com.example.shoppingcost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.shoppingcost.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private  FirebaseAuth auth;
    private Toolbar toolbar;
    private FloatingActionButton fab_btn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.home_toolbar);
        fab_btn=findViewById(R.id.fab);
        FirebaseUser mUser=auth.getCurrentUser();
        String uId=mUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Shopping List").child(uId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Shopping List");

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();

            }
        });

    }
    private void customDialog(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myview=inflater.inflate(R.layout.input_data,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(myview);
        final EditText type=myview.findViewById(R.id.edit_type);
        final EditText amount=myview.findViewById(R.id.edit_ammount);
        final EditText note=myview.findViewById(R.id.edit_note);
        Button btnsave=myview.findViewById(R.id.btn_save);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mType=type.getText().toString().trim();
                String mAmount=amount.getText().toString().trim();
                String mNote=note.getText().toString().trim();
                int amountt=Integer.parseInt(mAmount);

                if(TextUtils.isEmpty(mType)){
                    type.setError("Required Field..");
                    return;
                }
                if(TextUtils.isEmpty(mAmount)){
                    amount.setError("Required Field..");
                    return;
                }
                if(TextUtils.isEmpty(mNote)){
                    note.setError("Required Field..");
                    return;
                }
                String id=databaseReference.push().getKey();
                String date= DateFormat.getDateInstance().format(new Date());
                Data  data=new Data(mType,amountt,mNote,date,id);
                databaseReference.child(id).setValue(data);
                Toast.makeText(HomeActivity.this, "data add", Toast.LENGTH_LONG).show();


                dialog.dismiss();
            }
        });

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
