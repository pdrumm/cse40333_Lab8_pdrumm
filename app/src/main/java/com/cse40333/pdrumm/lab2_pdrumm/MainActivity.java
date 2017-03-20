package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.design.widget.CoordinatorLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Team notreDame;
    final ArrayList<Team> tableRows = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ND Athletics");

        // Create a "Notre Dame" team and then load in all opponent teams
        notreDame = new Team("Notre Dame", "Fighting Irish", "notre_dame", 20, 7, "Purcell Pavilion at the Joyce Center, Notre Dame, Indiana");
        loadTeamData(tableRows);

        // Create a Schedule adapter and attach it to the ListView
        ListAdapter scheduleAdapter = new ScheduleAdapter(this, tableRows);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        // Create an event listener for each row in the schedule
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Team team = tableRows.get(position);
                intent.putExtra("Game", team.getGame());
                startActivity(intent);
            }
        };
        scheduleListView.setOnItemClickListener(itemClickListener);
    }


    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BasketBall Matches");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, gameSchedule());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }

        else if (res_id == R.id.sync) {
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Sync is not yet implemented", Snackbar.LENGTH_LONG);
            // get snackbar view
            View snackbarView1 = snackbar1.getView();
            TextView tv1 = (TextView) snackbarView1.findViewById(android.support.design.R.id.snackbar_text);
            tv1.setTextColor(Color.WHITE);

            snackbar1.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout, "Wait for the next few labs. Thank you for your patience", Snackbar.LENGTH_LONG);
                    View snackbarView2 = snackbar2.getView();
                    TextView tv2 = (TextView) snackbarView2.findViewById(android.support.design.R.id.snackbar_text);
                    tv2.setTextColor(Color.WHITE);
                    snackbar2.show();
                }
            });
            snackbar1.show();
        }

        else if (res_id == R.id.settings) {
            View v = findViewById(R.id.scheduleListView);
            registerForContextMenu(v);
            openContextMenu(v);
            unregisterForContextMenu(v);
        }
        return true;
    }

    String gameSchedule() {
        StringBuilder sb = new StringBuilder();
        for (Team team : tableRows) {
            sb.append(team.getGameString() + "\n");
        }
        return sb.toString();
    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.floating_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int item_id = item.getItemId();
        if (item_id == R.id.women) {
            // to be implemented later
        }
        return false;
    }

    /**
     * Populate inputted ArrayList with the schedule data stored stored
     * in res/raw/schedule.csv
     */
    private void loadTeamData(ArrayList<Team> teamArray) {
        InputStream inputStream = getResources().openRawResource(R.raw.schedule);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            reader.readLine();  // skip header line
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                // row = School Name,Nickname,Wins,Losses,Stadium,City,State,Game Date,Home/Away,Home First Half Score, Away First Half Score, Home Second Half Score, Away Second Half Score
                String[] row = csvLine.split(",");
                // create the new team
                Team team = new Team(
                        row[0],  // teamName
                        row[1],  // teamNickname
                        row[0].toLowerCase().replace(' ','_'),  // teamLogo
                        Integer.parseInt(row[2]),  // teamWins
                        Integer.parseInt(row[3]),  // teamLosses
                        row[4]+", "+row[5]+", "+row[6]  // teamStadium
                );
                // set the game with the team
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                Date gameDate;
                try {
                    gameDate = format.parse(row[7]);
                } catch(ParseException e) {
                    gameDate = new Date();
                }
                Team away, home;
                if (row[8].equals("home")) {
                    home = this.notreDame;
                    away = team;
                } else {
                    away = this.notreDame;
                    home = team;
                }
                Game game = new Game(gameDate, away, home);
                game.setHomeScore(Integer.parseInt(row[9]), Integer.parseInt(row[11]));
                game.setAwayScore(Integer.parseInt(row[10]), Integer.parseInt(row[12]));
                team.setGame(game);
                // add the team to the array
                teamArray.add(team);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
    }

}
