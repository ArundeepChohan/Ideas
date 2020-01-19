	function measure(pt)
	{  
		// generally used geo measurement function
		var R = 6378.137; // Radius of earth in KM
		var dLat = pt.geometry.location.lat() * Math.PI / 180 - latLng.lat() * Math.PI / 180;
		var dLon = pt.geometry.location.lng()* Math.PI / 180 - latLng.lng() * Math.PI / 180;
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		Math.cos(latLng.lat() * Math.PI / 180) * Math.cos(pt.geometry.location.lat() * Math.PI / 180) *
		Math.sin(dLon/2) * Math.sin(dLon/2);
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		var d = R * c;
		return d * 1000; // meters
	};
