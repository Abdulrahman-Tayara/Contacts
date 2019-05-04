package com.tayara.abdulrahman.telephoneguide;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdditionActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {
    private EditText firstName, lastName, telephoneNumber, mobileNumber, address, birthday;
    private DataBase dataBase = DataBase.getInstance();
    private Calendar calendar = Calendar.getInstance();
    private String MODE;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_addition);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_new_contact));
        MODE = getIntent().getStringExtra("mode");
        firstName = findViewById(R.id.first_name_edit_text);
        lastName = findViewById(R.id.last_name_edit_text);
        telephoneNumber = findViewById(R.id.telephone_number_edit_text);
        mobileNumber = findViewById(R.id.mobile_number_edit_text);
        address = findViewById(R.id.address_edit_text);
        birthday = findViewById(R.id.birthday_edit_text);
        birthday.setOnClickListener(this);
        if (MODE.equals("edit")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            setInfoFromContact(contact);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthday_edit_text:
                new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        birthday.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addition_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_menu_item:
                if (checkAddition()) {
                    if (MODE.equals("add")) {
                        contact = new Contact();
                        getContact(contact);
                        if (dataBase.insertNewItem(contact)) {
                            Toast.makeText(this, getResources().getString(R.string.contact_saved),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else { // MODE == edit
                        getContact(contact);
                        if (dataBase.updateItem(contact)) {
                            Toast.makeText(this, getResources().getString(R.string.contact_saved),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private boolean checkAddition() {
        if (firstName.getText().toString().length() == 0
                && lastName.getText().toString().length() == 0
                && telephoneNumber.getText().toString().length() == 0
                && mobileNumber.getText().toString().length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.enter_name_or_number),
                    Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    private void getContact(Contact item) {
        item.setFirstName(editTextToString(firstName));
        item.setLastName(editTextToString(lastName));
        item.setTelephoneNumber(editTextToString(telephoneNumber));
        item.setMobileNumber(editTextToString(mobileNumber));
        item.setBirthDay(editTextToString(birthday));
        item.setAddress(editTextToString(address));
    }

    private void setInfoFromContact(Contact contact) {
        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        mobileNumber.setText(contact.getMobileNumber());
        telephoneNumber.setText(contact.getTelephoneNumber());
        address.setText(contact.getAddress());
        birthday.setText(contact.getBirthDay());
    }

    private String editTextToString(EditText editText) {
        return editText.getText().toString();
    }

}
