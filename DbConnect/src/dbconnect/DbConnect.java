/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbconnect;
import java.sql.*;
import java.io.*;
/**
 *
 * @author cl3
 */
public class DbConnect {
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here

        try {
            //DB接続
            Connection con = getConnection();
            //ステートメントオブジェクトを作成
            Statement stmt = con.createStatement();
            
            //テーブル選択
            // <editor-fold desc="...テーブル選択...">
            DatabaseMetaData dmd = con.getMetaData();
            ResultSet rest = null;
            String types[] = { "TABLE" };
            rest = dmd.getTables(null, null,"%", types);
            int tableNumb = 0;
            String tables[] = new String[20];
            try {
                System.out.println("テーブル選択");
                System.out.println("  ------------  ");
            while(rest.next()){
                tableNumb = tableNumb + 1;
                tables[tableNumb] = rest.getString("TABLE_NAME");
                System.out.println(tableNumb + ":" + tables[tableNumb]);
            }
            } finally {
                System.out.println("  ------------  ");
                System.out.print(">");
                rest.close();
            }
            tableNumb = inputI();
            String tableName = tables[tableNumb];
            String mySql = "select * from " + tableName;
            ResultSet rs = stmt.executeQuery(mySql);
            ResultSetMetaData rsmd= rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            System.out.println(columnCount);
            String columnNames[] = new String[10];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i] = rsmd.getColumnName(i);
            }
            // </editor-fold>
            
            final int DISPLAY = 1;
            final int EDIT = 2;
            final int CONFIRM = 3;
            
            int mode = DISPLAY;
            boolean loopFlg = true;
            while(loopFlg){
                int prefCd = 0;
                
                switch(mode){
                    case DISPLAY:
                    case CONFIRM:
                        //SQL文作成
                        mySql = "select * from " + tableName;
                        //検索するSQL実行
                        rs = stmt.executeQuery(mySql);
                        System.out.println("\n--------------------");

                        //結果セットからデータを取り出す next()で次の行に移動
                        while(rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(rs.getString(columnNames[i]) + "\t");
                            }
                            System.out.println("");
                            //prefCd = rs.getInt("PREF_CD");
                            //String prefName = rs.getString("PREF_NAME");
                            //System.out.print(prefCd + "\t");
                            //System.out.println(prefName + "\t");
                        }

                        System.out.println("--------------------");
                        rs.close();
                        if(mode == DISPLAY)mode = EDIT;
                        if(mode == CONFIRM)loopFlg = false;
                        break;
                        
                    case EDIT:
                        // <editor-fold desc="...コマンド...">
                        System.out.println("操作を選択してください");
                        System.out.println("  ------------  ");
                        System.out.println("| 1.レコード追加 |");
                        System.out.println("| 2.レコード更新 |");
                        System.out.println("| 3.レコード削除 |");
                        System.out.println("  ------------  ");
                        System.out.print(">");
                        int command = inputI();
                        
                        String doCommand = "";
                        switch(command){
                            case 1:
                                doCommand = "追加";
                                //SQL文作成
                                System.out.println("レコード追加");
                                prefCd = prefCd + 1;
                                System.out.println("追加する PREF_NAME を入力してください（PREF_CD:"+prefCd+"）");
                                String prefName = inputS();
                                mySql = "insert into " + tableName + " values(" + prefCd + ", '" + prefName + "')";
                                break;
                            case 2:
                                doCommand = "更新";
                                //SQL文作成
                                System.out.println("レコード更新");
                                System.out.println("更新する "+ columnNames[1] +"を入力してください。"); 
                                prefCd = inputI();
                                
                                System.out.println("更新する PREF_NAME を入力してください（PREF_CD:"+prefCd+"）");
                                prefName = inputS();
                                mySql = "update " + tableName + " set PREF_NAME = '" + prefName + "' where PREF_CD = " + prefCd;
                                break;
                            case 3:
                                doCommand = "削除";
                                //SQL文作成
                                System.out.println("レコード削除");
                                System.out.println("削除する PREF_CD を入力してください。"); 
                                prefCd = inputI();
                                mySql = "delete from " + tableName + " where PREF_CD = " + prefCd;
                                break;
                        }
                        //SQL実行
                        System.out.println("SQL：" + mySql);
                        int num = stmt.executeUpdate(mySql);
                        System.out.println(num + "件のレコードを" + doCommand + "しました。");
                        // </editor-fold>
                        mode = CONFIRM;
                        break;
                }
            }
            
            //オブジェクトを解放
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("例外発生：" + e );
        }

    }
    
        public static String inputS() throws IOException {
           BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
           String str = input.readLine( );
           return str;
       }
        public static int inputI() throws IOException {
            int tmpInputNum = 0;//入力番号初期化
                try {
                    //初期化処理
                    //final int IMPUT_MAX =2;//最大入力値
                    //キー入力読込処理（int型）
                    java.util.Scanner sc = new java.util.Scanner(System.in);
                    int inputInt = sc.nextInt();
                    //入力値チェックと入力番号への代入
                    //if (inputInt > 0 && inputInt <= IMPUT_MAX) {
                        tmpInputNum = inputInt;
                    /*} else {
                        System.out.println("※　コマンドは" + IMPUT_MAX + "以下で入力して下さい　※　");
                    }*/
                } catch (Exception e) {
                    System.out.println("※　数字以外は入力しないで下さい　※　");
                }
            int numb = tmpInputNum;
            return numb;
       }
        public static Connection getConnection() throws Exception {
            //JDBCドライバのロード
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //日本語対応処理
            java.util.Properties prop = new java.util.Properties();
            prop.put("charSet", "SJIS"); 
            //各設定
            String url = "jdbc:odbc:SampleDB030";
            String user = "";
            String pass = "";
            //データベースに接続
            Connection con = DriverManager.getConnection(url,prop);
            return con;
       }
    
}