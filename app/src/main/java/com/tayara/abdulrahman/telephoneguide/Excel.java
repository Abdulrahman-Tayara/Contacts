package com.tayara.abdulrahman.telephoneguide;


import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Excel {

    private String fileName;
    private Context context;
    Excel (String fileName , Context context) {
        this.fileName = fileName;
        this.context = context;
    }
    public void insertDataInExcelTable(List<Contact> data) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Telephone Guide";
        File directory = new File((new File(path)).getAbsolutePath());
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            File file = new File(directory,fileName);
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setLocale(new Locale("en","SYR"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file,workbookSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("contacts",0);
            createCells(sheet);
            for (int i=0;i<data.size();i++) {
                addItem(sheet,i+1,data.get(i));
            }
            workbook.write();
            workbook.close();
            Toast.makeText(context, context.getResources().getString(R.string.inserted_excel)
                    , Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void addItem(WritableSheet sheet,int row,Contact contact) {
        try {
            sheet.addCell(new Label(0,row,String.valueOf(contact.getId())));
            sheet.addCell(new Label(1,row,contact.getFirstName()));
            sheet.addCell(new Label(2,row,contact.getLastName()));
            sheet.addCell(new Label(3,row,contact.getMobileNumber()));
            sheet.addCell(new Label(4,row,contact.getTelephoneNumber()));
            sheet.addCell(new Label(5,row,contact.getAddress()));
            sheet.addCell(new Label(6,row,contact.getBirthDay()));
        }catch (Exception e) {

        }

    }
    private void createCells(WritableSheet sheet) {
        //column , row , cell name
        try {
            sheet.addCell(new Label(0,0,"id"));
            sheet.addCell(new Label(1,0,context.getResources().getString(R.string.first_name)));
            sheet.addCell(new Label(2,0,context.getResources().getString(R.string.last_name)));
            sheet.addCell(new Label(3,0,context.getResources().getString(R.string.mobile_number)));
            sheet.addCell(new Label(4,0,context.getResources().getString(R.string.telephone_number)));
            sheet.addCell(new Label(5,0,context.getResources().getString(R.string.address)));
            sheet.addCell(new Label(6,0,context.getResources().getString(R.string.birthday)));
        }catch (Exception e) {

        }


    }

}
