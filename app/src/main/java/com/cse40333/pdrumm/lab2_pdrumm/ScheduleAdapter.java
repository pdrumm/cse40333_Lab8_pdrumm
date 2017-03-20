package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ScheduleAdapter extends ArrayAdapter<Team> {

    ScheduleAdapter(Context context, ArrayList<Team> schedule) {
        super(context, R.layout.schedule_item, schedule);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        // Retrieve the View elements
        Team matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.teamName);
        TextView gameDate = (TextView) scheduleView.findViewById(R.id.gameDate);
        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.teamLogo);
        // Get team logo
        String mDrawableName = matchItem.getTeamLogo();
        int resID = getContext().getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());
        // Get date and format
        DateFormat df = new SimpleDateFormat("MMM d", Locale.ENGLISH);
        String dateString = df.format(matchItem.getGame().getGameDate());

        // Set the text/image properties of the View elements
        teamName.setText(matchItem.getTeamName());
        gameDate.setText(dateString);
        teamLogo.setImageResource(resID);

        return scheduleView;
    }
}
