package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.List;

public class ViewSessionsActivity extends AppCompatActivity {
    String error = null;

    private List<String> sessionIDs = new ArrayList<>();
    private ArrayAdapter<String> sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);

        Spinner sessionIDspinner = (Spinner) findViewById(R.id.sessionidspinner);
        sessionAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sessionIDs);
        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionIDspinner.setAdapter(sessionAdapter);

        refreshLists(this.getCurrentFocus());

    }

    /** Calls GET over here with "sessions" **/
    public void refreshLists(View view){
        refreshList(sessionAdapter, sessionIDs, "sessions"); //Maybe /sessions/?
    }

    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, final String restFunctionName) { //restFunctionName is the url "sessions"
        HttpUtils.get(
                restFunctionName,
                new RequestParams(),
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                        sessionIDs.clear();
                        sessionIDs.add("Please select...");
                        for(int i=0; i<response.length(); i++) {
                            try{
                                //TODO: Dom's part, handle the get
                                //sessionIDs.add(response.getJSONObject(i).getString("sessionId")); /** Change sessionId to something which will return? **/
                                sessionIDs.add("this "+i);//TO REMOEV
                            }catch(Exception e) {
                                error += e.getMessage();
                            }
                            refreshErrorMessage();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try{
                            error += errorResponse.get("message").toString();
                        }catch (JSONException e){
                            error += e.getMessage();
                        }
                        refreshErrorMessage();
                    }

                });
    }

    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.sessionsError);
        tvError.setText(error);

        if(error==null || error.length()==0){
            tvError.setVisibility(View.GONE);
        }else{
            tvError.setVisibility(View.VISIBLE);
        }
    }

}
