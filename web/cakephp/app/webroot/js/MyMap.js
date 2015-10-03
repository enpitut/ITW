///**
// * Created by ft4613-ac on 15/08/28.
// */
//
//var map;
//var mapnik;
//var markers;
//
//function init() {
//    map = new OpenLayers.Map("canvas")
//    mapnik = new OpenLayers.Layer.OSM();
//    markers = new OpenLayers.Layer.Markers("Markers");
//    map.addLayer(mapnik);
//    map.addLayer(markers);
//
//    var lonLat = new OpenLayers.LonLat(140.100726, 36.111035)
//        .transform(
//        new OpenLayers.Projection("EPSG:4326"),
//        new OpenLayers.Projection("EPSG:900913")
//    );
//    map.setCenter(lonLat, 18);
//    AddMarker(140.100726, 36.111035,'../img/marker/C.png');
//    AddMarker(140.100651, 36.110079,'../img/marker/D.png');
//}
//
//function AddMarker(lat,lon,img){
//    var markers = new OpenLayers.Layer.Markers("Markers");
//    map.addLayer(markers);
//    var size = new OpenLayers.Size(32, 32);
//    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
//    var icon = new OpenLayers.Icon(img, size, offset);
//
//    var marker = new OpenLayers.Marker(
//        new OpenLayers.LonLat(lat,lon)
//            .transform(
//            new OpenLayers.Projection("EPSG:4326"),
//            new OpenLayers.Projection("EPSG:900913")
//        ),
//        icon
//    );
//
//    markers.addMarker(marker);
//}
//
