<?php


header('Content-Type: text/plain');



$ch1=curl_init();
curl_setopt($ch1,CURLOPT_URL,"https://script.google.com/macros/s/AKfycbw3hs0kxKMBJTZTObqJEo6pwI1MwYQvI1LYxvpiAYmHV4UcqgJz/exec?".$_SERVER['QUERY_STRING']);
curl_setopt($ch1,CURLOPT_FOLLOWLOCATION,TRUE);
curl_setopt($ch1,CURLOPT_RETURNTRANSFER,TRUE);
$res1=curl_exec($ch1);
echo $res1;

?>