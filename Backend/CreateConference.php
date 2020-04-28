<?php
	require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

    if(isset($_POST['name']) && isset($_POST['uid']) && isset($_POST['email']) && isset($_POST['phone']) && isset($_POST['addr']) && isset($_POST['seats'])){    
		$name=$_POST['name'];
		$uid=$_POST['uid'];
		$email=$_POST['email'];
		$phone=$_POST['phone'];
		$addr=$_POST['addr'];
		$seats=$_POST['seats'];
        $latitude=$_POST['latitude'];
        $longitude=$_POST['longitude'];

	
    	$query = "INSERT INTO croom_db(	uid,cname,cemail,cphone,caddr,seats,latitude,longitude) VALUES('$uid','$name','$email','$phone','$addr','$seats','$latitude','$longitude')";
    	$output;
    	$result = mysqli_query($connection, $query);
    	//echo mysql_error($connection);
    	
    	if($result)
    	{
    		/*echo "Data saved Successfully..!";*/
    		$output['error']=false;
    		$output['message']="Conference Room added Successfully..!";
    	}
    	else
    	{
    		/*echo "Something went wrong..!";*/
    		$output['error']=true;
    		$output['message']="Some Details are already found in db...!";
    	}
    }
    else
    {
    	$output['error']=true;
    	$output['message']="Please enter all details...!";
    }

    echo json_encode($output);
?>