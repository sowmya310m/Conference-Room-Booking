<?php
	
	require_once dirname(__FILE__) . '/DbConnect.php';
  		// opening db connection
  		$db = new DbConnect();
  		$connection = $db->connect();
  	$adminId=$_POST['adminId'];
  	$userId=$_POST['userId'];
  	$priority=$_POST['priority'];
  	$list;
  	$sqlCheckAdmin="SELECT role from users_db where uid like '$adminId' ";
  	$resultCheckAdmin=mysqli_query( $connection ,$sqlCheckAdmin);
  	if($resultCheckAdmin)
  	{
  		if(mysqli_num_rows($resultCheckAdmin)==1)
  		{
  			$row=mysqli_fetch_array($resultCheckAdmin);
  			if($row['role']=='admin')
  			{
  				$sqlUpdate="UPDATE users_db set verified = '$adminId' , priority = '$priority' where uid = '$userId'";
  				$resultUpdate=mysqli_query( $connection ,$sqlUpdate);
  				if($resultUpdate)
  				{
  					$list['error']=false;
  					$list['message']="Permission Granted Successfully..!";
  				}
  				else
  				{
  					$list['error']=true;
  					$list['message']="User not found..!";	
  				}
  			}
  			else
  			{
  				$list['error']=true;
  				$list['message']="Please login with Admin account to give Permission..!";
  			}
  		}
  		else
  		{
  			$list['error']=true;
  			$list['message']="Error..!";
  		}
  	}
  	else
  	{
  		$list['error']=true;
  		$list['message']="Admin doesn't exist..!";
  	}
  	echo json_encode($list);
  ?>