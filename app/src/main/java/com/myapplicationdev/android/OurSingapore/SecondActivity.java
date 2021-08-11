package com.myapplicationdev.android.OurSingapore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv;
    ArrayList<Island> IslandList;
	ArrayAdapter<Island> adapter;
	String moduleCode;
    Button btn5Stars;

    ArrayList<String> area;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    CustomAdapter caIsland;

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        IslandList.clear();
        IslandList.addAll(dbh.getAllIslands());
        caIsland.notifyDataSetChanged();
        area.clear();
        area.addAll(dbh.getArea());
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spinner = (Spinner) this.findViewById(R.id.spinnerArea);

        DBHelper dbh = new DBHelper(this);
        IslandList = dbh.getAllIslands();
        area = dbh.getArea();
        dbh.close();

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, IslandList);
        //lv.setAdapter(adapter);

        caIsland = new CustomAdapter(this, R.layout.row, IslandList);

        lv.setAdapter(caIsland);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("Island", IslandList.get(position));
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                IslandList.clear();
                IslandList.addAll(dbh.getAllIslandsByStars(5));
                caIsland.notifyDataSetChanged();
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, area);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                IslandList.clear();
                IslandList.addAll(dbh.getAllIslandsByArea(Integer.valueOf(area.get(position))));
                caIsland.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
