package ca.mcgill.ecse321.tutoringsystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

class CreateSessionsActivity extends AppCompatActivity {

    String errorString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sessions);

        //String user = getIntent().getStringExtra("currentUserName");

    }
    /*  The layout class for this page is not yet implemented
    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.sessionsError);
        tvError.setText(errorString);

        if(errorString==null || errorString.length()==0){
            tvError.setVisibility(View.GONE);
        }else{
            tvError.setVisibility(View.VISIBLE);
        }
    }
    */
}
