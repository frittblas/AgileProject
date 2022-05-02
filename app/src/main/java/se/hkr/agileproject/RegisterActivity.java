package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void switchActivity(String name) {

        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.putExtra("username", name);
        startActivity(myIntent);

    }

    public void onClickRegister(View v){
        Encrypt enc = new Encrypt();
        EditText username = findViewById(R.id.username);
        EditText password  = findViewById(R.id.password);

        String str_user_name = username.getText().toString();
        String str_password  = password.getText().toString();

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matchUsername = p.matcher(str_user_name);
        Matcher matchPassword = p.matcher(str_password);
        boolean specialCharactersUsername = matchUsername.find();
        boolean specialCharactersPassword = matchPassword.find();
        boolean minimumCharacters = (str_user_name.length() >= 4 && str_password.length() >= 4);

        if(!specialCharactersUsername && !specialCharactersPassword && minimumCharacters){
            registerUser(str_user_name, enc.generateSecurePassword(str_password));
        } else{
            Toast.makeText(getApplicationContext(), "Error: Use characters A-Z or numbers 0-9", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(String username, String password){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/createaccount.php" +
                "?username=" + username + "&password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // check if the response is good
                        if(response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "User created successfully.", Toast.LENGTH_SHORT).show();
                            // switch activity to home
                            switchActivity(username);
                        } else if (response.equals("exists")){
                            Toast.makeText(getApplicationContext(), "Error: Username already taken." + response, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: " + response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);


    }
}