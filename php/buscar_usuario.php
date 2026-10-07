<?php
	include 'cn.php';

	if (isset($_GET['id'])) {
		$id = $_GET['id'];

		$sql = "SELECT * FROM usuarios WHERE id = '$id'";
		$resultado = mysqli_query($c, $sql);

		$usuarios = array();

		while ($fila = mysqli_get_array($resultado)) {
			$usuarios[] = array(
				'nombre' => $fila['nombre'],
				'apellido' => $fila['apellido'],
				'direccion' => $fila['direccion'],
				'telefono' => $fila['telefono'],
				'correo' => $fila['correo'],
				'sexo' => $fila['sexo'],
				'fecha' => $fila['fecha']
			);
		}

		echo json_encode($usuarios);
	} else {
		echo json_encode(array("message" => "No se proporcionó un ID"));
	}

	mysqli_close($c);
?>
