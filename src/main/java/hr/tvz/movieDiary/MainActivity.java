package hr.tvz.movieDiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.tvz.DB.DiaryOperations;
import hr.tvz.R;

public class MainActivity extends AppCompatActivity{

    private Button addDiaryEntryButton;
    private Button editDiaryEntryButton;
    private Button deleteDiaryEntryButton;
    private Button viewAllDiaryEntriesButton;
    private DiaryOperations diaryOps;

    private static final String EXTRA_EMP_ID = "hr.tvz.entryId";
    private static final String EXTRA_ADD_UPDATE = "hr.tvz.add_update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addDiaryEntryButton = (Button) findViewById(R.id.button_add_diary_entry);
        editDiaryEntryButton = (Button) findViewById(R.id.button_edit_diary_entry);
        deleteDiaryEntryButton = (Button) findViewById(R.id.button_delete_diary_entry);
        viewAllDiaryEntriesButton = (Button)findViewById(R.id.button_view_diary_entries);

        addDiaryEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,hr.tvz.AddUpdateMovieDiaryEntry.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editDiaryEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIdAndUpdateEntry();
            }
        });
        deleteDiaryEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIdAndRemoveEntry();
            }
        });
        viewAllDiaryEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, hr.tvz.ViewAllMovieDiaryEntries.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getIdAndUpdateEntry(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEntryIdView = li.inflate(R.layout.dialog_get_entry_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEntryIdView);

        final EditText userInput = (EditText) getEntryIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        try {
                            Intent i = new Intent(MainActivity.this,hr.tvz.AddUpdateMovieDiaryEntry.class);
                            i.putExtra(EXTRA_ADD_UPDATE, "Update");
                            i.putExtra(EXTRA_EMP_ID, Long.parseLong(userInput.getText().toString()));
                            startActivity(i);
                        } catch (NumberFormatException e) {
                            Toast t = Toast.makeText(MainActivity.this,"Invalid value, should be number!",Toast.LENGTH_SHORT);
                            t.show();
                        } catch (Throwable e) {
                        Toast t = Toast.makeText(MainActivity.this,"Nonexistant ID!",Toast.LENGTH_SHORT);
                        t.show();
                        }

                    }
                }).create()
                .show();

    }


    public void getIdAndRemoveEntry(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEntryIdView = li.inflate(R.layout.dialog_get_entry_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEntryIdView);

        final EditText userInput = (EditText) getEntryIdView.findViewById(R.id.editTextDialogUserInput);
        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        diaryOps = new DiaryOperations(MainActivity.this);
                        diaryOps.open();
                        try {
                            diaryOps.removeDiaryEntry(diaryOps.getDiaryEntry(Long.parseLong(userInput.getText().toString())));
                            Toast t = Toast.makeText(MainActivity.this,"Entry removed successfully!",Toast.LENGTH_SHORT);
                            t.show();
                        } catch (NumberFormatException e){
                            Toast t = Toast.makeText(MainActivity.this,"Invalid value, should be number!",Toast.LENGTH_SHORT);
                            t.show();
                        } catch (CursorIndexOutOfBoundsException e) {
                            Toast t = Toast.makeText(MainActivity.this,"Nonexistant ID!",Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }).create()
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryOps = new DiaryOperations(MainActivity.this);
        diaryOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        diaryOps = new DiaryOperations(MainActivity.this);
        diaryOps.close();

    }
}