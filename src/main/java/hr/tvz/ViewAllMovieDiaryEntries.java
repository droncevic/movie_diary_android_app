package hr.tvz;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import hr.tvz.movieDiary.Diary;
import hr.tvz.DB.DiaryOperations;

import java.util.List;

public class ViewAllMovieDiaryEntries extends ListActivity{

    private DiaryOperations diaryOps;
    List<Diary> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_entries);
        diaryOps = new DiaryOperations(this);
        diaryOps.open();
        entries = diaryOps.getAllDiaryEntries();
        diaryOps.close();
        ArrayAdapter<Diary> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, entries);
        setListAdapter(adapter);
    }
}