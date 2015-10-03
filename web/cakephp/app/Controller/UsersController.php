<?php
/**
 * Created by PhpStorm.
 * User: ft4613-ac
 * Date: 15/08/28
 * Time: 7:54
 */
require_once("location_distance.php");

class UsersController extends AppController
{
    public $helpers = array('Html', 'Form');
    public $components = array('Paginator', 'Session');

    public function index()
    {
        $this->layout = 'sampleLayout';
        $this->Session->write('Visitor.id', 1);

        $userdata = $this->User->find('all',
            array(
                'conditions' => array('user.parentid' => $this->Session->read('Visitor.id'))
            )
        );

        $otherdata = $this->User->find('all',
            array(
                'conditions' => array(
                    'NOT' => array(
                        'user.parentid' => ''
                    ),
                    'NOT' => array(
                        'user.parentid' => $this->Session->read('Visitor.id')
                    )


                )
            )
        );

        /* ◯◯くんの近くには誰くんと〜がいます を出力*/
        //userdataから1人取り出し、友達の情報をリストアップ
        $frienddata = array();
        $tmpdata = array();
        foreach ($userdata as $dat) {
            foreach ($dat['friend'] as $tmp) {
                $tmpdata[] = $this->User->find('all',
                    array(
                        'conditions' => array('user.id' => $tmp['friendsid'])
                    )
                );
            }
            $frienddata[]=$tmpdata;
            $tmpdata= array();
        }
        $resulttmp=array();

        //子供とその友達の距離を測り、近傍に居るか否か見分ける
        for($i=0;$i<count($userdata);$i++) {
            $userdata[$i]['message']='';

            foreach ($frienddata[$i] as $friend) {
                $hoge = location_distance(
                    $userdata[$i]['position']['latitude'],
                    $userdata[$i]['position']['longitude'],
                    $friend[0]['position']['latitude'],
                    $friend[0]['position']['longitude']);

                if ($hoge["distance"] < 20.0) {
                    $resulttmp[].= (
                         $friend[0]['User']['name']
                        . "が近くにいます"
                    );
                }

            }
            if(count($resulttmp)==0){
                $userdata[$i]['message'][] = ("ぼっちです");
            }else{
                $userdata[$i]['message'] = $resulttmp;
            }
            $resulttmp=array();
        }
        $this->set("otherdata", $otherdata);
        $this->set("userdata", $userdata);
    }
}
?>