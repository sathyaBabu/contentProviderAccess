package map.sathya.com.contentprovideraccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MoviesProvider {
    public MoviesProvider() {
    }
    static final String PROVIDER_NAME = "com.example.provider.Movies";

    static final String DATABASE_TABLE   = "titles";

    static final String _ID        = "_id"     ;
    static final String TITLE      = "title"   ;
    static final String DIRECTOR   = "director";





    // Uri path
//	//SMS_INBOX = Uri.parse("content://sms/inbox");
//                                          /outBox
//	                                       Sent
//			                                Deleted

    // Uri = iBinder

    static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/"+DATABASE_TABLE);
    //
    //
    //  "/movies/");
    //                                                                      /english




    static final int MOVIES        = 1         ;
    static final int MOVIE_ID      = 2         ;


}
