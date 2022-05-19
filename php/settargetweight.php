<?php

/*
 *
 *	Template for GET and POST functionality in a Mysql database.
 *
 *	For the agile project
 *
 *	By Hans Strömquist 2022
 *
 */


$submitData = $_REQUEST;

// prevent SQL-injection
$submitData["username"] = addslashes($submitData["username"]);
$submitData["targetweight"] = addslashes($submitData["targetweight"]);


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



// run the query

$query = "UPDATE user
          SET targetweight = '" . $submitData["targetweight"] . "'
          WHERE username = '" . $submitData["username"] . "'";


if (!mysqli_query($con, $query)) {
	
  //echo("Error: " . mysqli_error($con));
  echo("Something went wrong.");
  
} else {
	
	echo("success");
	
}

mysqli_close($con);

?>
