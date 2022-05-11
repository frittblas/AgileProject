package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    String currentUser;

    enum toActivity {
        SELECT_PROGRAM,
        THE_WALL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");

        // Assigning username to greeting message
        TextView greetUser = (TextView) findViewById(R.id.textUsername);
        greetUser.setText(currentUser);

    }

    public void switchActivity(toActivity to) {

        Intent myIntent = null;

        switch(to) {

            case SELECT_PROGRAM :
                myIntent = new Intent(this, SelectProgramActivity.class);
                break;
            case THE_WALL :
                myIntent = new Intent(this, WallActivity.class);
                break;

        }

        if(myIntent != null) {
            myIntent.putExtra("username", currentUser);
            startActivity(myIntent);
        }

    }

    public void onClickSelectProgram(View v) {
        switchActivity(toActivity.SELECT_PROGRAM);
    }

    public void onClickWall(View v) {
        switchActivity(toActivity.THE_WALL);
    }

}