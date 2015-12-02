<?php
  $dsn = 'mysql:dbname=enpit2015_ITW;host=localhost;charset=utf8';
  $user = 'root';
  $password = 'enPiT2015ITW';
  
  session_start();
  
  if(!isset($_POST["lat"])){$_POST["lat"]="";}
  if(!isset($_POST["lon"])){$_POST["lon"]="";}
  if(!isset($_POST["date"])){$_POST["date"]="";}
  if(!isset($_SESSION["Visitor"]["id"])){$_SESSION["Visitor"]["id"] = "";}
  
  $user_id = htmlspecialchars($_SESSION["Visitor"]["id"]);
  $lat = htmlspecialchars($_POST["lat"]);
  $lon = htmlspecialchars($_POST["lon"]);
  $date = new DateTime(htmlspecialchars($_POST["date"]));

  try{
    $dbh = new PDO($dsn, $user, $password);
    if($dbh == null){
      echo -1;
    }
    else{
      $dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
      $check_sql = 'SELECT * FROM positions WHERE user_id = :user_id';
      $check_stmt = $dbh->prepare($check_sql);      
      $check_stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
      $check_stmt->execute();
      $rc = $check_stmt->rowCount();
      $fp = fopen("registlog.txt", "a");
      fwrite($fp, "rc = ".$rc."\r\n");
      fclose($fp);
      if($rc == 0){
      	$insert_sql = 'INSERT INTO positions (user_id, latitude, longitude, date) values (:user_id, :lat, :lon, :date)';
      	$stmt = $dbh->prepare($insert_sql);
     	$stmt->bindValue(':lat', $lat, PDO::PARAM_STR);
     	$stmt->bindValue(':lon', $lon, PDO::PARAM_STR);
     	$stmt->bindValue(':date', $date->format('Y-m-d H:i:s'), PDO::PARAM_STR);
     	$stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
     	$stmt->execute();
      }
      else{
      	 
     	 $update_sql = 'UPDATE positions SET latitude = :lat, longitude = :lon, date = :date WHERE user_id=:user_id';
     	 $stmt = $dbh->prepare($update_sql);
     	 $stmt->bindValue(':lat', $lat, PDO::PARAM_STR);
     	 $stmt->bindValue(':lon', $lon, PDO::PARAM_STR);
     	 $stmt->bindValue(':date', $date->format('Y-m-d H:i:s'), PDO::PARAM_STR);
     	 $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
     	 $stmt->execute();
      }
      echo 1;
    }
  }catch (PDOException $e){
    //print('Connection failed : ' .$e->getMessage());
    echo -1;
    die();
  }
?>
