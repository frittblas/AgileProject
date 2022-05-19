package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    // get the graph from layout and create 2 lines (number series)
    GraphView graph;
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

    String currentUser; // current user nothing special
    // list with the layout: weight, date, weight, date etc
    List<String> userWeightListWithDates = new ArrayList<>();
    int targetWeight; // the users target weight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent myIntent = getIntent();
        currentUser = myIntent.getStringExtra("username");

        // call this to get target and user weight, onResponse will call populateGraph()
        getUserWeightAsync();


        // The date (x axis, Must be unique!! Otherwise crash)

/*
        userWeightListWithDates.add("12");
        userWeightListWithDates.add("2022-01-17");
        userWeightListWithDates.add("55");
        userWeightListWithDates.add("2022-02-17");
        userWeightListWithDates.add("34");
        userWeightListWithDates.add("2022-03-17");
        userWeightListWithDates.add("15");
        userWeightListWithDates.add("2022-04-17");
        userWeightListWithDates.add("100");
        userWeightListWithDates.add("2022-05-17");
*/

        //targetWeight = 107;

        //userWeightListWithDates.add("0");
        //userWeightListWithDates.add("0");

        //populateGraph(0, userWeightListWithDates);


    }

    public void onClickBack(View v) {

        finish();

    }

    public void onClickAddUserWeight(View v) {


        // first the weight
        EditText editWeight = findViewById(R.id.editUserWeight);
        String weight = editWeight.getText().toString();

        // then the date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String weightDate = formatter.format(date).toString();

        //Toast.makeText(getApplicationContext(), "weight: " + weight + "weightDate: " + weightDate, Toast.LENGTH_SHORT).show();

        if (!(weight.length() == 0)){
            addUserWeightAsync(weight, weightDate);
            editWeight.setText("");
            //Toast.makeText(getApplicationContext(), "Thanks!", Toast.LENGTH_SHORT).show();

            // add new weight and date to the local list aswell (so we can update immediately)
            //userWeightListWithDates.add(weight);
            //userWeightListWithDates.add(weightDate);
            //targetWeight = Integer.parseInt(weight);
            //populateGraph(targetWeight, userWeightListWithDates);
            //getUserWeightAsync();
            Intent intent = getIntent();
            //intent.putExtra("username", currentUser); // not needed
            finish();
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "No input!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickSetTargetWeight(View v) {

        // get target weight from editView
        EditText editWeight = findViewById(R.id.editTargetWeight);
        String weight = editWeight.getText().toString();

        if (!(weight.length() == 0)){
            targetWeight = Integer.parseInt(weight);
            setTargetWeightAsync(targetWeight);
            editWeight.setText("");
            Toast.makeText(getApplicationContext(), "Thanks!", Toast.LENGTH_SHORT).show();

            // update the target weight line
            //graph.removeSeries(series1);
            //series1.appendData(new DataPoint(0, targetWeight), true, 4);
            //series1.appendData(new DataPoint(365, targetWeight), true, 4);
            //graph.addSeries(series1);

            Intent intent = getIntent();
            //intent.putExtra("username", currentUser); // not needed
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No input!", Toast.LENGTH_SHORT).show();
        }

    }

    void populateGraph(int targetWeight, List<String> userWeightListWithDates) {


        // get the graph from layout and create 2 lines (number series)
        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setScrollable(true);
        //LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        //LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        // remove old series if we are updating
        //graph.removeAllSeries();


        // first set target weight (blue)
        series1.appendData(new DataPoint(0, targetWeight), true, 4);
        series1.appendData(new DataPoint(365, targetWeight), true, 4);

        // 2 extra points to fix the graph
        //series1.appendData(new DataPoint(400, 40), true, 4);
        //series1.appendData(new DataPoint(400, 120), true, 4);


        //Log.d("Debug", userWeightListWithDates.toString());

        //int test = userWeightListWithDates.get(0).compareTo("0");

        // only bother to plot user weight if there is data
        if(userWeightListWithDates.get(0).compareTo("0") != 0) {

            // then set user weight (red line)

            int x = 0;  // day
            int y = 0;  // weight
            int months;
            int days;
            int reps = userWeightListWithDates.size();

            for (int i = 0; i < reps; i += 2) {

                // store the weight in y
                y = Integer.parseInt(userWeightListWithDates.get(i));

                // then get the date from i + 1 in the list
                // first isolate the MM part and convert to int
                months = Integer.parseInt(userWeightListWithDates.get(i + 1).substring(5, 7));
                // then isolate the dd and convert to int
                days = Integer.parseInt(userWeightListWithDates.get(i + 1).substring(8, 10));

                // get the time in days
                days = days + months * 30;

                x = days;   // for clarity :)

                // add the point at x, y
                series2.appendData(new DataPoint(x, y), true, 365);

            }

        }

        // add the lines to the graph
        graph.addSeries(series1);
        graph.addSeries(series2);

        // set some nice graphical properties
        series1.setTitle("Target");
        series1.setThickness(6);
        series1.setColor(Color.BLUE);

        series2.setTitle("Weight");
        series2.setThickness(6);
        series2.setColor(Color.RED);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(12);

        // set graph parameters
        graph.setTitle("Your weight this year");
        graph.setTitleTextSize(60);
        graph.setTitleColor(Color.BLACK);
        //graph.setTitleColor(Color.BLUE);

        // legend (color codes op to the right)
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        // add x,y text

        //GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        //gridLabel.setHorizontalAxisTitle("Days");
        //gridLabel.setHorizontalAxisTitleTextSize(30);
        //gridLabel.setVerticalAxisTitle("Kg");
        //gridLabel.setVerticalAxisTitleTextSize(30);


    }


    public void getUserWeightAsync() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/getuserweight.php"  +
                "?username=" + currentUser;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<String> messageList = new ArrayList<>();
                        userWeightListWithDates.clear();

                        messageList = Arrays.asList(response.split(";"));
                        List<String> userWeightListWithDates = new ArrayList<>();

                        targetWeight = Integer.parseInt(messageList.get(0));

                        for (int i = 1; i < messageList.size(); i++) {
                            userWeightListWithDates.add(messageList.get(i));
                        }

                        //Toast.makeText(getApplicationContext(), "userW" + userWeightListWithDates.get(0), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Date: " + userWeightListWithDates.get(1), Toast.LENGTH_SHORT).show();

                        Log.d("Debug", "" + targetWeight);
                        Log.d("Debug", userWeightListWithDates.toString());

                        populateGraph(targetWeight, userWeightListWithDates);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void addUserWeightAsync(String weight, String date) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/adduserweight.php"  +
                "?username=" + currentUser + "&weight=" + weight + "&WeightDate=" + date;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.compareTo("success") != 0)
                            Toast.makeText(getApplicationContext(), "Can only log once per day!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void setTargetWeightAsync(int target) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://frittblas.se/agileproject/settargetweight.php"  +
                "?username=" + currentUser + "&targetweight=" + target;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // do stuff
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
