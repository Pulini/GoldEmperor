package com.goldemperor.ScanCode.CxStockIn.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.goldemperor.ScanCode.CxStockIn.deleteslide.MyAdapter;
import com.goldemperor.R;
import com.goldemperor.model.BarCode;
import com.goldemperor.model.MessageEnum;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by xufanglou on 2016-08-12.
 * 条形码表操作类
 */
public class  CxStockBarCodeDB{
    public static final String KEY_ROWID = "D_ID";
    public static final String KEY_BARCODE = "D_BarCode";

    private static final String TAG = "ScReportActivity";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
//    private LinkedList<String> myList;
    private TextView mytxtcount;
    static final String SQLITE_TABLE = "t_CxBarCode";
    private Handler myHandler;
    private Context myContext;
    private MyAdapter myAdapter;

    public CxStockBarCodeDB(Context ctx, Handler handler, MyAdapter mAdapter, TextView txtcount) {
        this.myHandler = handler;
        this.myContext = ctx;
        this.myAdapter = mAdapter;
        this.mytxtcount = txtcount;
        //open();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {

            super(context, CommDB.DATABASE_NAME, null, CommDB.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public CxStockBarCodeDB open() throws SQLException {

        mDbHelper = new DatabaseHelper(myContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    /*
    *刷新表格数据
    */
    public void RefreshTable() {
        ArrayList<BarCode> lists = GetAllData();
        int count = myAdapter.mlist.size();
        //扫描多次，则清除之前数据
        if (count > 0)
            myAdapter.mlist.clear();
//        int RandID = 1;//设定控件ID
//        mytxtcount.setTextColor(android.graphics.Color.RED);
//        mytxtcount.setTextColor(0xffff00ff);
        mytxtcount.setTextColor(myContext.getResources().getColor(R.color.red));
        for (int i = 0; i < lists.size(); i++) {
            BarCode rp = (BarCode) lists.get(i);
            //if (!myAdapter.mlist.contains(rp.getD_BarCode()))
            String[] array=new String[2];
            array[0]=String.valueOf(rp.getD_ID());
            array[1]=String.valueOf(rp.getD_BarCode());
            myAdapter.mlist.add(array);
//            RandID++;
        }
        myAdapter.notifyDataSetChanged();
        mytxtcount.setText("记录数：" + myAdapter.mlist.size() + "条");
//        myscan_table.refreshDrawableState();
    }

    /**
     * 新增条形码信息
     *
     * @param barcode
     * @return
     */
    public long InsertBarcode(String barcode) {
        long createResult = 0;
        int length = barcode.length();
        if (length == 0)
            return 0;//不插入空值
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BARCODE, barcode);
        try {
            createResult = mDb.insert(SQLITE_TABLE, null, initialValues);
            RefreshTable();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }

    /**
     * 删除所有条形码数据
     *
     * @return
     */
    public boolean deleteAllBarcodes() {
        int doneDelete = 0;
        Dialog myDialog = new Dialog(myContext, myHandler);
        myDialog.ShowYesOrNoDialog("确定", "确定要清空所有本地数据吗？");
//        doneDelete=ClearBarcodes();
        return doneDelete > 0;
    }
    public boolean ClearSubmitData(Context myContext,String MessageType) {
        int doneDelete = 0;
        this.myContext=myContext;
        if( myAdapter.mlist.size()>0) {
            if (MessageType!="CxLoginActivty"){
            Dialog myDialog = new Dialog(myContext, myHandler);
            myDialog.ClearSubmitDataDialog("确定", "确定要提交所有数据吗?提交完毕后，将清除本地所有扫描数据？", MessageType);}
            else {
                Toast.makeText(myContext, "登录成功", Toast.LENGTH_SHORT).show();
                Dialog myDialog = new Dialog(myContext, myHandler);
                myDialog. SendMessage("ClearSubmitData", MessageEnum.ClearSubmitData,MessageType);
            }
        }
       // Toast.makeText(myContext, "登录成功", Toast.LENGTH_SHORT).show();
//        doneDelete=ClearBarcodes();
        return doneDelete > 0;
    }
    public int ClearBarcodes() {
        int doneDelete = 0;
        try {
            if(mDb==null)
            {
                mDbHelper = new DatabaseHelper(myContext);
                mDb = mDbHelper.getWritableDatabase();
            }
            doneDelete = mDb.delete(SQLITE_TABLE, null, null);
            myAdapter.mlist.clear();
            myAdapter.notifyDataSetChanged();
            RefreshTable();
            Log.w(TAG, Integer.toString(doneDelete));
            Log.e("doneDelete", doneDelete + "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return doneDelete;
    }

    /**
     * 删除某一条条形码数据
     *
     * @param barcode
     * @return
     */
    public boolean deleteTicketbarcode(String barcode) {
        int isDelete;
        String[] tName;
        tName = new String[]{barcode};
        isDelete = mDb.delete(SQLITE_TABLE, KEY_BARCODE + "=?", tName);
        Log.e("deleteTicket", "isDelete:" + isDelete + "||" + "ticketID="
                + barcode);
        RefreshTable();
        return isDelete > 0;
    }
    /**
     * 删除某一条条形码数据
     *
     * @param ID
     * @return
     */
    public boolean deleteTicketbarcode(int ID) {
        int isDelete;
        String[] tName;
        tName = new String[]{String.valueOf(ID)};
        isDelete = mDb.delete(SQLITE_TABLE, KEY_ROWID + "=?", tName);
        Log.e("deleteTicket", "isDelete:" + isDelete + "||" + "ticketID="
                + ID);
        RefreshTable();
        return isDelete > 0;
    }

    /**
     * 获取表中的所有条形码信息
     *
     * @return
     */
    public ArrayList<BarCode> GetAllData() {

        ArrayList<BarCode> allTicketsList = new ArrayList<BarCode>();
        Cursor mCursor = null;
        if(mDb==null)
        {
            mDbHelper = new DatabaseHelper(myContext);
            mDb = mDbHelper.getWritableDatabase();
        }
        mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID, KEY_BARCODE}, null, null, null, null, KEY_BARCODE);
        if (mCursor.moveToFirst()) {
            do {
                BarCode st = new BarCode();
                st.setD_ID(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_ROWID)));
                st.setD_BarCode(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_BARCODE)));
                allTicketsList.add(st);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return allTicketsList;
    }

    public String GetAllDataJson() {
        try {
            JSONObject tmpObj = null;
            JSONObject object = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            ArrayList<BarCode> lists = GetAllData();
            for (int i = 0; i < lists.size(); i++) {
                BarCode rp = (BarCode) lists.get(i);
                tmpObj = new JSONObject();
                tmpObj.put("D_BarCode" ,rp.getD_BarCode());
                jsonArray.put(tmpObj);
                tmpObj = null;
            }
            String personInfos = jsonArray.toString();
            return personInfos;
        } catch (JSONException ex) {
            return ex.getMessage();
        }

    }
}
