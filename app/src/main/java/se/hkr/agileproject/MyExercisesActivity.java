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

    //INTE KLART!

    String currentUser;
    String selectedProgram;
    int userProgramId;
    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        selectedProgram = myIntent.getStringExtra("program");
        userProgramId = myIntent.getIntExtra("userprogramid", userProgramId);
        getUserExercisesAsync();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exercises);
        TextView program = (TextView) findViewById(R.id.activityName);
        program.setText(selectedProgram);
    }

    public void onClickBack(View v) {
        finish();
    }

    // create separate date list from dataList
    public static List<String> getDateList(List<String> data) {
        List<String> dateList = new ArrayList<>();
        for (int i = 1; i < data.size(); i = i + 2) {
            dateList.add(data.get(i));
        }
        return dateList;
    }

    // create separate program list from dataList
    public List<String> getProgramList (List<String> data) {
        List<String> programList = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 2) {
            programList.add(data.get(i));
        }
        return programList;
    }

    public void populateList(List<String> programList, List<String> dateList) {
        ListView listView = (ListView) findViewById(R.id.customListViewMyProgram);
        MyProgramsAdapter myProgramsAdapter = new MyProgramsAdapter(this, programList, dateList);
        listView.setAdapter(myProgramsAdapter);
    }

    public void getUserExercisesAsync() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/upload/uploads/getuserprograms.php" +
                "?username=" + currentUser;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dataList = Arrays.asList(response.split(";"));
                        List<String> programList = getProgramList(dataList);
                        List<String> dateList = getDateList(dataList);
                        populateList(programList, dateList);
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