/*
https://developers.google.com/maps/documentation/javascript/examples/place-search
*/

function callback(results, status) 
{
	deletePlaces();
	if (status == google.maps.places.PlacesServiceStatus.OK) 
	{
		for (var i = 0; i < results.length; i++)
		{
			place = results[i];
			//console.log(place.geometry.location);
			//console.log(place.geometry.location.lat);
			//console.log(place.geometry.location.lat());
			//console.log(place.geometry.location.lng);
			//console.log(place.geometry.location.lng());
			d = measure(place);
			
			if(d<radius2)
			{	
				createMarker(place);
			}
		}
	}
};

function deletePlaces()
{
	setPlaces(null);
    places = [];
};

function setPlaces(map)
{
    for (var i = 0; i < places.length; i++) 
	{
		places[i].setMap(map);
    }
};

function createMarker(place) 
{
    placeLoc = place.geometry.location;
	symbol = { 
		url: place.icon,
		scaledSize: new google.maps.Size(25, 25), // scaled size
	};

	spot = new google.maps.Marker
	({
		map: map,
        position: placeLoc,  
		icon: symbol,
    });
	
	places.push(spot);
	
	google.maps.event.addListener(spot, 'click', function() 
	{
        infowindow.setContent("<p>"+place.name+"</br>"+place.formatted_address+"</p>");
        infowindow.open(map, this);
    });
	
}
