<?php
  $dsn = 'mysql:dbname=enpit2015_ITW;host=localhost;charset=utf8';
  $user = 'root';
  $password = 'enPiT2015ITW';
  session_start();

  if(!isset($_POST["parent_id"])){$_POST["parent_id"]="";}
  if(!isset($_SESSION["Visitor"]["id"])){$_SESSION["Visitor"]["id"]="";}

  $user_id = htmlspecialchars($_SESSION["Visitor"]["id"]);
  $parentid = htmlspecialchars($_POST["parent_id"]);

  try{
    $dbh = new PDO($dsn, $user, $password);
    if($dbh == null){
      echo -1;
    }else{
      $dbh->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
      $check_sql = 'SELECT * FROM users WHERE user_id = :user_id';
      $check_stmt = $dbh->prepare($check_sql);
      $check_stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
      $check_stmt->execute();
      $rc = $check_stmt->rowCount();
      $fp = fopen("parentlog.txt", "a");
      fwrite($fp, "rc = ".$rc."\r\n");
      fclose($fp);
      if($rc == 0){
        $insert_sql = 'INSERT INTO positions (id, parentid) values (:user_id, :parentid)';
        $stmt = $dbh->prepare($insert_sql);
        $stmt->bindValue(':parentid', $parentid, PDO::PARAM_INT);
        $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);
        $stmt->execute();
      }else{
        $update_sql = 'UPDATE friends SET parentid = :parentid WHERE id=:user_id';
        $stmt = $dbh->prepare($update_sql);
        $stmt->bindValue(':parentid', $parentid, PDO::PARAM_INT);
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