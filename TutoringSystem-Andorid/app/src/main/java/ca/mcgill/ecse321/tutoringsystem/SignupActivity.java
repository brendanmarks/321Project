package ca.mcgill.ecse321.tutoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Username;
    private EditText StudentId;
    private EditText Email;
    private EditText Password;
    private Button fds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = (EditText) findViewById(R.id.nameForSignup);
        Username = (EditText) findViewById(R.id.usernameForSignup);
        StudentId = (EditText) findViewById(R.id.studentIdForSignup);
        Email = (EditText) findViewById(R.id.emailForSignup);
        Password = (EditText) findViewById(R.id.passwordForSignup);

    }
}
