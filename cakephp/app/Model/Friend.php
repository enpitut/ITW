<?php
/**
 * Created by PhpStorm.
 * User: ft4613-ac
 * Date: 15/08/28
 * Time: 16:09
 */

class Friend extends AppModel {
    public $name1 = 'id';

    public $name2 = 'friendsid';



    public function beforeSave($options = array())
    {
        $this->data['friendsid'] = AuthComponent::password($this->data['friendsid'] );
        return true;
    }

}

?>