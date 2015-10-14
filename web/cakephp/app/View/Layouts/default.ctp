<html>
<head>
    <?php echo $this->Html->charset(); ?>
    <?php
    echo $this->Html->css('bootstrap.min');
    echo $this->Html->meta('icon');

    echo $this->fetch('meta');
    echo $this->fetch('css');
    echo $this->fetch('script');

    ?>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<?php echo $this->Html->script('bootstrap.min'); ?>
<?php echo $this->fetch('script'); ?>
<?php echo $this->fetch('content'); ?>
</body>
</html>
