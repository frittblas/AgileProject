package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class MyExercisesActivity extends AppCompatActivity {

    String currentUser;
    String selectedProgram;
    String userProgramId;
    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        selectedProgram = myIntent.getStringExtra("program");
        userProgramId = myIntent.getStringExtra("userprogramid");
        getUserExercisesAsync();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exercises);
        TextView program = (TextView) findViewById(R.id.activityName);
        program.setText(selectedProgram);
    }

    public void onClickBack(View v) {
        finish();
    }

    // create separate weight list from dataList
    public static List<String> getWeightList(List<String> data) {
        List<String> weightList = new ArrayList<>();
        for (int i = 1; i < data.size(); i = i + 2) {
            weightList.add(data.get(i));
        }
        return weightList;
    }

    // create separate exercise name list from dataList
    public List<String> getExerciseNameList (List<String> data) {
        List<String> exerciseNameList = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 2) {
            exerciseNameList.add(data.get(i));
        }
        return exerciseNameList;
    }

    public void populateList(List<String> exerciseNameList, List<String> weightList) {
        ListView listView = (ListView) findViewById(R.id.customListViewMyExercises);
        MyProgramsAdapter myProgramsAdapter = new MyProgramsAdapter(this, exerciseNameList, weightList);
        listView.setAdapter(myProgramsAdapter);
    }

    public void getUserExercisesAsync() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/upload/uploads/getuserexercises.php" +
                "?user_program_count=" + userProgramId + "&username=" + currentUser;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataList = Arrays.asList(response.split(";"));
                        List<String> exerciseNameList = getExerciseNameList(dataList);
                        List<String> weightList = getWeightList(dataList);
                        populateList(exerciseNameList, weightList);
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