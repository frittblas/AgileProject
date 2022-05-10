package se.hkr.agileproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


// Att göra
// 1. Ska ta mot lista från ShowProgramActivity
// 2. Ska spara vikt och status (finished or in progress) i databasen

public class StartedProgramActivity extends AppCompatActivity {

    private ListView listView;
    private CustomListAdapter listAdapter;
    List<String> exercises = new ArrayList<>();
    Button btnFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_program);
        getExercises();
        listView = (ListView) findViewById(R.id.customListView);
        listAdapter = new CustomListAdapter(this,exercises);
        listView.setAdapter(listAdapter);
        btnFinished = (Button) findViewById(R.id.btnFinish);
    }

    // REMOVE THIS! PLACEHOLDER!
    public void getExercises() {
        exercises.add("Chins");
        exercises.add("Jump around");
        exercises.add("Hang on to ceiling");
        exercises.add("Push ups");
        exercises.add("Pull up");
        exercises.add("Squats");
        exercises.add("Heel touch");
        exercises.add("Plank");
    }
}