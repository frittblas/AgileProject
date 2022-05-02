package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    String currentUser;

    enum toActivity {
        SELECT_PROGRAM,
        SOMETHING_ELSE // change this :)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");
    }

    public void switchActivity(toActivity to) {

        Intent myIntent = null;

        switch(to) {

            case SELECT_PROGRAM :
                myIntent = new Intent(this, SelectProgramActivity.class);
                break;
            case SOMETHING_ELSE :
                // add new activity here
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

}