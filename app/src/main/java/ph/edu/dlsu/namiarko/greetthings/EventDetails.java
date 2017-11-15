package ph.edu.dlsu.namiarko.greetthings;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    Button btnCancel, btnSave, btnDelete;
    Calendar myCalendar = Calendar.getInstance();
    EditText etDate, etTime, etTitle, etRecipient, etNumber,  etMessage;
    Spinner spnrRepeat;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter, timeFormatter;

    TimePickerDialog timePickerDialog;
    int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSave = (Button) findViewById(R.id.btn_save);

        etTitle = (EditText) findViewById(R.id.et_title);

        etRecipient = (EditText) findViewById(R.id.et_recipient);
        etRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRecipientField();
            }
        });
        etNumber = (EditText) findViewById(R.id.et_number);

        etMessage = (EditText) findViewById(R.id.et_message);
        spnrRepeat = (Spinner) findViewById(R.id.spnr_repeat);

        etDate = (EditText) findViewById(R.id.et_date);
        etDate.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("MMMM dd, YYYY");
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateField();
            }
        });

        etTime = (EditText) findViewById(R.id.et_time);
        etTime.setInputType(InputType.TYPE_NULL);
        timeFormatter = new SimpleDateFormat("hh:mm");
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeField();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper dbhelper;

                String title = etTitle.getText().toString();
                String date = etDate.getText().toString();
                String time = etTime.getText().toString();
                String recipient = etRecipient.getText().toString();
                String message = etMessage.getText().toString();
                String repeat = spnrRepeat.getSelectedItem().toString();

                dbhelper = new DatabaseHelper(getBaseContext());

                boolean isInserted = dbhelper.addEvent(new Event(title, date, time, recipient, message, repeat));

                if(isInserted == true){

                    Toast.makeText(EventDetails.this,"Event Added!",Toast.LENGTH_LONG).show();

                    Intent i = new Intent();
                    i.setClass(getBaseContext(), Homescreen.class);
                    startActivityForResult(i, 0);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();

                i.setClass(getBaseContext(), Homescreen.class);

                startActivityForResult(i, 6);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                DeleteDialog dd = new DeleteDialog();
                dd.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void setDateField(){

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setTimeField() {

        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void setRecipientField() {
        /*
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventDetails.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_recipient_details, null);

        mBuilder.setView(mView);
       AlertDialog dialog = mBuilder.create();
        dialog.setTitle("Recipient");

        dialog.show();
        */

        Intent i = new Intent();

        i.setClass(getBaseContext(), RecipientDetails.class);

        startActivityForResult(i, 0);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("Recipient"));

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            EditText name = (EditText) findViewById(R.id.et_recipient);
            name.setText( intent.getStringExtra("name"));

            EditText number = (EditText) findViewById(R.id.et_number);
            number.setText( intent.getStringExtra("number"));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {

            EditText Name = (EditText) findViewById(R.id.et_recipient);
            Name.setText( data.getStringExtra("name"));

            EditText Number = (EditText) findViewById(R.id.et_number);
            Number.setText( data.getStringExtra("number"));

        }


    }
}
