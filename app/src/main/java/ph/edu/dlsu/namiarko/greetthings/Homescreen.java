package ph.edu.dlsu.namiarko.greetthings;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Homescreen extends AppCompatActivity {

    RecyclerView rvEvents;
    FloatingActionButton fabCreate, fabSend;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        rvEvents = (RecyclerView) findViewById(R.id.rv_events);
        fabCreate = (FloatingActionButton) findViewById(R.id.fab_create);

        checkContactsPermission();

        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();

                i.setClass(getBaseContext(), EventDetails.class);

                startActivityForResult(i, 0);
            }
        });

        dbhelper = new DatabaseHelper(getBaseContext());
        Cursor events = dbhelper.getAllEventCursor();

        EventAdapter eventAdapter = new EventAdapter(getBaseContext(), events);
        rvEvents.setAdapter(eventAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        fabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });

    }

    private boolean checkContactsPermission(){
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Contact read permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 789);
                return false;
            }else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 123);
                return false;
            }
        }
        return true;
    }

    public void sendSMS(){

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("09178478988", null, "This is to test if the SMS is actually sending", null, null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
    }

}
