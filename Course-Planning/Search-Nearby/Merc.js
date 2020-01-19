//Using http://jsbin.com/venufenula/edit?html,output code
	 //-------------------------------------------------------------------------------------------------------------------------
	 var TILE_SIZE = 256;
      //Mercator --BEGIN--
      function bound(value, opt_min, opt_max) {
          if (opt_min !== null) value = Math.max(value, opt_min);
          if (opt_max !== null) value = Math.min(value, opt_max);
          return value;
      }

      function degreesToRadians(deg) {
          return deg * (Math.PI / 180);
      }

      function radiansToDegrees(rad) {
          return rad / (Math.PI / 180);
      }

      function MercatorProjection() {
          this.pixelOrigin_ = new google.maps.Point(TILE_SIZE / 2,
          TILE_SIZE / 2);
          this.pixelsPerLonDegree_ = TILE_SIZE / 360;
          this.pixelsPerLonRadian_ = TILE_SIZE / (2 * Math.PI);
      }

      MercatorProjection.prototype.fromLatLngToPoint = function (latLng,
      opt_point) {
          var me = this;
          var point = opt_point || new google.maps.Point(0, 0);
          var origin = me.pixelOrigin_;

          point.x = origin.x + latLng.lng() * me.pixelsPerLonDegree_;

          // NOTE(appleton): Truncating to 0.9999 effectively limits latitude to
          // 89.189.  This is about a third of a tile past the edge of the world
          // tile.
          var siny = bound(Math.sin(degreesToRadians(latLng.lat())), - 0.9999,
          0.9999);
          point.y = origin.y + 0.5 * Math.log((1 + siny) / (1 - siny)) * -me.pixelsPerLonRadian_;
          return point;
      };

      MercatorProjection.prototype.fromPointToLatLng = function (point) {
          var me = this;
          var origin = me.pixelOrigin_;
          var lng = (point.x - origin.x) / me.pixelsPerLonDegree_;
          var latRadians = (point.y - origin.y) / -me.pixelsPerLonRadian_;
          var lat = radiansToDegrees(2 * Math.atan(Math.exp(latRadians)) - Math.PI / 2);
          return new google.maps.LatLng(lat, lng);
      };

      //Mercator --END--
	var desiredRadiusPerPointInMeters = 1;
	 function getNewRadius() {
          var numTiles = 1 << map.getZoom();
          var center = map.getCenter();
          var getPointsd = google.maps.geometry.spherical.computeOffset(center, 10000, 90); /*1000 meters to the right*/
          var projection = new MercatorProjection();
          var initCoord = projection.fromLatLngToPoint(center);
          var endCoord = projection.fromLatLngToPoint(getPointsd);
          var initPoint = new google.maps.Point(
            initCoord.x * numTiles,
            initCoord.y * numTiles);
           var endPoint = new google.maps.Point(
            endCoord.x * numTiles,
            endCoord.y * numTiles);
        var pixelsPerMeter = (Math.abs(initPoint.x-endPoint.x))/10000.0;
        var totalPixelSize = Math.floor(desiredRadiusPerPointInMeters*pixelsPerMeter);
        console.log(totalPixelSize);
        return totalPixelSize;
      
      }