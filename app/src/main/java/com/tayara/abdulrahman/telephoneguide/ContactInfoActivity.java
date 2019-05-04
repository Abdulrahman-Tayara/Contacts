package com.tayara.abdulrahman.telephoneguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    int color;
    Contact contact;
    TextView contactName, contactMobileNumber, contactTelephoneNumber, contactAddress, contactBirthday;
    DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        toolbar = findViewById(R.id.contact_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent intent = getIntent();
        color = intent.getIntExtra("color", 0);
        contact = (Contact) intent.getSerializableExtra("contact");
        toolbar.setBackgroundColor(color);
        getWindow().setStatusBarColor(color);
        contactName = findViewById(R.id.contact_info_name);
        contactMobileNumber = findViewById(R.id.contact_info_mobile_number);
        contactTelephoneNumber = findViewById(R.id.contact_info_telephone_number);
        contactAddress = findViewById(R.id.contact_info_address);
        contactBirthday = findViewById(R.id.contact_info_birthday);
        setInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_info_menu_delete:
                alertDialog();
                break;
            case R.id.contact_info_menu_edit:
                Intent intent = new Intent(this,AdditionActivity.class);
                intent.putExtra("mode","edit");
                intent.putExtra("contact",contact);
                startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void alertDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage(R.string.alert_delete_contact)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.deleteItem(contact.getId());
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void setInformation() {
        contactName.setText(contact.getFullName());
        if (contact.getMobileNumber().length() > 0) {
            contactMobileNumber.setText(contact.getMobileNumber());
        }
        if (contact.getTelephoneNumber().length() > 0) {
            contactTelephoneNumber.setText(contact.getTelephoneNumber());
        }
        if (contact.getAddress().length() > 0) {
            contactAddress.setText(contact.getAddress());
        }
        if (contact.getBirthDay().length() > 0) {
            contactBirthday.setText(contact.getBirthDay());
        }
    }
}
