<html>
<head>
<title>ログイン</title>
</head>
<body>

<?php echo $this->Session->flash(); ?>
<div class="text-center container" style=" position: relative; top: 50%; transform: translateY(-50%);">
    <div class="
     col-xs-offset-1 col-sm-offset-4 col-md-offset-4 col-lg-offset-4
     col-xs-10 col-sm-4 col-md-4 col-lg-4">
    <?php echo $this->Form->create('BoostCake', array(
        'inputDefaults' => array(
            'div' => 'form-group',
            'label' => false,
            'wrapInput' => false,
            'class' => 'form-control'
        ),
        'class' => 'well form-inline'
    )); ?>
    <?php
    ?>
    <?php
    echo $this->Form->create('User');
    echo $this->Html->image('logo.png',
        array('width'=>'200','height'=>'200'));
    echo $this->Form->input('username', array(
        'label'=>false,
        'placeholder' => 'ユーザ名',
        'class'=>'form-control'

    ));
    echo $this->Form->input('password', array(
        'label'=>false,
        'placeholder' => 'パスワード',
        'class'=>'form-control'
    ));
    echo $this->Form->submit('ログイン', array(
        'div' => 'form-group',
        'class' => 'btn btn-default'
    ));
    echo '<br>'.$this->Html->link(
            'アカウントの作成..',
            './register'
        );
    echo $this->Form->end();
?>
</div>
</div>
</body>
</html>
