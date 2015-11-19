<html lang="ja">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?php echo 'みんなといっしょ'; ?></title>
    <!-- Bootstrap -->
    <?php echo $this->Html->css('bootstrap.min'); ?>

    <!-- Le styles -->
    <style>
        body {
            padding-top: 50px;
        }
        .starter-template {
            padding: 40px 15px;
            text-align: center;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <?php echo $this->Html->charset(); ?>
    <?php echo $this->Html->meta('icon');?>
    <?php echo $this->fetch('meta');?>
    <?php echo $this->Html->css('base-style.css'); ?>
    <?php echo $this->fetch('css');?>
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbarEexample1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">みんなといっしょ：<?php print(h($user['username']." さん (ID: ")); ?>
												<?php print(h($user['id']).")"); ?>としてログイン</a>
        </div>

        <div class="collapse navbar-collapse  navbar-inversez" id="navbarEexample1">
            <ul class="nav navbar-nav navbar-right">
                <li><?php print($this->Html->link('メイン', 'index')); ?></li>
                <li><?php print($this->Html->link('おともだち', 'friend')); ?></li>
                <li><?php print($this->Html->link('おうちのかた', 'parent')); ?></li>
                <li><?php print($this->Html->link('ログアウト', 'logout')); ?></li>
            </ul>
        </div>
    </div>
</nav>

<?php echo $this->fetch('content'); ?>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<?php echo $this->Html->script('bootstrap.min'); ?>
<?php echo $this->fetch('script'); ?>

</body>
</html>