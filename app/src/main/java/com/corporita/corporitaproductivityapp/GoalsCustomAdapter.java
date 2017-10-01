package com.corporita.corporitaproductivityapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;


/**
 * Created by saif on 2017-08-29.
 */

public class GoalsCustomAdapter extends ArrayAdapter<String> {


    String [] name;
    String[] measure;
    String[] time;
    int[] progress;
    Context gContext;
    String[] cat;


    public GoalsCustomAdapter(Context context, String [] goalsName, String[] goalsMeasure, String[] goalsTime, String[] categoryList, int[] goalsProgress){
        super(context, R.layout.activity_goals_custom_adapter);
        this.name = goalsName;
        this.measure = goalsMeasure;
        this.time = goalsTime;
        this.cat = categoryList;
        this.progress = goalsProgress;
        this.gContext = context;

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder gViewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater gInflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = gInflater.inflate(R.layout.activity_goals_custom_adapter, parent, false);
            gViewHolder.nameGoals = (TextView) convertView.findViewById(R.id.nameGoals);
            gViewHolder.measuredBy = (TextView) convertView.findViewById(R.id.measuredBy);
            gViewHolder.timeFrame = (TextView) convertView.findViewById(R.id.timeFrame);
            gViewHolder.category = (TextView) convertView.findViewById(R.id.category);
            gViewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            gViewHolder.progressDisplay = (TextView) convertView.findViewById(R.id.progressDisplay);
            convertView.setTag(gViewHolder);
        } else {
            gViewHolder = (ViewHolder) convertView.getTag();
        }
        String progSpace = "  ";

        if (progress[position] < 10 ) {
            progSpace = "    ";
        }
        //Health, Career, Finance, Family, Personal, Recreation, Relationship, Social-->



        gViewHolder.nameGoals.setText(name[position]);
        gViewHolder.measuredBy.setText(measure[position]);
        gViewHolder.timeFrame.setText(time[position]);
        gViewHolder.progressBar.setProgress(progress[position]);
        gViewHolder.category.setText(cat[position]);
        gViewHolder.progressDisplay.setText(progSpace + Integer.toString(progress[position]) + "%");


        int colorId = setColor(cat[position]);

        gViewHolder.nameGoals.setBackgroundColor(ContextCompat.getColor(gContext, colorId));
        gViewHolder.measuredBy.setTextColor(ContextCompat.getColor(gContext, colorId));
        gViewHolder.timeFrame.setTextColor(ContextCompat.getColor(gContext, colorId));
        gViewHolder.category.setTextColor(ContextCompat.getColor(gContext, colorId));
        gViewHolder.progressDisplay.setTextColor(ContextCompat.getColor(gContext, colorId));
        gViewHolder.progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(gContext, colorId), PorterDuff.Mode.SRC_IN);


        return convertView;

    }
    static class ViewHolder {
        TextView category;
        TextView nameGoals;
        TextView measuredBy;
        TextView timeFrame;
        ProgressBar progressBar;
        TextView progressDisplay;
    }


    //Health, Career, Finance, Family, Personal, Recreation, Relationship, Social-->
    public int setColor(String category){

        int colorId;

        switch (category){
            case "Health":
                colorId = R.color.HealthColor;
                break;
            case "Career":
                colorId = R.color.CareerColor;
                break;
            case "Finance":
                colorId = R.color.FinanceColor;
                break;
            case "Personal":
                colorId = R.color.PersonalColor;
                break;
            case "Recreation":
                colorId = R.color.RecreationColor;
                break;
            case "Relationship":
                colorId = R.color.RelationshipColor;
                break;
            case "Family":
                colorId = R.color.FamilyColor;
                break;
            case "Social":
                colorId = R.color.SocialColor;
                break;
            default:
                colorId = R.color.appGeneral;
                break;

        }

        return colorId;



    }
}

