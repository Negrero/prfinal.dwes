<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript" src="js/coreAJAX.js"></script>
<style type="text/css">
#izq {
	float:left;
	width:30%;
}
#der {
	float:left;
	width:60%;
	padding-left: 10px;
}
#contenido {
	margin:auto;
	width:900px;
}
</style>
<script type="text/javascript">
function accion(id){
		
		botones=document.getElementsByName("submenu");
		for(var i=0;i<botones.length;i++){
			botones[i].style.display="none";
		}
		document.getElementById(id.name).style.display="block";
	}
	window.onload=function iniciar(){
		
		var loginsesion="<%= request.getSession().getAttribute("login")%>";
		if (loginsesion=="null"){
			document.getElementById("sesion.error").style.display="block";
			document.getElementById("contenido").style.display="none";
		}else{
			document.getElementById("sesion.error").style.display="none";
			document.getElementById("contenido").style.display="block";
		
			listaarchivo();
		}
	}
	function listaarchivo(){
		var parametros="accion=listaarchivo&login="+"<%= request.getSession().getAttribute("login")%>";
		var cargador = new net.CargadorContenidos("controlador", muestralistaarchivo,null,"POST",parametros, "application/x-www-form-urlencoded");
	}
	function muestralistaarchivo(){
		var listaconductor=document.getElementById("listaArchivoconductor");
		var listavehiculo=document.getElementById("listaArchivovehiculo");
		//lista.innerHTML=" ";
		 var respuesta=eval(this.req.responseText);
	      //respuesta=respuesta.trim();
	      
	      for(var i=0;i<respuesta.length;i++){
		      
		      	opcion=document.createElement("option");
		      	opcion.value=respuesta[i].nombre;
		      	texto=document.createTextNode(respuesta[i].nombre);
		      	opcion.appendChild(texto);
		      	alert(respuesta[i].tipo);
		      	if(respuesta[i].tipo=="C"){
		      		listaconductor.appendChild(opcion);
		      	}else{
		      		listavehiculo.appendChild(opcion);
		    
		      	
		      	}
	      	
	      }

	}
	
	function selectArchivo(select){
		lista=select
		for (var i=0;i<lista.options.length;i++){
			if (lista.options[i].selected)
				alert(lista[i].value);
		}
	}
	
	
	function seleccionado(){ 

		var archivos = document.getElementById("archivos");//Damos el valor del input tipo file
		var archivo = archivos.files; //Obtenemos el valor del input (los arcchivos) en modo de arreglo

		var data = new FormData();

		//Como no sabemos cuantos archivos subira el usuario, iteramos la variable y al
		//objeto de FormData con el metodo "append" le pasamos calve/valor, usamos el indice "i" para
		//que no se repita, si no lo usamos solo tendra el valor de la ultima iteración
		for(var i=0; i<archivo.length; i++){
			
		   data.append('archivo'+i,archivo[i]);
		}
		//url, funcion, funcionError, metodo, parametros, contentType
		var cargador = new net.CargadorContenidos("controlador?accion=grabararchivosusuario", muestraarchivo,null,"POST",data, false);
		
		
		}
		function muestraarchivo(){
			var respuesta=this.req.responseText;
		      respuesta=respuesta.trim();
		      
		      listaarchivo();
			}
</script>

<title>PrincipalUsuario</title>
</head>
<body>
<div id="sesion.error" style="display:none"> <span style="color:red" >sesion invalida, inicie sesion  <a href="index.jsp">aqui</a></span></div>
<div id="contenido" style="display:block">
	<h1>Sesion iniciada por :<%=request.getSession().getAttribute("login") %></h1>
	<div id="izq">
		<fieldset>
			<legend>Archivos de conductor</legend>
			<select id="listaArchivoconductor" name="listaArchivoconductor[]" multiple  onchange="selectArchivo(this)" style="height: 197px; width: 248px; ">	</select>
		</fieldset>
		<fieldset>
			<legend>Archivos de vehiculo</legend>
			<select id="listaArchivovehiculo" name="listaArchivovehiculo[]" multiple  onchange="selectArchivo(this)" style="height: 197px; width: 248px; ">	</select>
		</fieldset>
		
		
		<br>
		<button onclick="accion(this)" name="subir">Subir</button><br>
		<button onclick="accion(this)" name="interpretar">Interpretar</button>
		<div id="subir"  style="display:none" name="submenu">
			        
				Subir archivo:<input id="archivos" type="file" name="archivos[]" multiple="multiple" onchange="seleccionado();"  accept="application/TGD"/>
				<input type="submit" value="subir archivos"/>
			
		</div>
		<div id="interpretar"  style="display:none" name="submenu">
			<div id="interpretados"></div>
		</div>
	</div>
	<div id="der">
	arhcivo
	</div>
</div>

</body>
</html>