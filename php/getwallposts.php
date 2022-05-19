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

$query = "SELECT CONCAT(username, ';', message, ';', msg_date, ';') AS wallpost FROM wall ORDER BY msg_date DESC LIMIT 50";
$result = mysqli_query($con, $query);
$resultcount = mysqli_num_rows($result);



// print the exercises
while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
	printf("%s", $row["wallpost"]);

if ($resultcount < 1) {
	echo "no wallposts";
}


mysqli_free_result($result);
mysqli_close($con);

?>
