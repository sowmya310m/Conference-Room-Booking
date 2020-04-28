<?php
$conn = mysqli_connect("localhost","root","","hackathon");
$otpReceived = $_POST['otpReceived'];
$email = $_POST['email'];
$qryUid = "SELECT uid from users_db where email = '$email'";
$resUid = mysqli_query($conn,$qryUid);
$reqUid = mysqli_fetch_assoc($resUid);
$uid = $reqUid['uid'];
$qry = "SELECT otpSent from otp_db where uid = '$uid'";
$res = mysqli_query($conn,$qry);
$req = mysqli_fetch_assoc($res);
$otpSent = $req['otpSent'];
$result;
// echo $otpSent;
// echo $otpReceived;
if($otpSent==$otpReceived){
	$npassword = $_POST['npassword'];
	$npassword = hash('sha256',$npassword);
	// $npassword1 = $_POST['npassword1'];
	// $npassword = hash('sha256', $npassword);
	$qry1 = "UPDATE users_db set password = '$npassword' where uid = '$uid'";
	$res1 = mysqli_query($conn,$qry1);
	if(!$res1){
		$result['ans'] = "Some Problem has been Encountered";
	}
	else{
		$result['ans'] = "Password Changed";
		$qryDel = "DELETE from otp_db where uid = '$uid' and otpSent = '$otpSent'";
		$resDel = mysqli_query($conn,$qryDel);
	}
}
else{
		$result['ans'] = "Wrong OTP Entered!";
}
echo json_encode($result);
?>