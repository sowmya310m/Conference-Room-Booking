<?php

require 'PHPMailer/PHPMailerAutoload.php';
require_once dirname(__FILE__) . '/DbConnect.php';
session_start();

function sendEmail($email,$name,$mobile,$password,$role,$ld)
{
	# code...
  require_once dirname(__FILE__) . '/DbConnect.php';
     $db = new DbConnect();
     $connection = $db->connect();
	$mail = new PHPMailer(); // create a new object
	$mail->IsSMTP(); // enable SMTP
	$mail->SMTPDebug = 1; // debugging: 1 = errors and messages, 2 = messages only
	$mail->SMTPAuth = true; // authentication enabled
	$mail->SMTPSecure = 'ssl'; // secure transfer enabled REQUIRED for Gmail
	$mail->Host = "smtp.gmail.com";
	$mail->Port = 465; // or 587
	$mail->IsHTML(true);
	//Enter mail id
	$mail->Username = "eagles.vnr@gmail.com";
	//Enter password
	$mail->Password = "123456789eagles";
	$mail->SetFrom($email);
	$mail->Subject = "Registration Confirmation";
	$message_mail;
	if($role=='admin'){
		$message_mail="Hai $name,<br> You are Successfully registerd as $role for Conference Room Booking system with <br>user-id: $email <br> password: $password <br> for location $ld ";
	}
	else
	{
    $characters='0123456789abcdefghijklmnopqrstuvwxyz';
    $password='';
    for($i=0;$i<10;$i++)
    {
      $password=$password.$characters[rand(0,strlen($characters))];

    }$temp=$password;
    $password = hash('sha256', $temp);
    $sqlUpdate="UPDATE users_db set password = '$password' where email like '$email'";
    $resultUpdate=mysqli_query($connection, $sqlUpdate);
		$message_mail="Hey $name,<br> Your application for the role of $role for Conference Room Booking system with <br>user-id: $email <br> password: $temp <br> from ministry $ld has been received. Please login and change your randomly generated password";
   
	 }

	$mail->Body = $message_mail;

	$mail->AddAddress($email);

	if(!$mail->Send()) {
    	//echo "Mailer Error: " . $mail->ErrorInfo;
 	} 
	else {
    	//echo "Message has been sent"; 
 	}
}


        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

/*if(isset($_POST['register-submit']))
{*/
  $name=$_POST['name'];
  $email=$_POST['email'];
  $password=$_POST['password'];
  $tempPassword=$password;
  $password = hash('sha256', $password);
  $ld=$_POST['ld'];
  $phone=$_POST['mobile'];
  $role=$_POST['role'];
  $sql="INSERT INTO users_db (email,name,mobile,password,role,ld) VALUES ('$email','$name','$phone','$password','$role','$ld')";
  $result = mysqli_query($connection, $sql);
  		$out;
    	if($result)
    	{
    		/*echo "Data saved Successfully..!";*/
        sendEmail($email,$name,$phone,$tempPassword,$role,$ld);
    		$out['error']=false;
    		$out['message']="User details saved Successfully..!";
    		

    	}
    	else
    	{
    		$out['error']=true;
    		$out['message']="Unable to store details..!";
    	}

    	echo json_encode($out);
//}
?>