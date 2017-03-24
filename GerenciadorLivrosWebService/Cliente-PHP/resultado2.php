<<!DOCTYPE html>
<html>
<head>
	<title>Resultado</title>
</head>
<body>
<?php

    
    try{
    $soap = new SoapClient("http://samyralara:12973/MiniProjetoWS/GerenciadorWS?wsdl");
    $res2 = $soap->__soapCall("cadastrar", array("parameters"=>array("isbn"=>$_POST['isbn'], "titulo"=>$_POST['titulo'], "editora"=>$_POST['editora'], "edicao"=>$_POST['edicao'], "autor"=>$_POST['autor'])));
    //print_r($res2);
    foreach ($res2 as $value) {
        echo "$value";
    }

	}catch(SoapFault $soapE){
         echo $soapE->getMessage();
        }
?>
<br><a href="index.php">Voltar</a>


</body>
</html>
