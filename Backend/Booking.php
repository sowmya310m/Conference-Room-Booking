<?php
    
     //open connection to mysql db
 require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

    function distance($lat1, $lon1, $lat2, $lon2, $unit) {

        $theta = $lon1 - $lon2;
        $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
        $dist = acos($dist);
        $dist = rad2deg($dist);
        $miles = $dist * 60 * 1.1515;
        $unit = strtoupper($unit);

        if ($unit == "K") {
            return ($miles * 1.609344);
        } else if ($unit == "N") {
            return ($miles * 0.8684);
        } else {
            return $miles;
        }
    }      

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

    if (isset($_POST['city']) && isset($_POST['date1']) && isset($_POST['date2']) && isset($_POST['session']) && $_POST['city'] != '' && $_POST['date1'] != '' && $_POST['date2'] != '' && $_POST['session'] != '') {
        ob_start();
        $city = $_POST['city'];
        $date1 = $_POST['date1'];
        $date2= $_POST['date2'];
        $session = $_POST['session'];
        $latitude= $_POST['latitude'];
        $longitude=$_POST['longitude'];
        /*echo $city;
        echo $date1;
        echo $date2;
        echo $session;*/
        $sql="SELECT cid from croom_db where uid in(SELECT uid from users_db where role='admin' and ld='$city')";
        //$sql="SELECT uid from users_db where role='admin' and ld= '$city'";
        //echo $sql;
        $result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

        //create an array
        $list = array();

        $list;
        while($row =mysqli_fetch_assoc($result))
        {
            $val=$row['cid'];
           if(isRoomAvailable($val,$date1,$date2,$session))
           {
                //echo $row['cid']."<br>";
                //$val=$row['cid'];
                $details="SELECT * from croom_db where cid like '$val'";
                $resultDetails=mysqli_query($connection,$details);
                while ($rowDetails =mysqli_fetch_assoc($resultDetails)) {
                    # code...
                    $rowDetails['distance']=distance($latitude,$longitude,$rowDetails['latitude'],$rowDetails['longitude'],"K");
                    $rowDetails['status']="Available";
                    $list[] = $rowDetails;
                    
                }
                
           }
           else
           {
                //echo "Cannot book";
                $details="SELECT * from croom_db where cid like '$val'";
                $resultDetails=mysqli_query($connection,$details);
                while ($rowDetails =mysqli_fetch_assoc($resultDetails)) {
                    # code...
                    $rowDetails['distance']=distance($latitude,$longitude,$rowDetails['latitude'],$rowDetails['longitude'],"K");
                    $rowDetails['status']="Unavailable";
                    $list[] = $rowDetails;
                    
                }
           }
        }

        //echo json_encode($list);
        if (empty($list))
        {
            $finalResult['error']=true;
            $finalResult['message']="No Conference rooms are available for booking..!";
        }
        else{
            $finalResult['error']=false;
            $finalResult['halls']=$list;   
        }
        ob_end_clean();

        echo json_encode($finalResult);

    //echo json_encode($list);
  /*  if ($date1 ==$date2) {
        $ct = "AND branch= \"$cn\"";
    }else{
        $ct ="";
    }

    if ($jn != "ALL") {
        $jt = "AND job= \"$jn\"";
    }else{
        $jt ="";
    }

    if ($skeyn != "NONE") {
        $skt = "AND (branch LIKE '%".$skeyn."%' OR email LIKE '%".$skeyn."%' OR landline LIKE '%".$skeyn."%' OR mobile LIKE '%".$skeyn."%' OR job LIKE '%".$skeyn."%')";
    }else{
        $skt ="";
    }


    $sql ="";

    if( ($cn == "ALL" ) && ($jn == "ALL") && ($skeyn == "NONE")){
        // $sql = "select * from ".$catn;
                // $sql = "select * from TSSPDCL";
         $sql = "select * from ".$catn;

    }else{
        // $sql = "select * from \"$catn\" where 1 "."".$ct.$jt.$skt;
        $sql = "select * from ".$catn." where (1) "."".$ct.$jt.$skt;

    }

    //fetch table rows from mysql db
//$sql = "select * from all_data where job_cat= \"$catn\" "."".$ct.$jt.$skt;
    $result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

    //create an array
    $list = array();
    $i=0;

    while(($row =mysqli_fetch_assoc($result)) && ($i<50))
    {
        $list[] = $row;
        $i++;
    }

    $response = array();
    $response["error"] = false;

    if($list != NULL){
        $response["message"] = "Successfully Retrived Data!";
        $response["data"] = $list;
    }else {
        $response["error"] = true;
        $response["message"] = "No Data";
    }
    echo json_encode($response);

}
    

    //close the db connection
    mysqli_close($connection); */
}
?>

