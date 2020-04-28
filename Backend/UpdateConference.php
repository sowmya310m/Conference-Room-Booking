<?php

        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection

        $db = new DbConnect();
        $connection = $db->connect();


        $cid=$_POST['cid'];
        $name=$_POST['name'];
        $uid=$_POST['uid'];
        $email=$_POST['email'];
        $phone=$_POST['phone'];
        $addr=$_POST['addr'];
        $seats=$_POST['seats'];
        $queryUpdate = "UPDATE croom_db SET uid='$uid',cname='$name',cemail='$email',cphone='$phone',caddr='$addr',seats='$seats' WHERE cid like '$cid' ";
    	//echo $queryUpdate."<br>";
    	$resultUpdate = mysqli_query($connection, $queryUpdate); /*or die("Error".mysqli_error($connection));*/

        $list;
    	if($resultUpdate)
    	{
    		$list['error']=false;
            $list['message']="Update Successfully..!";
    	}
    	else
    	{
    		//echo "Something Went Wrong..!";
            $list['error']=true;
            $list['message']="Something went wrong..!";
    	}

    echo json_encode($list);   	
 ?>