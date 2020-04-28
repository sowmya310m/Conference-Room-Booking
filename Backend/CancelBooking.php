<?php
	 require_once dirname(__FILE__) . '/class_fcm.php';
     require_once dirname(__FILE__) . '/DbConnect.php';
     $db = new DbConnect();
     $connection = $db->connect();

     function isRoomAvailable($cid,$date1,$date2,$session1){
            # code...
            //echo $cid."...".$date1."-".$date2."-".$session1."<br>";
            $db = new DbConnect();
            $connection = $db->connect();
            $flag=0;
            $sql="SELECT * from booking where '$date1' between sdate and edate AND cid like '$cid' and status like 'Booked' ";
            //echo $sql."<br>";
            $result = mysqli_query($connection, $sql);

            if(mysqli_num_rows($result)>0){
            //echo "TEEEE";
                while ($row = $result->fetch_assoc()) {
                    # code...
                    //echo "string".$row['edate'].$row['sdate'].$row['fs'].$session1."<br>";
                    if(strtotime($row['sdate'])!=strtotime($row['edate'])){
                        //echo "Booking cannot be done..!";
                        $flag=1;
                        return false;
                    }
                    else if($row['fs']==$session1 || $row['fs']=='fullday' || $session1 =='fullday')
                    {
                        //echo "Booking can be done..!";

                        //echo "Session already booked..!";
                        $flag=1;
                        return false;
                    }
                }
            }

            if($flag!=1){
                //echo "2";
                $sql="SELECT * from booking where '$date2' between sdate and edate AND cid like '$cid' and status like 'Booked'";
                //echo $sql;
                $result = mysqli_query($connection, $sql);

                if(mysqli_num_rows($result)>0){
                    while ($row = $result->fetch_assoc()) {
                        # code...
                        //echo "string".$row['edate']."<br>";
                        if($row['sdate']!=$row['edate']){
                            //echo "Booking cannot be done..!";
                            $flag=2;
                            return false;
                        }
                        else if($row['fs']==$session1 || $row['fs']=='fullday' || $session1 =='fullday')
                        {
                            //echo "Booking can be done..!";
                            //echo "<br>".$row['fs']."<br>";
                            //echo "Session already booked..!";
                            $flag=2;
                            return false;
                        }
                    }
                }
            }

            if($flag!=2 && $flag!=1){
                //echo "3";
                $sql="SELECT * from booking where sdate between '$date1' and '$date2' AND cid like '$cid' and status like 'Booked'";
                //echo $sql;
                $result = mysqli_query($connection, $sql);
                if(mysqli_num_rows($result)>0){
                    while ($row = $result->fetch_assoc()){
                        # code...
                        //echo "string".$row['edate']."<br>";
                        if($row['sdate']!=$row['edate']){
                            //echo "Booking cannot be done..!";
                            $flag=3;
                            return false;
                        }
                        else if($row['fs']==$session1 || $row['fs']=='fullday' || $session1 =='fullday'){
                            //echo "Booking can be done..!";
                            //echo "<br>".$row['fs']."<br>";
                            //echo "Session already booked..!";
                            $flag=3;
                            return false;
                        }
                    }
                }
            }

            if($flag!=2 && $flag!=1 && $flag!=3){
                //echo "4";
                $sql="SELECT * from booking where edate between '$date1' and '$date2' AND cid like '$cid' and status like 'Booked'";
                //echo $sql;
                $result = mysqli_query($connection, $sql);
                if(mysqli_num_rows($result)>0){
                    while ($row = $result->fetch_assoc()) {
                        # code...
                        //echo "string".$row['edate']."<br>";
                        if($row['sdate']!=$row['edate']){
                            //echo "Booking cannot be done..!";
                            $flag=4;
                            return false;
                        }
                        else if($row['fs']==$session1 || $row['fs']=='fullday' || $session1 =='fullday'){
                            //echo "Booking can be done..!";
                            //echo "<br>".$row['fs']."<br>";
                            //echo "Session already booked..!";
                            $flag=4;
                            return false;
                        }
                    }
                }
            }

            if($flag==0){
                /*echo "Booking can be done..!";
                $sql="INSERT into booking (uid,cid,sdate,edate,fs,location,status) values ('$uid','$cid','$date1','$date2','$session1','$location','Booked') ";
                //echo $sql;
                $result = mysqli_query($connection, $sql);*/
                return true;
            }
        }

        $bid=$_POST['id'];
        $sq="UPDATE booking SET status = 'cancelled' where id = $bid";
        $result = mysqli_query($connection,$sq) or die("Error in Selecting" . mysqli_error($connection));
        $cids="SELECT cid from booking where id='$bid'";
        //echo "test";
       	$rcid = mysqli_query($connection, $cids);
       	$cid=mysqli_fetch_assoc($rcid) or die("Error".mysqli_error($connection));
       // echo "test";
       	$list;
       	if($cid){
       		$list['error']=false;
    		$list['message']="Cancellation Done..!";
       	//echo json_encode($cid);
       	$val=$cid['cid'];
       	//echo $val;
        $wait="SELECT id from booking where cid=$val and status like 'Waiting' ";
       	$resultw = mysqli_query($connection, $wait) or die("Error".mysqli_error($connection));
       	$data=array();
    	if($resultw)
   		{
            while($row =mysqli_fetch_assoc($resultw))
    		{
    			$val=$row['id'];
      			$waiting="SELECT * from booking where id like '$val'";
      			$waitres = mysqli_query($connection,$waiting);
            $rows=mysqli_fetch_assoc($waitres);
      			$cid = $rows['cid'];
      			$date1 = $rows['sdate'];
      			$date2 = $rows['edate'];
      			$session= $rows['fs'];
      			//echo "Entered loop";
      			if(isRoomAvailable($cid,$date1,$date2,$session))
       			 {

       			 	//echo "Successful";
       			 	$abc=$rows['uid'];
       			 	$sqlGetToken="SELECT token from users_db where uid like '$abc' ";
              //echo $sqlGetToken;
       			 	$resultGetToken = mysqli_query($connection, $sqlGetToken);
       			 	$rowToken=mysqli_fetch_assoc($resultGetToken);
              //echo $rowToken['token'];
       			 	array_push($data, $rowToken['token']);
              



       			 }
       		}
       		$message="The room you tried for booking is available now..If you wish to book please try again booking";
       		$var=new FCM();
       		$var->send_notification($data,$message);

       	}
       	else
       	{
       		$list['error']=true;
    		$list['message']="Something went wrong..!";
       	}
echo json_encode($list);  
       }
       



   ?>