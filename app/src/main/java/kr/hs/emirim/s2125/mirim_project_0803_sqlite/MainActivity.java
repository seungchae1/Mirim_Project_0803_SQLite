package kr.hs.emirim.s2125.mirim_project_0803_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText editName , editCnt , editResultName, editResultCnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit1);
        editCnt = findViewById(R.id.edit2);
        editResultName = findViewById(R.id.edit3);
        editResultCnt = findViewById(R.id.edit4);

        Button btnInit = findViewById(R.id.btn_init);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnSelect = findViewById(R.id.btn_select);
        btnInit.setOnClickListener(btnListener);
        btnInsert.setOnClickListener(btnListener);
        btnSelect.setOnClickListener(btnListener);

        dbHelper = new DBHelper(this);
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

    View.OnClickListener btnListener = new View.OnClickListener() {
        SQLiteDatabase db;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_init:
                    db = dbHelper.getWritableDatabase();
                    dbHelper.onUpgrade(db, 1, 2); //초기화하려면 버전을 바꿔야 한다
                    db.close();
                    break;
                case R.id.btn_insert:
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into idolTBL values('"+editName.getText().toString()+"',"+editCnt.getText().toString()+" );");
                    db.close();
                    Toast.makeText(getApplicationContext(), "새로운 IDOL 정보가 추가되었습니다.",Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editCnt.setText("");
                    break;
                case R.id.btn_select:
                    db = dbHelper.getReadableDatabase();

                    Cursor c = db.rawQuery("select * from idolTBL;",null); //select문에는 rawQuery
                    String strName = "아이돌명\r\n_______________\r\n";
                    String strCnt = "인원수\r\n_______________\r\n";

                    while (c.moveToNext()){
                        strName += c.getString(0)+"\r\n";
                        strCnt += c.getInt(1)+"\r\n";
                    }

                    editResultName.setText(strName);
                    editResultCnt.setText(strCnt);

                    c.close();
                    db.close();
                    break;
            }
        }
    };
}