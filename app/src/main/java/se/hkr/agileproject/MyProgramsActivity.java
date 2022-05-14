package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class MyProgramsActivity extends AppCompatActivity {

    //Att göra
    // Lägg in Errorhandling utifall användaren inte har kört något program

    String currentUser;
    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
        getUserProgramsAsync();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_programs);
        TextView program = (TextView) findViewById(R.id.activityName);
        program.setText("My Programs");
    }

    public void switchActivity(String selectedProgram, int userProgramId) {
        Intent myIntent = new Intent(this, MyExercisesActivity.class);
        myIntent.putExtra("username", currentUser);
        myIntent.putExtra("program", selectedProgram);
        myIntent.putExtra("userprogramid", userProgramId);
        startActivity(myIntent);
    }

    public void onClickBack(View v) {
        finish();
    }

    // create separate date list from dataList
    public static List<String> getDateList(List<String> data) {
        List<String> dateList = new ArrayList<>();
        for (int i = 1; i < data.size(); i = i + 3) {
            dateList.add(data.get(i));
        }
        return dateList;
    }

    // create separate program list from dataList
    public List<String> getProgramList (List<String> data) {
        List<String> programList = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 3) {
            programList.add(data.get(i));
        }
        return programList;
    }

    // create separate user program id list from dataList
    public List<Integer> getupIdList (List<String> data) {
        List<Integer> upIdList = new ArrayList<>();
        for (int i = 2; i < data.size(); i = i + 3) {
            upIdList.add(Integer.parseInt(data.get(i)));
        }
        return upIdList;
    }

    public void populateList(List<String> programList, List<String> dateList, List<Integer> upIdlist) {
        ListView listView = (ListView) findViewById(R.id.customListViewMyProgram);
        MyProgramsAdapter myProgramsAdapter = new MyProgramsAdapter(this, programList, dateList);
        listView.setAdapter(myProgramsAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = (String) parent.getItemAtPosition(position);
//                int userProgramId = upIdlist.get(position);
//                switchActivity(selectedItem, userProgramId);
//            }
//        });
    }

    public void getUserProgramsAsync() {

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
                        List<Integer> upIdList = getupIdList(dataList);
                        populateList(programList, dateList, upIdList);
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