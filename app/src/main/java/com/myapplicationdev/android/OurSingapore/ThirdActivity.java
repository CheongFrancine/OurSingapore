package com.myapplicationdev.android.OurSingapore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName, etDescription, etArea;
    //RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnCancel, btnUpdate, btnDelete;
    //RadioGroup rg;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

//        rb1 = (RadioButton) findViewById(R.id.radio1);
//        rb2 = (RadioButton) findViewById(R.id.radio2);
//        rb3 = (RadioButton) findViewById(R.id.radio3);
//        rb4 = (RadioButton) findViewById(R.id.radio4);
//        rb5 = (RadioButton) findViewById(R.id.radio5);
//        rg = (RadioGroup) findViewById(R.id.rgStars);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etArea = (EditText) findViewById(R.id.etArea);
        rb = findViewById(R.id.ratingBarStars3);

        Intent i = getIntent();
        final Island currentIsland = (Island) i.getSerializableExtra("Island");

        etID.setText(currentIsland.getId()+"");
        etName.setText(currentIsland.getName());
        etDescription.setText(currentIsland.getDescription());
        etArea.setText(currentIsland.getArea()+"");
//        switch (currentIsland.getStars()){
//            case 5: rb5.setChecked(true);
//                    break;
//            case 4: rb4.setChecked(true);
//                    break;
//            case 3: rb3.setChecked(true);
//                    break;
//            case 2: rb2.setChecked(true);
//                    break;
//            case 1: rb1.setChecked(true);
//        }
        rb.setRating ((float)currentIsland.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentIsland.setName(etName.getText().toString().trim());
                currentIsland.setDescription(etDescription.getText().toString().trim());
                int area = 0;
                try {
                    area = Integer.valueOf(etArea.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid area", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentIsland.setArea(area);

//                int selectedRB = rg.getCheckedRadioButtonId();
//                RadioButton rb = (RadioButton) findViewById(selectedRB);
//                currentIsland.setStars(Integer.parseInt(rb.getText().toString()));
                currentIsland.setStars((int)rb.getRating());

                int result = dbh.updateIsland(currentIsland);
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Island updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DBHelper dbh = new DBHelper(ThirdActivity.this);
//                int result = dbh.deleteIsland(currentIsland.getId());
//                if (result>0){
//                    Toast.makeText(ThirdActivity.this, "Island deleted", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
//                }
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the island\n\n" + currentIsland.getName());
                myBuilder.setCancelable(false);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        dbh.deleteIsland(currentIsland.getId());
                        finish();
                    }
                });

                // Configure the 'positive' button
                myBuilder.setPositiveButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                Dialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes\n\n" + currentIsland.getName());
                myBuilder.setCancelable(false);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                // Configure the 'positive' button
                myBuilder.setPositiveButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }


}