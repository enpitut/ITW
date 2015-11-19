<?php
/**
 * Created by PhpStorm.
 * User: ft4613-ac
 * Date: 15/08/28
 * Time: 7:54
 */
// require('Controller/UsersController.php'); と同じ
App::import('Vendor', 'location_distance');
App::uses('AppController', 'Controller');

class UsersController extends AppController
{
    public $helpers = array('Html', 'Form');
    //読み込むコンポーネントの指定
    public $components = array('Paginator', 'Session','Auth');

    public function index()
    {
        $this->set('user', $this->Auth->user());
        $this->layout = 'indexLayout';
        $this->Session->write('Visitor.id',$this->Auth->user('id'));

        $userdata = $this->User->find('all',
            array(
                'conditions' => array('user.parentid' => 1)
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

        /* ◯◯くんの近くには誰くんと?がいます を出力*/
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
                         $friend[0]['User']['username']
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

    //どのアクションが呼ばれてもはじめに実行される関数
    public function beforeFilter()
    {
        parent::beforeFilter();
        $this->response->disableCache();
        //未ログインでアクセスできるアクションを指定
        //これ以外のアクションへのアクセスはloginにリダイレクトされる規約になっている
        $this->Auth->allow('register', 'login');

    }

   public function friend(){

        //友達登録ボタンを押していたら、データベースを更新する
        if($this->request->is('post')){
            $this->loadModel('Friend');
            $this->Friend->save($this->request->data);
        }

        //ログインユーザの情報を取得する
        $user = $this->User->find('first',
            array(
                    'conditions' => array('user.id' => $this->Auth->user('id'))
            )
        );

        //ログインユーザの友達の情報を取得する
        $friends = array();
        foreach ($user['friend'] as $friend) {
            array_push($friends, 
                $this->User->find('all',
                    array(
                        'conditions' => array('user.id' => $friend['friendsid'])
                    )
                )
            );
        }

        //ログインユーザの友達の名前を取得する
        $friendsname = array();
        foreach ($friends as $friend) {
            array_push($friendsname, $friend[0]['User']['username']);
        }

        $this->set('friendsname', $friendsname);
        $this->set('user', $this->Auth->user());
        $this->layout = 'indexLayout';
    }


	public function parent() {
        $this->set('user', $this->Auth->user());
        $this->layout = 'indexLayout';
	}



    public function register(){
        //$this->requestにPOSTされたデータが入っている
        //POSTメソッドかつユーザ追加が成功したら
        if($this->request->is('post') && $this->User->save($this->request->data)){
            //ログイン
            //$this->request->dataの値を使用してログインする規約になっている
            $this->Auth->login();
            $this->redirect('index');
        }
    }


    public function login(){
        if($this->request->is('post')) {
            if($this->Auth->login())
                return $this->redirect('index');
            else
                $this->Session->setFlash('ログイン失敗');
        }
    }

    public function logout(){
        $this->Auth->logout();
        $this->redirect('login');
    }
}
