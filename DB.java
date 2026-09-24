package com.gulfmobiles.app;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class DB extends SQLiteOpenHelper {
    public DB(Context c){ super(c,"gulf_mobiles.db",null,1); }
    public void onCreate(SQLiteDatabase db){ db.execSQL("CREATE TABLE sales(id INTEGER PRIMARY KEY AUTOINCREMENT, sale REAL NOT NULL, expense REAL NOT NULL, created INTEGER NOT NULL)"); db.execSQL("CREATE TABLE jobs(id INTEGER PRIMARY KEY AUTOINCREMENT, customer TEXT NOT NULL, phone TEXT, device TEXT, issue TEXT, status TEXT, amount REAL, created INTEGER NOT NULL)"); }
    public void onUpgrade(SQLiteDatabase db,int oldV,int newV){ db.execSQL("DROP TABLE IF EXISTS sales"); db.execSQL("DROP TABLE IF EXISTS jobs"); onCreate(db); }
    public long add(double sale,double expense){ ContentValues v=new ContentValues(); v.put("sale",sale); v.put("expense",expense); v.put("created",System.currentTimeMillis()); return getWritableDatabase().insert("sales",null,v); }
    public Cursor all(){ return getReadableDatabase().rawQuery("SELECT id,sale,expense,created,(sale-expense) profit FROM sales ORDER BY created DESC",null); }
    public double total(String col){ Cursor c=getReadableDatabase().rawQuery("SELECT COALESCE(SUM("+col+"),0) FROM sales",null); double x=0; if(c.moveToFirst()) x=c.getDouble(0); c.close(); return x; }
    public void delete(long id){ getWritableDatabase().delete("sales","id=?",new String[]{String.valueOf(id)}); }
    public long addJob(String customer,String phone,String device,String issue,String status,double amount){ ContentValues v=new ContentValues(); v.put("customer",customer); v.put("phone",phone); v.put("device",device); v.put("issue",issue); v.put("status",status); v.put("amount",amount); v.put("created",System.currentTimeMillis()); return getWritableDatabase().insert("jobs",null,v); }
    public Cursor jobs(){ return getReadableDatabase().rawQuery("SELECT id,customer,phone,device,issue,status,amount,created FROM jobs ORDER BY created DESC",null); }
    public void deleteJob(long id){ getWritableDatabase().delete("jobs","id=?",new String[]{String.valueOf(id)}); }
}
