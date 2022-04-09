package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.BreakIterator;

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

    void tryLogin(String user_name, String password) {

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
                            // now we can safely switch activity to the start screen
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