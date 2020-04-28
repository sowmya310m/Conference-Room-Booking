<?php
  
  require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

	  $role=$_POST['role'];

    $sql="SELECT * from users_db where role like '$role' ";
  	$result = mysqli_query($connection, $sql);

  	$list1 ;
    $kis=array();
  	if(mysqli_num_rows($result)>0){
  		while($row = $result->fetch_assoc()){
  			$kis[]=$row;
  		}
      $list1["error"]=false;
      $list1[$role]=$kis;
  	}
  	else
  	{

  	}

  	echo json_encode($list1);
?>