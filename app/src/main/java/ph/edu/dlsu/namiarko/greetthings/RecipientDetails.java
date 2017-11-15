package ph.edu.dlsu.namiarko.greetthings;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipientDetails extends AppCompatActivity {

    RecyclerView rvContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_details);


        rvContacts = (RecyclerView) findViewById(R.id.drawerlist);

        ArrayList<Contact> stuff = new ArrayList<>();

        stuff = displayContacts();

        String name = "name", number = "number";


        ContactAdapter ca = new ContactAdapter(stuff);

        //rvContacts.setHasFixedSize(true);



        rvContacts.setAdapter(ca);
        rvContacts.setLayoutManager( new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));


        Log.i("MY INFO RD", name + " = " + number);



    }


    private ArrayList<Contact> displayContacts() {

        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{ id }, null);

            Log.i("MY INFO", id + "= " + name);

            while(phoneCursor.moveToNext()){
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Log.i("MY INFO", id + "= " + phoneNumber);

                Contact data = new Contact(name, phoneNumber);

                contacts.add(data);
            }//end while(phoneCursor.moveToNext()){

                /*

            Cursor emailCursor = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { id }, null);

            while(emailCursor.moveToNext()){
                String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                Log.i("MY INFO", id + "= " + email);

            }//end while(emailCursor.moveToNext()){

                */


        }//end while(cursor.moveToNext())

        return contacts;

    }//end loadContacts()

}
