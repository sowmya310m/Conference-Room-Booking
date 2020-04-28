<?php
$conn = mysqli_connect("localhost","root","","hackathon");
$uid = $_POST['uid'];
$password = $_POST['password'];
$qry = "SELECT password from users_db where uid = '$uid'";
$res = mysqli_query($conn,$qry);
$list = array();
if(mysqli_num_rows($res)>0){
	$row = $res->fetch_assoc();
	$list['password']=$row['password'];
}
$password = hash('sha256', $password);
$npassword = $_POST['npassword'];
$npassword1 = hash('sha256', $npassword);
$list1="";
if($password==$list['password']){
	$qry1 = "UPDATE users_db set password = '$npassword1' where uid = '$uid'";
	$res = mysqli_query($conn,$qry1);
	if(!$res){
		$list1['error']=true;
		$list1['message']="Update Failed";
	}
	else{
		$list1['message']="Update Successful";
		$list1['error']=false;
	}
}
else
{
	$list1['error']=true;
	$list1['message']="Current Password is not Correct";
}
echo json_encode($list1);
?>