package com.minjinsuo.zhongchou.utils.db;

import java.util.ArrayList;

import com.minjinsuo.zhongchou.model.Area;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBhelper {
    private SQLiteDatabase db;
    private Context context;
    private DBManager dbm;

    public DBhelper(Context context) {
        super();
        this.context = context;
        dbm = new DBManager(context);
    }

    public ArrayList<Area> getCity(Area pcArea) {
        dbm.openDatabase();
        db = dbm.getDatabase();
        ArrayList<Area> list = new ArrayList<Area>();

        try {
            String sql = "select * from ZC_city where provCode='" + pcArea.getPcode() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(3);
                String name = new String(bytes, "utf-8");
                Area area = new Area();
                area.setCityName(name);
                area.setCode(code);
                area.setProvinceName(pcArea.getProvinceName());
                area.setPcode(pcArea.getPcode());
                list.add(area);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(3);
            String name = new String(bytes, "utf-8");
            Area area = new Area();
            area.setCityName(name);
            area.setCode(code);
            area.setPcode(pcArea.getPcode());
            area.setProvinceName(pcArea.getProvinceName());
            list.add(area);

        } catch (Exception e) {
            return null;
        }
        dbm.closeDatabase();
        db.close();

        return list;

    }

    public ArrayList<Area> getProvince() {
        dbm.openDatabase();
        db = dbm.getDatabase();
        ArrayList<Area> list = new ArrayList<Area>();

        try {
//	 		String sql = "select * from province";//  
            String sql = "select * from ZC_prov";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "utf-8");
                Area area = new Area();
                area.setProvinceName(name);
                area.setPcode(code);
                list.add(area);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "utf-8");
            Area area = new Area();
            area.setProvinceName(name);
            area.setPcode(code);
            list.add(area);

        } catch (Exception e) {
            return null;
        }
        dbm.closeDatabase();
        db.close();
        return list;

    }

//    public ArrayList<Area> getDistrict(String pcode) {
//        dbm.openDatabase();
//        db = dbm.getDatabase();
//        ArrayList<Area> list = new ArrayList<Area>();
//        try {
//            String sql = "select * from district where pcode='" + pcode + "'";
//            Cursor cursor = db.rawQuery(sql, null);
//            if (cursor.moveToFirst()) {
//                while (!cursor.isLast()) {
//                    String code = cursor.getString(cursor
//                            .getColumnIndex("code"));
//                    byte bytes[] = cursor.getBlob(2);
//                    String name = new String(bytes, "gbk");
//                    Area Area = new Area();
//                    Area.setName(name);
//                    Area.setPcode(code);
//                    list.add(Area);
//                    cursor.moveToNext();
//                }
//                String code = cursor.getString(cursor.getColumnIndex("code"));
//                byte bytes[] = cursor.getBlob(2);
//                String name = new String(bytes, "gbk");
//                Area Area = new Area();
//                Area.setName(name);
//                Area.setPcode(code);
//                list.add(Area);
//            }
//
//        } catch (Exception e) {
//            Log.i("wer", e.toString());
//        }
//        dbm.closeDatabase();
//        db.close();
//        return list;
//
//    }
}
