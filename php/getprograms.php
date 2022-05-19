<?php

/*
 *
 *	Template for GET and POST functionality in a Mysql database.
 *
 *	By Hans Strömquist 2022
 *
 */

$submitData = $_GET;


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

$query = "SELECT DISTINCT name FROM program;";
$result = mysqli_query($con, $query);
$resultcount = mysqli_num_rows($result);

while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
	printf("%s;", $row["name"]);

if ($resultcount < 1) {
	echo "no programs";
}

mysqli_free_result($result);
mysqli_close($con);

?>
