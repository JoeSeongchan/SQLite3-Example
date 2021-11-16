package com.android.homework8;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.homework8.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "MainActivity_R";
  private ActivityMainBinding binding;
  private MyDbHelper myDbHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    myDbHelper = new MyDbHelper(this, 2);
    binding.btnInitMain.setOnClickListener(this::initDb);
    binding.btnInsertMain.setOnClickListener(this::insertDb);
    binding.btnSearchMain.setOnClickListener(this::searchDb);
  }

  public void initDb(View view) {
    try (SQLiteDatabase sqlDb = myDbHelper.getWritableDatabase();
    ) {
      myDbHelper.onUpgrade(sqlDb, 1, 2);
    }
    Toast.makeText(getApplicationContext(), "init db.", Toast.LENGTH_SHORT).show();
  }

  public void insertDb(View view) {
    try (SQLiteDatabase sqlDb = myDbHelper.getWritableDatabase()) {
      String movieTitle = binding.etMovieTitleMain.getText().toString();
      String director = binding.etDirectorMain.getText().toString();
      int released = Integer.parseInt(binding.etReleasedMain.getText().toString());
      sqlDb.execSQL("insert into groupTBL values("
          + "'" + movieTitle + "'"
          + ", '" + director + "'"
          + "," + released + ");");
      Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_SHORT).show();
    } catch (NumberFormatException e) {
      Log.d(TAG, "not number.");
    }
  }

  public void searchDb(View view) {
    try (SQLiteDatabase sqlDb = myDbHelper.getReadableDatabase()) {
      Cursor cursor = sqlDb.rawQuery("select * from groupTBL;", null);
      String str1 = "Movie Title" + System.lineSeparator();
      String str2 = "Director" + System.lineSeparator();
      String str3 = "Released Year" + System.lineSeparator();
      str1 += "-----------" + System.lineSeparator();
      str2 += "-----------" + System.lineSeparator();
      str3 += "-----------" + System.lineSeparator();
      while (cursor.moveToNext()) {
        str1 += cursor.getString(0) + System.lineSeparator();
        str2 += cursor.getString(1) + System.lineSeparator();
        str3 += cursor.getString(2) + System.lineSeparator();
      }

      binding.tvListTitleMain.setText(str1);
      binding.tvListDirectorMain.setText(str2);
      binding.tvListReleasedMain.setText(str3);
      Toast.makeText(getApplicationContext(), "searched.", Toast.LENGTH_SHORT).show();
    }
  }

  // table 생성 / 삭제에 사용.
  public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(Context context, int version) {
      super(context, "groupDB", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(
          "create table groupTBL (title char(20),director char(20), released_year integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("drop table if exists groupTBL;");
      onCreate(db);
    }
  }

}