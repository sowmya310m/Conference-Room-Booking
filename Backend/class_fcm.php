<?php

/*
    *developed: Anudeep Duba
    *file name:update_token.php
    *this file sends FCM notification. 
    */
/*$registation_id=$_GET['ID'];
$registation_ids=[];
$registation_ids=array($registation_id);*/
  /*$var = new FCM();
 $var -> send_order_placed($registation_ids,$message);*/
class FCM{

    function send_notification($registation_ids,$message){ 
       

       
          global $result1;
   // print_r($registation_ids);  
define( 'API_ACCESS_KEY', 'AIzaSyCIIvRH6q3tfbDp6Qn7XMY5cPv_vMn8zpI' ); // API access key from Google API's Console

// prep the bundle

$msg = array
(
	'message' 	=> $message,
	'title'		=> 'Conference Room',
	'body'		=> $message
	
	
);
$fields = array
(
	'registration_ids' 	=> $registation_ids,
	'notification'		=> $msg
);
 
$headers = array
(
	'Authorization: key=' . API_ACCESS_KEY,
	'Content-Type: application/json'
);
 
$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );

         $result = curl_exec($ch );
          if ($result === FALSE) {
                 die('Curl failed: ' . curl_error($ch));
             }
        
         curl_close( $ch );
          $result1['FCM'] = $result;
          //echo ($result);
        return(1);
 

    }
}//end of funtion
//end of class
?>