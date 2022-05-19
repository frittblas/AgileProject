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


// run query 1, target weight, always 0 if no registered target weight

$query1 = "SELECT targetweight FROM `user` WHERE `username` = '" . $submitData["username"] . "'";

$result1 = mysqli_query($con, $query1);


// print the target weight of the user.
while ($row1 = mysqli_fetch_array($result1, MYSQLI_ASSOC))
	printf("%s;", $row1["targetweight"]);


// run the second query, weight at different times

$query = "SELECT weight, WeightDate FROM `userweight` WHERE `username` = '" . $submitData["username"] . "'";

$result = mysqli_query($con, $query);
$resultcount = mysqli_num_rows($result);


// print the registered weight at different times.
while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
	printf("%s;%s;", $row["weight"], $row["WeightDate"]);

if ($resultcount < 1) {
	printf("0");
}

mysqli_free_result($result);
mysqli_close($con);

?>
