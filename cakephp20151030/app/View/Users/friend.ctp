<html>
<head>
    <title>登録しているフレンド</title>
</head>
<body>


<div class="text-center container" style=" position: relative; top: 50%; transform: translateY(-50%);">



    <div class="
     col-xs-offset-1 col-sm-offset-1 col-md-offset-1
     col-xs-2 col-sm-7 col-md-7">
        <?php
        echo $this->Form->create('BoostCake', array(
            'inputDefaults' => array(
                'div' => 'form-group',
                'label' => false,
                'wrapInput' => false,
                'class' => 'form-control'
            ),
            'class' => 'well form-inline'
        ));
		///ここに，配列に入っているフレンドをすべて表示
		$count = 0;

		while ($count < 15){
  			// 実行する処理
  			$count++;
			print('ふれんど'.$count.'<br>');
		}


        $this->Form->end();

        echo '<br>'.$this->Html->link(
                'メイン画面へ..',
                './	index'
            );
        ?>
    </div>


    <div class="
     col-xs-offset-1 col-sm-offset-1 col-md-offset-1
     col-xs-1 col-sm-3 col-md-3">
	 <?php
        echo $this->Form->create('BoostCake', array(
            'inputDefaults' => array(
                'div' => 'form-group',
                'label' => false,
                'wrapInput' => false,
                'class' => 'form-control'
            ),
            'class' => 'well form-inline'
        ));

        print(
            $this->Form->create('User') .
            $this->Form->input('email', array(
                'label'=>false,
                'placeholder' => 'メールアドレス',
                'class'=>'form-control'
            )) .

            $this->Form->input('username', array(
                'label'=>false,
                'placeholder' => 'ユーザ名',
                'class'=>'form-control'
            )).

            $this->Form->input('password', array(
                'label'=>false,
                'placeholder' => 'パスワード',
                'class'=>'form-control'
            )) .
            $this->Form->submit('登録', array(
                'div' => 'form-group',
                'class' => 'btn btn-default'
            )));

        $this->Form->end();


　 	 ?>
	</div>
</div>


</body>
</html>
