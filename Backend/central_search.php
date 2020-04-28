<?php
     //open connection to mysql db
 require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $connection = $db->connect();

    if(isset($_POST['search_key'])){
        $sql = "SELECT * from croom_db where role='admin' ";

    }


    //fetch table rows from mysql db
    $sql = "SELECT DISTINCT ld from users_db where role='admin' ";
    $result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

    //create an array
    $list = array();


    while($row =mysqli_fetch_assoc($result))
    {
        $list[] = $row;
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

    

    //close the db connection
    mysqli_close($connection);
?>