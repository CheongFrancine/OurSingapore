package com.myapplicationdev.android.OurSingapore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    Button btnInsert, btnShowList;
    //RadioGroup rg;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = (EditText) findViewById(R.id.etName);
        etSingers = (EditText) findViewById(R.id.etDescription);
        etYear = (EditText) findViewById(R.id.etArea);
        btnInsert = (Button) findViewById(R.id.btnInsertSong);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        //rg = (RadioGroup) findViewById(R.id.rgStars);
        rb = findViewById(R.id.ratingBarStars);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String singers = etSingers.getText().toString().trim();
                if (title.length() == 0 || singers.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }


                String year_str = etYear.getText().toString().trim();
                int year = 0;
                try {
                    year = Integer.valueOf(year_str);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbh = new DBHelper(MainActivity.this);

                int stars = getStars();
                dbh.insertIsland(title, singers, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

    }


    private int getStars() {
        int stars = 1;
//        switch (rg.getCheckedRadioButtonId()) {
//            case R.id.radio1:
//                stars = 1;
//                break;
//            case R.id.radio2:
//                stars = 2;
//                break;
//            case R.id.radio3:
//                stars = 3;
//                break;
//            case R.id.radio4:
//                stars = 4;
//                break;
//            case R.id.radio5:
//                stars = 5;
//                break;
//        }
        stars = (int) rb.getRating();
        return stars;
    }

}