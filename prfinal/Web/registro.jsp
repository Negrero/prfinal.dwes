<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
<title>Registro de usuario</title>


<script type="text/javascript">
function ValidaCampos(formulario){	
	var lista=document.getElementsByTagName("input");
	// recorre los inputs del formulario que tengan el atributo expresion
	var ok=true;
	var variable;
	
	for (var i=0;i<lista.length;i++ ){
		if(lista[i].title!=null && lista[i].title!=""  ){		
			if(!validar(lista[i])){
				ok=false;
			}
		}
	}
	
	return ok;	
}

function validar(elemento){	
	
	 //id de la etiqueta de error ejemplo id="login.error" donde login es <input name="login" ...
	iderror=elemento.name+".error";
	var expresion = eval(elemento.title);
	
	if (expresion.test(elemento.value)==false){
		document.getElementById(iderror).style.display="block";
		elemento.style.border="thin dotted red";
		return false;
	}
	document.getElementById(iderror).style.display="none";
	elemento.style.border=""; 
	return true;
	
}
</script>
</head>
<body>
<center>
	<form accion="controlador?accion=registrar"   onsubmit="return ValidaCampos(this)" method="post" ">          
              Login  :<input type="text" name="login" value="" size="12" maxlength="12"  onchange="validar(this)" title="/[a-zA-Z\d_]{8,12}$/ " />
              <span id="login.error" style="display:none;color:red" > entre 8-12 caracteres y al menos una mayuscula,minuscula y un digito</span><br>
              Clave :<input type="password" name="clave" value="" size="12" maxlength="12" onchange="validar(this)" title="/[a-zA-Z\d_]{8,12}$/"/>
              <span id="clave.error" style="display:none;color:red" > entre 8-12 caracteres y al menos una mayuscula,minuscula y un digito</span><br>
              Nombre :<input type="text" name="nombre" value="" size="20" maxlength="15" onchange="validar(this)" title="/[\w]{1,15}$/"/>
              <span id="nombre.error" style="display:none;color:red">solo letras,numeros y subrayado</span><br>
              <input type="submit" value="Registrar" name="botEnviar"/>
	</form>
</center>
</body> 
</html>