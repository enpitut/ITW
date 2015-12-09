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
    <a id="menu">一覧</a>

    <div id="sidr">
        <!-- Your content -->
        <ul>
            <?php
            foreach ($userdata as $dat) {
                echo "<li><a onclick=panTo({$dat['position']['longitude']},{$dat['position']['latitude']})>{$this->Html->image("marker/".$dat['User']['imgpath'],array('width'=>'30','height'=>'30'))}　{$dat['User']['username']}</a></li>";
            }
            ?>
        <br>
        <li><a href="#" onclick="jQuery.sidr('close', 'sidr');"><font size="4"><i class='glyphicon glyphicon-remove'></i> 閉じる</font></a></li>
        </ul>

    </div>
    <script>
        $(document).ready(function() {
          $('#menu').sidr({
            name: 'sidr',
            side: 'left',
            displace: false
        });
      });
    </script>
    <div class="container">
        <div>
            <div  id="canvas" style="width:100%; height:85vh ;padding: 10px; margin-bottom: 10px; border: 1px solid #333333;"></div>
        </div>
    </div>

    <?php

// foreach ($userdata as $dat) {

//     echo <<<EOD
//     <div class="media">
//         <a class="media-left" onclick="panTo({$dat['position']['longitude']},{$dat['position']['latitude']})" >
// EOD;
//     echo $this->Html->image('marker/'.$dat['User']['imgpath'],
//         array(
//             'width'=>'80',
//             'height'=>'80'
//             )
//         );
//     echo <<<EOD
//         </a>
//         <div class="media-body">
//            <h4 class="media-heading">
// EOD;
//     foreach($dat['message'] as $mes)echo $mes.'</br>';
//     echo <<<EOD
//         </h4>
//         </div>
//     </div>
// EOD;
// }
    ?>

    <script>
        <?php
        echo 'init('.json_encode($userdata).');';
        foreach ($otherdata as $dat) {
            echo 'AddNormalMarker(' . $dat['position']['longitude'] . ',' . $dat['position']['latitude'] . ');';
        }
        ?>
    </script>

</body>
</html>