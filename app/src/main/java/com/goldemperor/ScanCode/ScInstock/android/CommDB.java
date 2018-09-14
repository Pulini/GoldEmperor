package com.goldemperor.ScanCode.ScInstock.android;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

/**
 * Created by xufanglou on 2016-08-12.
 * 本地SQLLite访问类
 */
public class CommDB {
    public static final String DATABASE_NAME = "scBarCodeDataBase"; //数据库名称

    public static final int DATABASE_VERSION = 1;
    //创建该数据库下条形码表的语句
    private static final String CREATE_TABLE_BarCode =
            "CREATE TABLE if not exists " + BarCodeDB.SQLITE_TABLE + " (" +
                    BarCodeDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    BarCodeDB.KEY_BARCODE + " [nvarchar] (50)  NOT NULL ," +
                    " UNIQUE (" + BarCodeDB.KEY_BARCODE + "));";//设定条形码列不能重复

    // 创建索引
    private static final String CREATEINDEX = "   CREATE INDEX if not exists  IX_" + BarCodeDB.KEY_BARCODE + " ON  [" + BarCodeDB.SQLITE_TABLE + "]([" + BarCodeDB.KEY_BARCODE + "])";

    //创建该数据库下用户表的语句
    private static final String CREATE_TABLE_UserInfo =
            "CREATE TABLE if not exists " + UserInfoDB.SQLITE_TABLE + " (" +
                    UserInfoDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    UserInfoDB.KEY_AccountSuitID + " [nvarchar] (50)  NOT NULL ," +
                    UserInfoDB.KEY_OrgID + " [nvarchar] (50)  NOT NULL ," +
                    UserInfoDB.KEY_UseID + " [nvarchar] (50)  NOT NULL ," +

                    UserInfoDB.KEY_UserName + " [nvarchar] (50)  NOT NULL ," +
                    UserInfoDB.KEY_PassWord + " [nvarchar] (50)  NOT NULL ," +
                    UserInfoDB.KEY_BillTypeID + " [nvarchar] (50)  NOT NULL ," +

                    UserInfoDB.KEY_Red + " [nvarchar] (50) ," +
                    UserInfoDB.KEY_AutoLogin + " [nvarchar] (50)," +

                    UserInfoDB.KEY_AccountPosition + " INTEGER ," +
                    UserInfoDB.KEY_OrgPosition + " INTEGER," +
                    UserInfoDB.KEY_BillTypePosition + " INTEGER ," +
                    UserInfoDB.KEY_LoginTime+ " DATETIME ," +

                    " UNIQUE (" + UserInfoDB.KEY_UseID + "));";//设定用户ID不能重复

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /**
     * Constructor
     * @param ctx
     */
    public CommDB(Context ctx) {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
        //DBHelper.deleteDatabase(context);//需要添加新表时，执行此语句，删除数据库即可重建
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_BarCode);//创建条形码表
            db.execSQL(CREATEINDEX);//创建条形码表索引
            db.execSQL(CREATE_TABLE_UserInfo);//创建最后次用户登录信息表
        }

        /**
         * 删除数据库  *
         *@param context
         * @return
         */
        public boolean deleteDatabase(Context context) {
            return context.deleteDatabase(DATABASE_NAME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Adding any table mods to this guy here
        }
    }

    /**
     * open the db
     *
     * @return this
     * @throws SQLException return type: DBAdapter
     */
    public CommDB open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    /**
     * close the db
     * return type: void
     */
    public void close() {
        this.DBHelper.close();
    }

    /**
     * 如果没传表名的话，默认使用类名作为表名
     * @param clazz 实体类
     * @return
     */
    private <T> String createTable(Class<T> clazz){
        return createTable(clazz, clazz.getSimpleName());
    }
    /**
     * 根据类的自动生成建表语句的方法
     * @param clazz 实体类
     * @param tableName 表明
     * @return sql建表语句
     */
    private <T> String createTable(Class<T> clazz , String tableName){
        //实例化一个容器，用来拼接sql语句
        StringBuffer sBuffer = new StringBuffer();
        //sql语句，第一个字段为_ID 主键自增，这是通用的，所以直接写死
        sBuffer.append("create table if not exists "+ tableName + " "+
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        //得到实体类中所有的公有属性
        Field[] fields = clazz.getFields();
        //遍历所有的公有属性
        for(Field field : fields){
            //如果属性不为_id的话，说明是新的字段
            if (!field.getName().equals("_id")) {
                //得到属性的基本数据类型
                String type = field.getType().getSimpleName();
                //如果是String类型的属性，就把字段类型设置为TEXT
                if (type.equals("String")) {
                    sBuffer.append(field.getName()+" TEXT,");
                    //如果是int类型的属性，就把字段类型设置为INTEGER
                }else if (type.equals("int")) {
                    sBuffer.append(field.getName()+" INTEGER,");
                }
            }
        }
        //将最后的逗号删除
        sBuffer.deleteCharAt(sBuffer.length()-1);
        //替换成); 表明sql语句结束
        sBuffer.append(");");
        //返回这条sql语句
        return sBuffer.toString();
    }
}
