package se.hkr.agileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WallActivity extends AppCompatActivity {
    String currentUser;
    List<String> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        getWallPostsAsync();
    }

    public void onClickBack(View v) {

        finish();

    }

    public void populateList(List<String> messageTextList, List<String> nameDateList) {
        ListView listView = (ListView) findViewById(R.id.customListViewWall);
        WallAdapter wallAdapter = new WallAdapter(this, messageTextList, nameDateList);
        listView.setAdapter(wallAdapter);
    }

    public void onClickPostWall(View v){

        EditText message = findViewById(R.id.editTextWall);

        String str_message = message.getText().toString();

        if (!(str_message.length() == 0)){
            createWallPost(currentUser, str_message);
            message.setText("");
            getWallPostsAsync();
        } else {
            Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
            getWallPostsAsync();
        }

    }

    public void createWallPost(String username, String message){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/createwallpost.php" +
                "?username=" + username + "&message=" + message;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void getWallPostsAsync() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/getwallposts.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<String> posterName = new ArrayList<>();
                        List<String> messageText = new ArrayList<>();

                        messageList = Arrays.asList(response.split(";"));

                        for (int i = 0; i < messageList.size(); i += 3) {
                            posterName.add(messageList.get(i) + "  " + messageList.get(i + 2));
                        }
                        for (int i = 1; i < messageList.size(); i += 3) {
                            messageText.add(messageList.get(i));
                        }

                        populateList(messageText, posterName);
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
