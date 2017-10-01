package com.corporita.corporitaproductivityapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.util.Log;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Created by saif on 2017-08-29.
 */

public class GoalsPage extends AppCompatActivity {
    private static final String TAG = "Testing";
    static String [] goalsName;
    static String [] goalsMeasure;
    static String [] goalsTime;
    static String [] goalsCat;
    static int [] goalsProgress;
    static int [] goalsID;
    static String [] goalsUpdate;
    static DBAdapter goalsDB;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeButt:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_mid,R.anim.mid_to_right);
                return true;
            case R.id.taskButt:
                Intent t = new Intent(this, MenuMainActivity.class);
                startActivity(t);
                overridePendingTransition(R.anim.left_to_mid,R.anim.mid_to_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goal, menu);
        return true;
    }

    public void setupEvenlyDistributedToolbar() {
        // Use Display metrics to get Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        // Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Inflate your menu
        mToolbar.inflateMenu(R.menu.menu_goal);

        // Add 10 spacing on either side of the toolbar
        mToolbar.setContentInsetsAbsolute(10, 10);

        // Get the ChildCount of your Toolbar, this should only be 1
        int childCount = mToolbar.getChildCount();
        // Get the Screen Width in pixels
        int screenWidth = metrics.widthPixels;

        // Create the Toolbar Params based on the screenWidth
        Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(screenWidth, ActionMenuView.LayoutParams.WRAP_CONTENT);

        // Loop through the child Items
        for (int i = 0; i < childCount; i++) {
            // Get the item at the current index
            View childView = mToolbar.getChildAt(i);
            // If its a ViewGroup
            if (childView instanceof ViewGroup) {
                // Set its layout params
                childView.setLayoutParams(toolbarParams);
                // Get the child count of this view group, and compute the item widths based on this count & screen size
                int innerChildCount = ((ViewGroup) childView).getChildCount();
                int itemWidth = (screenWidth / innerChildCount);
                // Create layout params for the ActionMenuView
                ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(itemWidth, ActionMenuView.LayoutParams.WRAP_CONTENT);
                // Loop through the children
                for (int j = 0; j < innerChildCount; j++) {
                    View grandChild = ((ViewGroup) childView).getChildAt(j);
                    if (grandChild instanceof ActionMenuItemView) {
                        // set the layout parameters on each View
                        grandChild.setLayoutParams(params);
                    }
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_page);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupEvenlyDistributedToolbar();

        removeAlarms();


        openDB();


        //Create initial lists
        List<Integer> gID = new ArrayList<>();
        List<String> gName = new ArrayList<>();
        List<String> gMeasure = new ArrayList<>();
        List<String> gTime = new ArrayList<>();
        List<String> gCat = new ArrayList<>();
        List<Integer> gProgress = new ArrayList<>();
        List<String> gUpdate = new ArrayList<>();


        Cursor cursor = goalsDB.getAllRows();
        if (cursor.moveToFirst()){

            do {

                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String goal = cursor.getString(DBAdapter.COL_GOAL);
                String measure = cursor.getString(DBAdapter.COL_MEAS);
                String time = cursor.getString(DBAdapter.COL_TIME);
                String category = cursor.getString(DBAdapter.COL_CATE);
                int progress = cursor.getInt(DBAdapter.COL_PROG);
                String update = cursor.getString(DBAdapter.COL_UPDA);

                gID.add(id);
                gName.add(goal);
                gMeasure.add(measure);
                gTime.add(time);
                gCat.add(category);
                gProgress.add(progress);
                gUpdate.add(update);
            }while (cursor.moveToNext());

        }
        cursor.close();

        goalsName = gName.toArray(new String[gName.size()]);
        goalsMeasure = gMeasure.toArray(new String[gMeasure.size()]);
        goalsTime = gTime.toArray(new String[gTime.size()]);
        goalsCat = gCat.toArray(new String[gCat.size()]);
        goalsUpdate = gUpdate.toArray(new String[gUpdate.size()]);
        Integer [] gProg = gProgress.toArray(new Integer[gProgress.size()]);
        Integer [] gId = gID.toArray(new Integer[gID.size()]);

        int gProgSize = gProgress.size();
        goalsProgress = new int[gProgSize];
        for (int n = 0; n < gProgSize; n++){
            goalsProgress[n] = gProg[n];
        }

        int gIDsize = gID.size();
        goalsID = new int[gIDsize];
        for (int n = 0; n < gIDsize; n++){
            goalsID[n] = gId[n];
        }

        //test
        for (String a: goalsUpdate){
            Log.i(TAG, a);
        }




        updateGoals(goalsName, goalsMeasure, goalsTime, goalsCat, goalsProgress);

        if (goalsID.length >= 8){
            TextView goalWarn = (TextView) findViewById(R.id.goalWarn);
            goalWarn.setVisibility(View.VISIBLE);
        }


        ListView goalsList = (ListView) findViewById(R.id.goalsList);
        goalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GoalsPage.this, AddGoals.class);
                intent.putExtra("id", goalsID[i]);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {

        Log.i(TAG,"onDestry");
        Set<String> goalsUp = new HashSet<>(Arrays.asList(goalsUpdate));
        String[] updateGoals = goalsUp.toArray(new String[goalsUp.size()]);
        Date currentDateTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        dateFormat.format(currentDateTime);
        for (String date:updateGoals){

            try {
                Date newDateTime = dateFormat.parse(date);
                long duration = newDateTime.getTime() - currentDateTime.getTime();
                Log.i(TAG,Long.toString(duration));

                if (duration > 0){
                    setAlarm(duration);
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.i(TAG, "Didnt work");
            }






        }

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



    public void goToAddGoals(View view){
        if (goalsID.length >= 8){
            AlertDialog.Builder goalsAlert = new AlertDialog.Builder(this);
            goalsAlert.setTitle("Warning");
            goalsAlert.setMessage(R.string.warning_text);
            goalsAlert.setNegativeButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(GoalsPage.this, AddGoals.class);
                    startActivity(intent);
                }
            });
            goalsAlert.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog goalsWarning = goalsAlert.show();
            goalsWarning.show();
        }else{
            Intent intent = new Intent(this, AddGoals.class);
            startActivity(intent);
        }


    }


    public void updateGoals(String [] goalsName, String [] goalsMeasure, String [] goalsTime, String [] goalsCat, int [] goalsProgress){


        GoalsCustomAdapter gAdapter = new GoalsCustomAdapter(this, goalsName, goalsMeasure, goalsTime, goalsCat, goalsProgress);
        ListView goalsList = (ListView) findViewById(R.id.goalsList);
        goalsList.setAdapter(gAdapter);

        justifyListViewHeightBasedOnChildren(goalsList);


    }
    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public void setAlarm(Long time){

        Long alertTime = new GregorianCalendar().getTimeInMillis() + time;

        Intent alertIntent = new Intent(this, AlertReceiver.class);

        PendingIntent broadcastIntent = PendingIntent.getBroadcast(this,1,alertIntent,Intent.FILL_IN_DATA);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,broadcastIntent);


    }

    public void removeAlarms(){
        Intent alertIntent = new Intent(this, AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(this,1,alertIntent,Intent.FILL_IN_DATA);

        alarmManager.cancel(broadcastIntent);
    }

}
