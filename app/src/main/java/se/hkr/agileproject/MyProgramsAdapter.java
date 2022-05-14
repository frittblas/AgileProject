package se.hkr.agileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MyProgramsAdapter extends BaseAdapter {

    // Change this
    public List<String> listExercises;
    public List<String> listSetsReps;
    private Context context;

    public MyProgramsAdapter(Context context, List<String> listExercises, List<String> listSetsReps) {
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
        final MyProgramsViewHolder listViewHolder;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_my_programs_listview,parent,false);
            listViewHolder = new MyProgramsViewHolder();
            listViewHolder.programName = row.findViewById(R.id.programNameMyPrograms);
            listViewHolder.date = row.findViewById(R.id.dateMyPrograms);
            row.setTag(listViewHolder);
        }
        else
        {
            row=convertView;
            listViewHolder= (MyProgramsViewHolder) row.getTag();
        }

        listViewHolder.programName.setText(listExercises.get(position));
        listViewHolder.date.setText(listSetsReps.get(position));

        return row;
    }

}
