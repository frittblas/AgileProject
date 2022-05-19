<?php

/*
 *
 *	Template for GET and POST functionality in a Mysql database.
 *
 *	By Hans Strömquist 2022
 *
 */

$submitData = $_REQUEST;

$submitData["username"] = addslashes($submitData["username"]);
$submitData["message"]  = addslashes($submitData["program_name"]);


// connect to the database

$db_host = 'mysqlbla.blabla';
$db_username = 'agiledev@blabla';
$db_password = 'agilblabla';
$db_name = 'frittblabla';

$con = mysqli_connect($db_host, $db_username, $db_password, $db_name);

if(mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}


$query = "INSERT INTO userprogram (user_program_count, username,  program_name, exercise_id, exercise_weight, STATUS, DATE)
		SELECT (SELECT MAX(user_program_count)+1 FROM userprogram up WHERE up.username = '" . $submitData["username"] . "'),'" . $submitData["username"] . "', NAME, exercise_id, 0, 'In progress', SYSDATE()
		 FROM program WHERE NAME = '" . $submitData["program_name"] . "'";
$query2 = "SELECT MAX(user_program_count)+1 as user_program_count FROM userprogram up WHERE up.username = '" . $submitData["username"] . "'";


$result = mysqli_query($con, $query2);
$row = mysqli_fetch_array($result, MYSQLI_ASSOC);


if (!mysqli_query($con, $query)) {
	
  echo("error");
  
} else {
	
	echo $row["user_program_count"];
	
}


mysqli_close($con);

?>
