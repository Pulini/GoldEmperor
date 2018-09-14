package com.goldemperor.ScanCode.CxStockIn.androidsql;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtil {
    private static Connection getSQLConnection(String ip, String user, String pwd, String db) {
        Connection con = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + "/" + db + ";charset=utf8", user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public static String testSQL() {
        String result = "字段1  -  字段2\n";
        try {
            Connection conn = getSQLConnection("192.168.1.254,1578", "gold_NewErpUser", "Emperor*^*)Gold!!@*Group", "jderp");
            String sql = "select top 10 * from t_JJb_WorkLine";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {   //
                String s1 = rs.getString("FName");
                String s2 = rs.getString("FNumber");
                result += s1 + "  -  " + s2 + "\n";
                Log.i("ScReportActivity", "testSQL:" + s1 + "  -  " + s2);
                //Log.i(TAG, "testSQL:"+ s1 + "  -  " + s2);
                System.out.println(s1 + "  -  " + s2);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;


    }

    public static void main(String[] args) {
        testSQL();
    }

}
