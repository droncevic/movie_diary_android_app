package hr.tvz;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.*;
import org.json.*;
import android.util.Log;

import cz.msebera.android.httpclient.Header;
import hr.tvz.DB.DiaryOperations;
import hr.tvz.movieDiary.*;

public class AddUpdateMovieDiaryEntry extends AppCompatActivity {

    public static final String LOGTAG = "DRY_MNGMNT_SYS";
    private static final String EXTRA_ENTRY_ID = "hr.tvz.entryId";
    private static final String EXTRA_ADD_UPDATE = "hr.tvz.add_update";
    private EditText titleEditText;
    private EditText impressionEditText;
    private EditText myratingEditText;
    private Button addUpdateButton;
    private Diary newDiaryEntry;
    private Diary oldDiaryEntry;
    private String mode;
    private long entryId;
    private DiaryOperations entryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_entry);
        newDiaryEntry = new Diary();
        oldDiaryEntry = new Diary();
        titleEditText = (EditText)findViewById(R.id.edit_text_title);
        impressionEditText = (EditText)findViewById(R.id.edit_text_impr);
        myratingEditText = (EditText)findViewById(R.id.edit_text_myrating);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_entry);
        entryData = new DiaryOperations(this);
        entryData.open();

        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        Log.i(LOGTAG,"mode: " + mode);
        Log.i(LOGTAG,"EXTRA_ADD_UPDATE: " + EXTRA_ADD_UPDATE);

        if(mode.equals("Update")){

            addUpdateButton.setText("Update Diary Entry");
            entryId = getIntent().getLongExtra(EXTRA_ENTRY_ID,0);

            initializeEntry(entryId);

        }

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {

                    String title = titleEditText.getText().toString();

                    callOmdbAPI(title);

                    Intent i = new Intent(AddUpdateMovieDiaryEntry.this,MainActivity.class);
                    startActivity(i);

                }else {
                    Log.d("asd", "IN OLD!" );

                    oldDiaryEntry.setTitle(titleEditText.getText().toString());
                    oldDiaryEntry.setImpression(impressionEditText.getText().toString());
                    oldDiaryEntry.setMyrating(myratingEditText.getText().toString());
                    entryData.updateDiaryEntry(oldDiaryEntry);
                    Toast t = Toast.makeText(AddUpdateMovieDiaryEntry.this, "Entry "+ oldDiaryEntry.getTitle()
                            + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateMovieDiaryEntry.this,MainActivity.class);
                    startActivity(i);

                }

            }
        });
    }

    public void callOmdbAPI (String title) {

        OmdbRestClient.get("?i=tt3896198&apikey=345e9e1e&t=" + title, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("asd", "---------------- this is response : " + response);

                try {
                    newDiaryEntry.setTitle(response.getString("Title"));
                    newDiaryEntry.setYear(response.getString("Year"));
                    newDiaryEntry.setActors(response.getString("Actors"));
                    newDiaryEntry.setImdbrating(response.getString("imdbRating"));
                    newDiaryEntry.setWriter(response.getString("Writer"));
                    newDiaryEntry.setPlot(response.getString("Plot"));
                    newDiaryEntry.setGenre(response.getString("Genre"));
                    newDiaryEntry.setType(response.getString("Type"));

                    newDiaryEntry.setImpression(impressionEditText.getText().toString());
                    newDiaryEntry.setMyrating(myratingEditText.getText().toString());
                    entryData.addDiaryEntry(newDiaryEntry);

                    Toast t = Toast.makeText(AddUpdateMovieDiaryEntry.this, "Entry "+ response.getString("Title")
                            + " has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();

                } catch (JSONException e) {

                    Toast t = Toast.makeText(AddUpdateMovieDiaryEntry.this, "Nonexistent Movie/Series title!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }

        });
    }

    private void initializeEntry(long entryId) {

        try {

            oldDiaryEntry = entryData.getDiaryEntry(entryId);
            titleEditText.setText(oldDiaryEntry.getTitle());
            impressionEditText.setText(oldDiaryEntry.getImpression());
            myratingEditText.setText(oldDiaryEntry.getMyrating());

        } catch (CursorIndexOutOfBoundsException e) {

            setContentView(R.layout.activity_main);
            Toast t = Toast.makeText(AddUpdateMovieDiaryEntry.this, "Nonexistent ID!", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}