<?php
/**
 * Created by PhpStorm.
 * User: ft4613-ac
 * Date: 15/08/28
 * Time: 7:48
 */

class User extends AppModel {
    public $name = 'user';
    public $hasOne = array('position');
    public $hasMany = array('friend');
    }

?>