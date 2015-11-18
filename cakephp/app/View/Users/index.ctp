<?php
header('Expires: -1');
header('Pragma: no-cache');
header('Cache-Control: no-cache');?>
<html>
<head>
    <script type="text/javascript" src="http://www.openlayers.org/api/OpenLayers.js"></script>
    <style type="text/css">
        #canvas .olControlAttribution {
            font-size:13px;
            bottom:3px;
        }
    </style>
    <?php echo $this->Html->script('MyMap.js'); ?>
</head>
<body>
<div class="container">
    <div>
        <div  id="canvas" style="width:auto; height:420px;padding: 10px; margin-bottom: 10px; border: 1px solid #333333;"></div>
    </div>
</div>

<?php

foreach ($userdata as $dat) {
    $path='http://localhost:81/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
    echo <<<EOD
    <div class="media">
        <a class="media-left" onclick="panTo({$dat['position']['longitude']},{$dat['position']['latitude']})" >
            <img src="{$path}" width="80" height="80">
        </a>
        <div class="media-body">
           <h4 class="media-heading">
EOD;
    foreach($dat['message'] as $mes)echo $mes.'</br>';
    echo <<<EOD
        </h4>
        </div>
    </div>
EOD;
}
?>
<script>
    <?php
    echo 'init('.$userdata[0]['position']['longitude'].',' .$userdata[0]['position']['latitude'].');';

    foreach ($userdata as $dat) {
        $path='http://localhost:81/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
        echo 'AddMarker(' . $dat['position']['longitude'] . ',' . $dat['position']['latitude']. ',"' . $path . '");' ;
    }
    foreach ($otherdata as $dat) {
        //$path='http://localhost:81/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
        echo 'AddNormalMarker(' . $dat['position']['longitude'] . ',' . $dat['position']['latitude'] . ');';
    }
    ?>
</script>

</body>
</html>