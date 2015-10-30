var map;
var mapnik;
var markers;

function init(lon,lat) {
    map = new OpenLayers.Map("canvas");
    mapnik = new OpenLayers.Layer.OSM();
    markers = new OpenLayers.Layer.Markers("Markers");
    map.addLayer(mapnik);
    map.addLayer(markers);
    map.addControl(new OpenLayers.Control.ScaleLine());
    var lonLat = new OpenLayers.LonLat(lon,lat)
    .transform(
        new OpenLayers.Projection("EPSG:4326"),
        new OpenLayers.Projection("EPSG:900913")
    );
    map.setCenter(lonLat, 18);
}

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