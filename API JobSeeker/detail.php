<?php
require_once('connection.php');

//empty() karena isset() tidak bisa pada 000webshost
if (empty($_GET)) {
    $query = mysqli_query($mysqli, "SELECT * FROM tbdetail");

    $result = array();
    while ($row = mysqli_fetch_array($query)) {
        array_push($result, array(
            'idKerja' => $row['idKerja'],
            'deskripsi' => $row['deskripsi'],
            'skillReq' => $row['skillReq']
        ));
    }
    
    echo json_encode(
        array('result' => $result)
    );
} 
else {
    $idKerja = $_GET['idKerja'];
    $query = mysqli_query($mysqli, "SELECT * FROM tbdetail WHERE idKerja = '$idKerja'");

    $result = array();
    while ($row = $query->fetch_assoc()) {
        $result = array(
            'idKerja' => $row['idKerja'],
            'deskripsi' => $row['deskripsi'],
            'skillReq' => $row['skillReq']
        );
    }
    
    echo json_encode($result);
}
