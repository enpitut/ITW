//同じディレクトリにmysqlのドライバ(mysql-connector-java-5.1.36)を置くこと
import java.io.*;
import java.sql.*;
import java.text.*;

//データベースへの位置情報登録を実行する
public class DatabaseRegister{
  //ここはメソッドで適宜変更
  private String domain = "localhost";
  private int port = 3306;
  private String dbname = "enPiT2015_ITW";
  private String user_name = "hoge";
  private String password = "piyo";
  private Connection conn = null;

  DatabaseRegister(){//デフォルトコンストラクタ

  }

  //アドレス、ポート番号、データベース名を設定
  public void setDatabaseInfo(String domain, int port, String dbname){
    this.domain = domain;
    this.port = port;
    this.dbname = dbname;
  }

  //ユーザID、パスワードを指定
  public void setDatabaseUserInfo(String user_name, String password){
    this.user_name = user_name;
    this.password = password;
  }

  public boolean connectDB(){
    try{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      String url = "jdbc:mysql://" + this.domain + ":" + this.port + "/" + dbname;
      this.conn = DriverManager.getConnection(url, this.user_name, this.password);
      //System.out.println("接続に成功しました");
      return true;
    }
    catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }

  //ユーザIDの存在を確認
  public boolean exists(int user_id){
    try{
      Statement stmt = this.conn.createStatement();
      String sql = "SELECT * FROM positions WHERE user_id = " + user_id;
      ResultSet rs = stmt.executeQuery(sql);
      if(rs.next()){
        return true;
      }
      else{
        return false;
      }
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }

  }

  //位置情報データを登録
  public boolean insertPositions(int user_id, double latitude, double longitude, Timestamp date){//データベースのidはどうする？
    try{
      Statement stmt = this.conn.createStatement();
      String sql = "INSERT INTO positions (user_id, latitude, longitude, date) values (\'" + user_id + "\',\'" + latitude + "\',\'" + longitude + "\',\'" + date + "\')";
      int num = stmt.executeUpdate(sql);
      return true;
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
    
  }

  //位置情報データを更新
  public boolean updatePositions(int user_id, double latitude, double longitude, Timestamp date){
    try{
      Statement stmt = this.conn.createStatement();
      String sql = "UPDATE positions SET latitude = " + latitude + ", longitude = " + longitude + ", date = \'" + date + "\' WHERE user_id = " + user_id;
      int num = stmt.executeUpdate(sql);
      return true;
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public boolean closeDB(){
    try{
      if(conn != null){
        conn.close();
        // System.out.println("データベース切断に成功しました");
        return true;
      }
      else{
        //System.out.println("コネクションがありません");
        return false;
      }
    }
    catch (SQLException e){
      e.printStackTrace();
      return false;
    }
  }

}
