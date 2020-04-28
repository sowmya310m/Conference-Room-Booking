<?php
require 'PHPMailer/PHPMailerAutoload.php';
$conn = mysqli_connect("localhost","root","","hackathon");
$otpSent = rand(1000,9999);
// $otpSent = 9999;
$email = $_POST['email'];
$qryUid = "SELECT uid from users_db where email = '$email'";
$resUid = mysqli_query($conn,$qryUid);
$reqUid = mysqli_fetch_assoc($resUid);
$uid = $reqUid['uid'];
// $email = "ajayvarma1203@gmail.com";
$mail = new PHPMailer(); // create a new object
$mail->IsSMTP(); // enable SMTP
$mail->SMTPDebug = 1; // debugging: 1 = errors and messages, 2 = messages only
$mail->SMTPAuth = true; // authentication enabled
$mail->SMTPSecure = 'ssl'; // secure transfer enabled REQUIRED for Gmail
$mail->Host = "smtp.gmail.com";

$mail->Port = 465; // or 587
$mail->IsHTML(true);
$mail->Username = "eagles.vnr@gmail.com";
$mail->Password = "123456789eagles";
//$mail->SetFrom($email);
$mail->Subject = "Test";
$mail->Body = " OTP ".$otpSent;
$mail->AddAddress($email);
$answer;
 if($mail->Send()) {
    $answer['msg'] = "Message has been sent";
 } else {
    $answer['msg'] = "Mail Not Sent";
 }
 $qry1 = "SELECT * from otp_db where uid = '$uid'";
$res2 = mysqli_query($conn,$qry1);
 if(mysqli_num_rows($res2)==0){
	$qry2 = "INSERT into otp_db ( uid, otpSent ) values ('$uid','$otpSent')";
	$res1 = mysqli_query($conn,$qry2);
}
else{
	$qry2 = "UPDATE otp_db SET otpSent = '$otpSent' where uid = '$uid'";
	$res1 = mysqli_query($conn,$qry2);
}
echo json_encode($answer);
?>
