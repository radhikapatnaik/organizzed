package com.example.user.organized;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private File mPrivateRootDir;
    // The path to the "images" subdirectory
    private MediaStore.Files mDocumentDir;
    // Array of files in the images subdirectory
    File[] mDocumentFiles;
    // Array of filenames corresponding to mImageFiles
    String[] mDocumentFilenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent mResultIntent = new Intent("com.example.user.organized.ACTION_RETURN_FILE");
        // Get the files/ subdirectory of internal storage
        mPrivateRootDir = getFilesDir();
        // Get the files/images subdirectory;
        mDocumentDir = new MediaStore.Files(mPrivateRootDir,"documents");
        // Get the files in the images subdirectory
        mDocumentFiles = mDocumentDir.listFiles();
        // Set the Activity's result to null to begin with
        setResult(MainActivity.RESULT_CANCELED,null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Replace with your own action",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });
        // Define a listener that responds to clicks on a file in the ListView
        AdapterView mFileListView;
        mFileListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
            /*
             * When a filename in the ListView is clicked, get its
             * content URI and send it to the requesting app
             */
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view,
                                            int position,
                                            long rowId) {
                /*
                 * Get a File for the selected file name.
                 * Assume that the file names are in the
                 * mImageFilename array.
                 */
                        File requestFile = new File(mDocumentFilenames[position]);
                /*
                 * Most file-related method calls need to be in
                 * try-catch blocks.
                 */
                        // Use the FileProvider to get a content URI
                        try {
                            Uri fileUri = FileProvider.getUriForFile(
                                    MainActivity.this,
                                    "com.example.user.organized.fileprovider",
                                    requestFile);
                        } catch (IllegalArgumentException e) {
                            Log.e("File Selector",
                                    "The selected file can't be shared: " +
                                            clickedFilename);
                        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
