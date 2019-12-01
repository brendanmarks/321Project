package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.AsyncHttpClient;



public class SignupActivity extends AppCompatActivity {
    String errorString = null;

    private EditText Name;
    private EditText Username;
    private EditText StudentId;
    private EditText Email;
    private EditText Password;
    private Button ButtonForSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = (EditText) findViewById(R.id.nameForSignup);
        Username = (EditText) findViewById(R.id.usernameForSignup);
        StudentId = (EditText) findViewById(R.id.studentIdForSignup);
        Email = (EditText) findViewById(R.id.emailForSignup);
        Password = (EditText) findViewById(R.id.passwordForSignup);
        ButtonForSignup = (Button)findViewById(R.id.SignupBtn);

        ButtonForSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addUser(view);
            }
        });
    }

    private void refreshErrorMessage() {
        TextView errorTextView = (TextView) findViewById(R.id.errorMessageSignup);
        errorTextView.setText(errorString);

        if(errorString==null || errorString.length()==0){
            errorTextView.setVisibility(View.GONE);
        }else{
            errorTextView.setVisibility(View.VISIBLE);
        }
    }

    public void addUser(View v){
        errorString = "";
        final TextView nameTextView = (TextView) findViewById(R.id.nameForSignup);
        final TextView emailTextView = (TextView) findViewById(R.id.emailForSignup);
        final TextView studentIdTextView = (TextView) findViewById(R.id.studentIdForSignup);
        final TextView userNameTextView = (TextView) findViewById(R.id.usernameForSignup);
        final TextView passwordTextView = (TextView) findViewById(R.id.passwordForSignup);

        String name = nameTextView.getText().toString();
        Log.i("name", "Name: "+name);
        //encoding spaces before adding them to the POST url
        String encodedName = name.replaceAll("\\s+", "%20");
        Log.i("encodedName", "Encoded Name: "+encodedName);

        String email = emailTextView.getText().toString();
        String username = userNameTextView.getText().toString();

        String password = passwordTextView.getText().toString();
        Log.i("password", "Password: "+password);

        //POST url with parameters and student name endpoint
        String urlToPost = "students/"
                +username
                +"?email="+email
                +"&username="+encodedName
                +"&password="+password;

        //POST request
        HttpUtils.post(urlToPost, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                refreshErrorMessage();
                nameTextView.setText("");
                emailTextView.setText("");
                studentIdTextView.setText("");
                userNameTextView.setText("");
                passwordTextView.setText("");
                openLogin();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    errorString += "Could not add this user: "+errorResponse.get("message").toString();
                } catch (JSONException e) {
                    errorString += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
    //return to login
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}