package com.cse40333.pdrumm.lab2_pdrumm;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.File;
import android.provider.MediaStore;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static java.security.AccessController.getContext;


public class DetailActivity extends AppCompatActivity {
    private static final int CAMERA_INTENT = 1;
    private File OUTPUT;
    private String AUTHORITY = "com.cse40333.pdrumm.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Grab items passed from previous activity
        Game game = (Game) getIntent().getSerializableExtra("Game");
        Team awayTeam = game.getAwayTeam();
        Team homeTeam = game.getHomeTeam();

        // Get View elements
        TextView gameDate = (TextView) this.findViewById(R.id.headerGameDate);
        TextView gameLocation = (TextView) this.findViewById(R.id.headerGameLocation);
        TextView school1Name = (TextView) this.findViewById(R.id.school1Name);
        TextView school1Nickname = (TextView) this.findViewById(R.id.school1Nickname);
        TextView school1Record = (TextView) this.findViewById(R.id.school1Record);
        ImageView school1Logo = (ImageView) this.findViewById(R.id.school1Logo);
        TextView school2Name = (TextView) this.findViewById(R.id.school2Name);
        TextView school2Nickname = (TextView) this.findViewById(R.id.school2Nickname);
        TextView school2Record = (TextView) this.findViewById(R.id.school2Record);
        ImageView school2Logo = (ImageView) this.findViewById(R.id.school2Logo);
        TextView scoreboardScore = (TextView) this.findViewById(R.id.scoreboardGameScore);
        TextView scoretableSchool1Name = (TextView) this.findViewById(R.id.scoretableSchool1Name);
        TextView scoretableSchool1Score1 = (TextView) this.findViewById(R.id.school1FirstHalfScore);
        TextView scoreTableSchool1Score2 = (TextView) this.findViewById(R.id.school1SecondHalfScore);
        TextView scoreTableSchool1Score = (TextView) this.findViewById(R.id.school1TotalScore);
        TextView scoretableSchool2Name = (TextView) this.findViewById(R.id.scoretableSchool2Name);
        TextView scoretableSchool2Score1 = (TextView) this.findViewById(R.id.school2FirstHalfScore);
        TextView scoreTableSchool2Score2 = (TextView) this.findViewById(R.id.school2SecondHalfScore);
        TextView scoreTableSchool2Score = (TextView) this.findViewById(R.id.school2TotalScore);
        Button cameraButton = (Button) this.findViewById(R.id.btnCamera);

        // Get images
        String mDrawableName = awayTeam.getTeamLogo();
        int awayLogoId = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        mDrawableName = homeTeam.getTeamLogo();
        int homeLogoId = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getPackageName());

        // Format date
        DateFormat df = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dateString = df.format(game.getGameDate());

        // Set View elements
        gameDate.setText(dateString);
        gameLocation.setText(homeTeam.getTeamStadium());
        school1Name.setText(awayTeam.getTeamName());
        school1Nickname.setText(awayTeam.getTeamNickname());
        school1Record.setText(awayTeam.getTeamRecord());
        school1Logo.setImageResource(awayLogoId);
        school2Name.setText(homeTeam.getTeamName());
        school2Nickname.setText(homeTeam.getTeamNickname());
        school2Record.setText(homeTeam.getTeamRecord());
        school2Logo.setImageResource(homeLogoId);
        scoreboardScore.setText(game.getFinalScore());
        scoretableSchool1Name.setText(awayTeam.getTeamName());
        scoretableSchool1Score1.setText(String.valueOf(game.getAwayFirstHalfScore()));
        scoreTableSchool1Score2.setText(String.valueOf(game.getAwaySecondHalfScore()));
        scoreTableSchool1Score.setText(String.valueOf(game.getAwayFinalScore()));
        scoretableSchool2Name.setText(String.valueOf(homeTeam.getTeamName()));
        scoretableSchool2Score1.setText(String.valueOf(game.getHomeFirstHalfScore()));
        scoreTableSchool2Score2.setText(String.valueOf(game.getHomeSecondHalfScore()));
        scoreTableSchool2Score.setText(String.valueOf(game.getHomeFinalScore()));

        // Create listener for camera button to start camera intent
        View.OnClickListener camClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filename = getPictureName();
                File imagePath = new File(getApplicationContext().getFilesDir(), "images");
                if (!imagePath.exists()) imagePath.mkdirs();
                OUTPUT = new File(imagePath, filename);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                final Uri outputUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, OUTPUT);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                getApplicationContext().grantUriPermission(
                        "com.google.android.GoogleCamera",
                        outputUri,
                        FLAG_GRANT_WRITE_URI_PERMISSION | FLAG_GRANT_READ_URI_PERMISSION
                );
                cameraIntent.setFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(cameraIntent, CAMERA_INTENT);
            }
        };
        cameraButton.setOnClickListener(camClick);
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "BestMoments" + timestamp + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_INTENT) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                final Uri outputUri = FileProvider.getUriForFile(this, AUTHORITY, OUTPUT);
                ImageView imgView = (ImageView) findViewById(R.id.detailImg);
                imgView.setImageURI(outputUri);
            }
        }
    }

}
