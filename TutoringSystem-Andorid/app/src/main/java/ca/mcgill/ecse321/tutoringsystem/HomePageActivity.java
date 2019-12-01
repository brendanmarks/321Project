package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button buttonToViewSessions;
    private Button buttonToAddSessions;

    String errorString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final String user = getIntent().getStringExtra("currentUserName");
        Log.d("User", "Current User: "+user);



        /*  Other implementation that gets all stored extra variables from intents.
            Could be useful when passing user roles through the different activities

        Bundle globalVariables = getIntent().getExtras();
        if (globalVariables != null) {
            String currentUser = globalVariables.getString("currentUserName");
            Log.d("currentUser", "Current User Name: "+currentUser);
        }
        */


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