<?php

$json = file_get_contents('https://xkcd.com/info.0.json');

// true returns json as array, false return json as object
$data = json_decode( $json, true);

// PHP_EOL prints an 'ENTER' or \n after the url of the image
echo $data['img'].PHP_EOL;