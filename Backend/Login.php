<?php
  require_once dirname(__FILE__) . '/DbConnect.php';
  // opening db connection
  $db = new DbConnect();
  $connection = $db->connect();

  $username=$_POST['username'];
  $password=$_POST['password'];
  $token=$_POST['token'];
  //$_SESSION['user'] = $username;
  $password = hash('sha256', $password);
  $sql="SELECT * FROM users_db WHERE email='$username' and password='$password'";
  $result=mysqli_query( $connection ,$sql)or die("Error" . mysqli_error($connection));

  $list;
  if (mysqli_num_rows($result) == 1) {
    $row=mysqli_fetch_array($result);
    $role=$row['role'];
    $mobile=$row['mobile'];
    $ld=$row['ld'];
    $name=$row['name'];
    $pwd=$row['password'];
    //echo $pwd;
    //echo "Logged in Successfully..!";
    $list['error']=false;
    $list['message']="Login Successful";
    $list['role']=$row["role"];
    $list['mobile']=$row["mobile"];
    $list['ld']=$row["ld"];
    $list['name']=$row["name"];
    $list['uid']=$row['uid'];
    $val=$list['uid'];
    $sqlToken="UPDATE users_db set token='$token' where uid like '$val'";
    $resToken=mysqli_query( $connection ,$sqlToken)or die("Error" . mysqli_error($connection));
  } 
  else
  {
    //echo "Wrong mail id or password";
    $list['error']=true;
    $list['message']="Wrong mail id or password";
  }

  echo json_encode($list);


//}
?>