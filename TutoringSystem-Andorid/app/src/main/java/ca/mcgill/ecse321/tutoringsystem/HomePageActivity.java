package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    private Button buttonToViewSessions;
    private Button buttonToAddSessions;
    private TextView currentUserBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final String user = getIntent().getStringExtra("currentUserName");
        String popUpMessage = "Login successful for user: "+user;
        Toast.makeText(getApplicationContext(), popUpMessage, Toast.LENGTH_LONG).show();

        currentUserBanner = (TextView) findViewById(R.id.welcomeBanner);
        currentUserBanner.setText("Hey there "+user+"!\nWelcome to GradeSmash!");

        //add onclick listeners to the buttons
        buttonToViewSessions = (Button) findViewById(R.id.GetSessionBtn);
        buttonToViewSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openViewSessionsActivity(user);
            }
        });
        buttonToAddSessions = (Button) findViewById(R.id.CreateSessionBtn);
        buttonToAddSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCreateSessionsActivity(user);
            }
        });
    }

    public void openViewSessionsActivity(String currentUser){
        Intent intent = new Intent(this, ViewSessionsActivity.class);
        //stores the current userName in intent extras ( ~ storage), in order to pass the user name across activities
        intent.putExtra("currentUserName", currentUser);
        startActivity(intent);
    }

    public void openCreateSessionsActivity(String currentUser){
        Intent intent = new Intent(this, CreateSessionsActivity.class);
        //stores the current userName in intent extras ( ~ storage), in order to pass the user name across activities
        intent.putExtra("currentUserName", currentUser);
        startActivity(intent);
    }

}