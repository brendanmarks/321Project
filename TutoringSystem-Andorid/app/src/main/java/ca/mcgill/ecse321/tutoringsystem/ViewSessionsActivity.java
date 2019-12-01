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

    private TextView courseName;
    private TextView sessionDate;
    private TextView startTime;
    private TextView endTime;
    private TextView tutorName;

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

        courseName = (TextView) findViewById(R.id.courseName);
        sessionDate = (TextView) findViewById(R.id.sessionDate);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        tutorName = (TextView) findViewById(R.id.tutorName);

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
        if(selection != "Sessions:") {
            String popUpMessage = "The selected session ID is: " + selection;
            Toast.makeText(parent.getContext(), popUpMessage, Toast.LENGTH_SHORT).show();
            updateDisplay(selection);
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
                        sessionIDs.add("Sessions:");

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

    private void updateDisplay(String sessionId) {
        errorString = "";

        String getRequestUrl = "sessions/"+sessionId;

        HttpUtils.get(
                getRequestUrl,
                new RequestParams(),
                new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                    Log.d("response", ""+response.toString());
                    /*
                    try {
                        //search in the returned JSON response for the password field, then return its value


                        //String course = response.getString("course");
                        //Log.d("databasePassword", "Database password: "+databasePassword);
                    } catch (JSONException e) {
                        //If the password could not be retrieved (usually errors with the JSON object saved in DB)
                        //Info.setText("Error " + statusCode + ": Password could not be retrieved.");
                        e.printStackTrace();
                    }*/
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
