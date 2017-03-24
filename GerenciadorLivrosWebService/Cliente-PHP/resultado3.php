<<!DOCTYPE html>
<html>
<head>
	<title>Resultado</title>
</head>
<body>
<?php

    
    try{
    $soap = new SoapClient("http://samyralara:12973/MiniProjetoWS/GerenciadorWS?wsdl");
    $res2 = $soap->__soapCall("alterar", array("parameters"=>array("codigo"=>$_POST['codigo'],"autor"=>$_POST['autor'], "titulo"=>$_POST['titulo'], "edicao"=>$_POST['edicao'], "editora"=>$_POST['editora'], "isbn"=>$_POST['isbn'])));
    echo $res2->return;

	}catch(SoapFault $soapE){
         echo $soapE->getMessage();
        }
?>
<br><a href="index.php">Voltar</a>


</body>
</html>
