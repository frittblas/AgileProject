package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    // Lös så du kan skicka in 2 listor i arrayadaptern
    // Ändra så de knapparna ej blir fjädrande
    // Lägg till en start workout knapp

    String currentUser;
    String selectedProgram;
    List<String> dataList = new ArrayList<>();

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

    public void switchActivity(String name, String selectedProgram,
                               List<String> repsSetsList, List<String> exerciseList) {
        Intent myIntent = new Intent(this, WallActivity.class);
        myIntent.putExtra("username", name);
        myIntent.putExtra("program", selectedProgram);
        myIntent.putStringArrayListExtra("repsSetsList", (ArrayList<String>) repsSetsList);
        myIntent.putStringArrayListExtra("exerciseList", (ArrayList<String>) exerciseList);
        startActivity(myIntent);
    }

    public void onClickBack(View v) {
        finish();
    }

    // create separate reps and sets list from dataList
    public static List<String> getRepsSetsList(List<String> data) {
        List<String> repsSetsList = new ArrayList<>();
        char x = 'x';
        for (int i = 0; i < data.size(); i = i + 3) {
            String rs = data.get(i + 1)  + x + data.get(i + 2);
            repsSetsList.add(rs);
        }
        return repsSetsList;
    }

    // create separate exercise list from dataList
    public List<String> getExerciseList (List<String> data) {
        List<String> exerciseList = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 3) {
            exerciseList.add(data.get(i));
        }
        return exerciseList;
    }

    public void populateList(List<String> exerciseList) {
        ListView listView = (ListView) findViewById(R.id.listview);

        // Using androids template = (this, android.R.layout.selectable_list, programList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, exerciseList);
        listView.setAdapter(arrayAdapter);
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
                        populateList(exerciseList);
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