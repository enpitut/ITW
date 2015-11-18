<?php
  $dsn = 'mysql:dbname=enpit2015_ITW;host=localhost;charset=utf8';
  $user = 'root';
  $password = 'enPiT2015ITW';
  if(!isset($_POST["user_id"])){$_POST["user_id"]="";}
  if(!isset($_POST["friend_id"])){$_POST["friend_id"]="";}
  /*
  $user_id = htmlspecialchars($_POST["user_id"]);
  $friend_id = htmlspecialchars($_POST["friend_id"]);
  */

  try{
    $dbh = new PDO($dsn, $user, $password);
    if($dbh == null){
      echo -1;
    }
    else{
      $dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
      $sql = 'INSERT INTO friends VALUES (?, ?)';
      $stmt = $dbh->prepare($sql);
      /*
      $stmt->bindValue(':friendsid', $friend_id, PDO::PARAM_INT);
      $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
      */
      $stmt->execute(array($_POST["user_id"], $_POST["friend_id"]));
      $stmt->execute(array($_POST["friend_id"], $_POST["user_id"]));
      echo 1;
    }
  }catch (PDOException $e){
    //print('Connection failed : ' .$e->getMessage());
    echo -1;
    die();
  }
?>
