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
$submitData["password"]  = addslashes($submitData["password"]);


// connect to the database

$db_host = 'mysql410.blabla';
$db_username = 'agiledev@blabla';
$db_password = 'agilblabla';
$db_name = 'frittblas_se_bla_bla';

$con = mysqli_connect($db_host, $db_username, $db_password, $db_name);

if(mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$query = "INSERT INTO `user` (username, password)
			VALUES ('" . $submitData["username"] . "',
					'" . $submitData["password"] . "' ) ";
	

if (!mysqli_query($con, $query)) {
	
  echo("Error: " . mysqli_error($con));
  
} else {
	
	echo("success");
	
}


mysqli_close($con);

?>
