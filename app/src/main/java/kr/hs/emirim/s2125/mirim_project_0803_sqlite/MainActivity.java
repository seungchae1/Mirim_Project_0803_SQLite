package kr.hs.emirim.s2125.mirim_project_0803_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(this);
    }

    public class DBHelper extends SQLiteOpenHelper{
        //db 생성
        public DBHelper(Context context){
            super(context, "IdolDB", null,1); //SQLiteOpenHelper에는 기본생성자가 없다
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE idolTBL( name CHAR(30) PRIMARY KEY,"+" cnt INTEGER);"); //테이블 생성
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldv, int newv) {   //버전이 달라졌을 때 호출됨
            db.execSQL("drop table if exists idolTBL"); // idolTBL 테이블 삭제
            onCreate(db);
        }
    }
}