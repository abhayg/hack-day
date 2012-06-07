<?php
class LocationFromAddress {

	public static function get_location($address = 'blore kormangala 6th block') {
		
		$ch = curl_init();
		$encoded_address = urlencode($address);
		curl_setopt($ch, CURLOPT_URL, 'http://maps.googleapis.com/maps/api/geocode/json?address=' . $encoded_address . '&sensor=true');
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		$json_response = curl_exec($ch);
		$response = json_decode($json_response, true);
		if ($response['status'] == "OK") {
			$location = $response['results'][0]['geometry']['location'];
			curl_close($ch);
			$location_hash = array('success' => true, 'lat' => $location['lat'], 'lng' => $location['lng']);
			return json_encode($location_hash);
		} else {
			return json_encode(array('success' => false, 'error_msg' => 'ERROR: ' . $response['status']));
		}
	}

}

print_r(LocationFromAddress::get_location());
?>
