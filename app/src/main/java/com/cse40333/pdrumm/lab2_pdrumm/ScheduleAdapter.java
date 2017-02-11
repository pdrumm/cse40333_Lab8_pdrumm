package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<String[]> {

    ScheduleAdapter(Context context, ArrayList<String[]> schedule) {
        super(context, R.layout.schedule_item, schedule);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        // Retrieve the View elements
        String[] matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.teamName);
        TextView gameDate = (TextView) scheduleView.findViewById(R.id.gameDate);
        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.teamLogo);
        String mDrawableName = matchItem[2];
        int resID = getContext().getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());

        // Set the text/image properties of the View elements
        teamName.setText(matchItem[0]);
        gameDate.setText(matchItem[1]);
        teamLogo.setImageResource(resID);

        return scheduleView;
    }
}
