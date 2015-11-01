<?php
//$dsn="mysql:dbname= database;host=127.0.0.1";
/*  $url = "127.0.0.1";
  $user = "user";
  $pass = "password";
  $db = "database";
  $table = "data";*/
  if(!isset($_POST["lat"])){$_POST["lat"]="";}
  if(!isset($_POST["lon"])){$_POST["lon"]="";}
  $lat = htmlspecialchars($_POST["lat"]);
  $lon = htmlspecialchars($_POST["lon"]);

 /*
  // MySQLへ接続する
  $link = mysql_connect($url,$user,$pass) or die("MySQLへの接続に失敗しました。");
 
  // データベースを選択する
  $sdb = mysql_select_db($db,$link) or die("データベースの選択に失敗しました。");
 
  // クエリを送信する
  $sql = "SELECT * FROM $table where text LIKE BINARY '".$moji."'";
  $result = mysql_query($sql, $link) or die("クエリの送信に失敗しました。<br />SQL:".$sql);
 
  //結果セットの行数を取得する（PHP 5.5.0 では非推奨です）
  $rows = mysql_num_rows($result);
//一致行数を返す
*/
	$rows = 100;
    echo $rows;
?>
