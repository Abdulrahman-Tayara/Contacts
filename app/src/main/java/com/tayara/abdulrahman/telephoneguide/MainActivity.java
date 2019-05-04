package com.tayara.abdulrahman.telephoneguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnClickItemRecyclerView.OnRecyclerClickListener, MaterialSearchView.OnQueryTextListener {
    FloatingActionButton addButton;
    FloatingActionButton printButton;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DataBase dataBase = DataBase.getInstance();
    List<Contact> data = new ArrayList<>();
    MaterialSearchView searchView;
    Toolbar toolbar;
    private final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.add_button);
        printButton = findViewById(R.id.print_button);
        addButton.setOnClickListener(this);
        printButton.setOnClickListener(this);
        printButton.hide();
        toolbar = findViewById(R.id.main_toolbar);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new OnClickItemRecyclerView(this,
                recyclerView, this));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        getDataFromDB(data, "all");
        recyclerViewAdapter.loadNewData(data);
        super.onResume();
    }

    //buttons listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                Intent intent = new Intent(this, AdditionActivity.class);
                intent.putExtra("mode", "add");
                startActivity(intent);
                break;
            case R.id.print_button:
                if (getPermission() && data.size() > 0) {
                    Excel excel = new Excel("contacts.xls", this);
                    excel.insertDataInExcelTable(data);
                }
                break;
        }
    }

    //onClick recyclerView item
    @Override
    public void onClick(View view, int position) {
        Contact contact = recyclerViewAdapter.getItemAt(position);
        int color = recyclerViewAdapter.getColorAt(position);
        Intent intent = new Intent(this, ContactInfoActivity.class);
        intent.putExtra("contact", contact);
        intent.putExtra("color", color);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.main_menu_search);
        searchView.setMenuItem(item);
        return true;
    }

    //search view methods
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.equals("")) {
            printButton.hide();
            getDataFromDB(data, "all");
        } else {
            getDataFromDB(data, query);
        }
        recyclerViewAdapter.loadNewData(data);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        printButton.show();
        if (newText.equals("")) {
            printButton.hide();
            getDataFromDB(data, "all");
        } else {
            getDataFromDB(data, newText);
        }
        recyclerViewAdapter.loadNewData(data);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            printButton.hide();
        } else {
            super.onBackPressed();
        }
    }

    boolean getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
            String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            if (checkSelfPermission(writePermission) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(readPermission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{readPermission, writePermission}, REQUEST_PERMISSION);
            }
            return checkSelfPermission(writePermission) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(readPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED
                    || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, getResources().getString(R.string.permission_denied),
                        Toast.LENGTH_SHORT).show();
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void getDataFromDB(List<Contact> data, String tag) {
        Cursor cursor;
        if (tag.equals("all"))
            cursor = dataBase.getAllItems();
        else
            cursor = dataBase.search(tag);
        data.clear();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Contact item = new Contact();
                item.setData(cursor);
                data.add(item);
            }
        }
        sortData(data);
    }

    void sortData(List<Contact> data) {
        Collections.sort(data);
    }

}
