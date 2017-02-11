package com.cse40333.pdrumm.lab2_pdrumm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String[]> table_rows = new ArrayList<String[]>() {
        {
            // Instantiate the ArrayList with the basketball game data
            String[] schools = {"Ohio State", "Florida State", "Wake Forest", "Boston College", "North Carolina State", "Georgia Tech", "Virginia", "Michigan State"};
            String[] dates = {"Feb 11", "Feb 14", "Feb 18", "Feb 26", "March 1", "March 4", "March 7", "March 16"};
            for (int i=0; i<schools.length; ++i) {
                String logo_file = schools[i].toLowerCase().replace(' ','_');
                String[] row = {schools[i], dates[i], logo_file};
                add(row);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a Schedule adapter and attach it to the ListView
        ListAdapter scheduleAdapter = new ScheduleAdapter(this, this.table_rows);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);
    }
}
