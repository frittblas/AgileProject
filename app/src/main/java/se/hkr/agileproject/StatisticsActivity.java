package se.hkr.agileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        populateGraph();

    }

    public void onClickBack(View v) {

        finish();

    }

    void populateGraph(){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        graph.addSeries(series);

        series.setDataPointsRadius(12);
        series.setThickness(8);

        // set graph parameters

        graph.setTitle("Your weight the last year.");
        graph.setTitleTextSize(60);
        //graph.setTitleColor(Color.BLUE);


    }

}

