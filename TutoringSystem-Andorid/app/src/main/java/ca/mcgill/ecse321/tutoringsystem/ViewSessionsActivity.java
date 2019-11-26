package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class ViewSessionsActivity extends AppCompatActivity {
    String error = null;

    private List<String> sessionIDs;
    private List<String> dates;
    private List<String> startTimes;
    private List<String> endTimes;
    private List<String> tutorialID;
    private List<String> courses;
    private List<String> tutors;
    private ArrayAdapter<String> sessionAdapter;
    private ArrayAdapter<String> dateAdapter;
    private ArrayAdapter<String> startTimeAdapter;
    private ArrayAdapter<String> endTimeAdapter;
    private ArrayAdapter<String> tutorialAdapter;
    private ArrayAdapter<String> courseAdapter;
    private ArrayAdapter<String> tutorAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);
    }
}
