package com.example.cs360dariangernandeventtrackingappoption2projectone;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignUp;
    private EventDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // your login.xml layout

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        dbHelper = new EventDatabaseHelper(this);

        btnLogin.setOnClickListener(v -> login());
        btnSignUp.setOnClickListener(v -> signUp());
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (dbHelper.checkUser(username, password)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Navigate to EventTrackerActivity
            Intent intent = new Intent(LoginActivity.this, EventTrackerActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (dbHelper.addUser(username, password)) {
                Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        }
    }
}

