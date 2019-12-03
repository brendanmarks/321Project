package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SignupActivity extends AppCompatActivity {
    String errorString = null;

    private EditText Name;
    private EditText Username;
    private EditText Email;
    private EditText Password;
    private Button ButtonForSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = (EditText) findViewById(R.id.nameForSignup);
        Username = (EditText) findViewById(R.id.usernameForSignup);
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
        final TextView userNameTextView = (TextView) findViewById(R.id.usernameForSignup);
        final TextView passwordTextView = (TextView) findViewById(R.id.passwordForSignup);


        final String name = nameTextView.getText().toString();
        //check here for empty edit texts
        if(name.length()==0||name.equals(null)) {
            errorString += "Please specify a username.";
            refreshErrorMessage();
            return;
        }
        //encoding spaces before adding them to the POST url
        final String encodedUserName = name.replaceAll("\\s+", "%20");

        final String username = userNameTextView.getText().toString();
        //check here for empty edit texts
        if(username.length()==0||username.equals(null)) {
            errorString += "Please specify a name.";
            refreshErrorMessage();
            return;
        }
        //encoding spaces before adding them to the POST url
        final String encodedName = username.replaceAll("\\s+", "%20");

        //retrieve all url parameters for the post request
        final String email = emailTextView.getText().toString();
        //check here for empty edit texts
        if(email.length()==0||email.equals(null)) {
            errorString += "Please specify an Email address.";
            refreshErrorMessage();
            return;
        }
        final String password = passwordTextView.getText().toString();
        //check here for empty edit texts
        if(password.length()==0||password.equals(null)) {
            errorString += "Please specify a Password.";
            refreshErrorMessage();
            return;
        }
        String urlToGet = "students/"+username;

        HttpUtils.get(urlToGet, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //the user already exists in database
                errorString += "Username ("+name+") already exists. Please enter a different Username.";
                refreshErrorMessage();
                userNameTextView.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //the user does not exist already, thus we go ahead and create it
                errorString = "";
                //POST url with parameters and student name endpoint
                String urlToPost = "students/"
                        +encodedUserName
                        +"?email="+email
                        +"&username="+name
                        +"&password="+password;
                //POST request
                HttpUtils.post(urlToPost, new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        String popUpMessage = "User "+name+" has been created.";
                        Toast.makeText(getApplicationContext(), popUpMessage, Toast.LENGTH_LONG).show();

                        refreshErrorMessage();
                        nameTextView.setText("");
                        emailTextView.setText("");
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
        });
    }

    //return to login
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}