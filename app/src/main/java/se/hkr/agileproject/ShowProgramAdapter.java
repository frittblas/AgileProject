package se.hkr.agileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ShowProgramAdapter extends BaseAdapter {

    public List<String> exerciseList;
    public List<String> setsRepsList;
    private Context context;

    public ShowProgramAdapter(Context context, List<String> exerciseList, List<String> setsRepsList) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.setsRepsList = setsRepsList;
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int i) {
        return exerciseList.get(i);
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
        final ShowProgramViewHolder listViewHolder;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_select_program,parent,false);
            listViewHolder = new ShowProgramViewHolder();
            listViewHolder.exerciseName = row.findViewById(R.id.exerciseName);
            listViewHolder.setsReps = row.findViewById(R.id.setsReps);
            row.setTag(listViewHolder);
        }
        else
        {
            row=convertView;
            listViewHolder= (ShowProgramViewHolder) row.getTag();
        }

        listViewHolder.exerciseName.setText(exerciseList.get(position));
        listViewHolder.setsReps.setText(setsRepsList.get(position));

        return row;
    }

}
