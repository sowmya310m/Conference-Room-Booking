<?php 

	$latitudeFrom=$_POST['latitudeFrom'];
	$latitudeTo=$_POST['latitudeTo'];
	$longitudeFrom=$_POST['longitudeFrom'];
	$longitudeTo=$_POST['longitudeTo'];

	$latitudeFrom=floatval($latitudeFrom);
	$longitudeFrom=floatval($longitudeFrom);
	$latitudeTo=floatval($latitudeTo);
	$longitudeFrom=floatval($longitudeFrom);
	$dist=haversineGreatCircleDistance($latitudeFrom,$longitudeFrom,$latitudeTo,$longitudeTo);
	echo $dist;

	function haversineGreatCircleDistance(
  $latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo, $earthRadius = 6371000)
	{
  		// convert from degrees to radians
  		$latFrom = deg2rad($latitudeFrom);
  		$lonFrom = deg2rad($longitudeFrom);
  		$latTo = deg2rad($latitudeTo);
	    $lonTo = deg2rad($longitudeTo);

  		$latDelta = $latTo - $latFrom;
  		$lonDelta = $lonTo - $lonFrom;

  		$angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
  		return $angle * $earthRadius;
	}



 ?>