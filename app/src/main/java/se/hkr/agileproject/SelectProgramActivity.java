package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class SelectProgramActivity extends AppCompatActivity {

    String currentUser;
    List<String> programList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_program);

        // test array before we collect data with responseListener
        // String[] programs = new String[]{"Beginner", "Push", "Pull", "Legs"};
        // List<String> programList = new ArrayList<String>(Arrays.asList(programs));
        getPrograms();
        ListView listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, programList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "You choose: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
    }

    public void getPrograms() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/getprograms.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // check if the response is good
                        if (response.equals("no programs")) {
                            // show a short little error msg
                            Toast.makeText(getApplicationContext(), "Error: " + response, Toast.LENGTH_SHORT).show();
                        } else {
                            programList = Arrays.asList(response.split(" "));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}