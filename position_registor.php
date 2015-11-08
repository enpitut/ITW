<?php
  $dsn = 'mysql:dbname=enpit2015_ITW;host=localhost;charset=utf8';
  $user = 'root';
  $password = 'enPiT2015ITW';
  if(!isset($_POST["user_id"])){$_POST["user_id"]="";}
  if(!isset($_POST["lat"])){$_POST["lat"]="";}
  if(!isset($_POST["lon"])){$_POST["lon"]="";}
  if(!isset($_POST["date"])){$_POST["date"]="";}
  $user_id = htmlspecialchars($_POST["user_id"]);
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
      $sql = 'UPDATE positions SET latitude = :lat, longitude = :lon, date = :date WHERE user_id=:user_id';
      $stmt = $dbh->prepare($sql);
      $stmt->bindValue(':lat', $lat, PDO::PARAM_STR);
      $stmt->bindValue(':lon', $lon, PDO::PARAM_STR);
      $stmt->bindValue(':date', $date->format('Y-m-d H:i:s'), PDO::PARAM_STR);
      $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
      $stmt->execute();
      echo 1;
    }
  }catch (PDOException $e){
    //print('Connection failed : ' .$e->getMessage());
    echo -1;
    die();
  }
?>
