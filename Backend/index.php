<?php
require 'PHPMailer/PHPMailerAutoload.php';
$conn = mysqli_connect("localhost","root","","hackathon");
$otpSent = rand(1000,9999);
// $otpSent = 9999;
$uid = $_POST['uid'];
$email;
$qry = "SELECT email from users_db where uid = '$uid'";
$res = mysqli_query($conn,$qry);
$req = mysqli_fetch_assoc($res);
$email = $req['email'];
$email = $req['email'];
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
$mail->SetFrom($email);
$mail->Subject = "Test";
$mail->Body = " OTP ".$otpSent;
$mail->AddAddress($email);
$qry1 = "SELECT * from otp_db where uid = '$uid'";
$res = mysqli_query($conn,$qry1);
 if(!$mail->Send()) {
    $msg = "Mail Not Sent";
 } else {
    $msg = "Message has been sent";
 }
 if(!$res){
	$qry2 = "INSERT into otp_db ( uid, otpSent ) values ('$uid','$otpSent')";
	$res1 = mysqli_query($conn,$qry2);
}
else{
	$qry2 = "UPDATE table otp_db set otpSent = '$otpSent' where uid = '$uid'";
	$res1 = mysqli_query($conn,$qry2);
}
echo json_encode($msg);
 ?>
