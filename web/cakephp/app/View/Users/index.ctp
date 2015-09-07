<html>
<head>
    <script type="text/javascript" src="http://www.openlayers.org/api/OpenLayers.js"></script>
    <style type="text/css">
        #canvas .olControlAttribution {
            font-size:13px;
            bottom:3px;
        }
    </style>

    <script>
        /**
         * Created by ft4613-ac on 15/08/28.
         */

        var map;
        var mapnik;
        var markers;

        function init() {
            map = new OpenLayers.Map("canvas")
            mapnik = new OpenLayers.Layer.OSM();
            markers = new OpenLayers.Layer.Markers("Markers");
            map.addLayer(mapnik);
            map.addLayer(markers);
            map.addControl(new OpenLayers.Control.ScaleLine());
            var lonLat = new OpenLayers.LonLat(<?php echo $userdata[0]['position']['longitude'].',' .$userdata[0]['position']['latitude'] ?>)
                .transform(
                new OpenLayers.Projection("EPSG:4326"),
                new OpenLayers.Projection("EPSG:900913")
            );
            map.setCenter(lonLat, 18);
            <?php
              foreach ($userdata as $dat) {
                 $path='http://localhost/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
                 echo 'AddMarker(' . $dat['position']['longitude'] . ',' . $dat['position']['latitude']. ',"' . $path . '");' ;
              }
            ?>
            <?php
              foreach ($otherdata as $dat) {
                 //$path='http://localhost/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
                 echo 'AddNormalMarker(' . $dat['position']['longitude'] . ',' . $dat['position']['latitude'] . ');';
              }
            ?>
        }
    </script>

    <script>
        function AddMarker(lat,lon,img){
            var markers = new OpenLayers.Layer.Markers("Markers");
            map.addLayer(markers);
            var size = new OpenLayers.Size(32, 32);
            var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
            var icon = new OpenLayers.Icon(img, size, offset);
            var marker = new OpenLayers.Marker(
                new OpenLayers.LonLat(lat,lon)
                    .transform(
                    new OpenLayers.Projection("EPSG:4326"),
                    new OpenLayers.Projection("EPSG:900913")
                ),
                icon
            );
            markers.addMarker(marker);
        }
        function AddNormalMarker(lat,lon){
            var markers = new OpenLayers.Layer.Markers("Markers");
            map.addLayer(markers);
            var marker = new OpenLayers.Marker(
                new OpenLayers.LonLat(lat,lon)
                    .transform(
                    new OpenLayers.Projection("EPSG:4326"),
                    new OpenLayers.Projection("EPSG:900913")
                )
            );
            markers.addMarker(marker);
        }
        function panTo(lon, lat) {
            var lonLat = map.getCenter().transform(
                new OpenLayers.Projection("EPSG:900913"),
                new OpenLayers.Projection("EPSG:4326")
            );
            map.zoomTo(18);
            map.panTo(lonLat.add(lon-lonLat.lon, lat-lonLat.lat).transform(
                    new OpenLayers.Projection("EPSG:4326"),
                    new OpenLayers.Projection("EPSG:900913")
                )
            );
        }

    </script>
</head>
<body onload="init();">
　
<div class="container">
    <div>
        <div  id="canvas" style="width:auto; height:420px;padding: 10px; margin-bottom: 10px; border: 1px solid #333333;"></div>
    </div>
</div>
<!--   echo $info[0];-->

<?php

//var_dump($userdata);
foreach ($userdata as $dat) {
    $path='http://localhost/cakephp/app/webroot/img/marker/'.$dat['User']['imgpath'];
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

</body>
</html>