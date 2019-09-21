package map.sathya.com.contentprovideraccess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

// IMP LINK
// https://www.101apps.co.za/index.php/articles/using-a-content-resolver-to-access-another-app-s-database.html
/*
Yes, it's possible to access a custom content provider from another app. Using your terminology we'll call the content provider CustomCP and the other app AppA.
 (AppA is the one that wants to access to the provider). This approach is proven to work:

Specify the desired content provider (CustomCP) from within AppA by using a ContentProviderClient:

Uri yourURI = Uri.parse("content://com.example.customcp/YourDatabase");
ContentProviderClient yourCR = getContentResolver().acquireContentProviderClient(yourURI);
Access the content provider as you would normally from App A. For example:

yourCursor = yourCR.query(yourURI, null, null, null, null);

Note: you must either enclose the code within a try/catch block or include a "throws RemoteException" since the provider is not in App A.
CustomCP's Manifest must specify the provider, include the permissions allowed (e.g., read and/or write), and the provider must be exported. Here's an example:
<provider
    android:name="your.package.contentprovider.YourProvider"
    android:authorities="YourAuthority"
    android:readPermission="android.permission.permRead"
    android:exported="true" >
 </provider>
 */


// works perfect except for add button check on that(Its adding the data tooo)!!!
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Uri allTitles = Uri.parse("content://com.example.provider.Movies/movies/");
//        try (ContentProviderClient yourCR = getContentResolver().acquireContentProviderClient(allTitles)) {
//            // Access the content provider as you would normally from App A. For example:
//
//            try {
//                Cursor yourCursor = yourCR.query(allTitles, null, null, null, null);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }

    }
// Throwing error !!!
    public void onClickAddTitle(View view) {
        //---add a movie---
        ContentValues values = new ContentValues();

        values.put(MoviesProvider.TITLE, ((EditText)findViewById(R.id.txtTitle)).getText().toString());
        values.put(MoviesProvider.DIRECTOR, ((EditText)findViewById(R.id.txtDirector)).getText().toString());

        //for external packages use the fully qualified URI
        //Uri.parse("content://com.example.provider.Movies/movies")

        Uri uri = getContentResolver().insert(MoviesProvider.CONTENT_URI, values);
       // uri.toString() dosent work in case of remote content providers...

        // report the change to master
      //  getContentResolver().notifyChange(uri, null);
       // Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("NewApi")
    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse("content://com.example.provider.Movies/movies/");
        //1 content : standerd prefix
        // 2 .b.c.d.Movies :  Authority
        // 3. Data path - movieIndex - subcat
        // 4.  id

        Cursor c;

        //return the result sorted in descending order based on the title field
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---pre Honeycomb---
            c = managedQuery(allTitles, null, null, null, "title DESC");
        }
        else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title DESC");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(MoviesProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(MoviesProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(MoviesProvider.DIRECTOR)),
                        Toast.LENGTH_SHORT).show();
            }
            while (c.moveToNext());
        }
    }

    public void onClickUpdateTitle(View view) {
        //---update a title---
        ContentValues editedValues = new ContentValues();
        editedValues.put(MoviesProvider.TITLE, "New Movie Title - VErifone");

        int c = getContentResolver().update(
                Uri.parse("content://com.example.provider.Movies/movies/2"),
                editedValues,
                null,
                null);
        if(c == 0) {
            Toast.makeText(this, "No record found!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "" + c + " record updated!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickDeleteTitle(View view) {
        //---delete a title---
        int c = getContentResolver().delete(
                Uri.parse("content://com.example.provider.Movies/movies/1"),
                null,
                null);

        if(c == 0) {
            Toast.makeText(this, "No record found!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "" + c + " record deleted!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickDeleteAllTitle(View view) {
        //---delete a title---
        int c = getContentResolver().delete(
                Uri.parse("content://com.example.provider.Movies/movies"),
                null,
                null);

        if(c == 0) {
            Toast.makeText(this, "No records found!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "" + c + " records deleted!", Toast.LENGTH_LONG).show();
        }
    }
}

/*


 */