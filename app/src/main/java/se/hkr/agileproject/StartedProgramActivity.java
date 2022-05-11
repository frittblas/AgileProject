package se.hkr.agileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


// Att göra
// 2. Ska spara vikt och status (finished or in progress) i databasen

public class StartedProgramActivity extends AppCompatActivity {

    String currentUser;
    String selectedProgram;
    int userProgramCount;
    List<String> exerciseList = new ArrayList<>();
    List<String> setsRepsList = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter listAdapter;
    Button btnFinished;


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

        listView = (ListView) findViewById(R.id.customListView);
        listAdapter = new CustomListAdapter(this,exerciseList, setsRepsList);
        listView.setAdapter(listAdapter);
        btnFinished = (Button) findViewById(R.id.btnFinish);
    }

    public void onClickBack(View v) {
        finish();
    }
}