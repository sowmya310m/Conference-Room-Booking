<?php
require 'index.php';
// $otpSent = $_POST['otpSent'];
$otpReceived = $_POST['otpReceived'];
$uid = $_POST['uid'];
if($otpSent==$otpReceived){
	$npassword = $_POST['npassword'];
	// $npassword = hash('sha256', $npassword);
	$qry = "UPDATE users_db set password = '$npassword' where uid = '$uid'";
	$res = mysqli_query($conn,$qry);
	
}
else{
		$result = "Wrong OTP Entered!";
}
echo json_encode($result);
?>