package com.corporita.corporitaproductivityapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.corporita.corporitaproductivityapp.data.TaskContract;

import java.util.Calendar;

/**
 * Created by saif on 2017-08-29.
 */

public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {

        if (mPriority == 1) {
            // Not yet implemented
            // Check if EditText is empty, if not retrieve input and store it in a ContentValues object
            // If the EditText input is empty -> don't create an entry
            String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
            if (input.length() == 0) {
                return;
            }

            // Insert new task data via a ContentResolver
            // Create new empty ContentValues object
            ContentValues contentValues = new ContentValues();
            // Put the task description and selected mPriority into the ContentValues
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);
            // Insert the content values via a ContentResolver
            Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

            // Display the URI that's returned with a Toast
            // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
            // Finish activity (this returns back to MainActivity)
            finish();
        } else if (mPriority == 2) {
            String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
            if (input.length() == 0) {
                return;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION2, input);
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY2, mPriority);
            Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

            if(uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
            finish();
        } else if (mPriority == 3) {
            String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
            if (input.length() == 0) {
                return;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION3, input);
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY3, mPriority);
            Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

            if(uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
            finish();
        } else if (mPriority == 4){
            String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
            if (input.length() == 0) {
                return;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION4, input);
            contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY4, mPriority);
            Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

            if(uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        } else if (((RadioButton) findViewById(R.id.radButton4)).isChecked()) {
            mPriority = 4;
        }
    }
}

