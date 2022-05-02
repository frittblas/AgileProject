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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectProgramActivity extends AppCompatActivity {

    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_program);

        // test array before we collect data with responseListener
        String[] programs = new String[] {"Beginner", "Push", "Pull", "Legs"};

        ListView listView = (ListView) findViewById(R.id.listview);
        List<String> programList = new ArrayList<String>(Arrays.asList(programs));
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
}