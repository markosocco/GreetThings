package ph.edu.dlsu.namiarko.greetthings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>{

    public LayoutInflater inflater;
    public Context context;

    List<Contact> data = Collections.emptyList();

    public ContactAdapter(List<Contact> data){

        this.data = data;
    }


    //@Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.from(parent.getContext()).inflate(R.layout.activity_contact_layout, parent, false);
        Activity a = new Activity();

        return new MyViewHolder(v);
    }


    //@Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Contact current = data.get(position);

        if (position % 2 == 1)
            holder.itemView.setBackgroundColor(Color.WHITE);
        else
            holder.itemView.setBackgroundColor(Color.GRAY);


        holder.name.setText(current.getName());
        holder.number.setText(current.getNumber());


        holder.name.setTag(current);
        holder.number.setTag(current);
        holder.itemView.setTag(current);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = (Contact) view.getTag();

                Toast.makeText(view.getContext(), "User clicked on " +c.getName() + " " + c.getNumber() + "| Press Back to Confirm", Toast.LENGTH_SHORT).show();

                String name =   c.getName();
                String number = c.getNumber();
                Intent intent = new Intent("Recipient");

                intent.putExtra("name", name);
                intent.putExtra("number", number);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

               // ((Activity) context).finish();

                Log.i("MY INFO", name + " = " + number);
            }


        });
    }

    //@Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView number;

        private Activity activity;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_txt);
            number = (TextView) itemView.findViewById(R.id.number_txt);
        }
    }
}
