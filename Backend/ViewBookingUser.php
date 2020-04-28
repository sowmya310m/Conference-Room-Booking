<?php
	
	require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

    $uid=$_POST['uid'];

    $sql="SELECT * from booking where uid like '$uid' ";
  	$result = mysqli_query($connection, $sql);

  	
    $kis=array();
  	if(mysqli_num_rows($result)>0){
  		while($row = $result->fetch_assoc()){
  			/*$output['cid']=$row['cid'];
  			$output['uid']=$row['uid'];
  			$output['cname']=$row['cname'];
  			$output['seats']=$row['seats'];
  			$output['caddr']=$row['caddr'];
  			$output['cphone']=$row['cphone'];
  			$output['cemail']=$row['cemail'];*/

  			//$list1 = $row;
        	//$id=$row['cphone'];

        	$kis[]=$row;


  			
  		}
      //echo json_encode($kis);
      $list1['bookings']=$kis;
      $list1['error']=false;
  	}
  	else
  	{
  		$list1['error']=true;
  		$list1['message']="No bookings found..!";
  	}

  	echo json_encode($list1);
  ?>