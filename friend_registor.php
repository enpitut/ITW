<?php
  $dsn = 'mysql:dbname=enpit2015_ITW;host=localhost;charset=utf8';
  $user = 'root';
  $password = 'enPiT2015ITW';
  session_start();

  if(!isset($_POST["friend_id"])){$_POST["friend_id"]="";}
  if(!isset($_SESSION["Visitor"]["id"])){$_SESSION["Visitor"]["id"]="";}

  $user_id = htmlspecialchars($_SESSION["Visitor"]["id"]);
  $friend_id = htmlspecialchars($_POST["friend_id"]);

  try{
    $dbh = new PDO($dsn, $user, $password);
    if($dbh == null){
      echo -1;
    }else{
      $dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
      $check_sql = 'SELECT * FROM friends WHERE user_id = :user_id and friendsid = :friend_id';
      $check_stmt = $dbh->prepare($check_sql);
      $check_stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
      $check_stmt->execute();
      $rc = $check_stmt->rowCount();
      $fp = fopen("friendlog.txt", "a");
      fwrite($fp, "rc = ".$rc.", user_id = ".$user_id."\r\n");
      fclose($fp);
      if($rc == 0){
        $insert_sql = 'INSERT INTO friends (user_id, friendsid) values (:user_id, :friend_id)';
        $stmt = $dbh->prepare($insert_sql);
        $stmt->bindValue(':friend_id', $friend_id, PDO::PARAM_INT);
        $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
        $stmt->execute();
      }else{
        $update_sql = 'UPDATE friends SET friendsid = :friend_id WHERE user_id=:user_id';
        $stmt = $dbh->prepare($update_sql);
        $stmt->bindValue(':friend_id', $friend_id, PDO::PARAM_INT);
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
