package se.hkr.agileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MyExercisesAdapter extends BaseAdapter {

    public List<String> listExercises;
    public List<String> listWeight;
    private Context context;

    public MyExercisesAdapter(Context context, List<String> listExercises, List<String> listWeight) {
        this.context = context;
        this.listExercises = listExercises;
        this.listWeight = listWeight;
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
        final MyExercisesViewHolder listViewHolder;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_my_exercises_listview,parent,false);
            listViewHolder = new MyExercisesViewHolder();
            listViewHolder.exerciseName = row.findViewById(R.id.exerciseNameMyExercises);
            listViewHolder.weight = row.findViewById(R.id.weightMyExercises);
            row.setTag(listViewHolder);
        }
        else
        {
            row=convertView;
            listViewHolder= (MyExercisesViewHolder) row.getTag();
        }

        listViewHolder.exerciseName.setText(listExercises.get(position));
        listViewHolder.weight.setText(listWeight.get(position));

        return row;
    }

}
