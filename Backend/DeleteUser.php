<?php
require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $conn = $db->connect();
$uid = $_POST['uid'];
$qry = "SELECT cid from croom_db where uid = '$uid'";
$res = mysqli_query($conn,$qry);
$list = array();
if(mysqli_num_rows($res)>0){
	while($row = $res->fetch_assoc()){
  		array_push($list, array(
  			'cid' => $row['cid'])
		);
	}
}
$i=mysqli_num_rows($res);
while ($i!=0) {
	$qry2 = "DELETE from croom_db where uid like '$list[0]'";
	$res2 = mysqli_query($conn,$qry2);
	$i = $i-1;
}

$qry3 = "DELETE from users_db where uid = '$uid'";
$res3 = mysqli_query($conn,$qry3);
$list;
if($res3)
{
	$list['error']=false;
	$list['message']="Successfully Deleted..!";
}
else
{
	$list['error']=true;
	$list['message']="Something went wrong Please try again laters..!";
}
echo json_encode($list);

?>