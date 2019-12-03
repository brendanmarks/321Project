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

public class LoginActivity extends AppCompatActivity {

    String errorString = null;

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.usernameText);
        Password = (EditText) findViewById(R.id.passwordText);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.loginButton);

        Info.setText("");

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate(view);
            }
        });
    }

    private void refreshErrorMessage() {
        TextView errorTextView = (TextView) findViewById(R.id.errorMessageLogin);
        errorTextView.setText(errorString);
        if(errorString==null || errorString.length()==0){
            errorTextView.setVisibility(View.GONE);
        }else{
            errorTextView.setVisibility(View.VISIBLE);
        }
    }

    public void validate(View v) {
        errorString = "";

        final TextView usernameTextView = (TextView) findViewById(R.id.usernameText);
        final TextView passwordTextView = (TextView) findViewById(R.id.passwordText);

        final String username = usernameTextView.getText().toString();
        if(username.length()==0||username.equals(null)) {
            errorString += "Please specify a Username to login.";
            refreshErrorMessage();
            return;
        }
        //replace spaces with url encode value %20
        String usernameEncoded = username.replaceAll("\\s+", "%20");

        final String enteredPassword = passwordTextView.getText().toString();
        if(enteredPassword.length()==0||enteredPassword.equals(null)) {
            errorString += "Please specify a Password for this Username.";
            refreshErrorMessage();
            return;
        }

        String urlToGet = "students/"+usernameEncoded;

        HttpUtils.get(urlToGet, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();

                String databasePassword = "";
                try {
                    //search in the returned JSON response for the password field, then return its value
                    databasePassword = response.getString("password");
                    Log.d("databasePassword", "Database password: "+databasePassword);

                    //compare the given password with the password stored in the database for the requested user
                    if (enteredPassword.equals(databasePassword)) {
                        Log.d("success", "Login successful. The entered password ("+enteredPassword+") matched the database password ("+databasePassword+")");
                        Info.setText("");

                        //launch the user homepage activity
                        openHomePage(username);

                    } else {
                        Info.setText("Invalid password.");
                        passwordTextView.setText("");
                    }
                } catch (JSONException e) {
                    //If the password could not be retrieved (usually errors with the JSON object saved in DB)
                    Info.setText("Error: Password could not be validated.");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                usernameTextView.setText("");
                passwordTextView.setText("");
                try {
                    errorString += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    errorString += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void openHomePage(String currentUser) {
        Intent intent = new Intent(this, HomePageActivity.class);
        //stores the current userName in intent extras ( ~ storage), in order to pass the user name across activities
        intent.putExtra("currentUserName", currentUser);
        startActivity(intent);
    }
}
