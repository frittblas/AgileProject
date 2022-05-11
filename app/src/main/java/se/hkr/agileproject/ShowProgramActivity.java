package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class ShowProgramActivity extends AppCompatActivity {

    // Kvar att göra:
    // Ändra så de knapparna ej blir fjädrande

    String currentUser;
    String selectedProgram;
    int userProgramCount;
    List<String> dataList = new ArrayList<>();
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        selectedProgram = myIntent.getStringExtra("program");
        getExercisesAsync();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program);
        TextView program = (TextView) findViewById(R.id.activityName);
        program.setText(selectedProgram);
    }

    public void switchActivity(List<String> repsSetsList, List<String> exerciseList) {
        Intent myIntent = new Intent(this, StartedProgramActivity.class);
        myIntent.putExtra("username", currentUser);
        myIntent.putExtra("program", selectedProgram);
        myIntent.putExtra("count", userProgramCount);
        myIntent.putStringArrayListExtra("repssets", (ArrayList<String>) repsSetsList);
        myIntent.putStringArrayListExtra("exercises", (ArrayList<String>) exerciseList);
        startActivity(myIntent);
    }

    public void onClickStartWorkout(View v) {
        createUserProgram(currentUser, selectedProgram);
    }

    public void createUserProgram(String username, String program) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://frittblas.se/agileproject/createuserprogram.php" +
                "?username=" + username + "&program_name=" + program;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // check if the response is good
                        if(response.equals("error")) {
                            Toast.makeText(getApplicationContext(), "Error: " + response, Toast.LENGTH_SHORT).show();
                        } else {
                            userProgramCount = Integer.parseInt(response);
                            switchActivity(getSetsRepsList(dataList), getExerciseList(dataList));
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

    public void onClickBack(View v) {
        finish();
    }

    // create separate reps and sets list from dataList
    public static List<String> getSetsRepsList(List<String> data) {
        List<String> setsRepsList = new ArrayList<>();
        char x = 'x';
        for (int i = 0; i < data.size(); i = i + 3) {
            String rs = data.get(i + 1)  + x + data.get(i + 2);
            setsRepsList.add(rs);
        }
        return setsRepsList;
    }

    // create separate exercise list from dataList
    public List<String> getExerciseList (List<String> data) {
        List<String> exerciseList = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 3) {
            exerciseList.add(data.get(i));
        }
        return exerciseList;
    }

    public void populateList(List<String> exerciseList, List<String> setRepsList) {
        ListView listView = (ListView) findViewById(R.id.customListViewShowProgram);
        ShowProgramAdapter showProgramAdapter = new ShowProgramAdapter(this, exerciseList, setRepsList);
        listView.setAdapter(showProgramAdapter);
        btnStart = (Button) findViewById(R.id.btnStart);
    }

    public void getExercisesAsync() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/getexercises.php" +
                "?programname=" + selectedProgram;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataList = Arrays.asList(response.split(";"));
                        List<String> exerciseList = getExerciseList(dataList);
                        List<String> setRepsList = getSetsRepsList(dataList);
                        populateList(exerciseList, setRepsList);
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