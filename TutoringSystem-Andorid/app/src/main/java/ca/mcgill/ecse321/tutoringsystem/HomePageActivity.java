package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    private Button buttonToViewSessions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonToViewSessions = (Button) findViewById(R.id.GetSessionBtn);
        buttonToViewSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openViewSessionsActivity();
            }
        });
    }

    public void openViewSessionsActivity(){
        Intent intent = new Intent(this, ViewSessionsActivity.class);
        startActivity(intent);
    }
}
