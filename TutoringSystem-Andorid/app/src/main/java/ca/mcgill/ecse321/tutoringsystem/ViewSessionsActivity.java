package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.List;

public class ViewSessionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String errorString = null;

    private List<String> sessionIDs = new ArrayList<>();
    private ArrayAdapter<String> sessionAdapter;

    private TextView courseNameUI;
    private TextView sessionDateUI;
    private TextView startTimeUI;
    private TextView endTimeUI;
    private TextView tutorNameUI;

    private Button review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);

        String user = getIntent().getStringExtra("currentUserName");
        Log.d("User", "Current User: "+user);

        Spinner sessionIDspinner = (Spinner) findViewById(R.id.sessionidspinner);
        sessionAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sessionIDs);
        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionIDspinner.setAdapter(sessionAdapter);
        sessionIDspinner.setOnItemSelectedListener(this);

        courseNameUI = (TextView) findViewById(R.id.courseName);
        sessionDateUI = (TextView) findViewById(R.id.sessionDate);
        startTimeUI = (TextView) findViewById(R.id.startTime);
        endTimeUI = (TextView) findViewById(R.id.endTime);
        tutorNameUI = (TextView) findViewById(R.id.tutorName);

        review = (Button) findViewById(R.id.reviewBtn);

        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                postReview(view);
            }
        });

        //create the list of user sessions
        refreshLists(this.getCurrentFocus(), user);

    }

    //on item selected method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        if(selection != "Sessions") {
            String popUpMessage = "The selected session ID is: " + selection;
            Toast.makeText(parent.getContext(), popUpMessage, Toast.LENGTH_SHORT).show();
            updateDisplay(selection);
        }   else    {
            //set the TextView text values
            courseNameUI.setText("Course");
            tutorNameUI.setText("Tutor");
            sessionDateUI.setText("YYYY-MM-DD");
            startTimeUI.setText("HH:mm");
            endTimeUI.setText("HH:mm");
        }
    }

    //empty method for the implemented AdapterView.OnItemSelectedListener class
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    /** Calls GET **/
    public void refreshLists(View view, String currentUser){
        refreshList(sessionAdapter, sessionIDs, "sessions/student/"+currentUser );
    }

    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, final String userSessionsURL) {
        errorString = "";

        HttpUtils.get(
            userSessionsURL,
            new RequestParams(),
            new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){

                if(response.length() == 0)    {
                    errorString = "This user does not have any sessions yet.";
                    refreshErrorMessage();
                    sessionIDs.add("EMPTY");
                    return;
                }
                sessionIDs.clear();
                sessionIDs.add("Sessions");

                for(int i=0; i < response.length(); i++) {
                    try{
                        Log.d("response", ""+response.toString());
                        //add the session ids in the spinner array
                        sessionIDs.add(response.getJSONObject(i).getString("sessionId"));
                    }catch(Exception e) {
                        errorString += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    errorString += errorResponse.get("message").toString();
                }catch (JSONException e){
                    errorString += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    private void updateDisplay(final String sessionId) {
        errorString = "";

        String getRequestUrl = "sessions/"+sessionId;

        HttpUtils.get(
                getRequestUrl,
                new RequestParams(),
                new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                    Log.d("response", ""+response.toString());

                    try {
                        //the session object contains a tutorial object
                        JSONObject tutorial = response.getJSONObject("assignedTutorial");
                        //the tutorial object contains the Tutor and course assigned to the selected session
                        String tutorName = tutorial.getJSONObject("tutor").getString("name");
                        String courseName = tutorial.getJSONObject("course").getString("courseName");

                        //get the rest of the session information to be displayed
                        String date = response.getString("date");
                        String startTime = response.getString("startTime");
                        String endTime = response.getString("endTime");

                        //set the TextView text values
                        courseNameUI.setText(courseName);
                        tutorNameUI.setText(tutorName);
                        sessionDateUI.setText(date);
                        startTimeUI.setText(startTime);
                        endTimeUI.setText(endTime);

                        refreshErrorMessage();

                    } catch (JSONException e) {
                        errorString += "An error occurred when selecting session "+sessionId+". Some information might be invalid.";
                        e.printStackTrace();
                    }
                    refreshErrorMessage();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try{
                        errorString += errorResponse.get("message").toString();
                    }catch (JSONException e){
                        errorString += e.getMessage();
                    }
                    refreshErrorMessage();
                }
        });

    }
    private void postReview(View view) {
        String popUpMessage = "The Post Review feature has been requested, but is not yet implemented.";
        Toast.makeText(getApplicationContext(), popUpMessage, Toast.LENGTH_SHORT).show();
    }

    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.sessionsError);
        tvError.setText(errorString);

        if(errorString==null || errorString.length()==0){
            tvError.setVisibility(View.GONE);
        }else{
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
