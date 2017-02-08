package com.domain.thomas.readit_tesseract;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.domain.thomas.readit_tesseract.EditImageActivity.rawText;

public class EndActivity extends AppCompatActivity {

    String dirName = "ReadIt";
    String parentDirName = Environment.DIRECTORY_DOCUMENTS;
    String fileName = "readit1.txt";

    final Context context = this;

    public static TextView editText;
    private Button saveFile;
    private TextView filename;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editText = (TextView) findViewById(R.id.editText);
        saveFile = (Button) findViewById(R.id.buttonSave);

        saveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_save);

                saveButton = (Button) dialog.findViewById(R.id.buttonSaveConfirm);
                filename = (TextView) dialog.findViewById(R.id.filename);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fileName = filename.getText().toString() + ".txt";

                        saveFile();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        //set the text to the textbox
        editText.setText(rawText);
    }


    private void saveFile() {

        if (isExternalStorageWritable()) {
            //File file = getAlbumStorageDir("ReadIt");
            String text = editText.getText().toString();

            generateNoteOnSD(parentDirName, dirName, fileName, text);

            Toast.makeText(this, "file saved as " + fileName, Toast.LENGTH_LONG).show();
        }

    }

    public void generateNoteOnSD(String parentDir, String dirName, String fileName, String text) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(parentDir), dirName);

            if (!file.exists()) {
                file.mkdirs();
            }
            File gpxfile = new File(file, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(this, "Something bad happened while saving the file.", Toast.LENGTH_LONG);
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}

