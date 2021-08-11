package com.myapplicationdev.android.OurSingapore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Island> islandList;

    public CustomAdapter (Context context, int resource, ArrayList<Island> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        islandList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //"Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        //Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName2);
        TextView tvDescription = rowView.findViewById(R.id.tvDescription2);
        TextView tvArea = rowView.findViewById(R.id.tvArea2);
        //TextView tvStar = rowView.findViewById(R.id.tvStar);
        RatingBar rb = rowView.findViewById(R.id.ratingBarStars);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);

        //Obtain the to do information based on the position
        Island currentIsland = islandList.get(position);

        //Set values to the TextView to display the corresponding information
        tvName.setText(currentIsland.getName());
        tvDescription.setText(currentIsland.getDescription());
        tvArea.setText(Integer.toString(currentIsland.getArea()));
        //tvStar.setText(currentIsland.toString());
        rb.setRating(currentIsland.getStars());

        if (currentIsland.getArea() >= 2019) {
            ivNew.setVisibility(View.VISIBLE);
        }
        else {
            ivNew.setVisibility(View.INVISIBLE);
        }

        return rowView;

    }

}
