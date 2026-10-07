<?php
include 'cn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $correo = $_POST['username']; 
    $contrasena = $_POST['password'];

    $sql = "SELECT * FROM usuarios WHERE correo = '$correo' AND contrasena = '$contrasena'";
    $resultado = mysqli_query($c, $sql);

    if (mysqli_num_rows($resultado) > 0) {
        echo json_encode(array("success" => true, "message" => "¡Bienvenido al sistema!"));
    } else {
        echo json_encode(array("success" => false, "message" => "Usuario o contraseña incorrectos"));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Método no permitido"));
}
?>
