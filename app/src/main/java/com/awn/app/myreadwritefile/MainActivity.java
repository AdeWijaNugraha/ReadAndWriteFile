package com.awn.app.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNew, btnSave, btnOpen;
    EditText edtText, edtTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.btn_new);
        btnOpen = (Button) findViewById(R.id.btn_open);
        btnSave = (Button) findViewById(R.id.btn_save);
        edtText = (EditText) findViewById(R.id.edt_text);
        edtTitle = (EditText) findViewById(R.id.edt_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_new:
                newFile();
                break;
            case R.id.btn_open:
                openFile();
                break;
            case R.id.btn_save:
                saveFile();
                break;
        }
    }

    public void newFile() {
        edtTitle.setText("");
        edtText.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    public void saveFile() {
        if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString().trim();
            String text = edtText.getText().toString().trim();

            FileHelper.writeToFile(title, text, this);

            Toast.makeText(this, "Saving " + title + " file", Toast.LENGTH_SHORT).show();
        }
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                // Do something with the selection
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        edtTitle.setText(title);
        edtText.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }
}
