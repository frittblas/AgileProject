package se.hkr.agileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class StartedProgramActivity extends AppCompatActivity {

    String currentUser;
    String selectedProgram;
    int userProgramCount;
    List<String> exerciseList = new ArrayList<>();
    List<String> setsRepsList = new ArrayList<>();
    List<String> weightList = new ArrayList<>();
    List<EditText> etList = CustomListAdapter.getIdArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_program);

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        selectedProgram = myIntent.getStringExtra("program");
        userProgramCount = myIntent.getIntExtra("count", -1);
        setsRepsList = myIntent.getStringArrayListExtra("repssets");
        exerciseList = myIntent.getStringArrayListExtra("exercises");
        TextView program = (TextView) findViewById(R.id.activityName);
        program.setText(selectedProgram);
        //populateWeightList();

        ListView listView = (ListView) findViewById(R.id.customListView);
        CustomListAdapter listAdapter = new CustomListAdapter(this, exerciseList, setsRepsList);
        listView.setAdapter(listAdapter);
    }

    public void onClickBack(View v) {
        finish();
    }

    public void finishWorkout(View v) {
        getWeights();
        Toast.makeText(getApplicationContext(), "Workout finished.", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < exerciseList.size(); i++) {
            postUserProgram(i);
        }
        switchActivity();
    }

    public void switchActivity() {
        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.putExtra("username", currentUser);
        startActivity(myIntent);
    }

    public void getWeights() {
        for (EditText e: etList) {
            weightList.add(e.getText().toString());
        }
    }

    public void populateEditTexts(List<String> editTextList) {
        int i = 0;
        for (EditText e: etList) {
            e.setText(editTextList.get(i));
            i = i+1;
        }
    }

    public void postUserProgram(int i) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/finishuserprogram.php" + "?username=" + currentUser +
                "&userprogramcount=" + userProgramCount + "&exercise_name=" + exerciseList.get(i) + "&exercise_weight=" + weightList.get(i);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("success")) {
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

    public void populateWeightList() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/upload/uploads/getexerciseweight.php" +
                "?username=" + currentUser + "&program_name=" + selectedProgram;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("empty")) {
                            List<String> editTextList = Arrays.asList(response.split(";"));
                            populateEditTexts(editTextList);
                        } else {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
