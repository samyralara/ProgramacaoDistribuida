<<!DOCTYPE html>
<html>
<head>
	<title>Resultado</title>
</head>
<body>
<?php

	$isbn = $_POST['isbn'];
    
    try{
    $soap = new SoapClient("http://samyralara:12973/MiniProjetoWS/GerenciadorWS?wsdl");
    $res2 = $soap->__soapCall("consultar", array("parameters"=>array("isbn"=>$isbn)));
    echo $resul = $res2->return;
    
	}catch(SoapFault $soapE){
         echo $soapE->getMessage();
        }
?>
<br>
<br>
<a href="index.php">Voltar</a>
</body>
</html>
