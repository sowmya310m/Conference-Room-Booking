<?php
$conn = mysqli_connect("localhost","root","","hackathon");
$uid = $_POST['uid'];
$email=$_POST['email'];
$name=$_POST['name'];
$mobile=$_POST['mobile'];
$ld=$_POST['ld'];
$qry = "UPDATE users_db set email = '$email' , name = '$name' , mobile = '$mobile' , ld = '$ld' where uid = '$uid'";
$res = mysqli_query($conn,$qry) or die("error".mysqli_error($conn));
$list;
if(!$res){
	$list['error']=true;
	$list['message'] = "Not Updated";
}
else{
	$list['error']=false;
	$list['message'] = "Updated Successfully..!";

}
echo json_encode($list);
?>
<!-- SuperAdmin -->