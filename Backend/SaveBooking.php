<?php
	require 'PHPMailer/PHPMailerAutoload.php';
	require_once dirname(__FILE__) . '/DbConnect.php';

    function sendEmail($value,$status)
    {
    # code...
        //echo "Mail";
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
        $mail->SetFrom($value);
        $sub;
        $bdy;
        if($status=='Booked'){
            //echo $status;
            $sub="Booking Confirmed";
            $bdy="Your booking is confirmed..!<br><br><br>Note:<br>Your booking may be cancelled if any higher priority request is made";
        }
        else if($status=='Waiting')
        {
            $sub="Booking Status Waiting";
            $bdy="Your request is in waiting state..<br> You will receive a mail once if your request in confirmed..!";
        }
        $mail->Subject = $sub;
        $mail->Body = $bdy;
        $mail->AddAddress($value);

        if(!$mail->Send()) {
            //echo "Mailer Error: " . $mail->ErrorInfo;
        } 
        else {
            //echo "Message has been sent";
        }
        /*if($status=="waiting"){
            echo "Temp";
            $mail->Subject = "Booking Status Waiting";
            $mail->Body = "Your request is in waiting state..<br> You will receive a mail once if your request in confirmed..!";
            $mail->AddAddress($value);

            if(!$mail->Send()) {
               echo "Mailer Error: " . $mail->ErrorInfo;
            } 
            else {
                echo "Message has been sent";
            }
        }
        else if($status=="Booked"){
            echo "Temp2";
            $mail->Subject = "Booking Confirmed";
            $mail->Body = "Your booking is confirmed..!<br><br><br>Note:<br>Your booking may be cancelled if any higher priority request is made";
            $mail->AddAddress($value);

            if(!$mail->Send()) {
               echo "Mailer Error: " . $mail->ErrorInfo;
            } 
            else {
                echo "Message has been sent";
            }

        }*/
    }
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

        $city = $_POST['city'];
        $date1 = $_POST['date1'];
        $date2= $_POST['date2'];
        $session = $_POST['session'];
        $cid=$_POST['cid'];
        $uid=$_POST['uid'];
        $status=$_POST['status'];

        $sql="INSERT into booking(cid,uid,sdate,edate,fs,status) values ('$cid','$uid','$date1','$date2','$session','$status')";
        $result = mysqli_query($connection, $sql) or die("error".mysqli_error($connection));
        $output;
        if($result)
        {
            $sqlEmail="SELECT email from users_db where uid like '$uid'";
            $resultEmail=mysqli_query($connection,$sqlEmail);
            while($row=$resultEmail->fetch_assoc())
            {
                $mail=$row['email'];
            }
            sendEmail($mail,$status);
        	$output['error']=false;
        	$output['message']="Please check your mail for Confirmation of booking..!";
        }
        else
        {
        	$output['error']=true;
        	$output['message']="Something went wrong..!";
        }
        echo json_encode($output);


  ?>