<?php
/*
 * To Run this example of api, you need to run router.php file with the next command:
 * $ php -S localhost:8000 router.php
 * 
 * To make a request to the api, first you need to get a token... to do this, run the
 * token server executing the next command:
 * $ php -S localhost:8001 token.php
 * $ curl http://localhost:8001 -X 'POST' -H 'X-Client-Id: 1' -H 'X-Secret:SuperSecreto!'
 * 
 * Then make a request to the API, writing the next command:
 * $ curl http://localhost:8000/books -H 'X-Token: <token0000001>'
 */
/*
// Authentication using Access Tokens
if ( !array_key_exists('HTTP_X_TOKEN', $_SERVER) ) {
    http_response_code(400);

    die;
}

$url = 'http://localhost:8001';

$ch = curl_init( $url );
curl_setopt(
    $ch,
    CURLOPT_HTTPHEADER,
    [
        "X-Token: {$_SERVER['HTTP_X_TOKEN']}"
    ]
);
// we need to view the response, so set RETURNTRANSFER
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$ret = curl_exec( $ch );

if( $ret !== 'true') {
    http_response_code(400);

    die;
}
*/
// here we are defined the allowed resources
$allowedResourceTypes = [
    'books',
    'authors',
    'genres'
];

// define the resources
$books = [
    1 => [
        'titulo' => 'Lo que el viento se llevo',
        'id_autor' => 2,
        'id_genero' => 2
    ],
    2 => [
        'titulo' => 'La Iliada',
        'id_autor' => 1,
        'id_genero' => 1
    ],
    3 => [
        'titulo' => 'La Odisea',
        'id_autor' => 1,
        'id_genero' => 1
    ]
];

// we validate that the resource is available
$resourceType = $_GET['resource_type'];

if ( !in_array( $resourceType, $allowedResourceTypes ) ) {
    http_response_code(400);

    die; // die by default, return http 200... things of php
}

header('Content-Type: application/json');

// we get the id of the resource
$resourceId = array_key_exists('resource_id', $_GET) ? $_GET['resource_id'] : '';

// we generate the answer assuming that the order is correct
switch ( strtoupper($_SERVER['REQUEST_METHOD'])) {
    case 'GET':
        if ( empty( $resourceId ) ) {
            echo json_encode( $books );
        } elseif ( array_key_exists($resourceId, $books) ) {
            echo json_encode( $books[ $resourceId ] );
        } else {
            http_response_code(404);
        }
        break;

    case 'POST':
        $json = file_get_contents( 'php://input' );

        // Normally this step in real world scenarios... store
        // and save the registry into a database.
        $books[] = json_decode( $json, true );

        echo '{ "status": "Success", "message": "Book added with ID: ' . array_keys( $books )[ count($books) - 1 ] . '" }';
        break;

    case 'PUT':
        if ( !empty( $resourceId ) && array_key_exists( $resourceId, $books )) {
            $json = file_get_contents( 'php://input' );
            $books[ $resourceId ] = json_decode( $json, true );
            echo json_encode( $books );
        }
        break;

    case 'DELETE':
        if ( !empty( $resourceId ) && array_key_exists( $resourceId, $books )) {
            unset( $books[ $resourceId ] );
        }
        echo json_encode( $books );
        break;
}