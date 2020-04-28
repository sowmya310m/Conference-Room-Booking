<?php 

	$latitudeFrom=$_POST['latitudeFrom'];
	$latitudeTo=$_POST['latitudeTo'];
	$longitudeFrom=$_POST['longitudeFrom'];
	$longitudeTo=$_POST['longitudeTo'];

	function distance($lat1, $lon1, $lat2, $lon2, $unit) {

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);

  if ($unit == "K") {
    return ($miles * 1.609344);
  } else if ($unit == "N") {
      return ($miles * 0.8684);
    } else {
        return $miles;
      }
}

echo distance(17.496421, 78.395679,17.496175, 78.396376, "M") . " Miles<br>";
echo distance(17.496421, 78.395679,17.496175, 78.396376, "K") . " Kilometers<br>";
echo distance(17.496421, 78.395679,17.496175, 78.396376, "N") . " Nautical Miles<br>";


 ?>