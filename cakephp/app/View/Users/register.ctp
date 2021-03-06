<html>
<head>
    <title>ユーザ登録</title>
</head>
<body>

<div class="text-center container" style=" position: relative; top: 50%; transform: translateY(-50%);">
    <div class="
     col-xs-offset-1 col-sm-offset-4 col-md-offset-4
     col-xs-10 col-sm-4 col-md-4">
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

        echo '<br>'.$this->Html->link(
                'ログイン画面へ..',
                './login'
            );
        ?>
    </div>
</div>
</body>
</html>
