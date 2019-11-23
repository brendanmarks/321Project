package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

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
                validate(Name.getText().toString() , Password.getText().toString());
            }
        });

    }

    //TODO: Implement Login by connecting to back-end!!
    private void validate(String username, String password) {
        if((username.equals("sean")) && (password.equals("seany"))){
            Intent intent = new Intent(LoginActivity.this , HomePageActivity.class);
            startActivity(intent);
        }else{
            // This part shows the number of attempts remaining before we disable the login button
            counter --;
            Info.setText("Number of attempts remaining : " + String.valueOf(counter));
            if(counter <= 0){
                Login.setEnabled(false);
            }
        }
    }

}
