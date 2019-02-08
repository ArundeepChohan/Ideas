/*
https://developers.google.com/maps/documentation/javascript/examples/marker-remove
*/

	function addMarker(latLng) 
	{
		marker = new google.maps.Marker
		({
			position: latLng,
			title: 'Point A',
			map: map,
			icon:icon,
			draggable: true
		});
		
		// Add circle overlay and bind to marker.
		//This is where you can visit and come back in 1 hour.
		circle1	= new google.maps.Circle
		({
			map: map,
			radius: radius1, 
			strokeColor: '#33cc33',
			strokeOpacity: 0.4,
			strokeWeight: 2,	
			fillOpacity: 0.15,		
			fillColor: '#33cc33'
		});
		circle1.bindTo('center', marker, 'position');
			
		// This is the maximum spot you can go in 1 hour.
		circle2 = new google.maps.Circle
		({
			map: map,
			radius: radius2,
			strokeColor: '#e67300',
			strokeOpacity: 0.4,
			strokeWeight: 2,
			fillOpacity: 0.15,	
			fillColor: '#e67300'
		});
		circle2.bindTo('center', marker, 'position');
		
		marker.circle1 = circle1;
		marker.circle2 = circle2;
		markers.push(marker);
	
 	};

	// Removes the markers from the map, but keeps them in the array.
	function clearMarkers() 
	{
       	 setMapOnAll(null);
    	};
	
	function setMapOnAll(map)
	{
       		for (var i = 0; i < markers.length; i++) 
		{
			markers[i].setMap(map);
			markers[i].circle1.unbindAll();
			markers[i].circle1.setMap(null);
			markers[i].circle2.unbindAll();
			markers[i].circle2.setMap(null);
       		}
    	};
	
	function showMarkers() 
	{
		setMapOnAll(map);
    	};

      // Deletes all markers in the array by removing references to them.
	function deleteMarkers() 
	{
		clearMarkers();
        	markers = [];
    	};
	  
