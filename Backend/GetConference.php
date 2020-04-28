<?php 
	require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();
        $cid = $_POST['cid'];
    	$queryGetDetails="SELECT * from croom_db WHERE cid like '$cid' ";
    	$resultGetDetails = mysqli_query($connection, $queryGetDetails) or die("Error" . mysqli_error($connection));
    	if(mysqli_num_rows($resultGetDetails)>0){
    		while($row = $resultGetDetails->fetch_assoc()) {
    			// echo "Uid= ".$row['uid']." CName: ".$row['cname']." Email ".$row['cemail']." Phone ".$row['cphone']." Address ".$row['caddr']." Seats ".$row['seats']."<br> ";
    			$list['error']=false;
    			$list['message']="Retrieval Successful";
    			$list['uid']=$row["uid"];
    			$list['cname']=$row["cname"];
    			$list['cemail']=$row["cemail"];
    			$list['cphone']=$row["cphone"];
    			$list['caddr']=$row["caddr"];
    			$list['seats']=$row["seats"];

            }
    	}
     	else{
    		$list['error']=true;
    		$list['message']="No Conference Rooms found";
    	}
    	echo json_encode($list);

?>
    	