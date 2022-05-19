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
$submitData["userprogramcount"]  = addslashes($submitData["userprogramcount"]);
$submitData["exercise_name"]  = addslashes($submitData["exercise_name"]);
$submitData["exercise_weight"]  = addslashes($submitData["exercise_weight"]);


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

$query = "UPDATE userprogram
		SET exercise_weight = '" . $submitData["exercise_weight"] . "', STATUS = 'Finished'
		WHERE exercise_id IN (SELECT e.eid FROM exercises e WHERE e.NAME = '" . $submitData["exercise_name"] . "')
		AND username = '" . $submitData["username"] . "'
		AND user_program_count = '" . $submitData["userprogramcount"] . "'";



if (!mysqli_query($con, $query)) {
	
  //echo("Error: " . mysqli_error($con));
  echo("Something went wrong.");
  
} else {
	
	echo("success");
	
}


mysqli_close($con);

?>
