<?php
include 'cn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nombre = $_POST['nombre'];
    $apellido = $_POST['apellido'];
    $direccion = $_POST['direccion'];
    $telefono = $_POST['telefono'];
    $correo = $_POST['correo'];
    $sexo = $_POST['sexo'];
    $contrasena = $_POST['contrasena'];

    // Sentencia de inserción clásica en la tabla usuarios usando la variable $c
    $sql = "INSERT INTO usuarios (nombre, apellido, direccion, telefono, correo, sexo, contrasena) 
            VALUES ('$nombre', '$apellido', '$direccion', '$telefono', '$correo', '$sexo', '$contrasena')";
            
    $ejecutar = mysqli_query($c, $sql);

    if ($ejecutar) {
        echo json_encode(array("success" => true, "message" => "¡Usuario registrado correctamente en AWS!"));
    } else {
        echo json_encode(array("success" => false, "message" => "No se pudo registrar el usuario"));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Método no permitido"));
}
?>
