package be.ciesites.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addRecord(View view){

        ContentValues values = new ContentValues();
        if((!((EditText)findViewById(R.id.name)).getText().toString().isEmpty()) &&
                (!((EditText)findViewById(R.id.nickname)).getText().toString().isEmpty())) {
            values.put(CustomContentProvider.NAME, ((EditText)findViewById(R.id.name)).getText().toString());

            values.put(CustomContentProvider.NICK_Name, ((EditText)findViewById(R.id.nickname)).getText().toString());

            Uri uri = getContentResolver().insert(CustomContentProvider.CONTENT_URI, values);
            Toast.makeText(getBaseContext(), "Record Inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void showAllRecords(View view){
        String URL = "content://be.ciesites.provider/nicknames";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name");

        String result = "Content Provider Results: ";

        if (!c.moveToFirst()){
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n"
                        +c.getString(c.getColumnIndex(CustomContentProvider.NAME))
                        + " has nickname : "
                        +c.getString(c.getColumnIndex(CustomContentProvider.NICK_Name));
            } while (c.moveToNext());
            if (!result.isEmpty())
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "No Records present", Toast.LENGTH_LONG).show();
        }

    }

    public void deleteAllRecords (View view){
        String URL = "content://be.ciesites.provider/nicknames";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(friends, null, null);
        String countNum = count + " records are deleted.";
        Toast.makeText(getBaseContext(), countNum, Toast.LENGTH_LONG).show();
    }
}
