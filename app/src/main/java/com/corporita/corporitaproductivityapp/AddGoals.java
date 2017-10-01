package com.corporita.corporitaproductivityapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by saif on 2017-08-29.
 */

public class AddGoals extends AppCompatActivity {
    private static final String TAG = "Testing";

    DBAdapter goalsDB;
    int ID;
    String Goal;
    String Measure;
    String Time;
    String Category;
    int Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals);
        openDB();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Button deleteButton = (Button) findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.VISIBLE);
            Cursor cursor = goalsDB.getRow(bundle.getInt("id"));
            if (cursor.moveToFirst()){
                ID = bundle.getInt("id");
                Goal = cursor.getString(DBAdapter.COL_GOAL);
                Measure = cursor.getString(DBAdapter.COL_MEAS);
                Time = cursor.getString(DBAdapter.COL_TIME);
                Category = cursor.getString(DBAdapter.COL_CATE);
                Progress = cursor.getInt(DBAdapter.COL_PROG);
            }

            cursor.close();

        }else {
            ID = -1;
            Goal = null;
            Measure = null;
            Time = null;
            Category = "Health";
            Progress = 0;
        }

        Log.i(TAG, Goal + ' ' + Measure + ' ' + Time + ' ' + Category + ' ' + Integer.toString(Progress));
        setUpVals(Goal,Measure,Category,Time,Progress);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        closeDB();
    }

    private void openDB(){

        goalsDB = new DBAdapter(this);
        goalsDB.open();

    }

    private void closeDB(){

        goalsDB.close();

    }

    public void setUpVals(String goal, String measure, String category, String time, int progress){
        //GOAL
        EditText goalEntry = (EditText) findViewById(R.id.goalEntry);
        goalEntry.setText(goal);

        //MEASURE
        EditText measureEntry = (EditText) findViewById(R.id.measureEntry);
        measureEntry.setText(measure);

        //CATEGORY
        Spinner categoryEntry = (Spinner) findViewById(R.id.categoryEntry);

        ArrayAdapter<String> categoryEntryAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));

        categoryEntryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryEntry.setAdapter(categoryEntryAdapter);

        int setCatVal = Arrays.asList(getResources().getStringArray(R.array.categories)).indexOf(category);
        categoryEntry.setSelection(setCatVal);

        Log.i(TAG, category);

        //TIME
        final EditText timeFrameEntry = (EditText) findViewById(R.id.timeFrameEntry);
        timeFrameEntry.setText(time);

        timeFrameEntry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar curDate = Calendar.getInstance();
                int year = curDate.get(Calendar.YEAR);
                int month = curDate.get(Calendar.MONTH);
                int day = curDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickDate = new DatePickerDialog(AddGoals.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int sYear, int sMonth, int sDay) {
                        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                        timeFrameEntry.setText(months[sMonth] + " " + sDay + ", " + sYear);
                    }
                }, year, month, day);
                pickDate.show();

            }
        });

        ///PROGRESS UPDATE
        final EditText progressUpEntry = (EditText) findViewById(R.id.progressUpEntry);
        progressUpEntry.setText("");

        progressUpEntry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar curDate = Calendar.getInstance();
                int year = curDate.get(Calendar.YEAR);
                int month = curDate.get(Calendar.MONTH);
                int day = curDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog pickDate = new DatePickerDialog(AddGoals.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int sYear, int sMonth, int sDay) {
                        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
                        progressUpEntry.setText(months[sMonth] + " " + sDay + ", " + sYear);
                    }
                }, year, month, day);
                pickDate.show();

            }
        });

        //PROGRESS
        SeekBar progressEntry = (SeekBar) findViewById(R.id.progressEntry);
        progressEntry.setProgress(progress);
    }

    public void goalSave(View view){

        EditText goalEntry = (EditText) findViewById(R.id.goalEntry);
        EditText measureEntry = (EditText) findViewById(R.id.measureEntry);
        Spinner categoryEntry = (Spinner) findViewById(R.id.categoryEntry);
        EditText timeFrameEntry = (EditText) findViewById(R.id.timeFrameEntry);
        EditText progressUpEntry = (EditText) findViewById(R.id.progressUpEntry);
        SeekBar progressEntry = (SeekBar) findViewById(R.id.progressEntry);

        String goal = goalEntry.getText().toString();
        String measure = measureEntry.getText().toString();
        String category = categoryEntry.getSelectedItem().toString();
        String timeFrame = timeFrameEntry.getText().toString();
        int progress = progressEntry.getProgress();
        String updateProg = progressUpEntry.getText().toString();

        if (ID == -1) {
            long newId = goalsDB.insertRow(goal, measure, category, timeFrame, progress, updateProg);
        }else if (progress == 100){
            boolean onDelete = goalsDB.deleteRow(ID);
        }else{
            boolean updateId = goalsDB.updateRow(ID, goal,measure,category,timeFrame,progress,updateProg);
        }

        Intent saveAddGoal = new Intent(this, GoalsPage.class);
        startActivity(saveAddGoal);


    }

    public void goalCancel(View view){
        Intent cancelAddGoal = new Intent(this, GoalsPage.class);
        startActivity(cancelAddGoal);
    }

    public void goalDelete(View view){

        boolean deleteGoal = goalsDB.deleteRow(ID);
        Intent deleteAddGoal = new Intent(this, GoalsPage.class);
        startActivity(deleteAddGoal);
    }


    public void csmartClick(View view){
        AlertDialog.Builder csmarter = new AlertDialog.Builder(this);
        csmarter.setMessage(R.string.csmarter_text);
        csmarter.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog goalsWarning = csmarter.show();
        goalsWarning.show();
    }


}
