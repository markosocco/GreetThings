package ph.edu.dlsu.namiarko.greetthings;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.R.attr.data;


public class EventAdapter extends CursorRecyclerViewAdapter<EventAdapter.EventViewHolder> {

    public EventAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(EventViewHolder viewHolder, Cursor cursor) {

        String date = cursor.getString(cursor.getColumnIndex(Event.COLUMN_DATE));
        String time = cursor.getString(cursor.getColumnIndex(Event.COLUMN_TIME));
        String title = cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE));

        viewHolder.tvDate.setText(date);
        viewHolder.tvTime.setText(time);
        viewHolder.tvTitle.setText(title);

    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);

        return new EventViewHolder(v);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate;
        TextView tvTime;
        TextView tvTitle;


        public EventViewHolder(View itemView){
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }

}
