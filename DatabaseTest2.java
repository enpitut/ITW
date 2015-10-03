import java.sql.Timestamp;
import java.text.*;

class DatabaseTest2{
  //このmain関数を参考にサーバへ組み込む
  public static void main(String args[]){
    DatabaseRegister dr = new DatabaseRegister();
    //データベースに接続
    if(!dr.connectDB()){
      System.out.println("データベース接続に失敗");
    }
    else{
      //入力データは,区切りのStringと仮定
      String dummy = "12,35.689521,139.691704,2015/09/21 17:09:00";
      String[] positions = dummy.split(",", 0);
      int user_id = Integer.parseInt(positions[0]);
      double lat = Double.parseDouble(positions[1]);
      double lon = Double.parseDouble(positions[2]);
      Timestamp date = null;
      try{
        Long dateTimeLong = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(positions[3]).getTime();
        date = new Timestamp(dateTimeLong);
      }catch(Exception e){
        e.printStackTrace();
      }

      System.out.println(user_id + " " + lat + " " + lon + " " + date);

      if(dr.exists(user_id)){
        dr.updatePositions(user_id, lat, lon, date);
      }
      else{
        dr.insertPositions(user_id, lat, lon, date);
      }
      //データベースを閉じる
      dr.closeDB();
    }
  }
}
