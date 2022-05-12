package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    String currentUser;

    List<String> messageList = new ArrayList<>();

    enum toActivity {
        SELECT_PROGRAM,
        THE_WALL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");

        // Assigning username to greeting message
        TextView greetUser = (TextView) findViewById(R.id.textUsername);
        greetUser.setText(currentUser);

        // set latest post
        getLatestPostAsync();

    }

    public void switchActivity(toActivity to) {

        Intent myIntent = null;

        switch(to) {

            case SELECT_PROGRAM :
                myIntent = new Intent(this, SelectProgramActivity.class);
                break;
            case THE_WALL :
                myIntent = new Intent(this, WallActivity.class);
                break;

        }

        if(myIntent != null) {
            myIntent.putExtra("username", currentUser);
            startActivity(myIntent);
        }

    }

    public void onClickSelectProgram(View v) {
        switchActivity(toActivity.SELECT_PROGRAM);
    }

    public void onClickWall(View v) {
        switchActivity(toActivity.THE_WALL);
    }

    public void setLatestPost(String post) {

        TextView latestPost = (TextView) findViewById(R.id.latestPostHome);
        latestPost.setText(post);

    }

    public void getLatestPostAsync() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/getlatestwallpost.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setLatestPost(response.toString());
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