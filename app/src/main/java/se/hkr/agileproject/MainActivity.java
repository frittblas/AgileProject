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
        setContentView(R.layout.activity_main);

    }

    public void onClickLogin(View v) {

        //v.setEnabled(false);

        EditText user_name = findViewById(R.id.edit_name);
        EditText password  = findViewById(R.id.edit_pwd);

        String str_user_name = user_name.getText().toString();
        String str_password  = password.getText().toString();

        if(!str_user_name.isEmpty() && !str_password.isEmpty())
            tryLogin(str_user_name, str_password);

    }

    public void switchActivity(String name) {

        Intent myIntent = new Intent(this, StartScreenActivity.class);
        myIntent.putExtra("user_name", name);
        startActivity(myIntent);

    }

    public void tryLogin(String user_name, String password) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/login.php" +
                    "?user_name=" + user_name + "&password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // check if the response is good
                        if(response.compareTo(user_name) == 0) {
                            // show a short little success msg (toast)
                            Toast.makeText(getApplicationContext(), "Logged in " + response, Toast.LENGTH_SHORT).show();
                            // switch activity to the start screen
                            switchActivity(user_name);
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

    // Returns a string with 61 chars
    public String generateSecurePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
    }

    // Encrypt users input to see if it matches password saved in db
    public boolean verifyUserPassword(String providedPassword, String storedPassword) {
        boolean confirmed = false;
        String newSecurePassword = generateSecurePassword(providedPassword);
        if (newSecurePassword.equals(storedPassword))
            confirmed = true;

        return confirmed;
    }
}