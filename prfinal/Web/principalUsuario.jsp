<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	window.onload=function iniciar(){
		var loginsesion=<%= request.getSession().getAttribute("login")%>
		if (login=null){
			document.getElementById("sesion.error").display="block";
		}else{
			listaarchivo();
		}
	}
	function listaarchivo(){
		var parametros="accion=listaarchivo&login="+<%= request.getSession().getAttribute("login")%>;
		var cargador = new net.CargadorContenidos("controlador", muestraloginentrar,null,"POST",parametros, "application/x-www-form-urlencoded");
	}
	function muestralistaarchivo(){
		 var respuesta=eval(this.req.response);
	      respuesta=respuesta.trim();
	}
</script>

<title>PrincipalUsuario</title>
</head>
<body>
<select id="listaArchivo" multiple>
</select>
</body>
</html>