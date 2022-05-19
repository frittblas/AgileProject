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


// @ before $_GET prevent PHP warning.
$submitData = @$_GET;

// prevent SQL-injection
$submitData["username"] = addslashes($submitData["username"]);
$submitData["password"]  = addslashes($submitData["password"]);


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

$query = "SELECT * FROM `user` WHERE `username` = '" . $submitData["username"] . "' AND `password` = '" . $submitData["password"] . "'";

$result = mysqli_query($con, $query);
$resultcount = mysqli_num_rows($result);

// fetch the result from the query in an array of strings
$row = mysqli_fetch_array($result, MYSQLI_ASSOC);

// print the name of the user (this gets picked up by the app)
printf("%s", $row["username"]);


// if we get no results, the user must not exist or wrong password.
if ($resultcount < 1) {
	echo "incorrect credentials";
}

mysqli_free_result($result);
mysqli_close($con);

?>
