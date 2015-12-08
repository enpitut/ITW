<?php
  session_start();

  if(!isset($_SESSION["Visitor"]["id"])){echo -1;}
  else{
    $user_id = htmlspecialchars($_SESSION["Visitor"]["id"]);
    echo $user_id;
  }
?>