package se.hkr.agileproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class WallAdapter extends BaseAdapter {

    public List<String> listMessages;
    public List<String> listPosterNames;
    public List<String> listPostDates;
    private Context context;

    public WallAdapter(Context context, List<String> listMessages, List<String> listPosterNames) {
        this.context = context;
        this.listMessages = listMessages;
        this.listPosterNames = listPosterNames;
    }

    @Override
    public int getCount() {
        return listMessages.size();
    }

    @Override
    public String getItem(int position) {
        return listMessages.get(position);
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
        final WallViewHolder listViewHolder;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_wall_listview,parent,false);
            listViewHolder = new WallViewHolder();
            listViewHolder.messageText = row.findViewById(R.id.messageWall);
            listViewHolder.posterName = row.findViewById(R.id.nameDateWall);
            row.setTag(listViewHolder);
        }
        else
        {
            row=convertView;
            listViewHolder= (WallViewHolder) row.getTag();
        }

        listViewHolder.messageText.setText(listMessages.get(position));
        listViewHolder.posterName.setText(listPosterNames.get(position));

        return row;
    }

}
