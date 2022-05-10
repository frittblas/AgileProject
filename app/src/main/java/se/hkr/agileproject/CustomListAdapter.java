package se.hkr.agileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class CustomListAdapter extends BaseAdapter {

    public List<String> listExercises;
    public List<String> listSetsReps;
    private Context context;

    public CustomListAdapter(Context context,List<String> listExercises, List<String> listSetsReps) {
        this.context = context;
        this.listExercises = listExercises;
        this.listSetsReps = listSetsReps;
    }

    @Override
    public int getCount() {
        return listExercises.size();
    }

    @Override
    public String getItem(int position) {
        return listExercises.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    // Reads the given list of exercises and creates rows
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView
            , ViewGroup parent) {
        View row;
        final CustomListViewHolder listViewHolder;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_listview,parent,false);
            listViewHolder = new CustomListViewHolder();
            listViewHolder.exerciseName = row.findViewById(R.id.exerciseName);
            listViewHolder.setsReps = row.findViewById(R.id.setsReps);
            listViewHolder.btnAdd = row.findViewById(R.id.btnAdd);
            listViewHolder.editTextWeight = row.findViewById(R.id.editTextWeight);
            listViewHolder.btnSubtract = row.findViewById(R.id.btnSubtract);
            row.setTag(listViewHolder);
        }
        else
        {
            row=convertView;
            listViewHolder= (CustomListViewHolder) row.getTag();
        }

        listViewHolder.exerciseName.setText(listExercises.get(position));
        listViewHolder.setsReps.setText(listSetsReps.get(position));

        // Adds functionality to the add button
        listViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                displayWeight(listViewHolder.editTextWeight, 1);
            }
        });
        // Adds functionality to the subtract button
        listViewHolder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWeight(listViewHolder.editTextWeight, -1);
            }
        });

        return row;
    }

    // Method for updating weight in Edittext
    @SuppressLint("SetTextI18n")
    private void displayWeight(EditText editTextWeight, int value) {
        // Reads current value in editTextWeight
        int weight = Integer.parseInt(editTextWeight.getText().toString());
        // If the value is 1
        if (value > 0)
            weight += value;
        else {
            // If the value is -1
            if (weight > 0)
                weight += value;
        }
        // Sets new value in editTextWeight
        editTextWeight.setText("" + weight);
    }
}
