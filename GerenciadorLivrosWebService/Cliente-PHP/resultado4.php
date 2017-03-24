<<!DOCTYPE html>
<html>
<head>
	<title>Resultado</title>
</head>
<body>
<?php
    
    try{
    $soap = new SoapClient("http://samyralara:12973/MiniProjetoWS/GerenciadorWS?wsdl");
    $res2 = $soap->__soapCall("excluir", array("parameters"=>array("codigo"=>$_POST['codigo'])));
    $reu = $res2->return;
    if(strstr($reu,"Nao existe")){
    	echo "Livro Excluido com sucesso!<br>";
    }
    echo $reu;
    
	}catch(SoapFault $soapE){
         echo $soapE->getMessage();
        }
?>
<br><a href="index.php">Voltar</a>
</body>
</html>
