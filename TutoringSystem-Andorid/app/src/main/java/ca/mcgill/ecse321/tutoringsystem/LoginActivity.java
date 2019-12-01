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
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.usernameText);
        Password = (EditText) findViewById(R.id.passwordText);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.loginButton);

        Info.setText("Number of attempts remaining: 5");

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
        Log.d("validateUsername","Username to validate: "+username);
        //replace spaces with url encode value %20
        String usernameEncoded = username.replaceAll("\\s+", "%20");

        String urlToGet = "students/"+usernameEncoded;

        String popUpMessage = "User is: "+usernameEncoded;
        Toast.makeText(getApplicationContext(), popUpMessage, Toast.LENGTH_LONG).show();

        HttpUtils.get(urlToGet, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();

                String popUpMessage = "User ("+username+") was found in the database.";
                Toast.makeText(getApplicationContext(), popUpMessage, Toast.LENGTH_LONG).show();

                String databasePassword = "";
                try {
                    //search in the returned JSON response for the password field, then return its value
                    databasePassword = response.getString("password");
                    Log.d("databasePassword", "Database password: "+databasePassword);

                } catch (JSONException e) {
                    //If the password could not be retrieved (usually errors with the JSON object saved in DB)
                    Info.setText("Error " + statusCode + ": Password could not be retrieved.");
                    if (counter <= 0) {
                        Login.setEnabled(false);
                    }
                    e.printStackTrace();
                }

                //get the password in the text field
                String enteredPassword = passwordTextView.getText().toString();

                //compare the given password with the password stored in the database for the requested user
                if (enteredPassword.equals(databasePassword)) {
                    Log.d("success", "Login successful. The entered password ("+enteredPassword+") matched the database password ("+databasePassword+")");
                    //launch the user homepage activity
                    openHomePage(username);

                } else {
                    // This part shows the number of attempts remaining before we disable the login button
                    counter--;
                    Info.setText("Invalid password. Number of attempts remaining : " + String.valueOf(counter));
                    if (counter <= 0) {
                        Login.setEnabled(false);
                    }
                }
                usernameTextView.setText("");
                passwordTextView.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("code", "Error when retrieving the requested user in the system database (code: "+statusCode+")");
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
