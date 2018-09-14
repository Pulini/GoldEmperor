package com.goldemperor.ScanCode.SupperInstock.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.goldemperor.ScanCode.SupperInstock.model.UserInfo;
import com.goldemperor.ScanCode.SupperInstock.model.UserLoginInfo;

import java.util.ArrayList;

/**
 * Created by xufanglou on 2016-08-12.
 * 用户表操作类
 */
public class UserInfoDB {
    public static final String KEY_ROWID = "D_ID";
    public static final String KEY_AccountSuitID="D_AccountSuitID";//账套ID
    public static final String KEY_OrgID="D_OrgID";//组织ID
    public static final String KEY_UseID = "D_UserID";
    public static final String KEY_UserName="D_UserName";//erp用户名
    public static final String KEY_PassWord="D_PassWord";//erp密码
    public static final String KEY_BillTypeID="D_BillTypeID";//单据类型
    public static final String KEY_Red="D_Red";//红充
    public static final String KEY_AutoLogin="D_AutoLogin";//自动登录

    public static final String KEY_AccountPosition="D_AccountPosition";//账套序号
    public static final String KEY_OrgPosition="D_OrgPosition";//组织序号
    public static final String KEY_BillTypePosition="D_BillTypePosition";//单据类型序号
    public static final String KEY_LoginTime="D_LoginTime";//单据类型序号

    private static final String TAG = "MainActivity";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    static final String SQLITE_TABLE = "t_LastUserInfo";//表名
    private Handler myHandler;
    private final Context myContext;
    private UserLoginInfo myuserLoginInfo = null;

    public UserInfoDB(Context ctx, Handler handler, UserLoginInfo userLoginInfo) {
        this.myHandler = handler;
        this.myContext = ctx;
        myuserLoginInfo=userLoginInfo;
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

    public UserInfoDB open() throws SQLException {

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
    public void IniLoginInfo() {
        ArrayList<UserInfo> lists = GetAllData();
//        //扫描多次，则清除之前数据
        if (lists.size()==0)
            return;
        for (int i = 0; i < lists.size(); i++) {
            UserInfo rp = (UserInfo) lists.get(i);
            //rp.UserName="毛维翰";
            rp.BillTypePosition=1;
//            myuserLoginInfo.spinner_zzt.setSelection(Integer.parseInt(rp.getAccountPosition()),true);
//            myuserLoginInfo.spinner_org.setSelection(Integer.parseInt(rp.getOrgPosition()),true);
//            myuserLoginInfo.spinner_billtype.setSelection(Integer.parseInt(rp.getBillTypePosition()),true);
            myuserLoginInfo.spinner_zzt.setSelection(rp.getAccountPosition(),true);
            myuserLoginInfo.spinner_org.setSelection(rp.getOrgPosition(),true);
            myuserLoginInfo.spinner_billtype.setSelection(rp.getBillTypePosition(),true);
            myuserLoginInfo.login_username.setText(rp.UserName.toCharArray(), 0, rp.UserName.length());
            myuserLoginInfo.login_password.setText(rp.PassWord.toCharArray(), 0, rp.PassWord.length());
            if(rp.getRed().equals("-1"))
                myuserLoginInfo.login_red.setChecked(true);
            else
                myuserLoginInfo.login_red.setChecked(false);
        }
    }

    /**
     * 新增用户信息
     *
     * @param useinfo
     * @return
     */
    public long InsertUserInfo(UserInfo useinfo) {
        long createResult = 0;
        if(useinfo==null)
            return 0;//不插入空值
        ClearUserInfo();//先删除所有数据，因为只需要保存一条的登录信息就够了
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AccountSuitID, useinfo.getAccountSuitID());
        initialValues.put(KEY_OrgID, useinfo.getOrganizationID());
        initialValues.put(KEY_UseID, useinfo.getUserID());
        initialValues.put(KEY_UserName, useinfo.getUserName());
        initialValues.put(KEY_PassWord, useinfo.getPassWord());
        initialValues.put(KEY_BillTypeID, useinfo.getBillTypeID());
        initialValues.put(KEY_Red, useinfo.getRed());
        initialValues.put(KEY_AutoLogin, GetDefaultValue(useinfo.AutoLogin));

        initialValues.put(KEY_OrgPosition, useinfo.getAccountPosition());
        initialValues.put(KEY_BillTypePosition, useinfo.getBillTypePosition());
        initialValues.put(KEY_AccountPosition,useinfo.getAccountPosition());

        try {
            createResult = mDb.insert(SQLITE_TABLE, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }
    /*
    更新用户表
     */
    public void UpdateUserInfo(UserInfo useinfo) {
        String where =KEY_UseID + "=?";
        String[] whereValue = { useinfo.getUserID() };
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AccountSuitID, useinfo.AccountSuitID);
        initialValues.put(KEY_OrgID, useinfo.OrganizationID);
        initialValues.put(KEY_UseID, useinfo.UserID);
        initialValues.put(KEY_UserName, useinfo.UserName);
        initialValues.put(KEY_PassWord, useinfo.PassWord);
        initialValues.put(KEY_BillTypeID, useinfo.BillTypeID);
        initialValues.put(KEY_Red, useinfo.Red);
        initialValues.put(KEY_AutoLogin, GetDefaultValue(useinfo.AutoLogin));
        mDb.update(SQLITE_TABLE, initialValues, where, whereValue);
        mDb.close();
    }

    public String GetDefaultValue(String Value)
    {
        if(Value==null)
            return "0";
        return Value;
    }
    /**
     * 删除所有用户数据
     *
     * @return
     */
    public boolean ConfirmDeleteAllUserInfo() {
        int doneDelete = 0;
        Dialog myDialog = new Dialog(myContext,myHandler);
        myDialog.ShowYesOrNoDialog("确定", "确定要清空所有本地数据吗？");
        //doneDelete=ClearBarcodes();
        return doneDelete > 0;
    }
    public int ClearUserInfo() {
        int doneDelete = 0;
        try {
            doneDelete = mDb.delete(SQLITE_TABLE, null, null);
            Log.w(TAG, Integer.toString(doneDelete));
            Log.e("doneDelete", doneDelete + "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return doneDelete;
    }
    /**
     * 删除某一条用户数据
     *
     * @param userID
     * @return
     */
    public boolean deleteTicketbarcode(String userID) {
        int isDelete;
        String[] tuserID;
        tuserID = new String[]{userID};
        isDelete = mDb.delete(SQLITE_TABLE, KEY_UseID + "=?", tuserID);
        Log.e("deleteTicket", "isDelete:" + isDelete + "||" + "ticketID="
                + tuserID);
        return isDelete > 0;
    }

    /**
     * 获取表中的所有用户信息
     *
     * @return
     */
    public ArrayList<UserInfo> GetAllData() {

        ArrayList<UserInfo> allUserList = new ArrayList<UserInfo>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID, KEY_AccountSuitID,KEY_OrgID,KEY_UseID,KEY_UserName,KEY_PassWord,KEY_BillTypeID,KEY_Red,KEY_AutoLogin,KEY_AccountPosition,KEY_OrgPosition,KEY_BillTypePosition},
                null, null, null, null, KEY_UseID);
        if (mCursor.moveToFirst()) {
            do {
                UserInfo st = new UserInfo();
                st.setROWID(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_ROWID)));
                st.setAccountSuitID(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_AccountSuitID)));
                st.setOrganizationID(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_OrgID)));
                st.setUserID(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_UseID)));
                st.setUserName(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_UserName)));
                st.setPassWord(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_PassWord)));
                st.setBillTypeID(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_BillTypeID)));
                st.setRed(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_Red)));
                st.setAutoLogin(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_AutoLogin)));
                st.setAccountPosition(mCursor.getInt(mCursor.getColumnIndexOrThrow(KEY_AccountPosition)));
                st.setOrgPosition(mCursor.getInt(mCursor.getColumnIndexOrThrow(KEY_OrgPosition)));//st.setOrgPosition(mCursor.getInt(mCursor.getColumnIndexOrThrow(KEY_OrgPosition)));
                st.setBillTypePosition(mCursor.getInt(mCursor.getColumnIndexOrThrow(KEY_BillTypePosition)));
                allUserList.add(st);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return allUserList;
    }

}
