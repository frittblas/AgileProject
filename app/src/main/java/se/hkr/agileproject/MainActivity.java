package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onClickLogin(View v) {

        //v.setEnabled(false);

        EditText username = findViewById(R.id.username);
        EditText password  = findViewById(R.id.password);

        String str_user_name = username.getText().toString();
        String str_password  = password.getText().toString();

        if(!str_user_name.isEmpty() && !str_password.isEmpty())
            tryLogin(str_user_name, str_password);

    }

    public void switchActivity(String name) {

        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.putExtra("username", name);
        startActivity(myIntent);

    }

    public void tryLogin(String username, String password) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/login.php" +
                    "?username=" + username + "&password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // check if the response is good
                        if(response.compareTo(username) == 0) {
                            // show a short little success msg (toast)
                            Toast.makeText(getApplicationContext(), "Logged in " + response, Toast.LENGTH_SHORT).show();
                            // switch activity to the start screen
                            switchActivity(username);
                        }
                        else {
                            // show a short little error msg
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