<?php
$conn = mysqli_connect("localhost","root","","hackathon");
$uid = $_POST['uid'];
$qry = "SELECT * FROM users_db where uid = '$uid'";
$res = mysqli_query($conn,$qry);
if(!$res){
	$list['error'] = "No Users";
}
else{
    $list=array();
    if(mysqli_num_rows($res)>0){
  		while($row = $res->fetch_assoc()){

  			$list['name']=$row['name'];
  			$list['email']=$row['email'];
  			$list['mobile']=$row['mobile'];
  			$list['password']=$row['password'];
  			$list['ld']=$row['ld'];
  			
  		}
  	}
}
echo json_encode($list);
?>