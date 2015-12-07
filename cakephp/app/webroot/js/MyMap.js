var map;
var markers;
var select;

function init(dat) {
    var lon=dat[0]['position']['longitude'];
    var lat=dat[0]['position']['latitude'];
    var myLocation = new OpenLayers.Geometry.Point(lon,lat).transform('EPSG:4326', 'EPSG:3857');
    //overlays=new Array();
    var features=new Array(dat.length);
    // We add the marker with a tooltip text to the overlay
    // The overlay layer for our marker, with a simple diamond as symbol
    var overlay = new OpenLayers.Layer.Vector('Overlay', {
        styleMap: new OpenLayers.StyleMap({
            title: '${tooltip}',
            graphicWidth: 28
        })
    });
    for(var i=0;i<dat.length;i++){
        var lon=dat[i]['position']['longitude'];
        var lat=dat[i]['position']['latitude'];
        features[i] = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(lon,lat).transform('EPSG:4326', 'EPSG:3857'), {
            description: dat[i]['message'][0]
        }, {externalGraphic:'http://localhost/cakephp/app/webroot/img/marker/'+dat[i]['User']['imgpath'], graphicHeight: 32, graphicWidth: 32, graphicXOffset:-12, graphicYOffset:-25}
        );
    }

    overlay.addFeatures(features);

    overlay.events.on({
        'featureselected': onFeatureSelect,
        'featureunselected': onFeatureUnselect
    });

    map = new OpenLayers.Map({
        div: "canvas", projection: "EPSG:3857",
        layers: [new OpenLayers.Layer.OSM(),overlay],
        center: myLocation.getBounds().getCenterLonLat(), zoom: 18
    });
    // Create a select feature control and add it to the map.
    select = new OpenLayers.Control.SelectFeature(overlay);
    map.addControl(select);
    select.activate();
}

//コールバック関数、ポップアップ右上を押下した時、選択状態を解除
function onPopupClose(evt) {
    select.unselect(this.feature);
}

//コールバック関数、アイコンクリック時の処理
function onFeatureSelect(evt) {
    var feature = evt.feature;
    var content = '';
    popup = new OpenLayers.Popup.FramedCloud("featurePopup",
        feature.geometry.getBounds().getCenterLonLat(),
        new OpenLayers.Size(100, 100),feature.attributes.description,
        null, true, onPopupClose);
    feature.popup = popup;
    popup.feature = feature;
    map.addPopup(popup);
    map.zoomTo(18);
    var lonLat = map.getCenter().transform(
        new OpenLayers.Projection("EPSG:900913"),
        new OpenLayers.Projection("EPSG:4326")
    );
    map.zoomTo(18);
    map.panTo(feature.geometry.getBounds().getCenterLonLat());
}

//コールバック関数、アイコン外をクリックした時の処理
function onFeatureUnselect(evt) {
    var feature = evt.feature;
    if (feature.popup) {
        popup.feature = null;
        map.removePopup(feature.popup);
        feature.popup.destroy();
        feature.popup = null;
    }
}

//デバッグ用。友達の位置を表示
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

//(lon,lat)にカメラを移動
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
