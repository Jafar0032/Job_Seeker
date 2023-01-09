<?php
require_once('connection.php');

//empty() karena isset() tidak bisa pada 000webshost
if (empty($_GET)) {
    $query = mysqli_query($mysqli, "SELECT * FROM tbkerja WHERE namaKategori LIKE 'Marketing'");

    $result = array();
    while ($row = mysqli_fetch_array($query)) {
        array_push($result, array(
            'idKerja' => $row['idKerja'],
            'namaPerusahaan' => $row['namaPerusahaan'],
            'logoPerusahaan' => $row['logoPerusahaan'],
            'alamat' => $row['alamat'],
            'namaKategori' => $row['namaKategori'],
            'jobDesk' => $row['jobDesk'],
            'gaji' => $row['gaji'],
            'fulltime' => $row['fulltime']
        ));
    }
    
    echo json_encode(
        array('result' => $result)
    );
    
} 
else {
    $jobDesk = $_GET['jobDesk'];
    $query = mysqli_query($mysqli, "SELECT * FROM tbkerja WHERE jobDesk LIKE '%$jobDesk%' AND namaKategori LIKE 'Marketing'");

    $result = array();
    while ($row = mysqli_fetch_array($query)) {
        array_push($result, array(
            'idKerja' => $row['idKerja'],
            'namaPerusahaan' => $row['namaPerusahaan'],
            'logoPerusahaan' => $row['logoPerusahaan'],
            'alamat' => $row['alamat'],
            'namaKategori' => $row['namaKategori'],
            'jobDesk' => $row['jobDesk'],
            'gaji' => $row['gaji'],
            'fulltime' => $row['fulltime']
        ));
    }
    
    echo json_encode(
        array('result' => $result)
    );
}
