<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ page import="ajaxmvc.modelo.beans.*"%>
 <jsp:useBean id="modelo" scope="session" class="ajaxmvc.modelo.beans.ModeloAjax"></jsp:useBean>
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
	width:1200px;

}
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	float:left;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

table thead { display:block; }
#informacion
</style>
<script type="text/javascript" >
var archivos;
function accion(id){
		botones=document.getElementsByName("submenu");
		for(var i=0;i<botones.length;i++){
			botones[i].style.display="none";
		}
		document.getElementById(id.name).style.display="block";
	}
	
	
	// 
	function muestralistaarchivo(){
		var listaconductor=document.getElementById("listaArchivoconductor");
		var listavehiculo=document.getElementById("listaArchivovehiculo");
		listaconductor.innerHTML="";
		listavehiculo.innerHTML="";
		//lista.innerHTML=" ";
		 var respuesta=archivos;
	      //respuesta=respuesta.trim();
	      
	      for(var i=0;i<respuesta.length;i++){
		      if (respuesta[i].mensaje=="true"){
		      	opcion=document.createElement("option");
		      	opcion.value=respuesta[i].nombre;
		      	texto=document.createTextNode(respuesta[i].nombre);
		      	opcion.appendChild(texto);
		      	if(respuesta[i].tipo=="C"){
		      		listaconductor.appendChild(opcion);
		      	}else{
		      		listavehiculo.appendChild(opcion);
		      	}
		      }
	      	
	      }

	}
	
	function selectArchivo(select){
		var respuesta=archivos;
	      //Datos json devuelto por el servidor:
	      //[{nombre:'C_E34055105V000000_E_20080401_1710.TGD,tipo:'C',datosInterpretados:'.....................'}]
	      tablaconductor=document.getElementById("tablaconductor");
	      tablavehiculo=document.getElementById("tablavehiculo");
	       var fila=0;
		var data="";
		if (select.id=="listaArchivoconductor"){
			document.getElementById("tablaconductor").tBodies[0].innerHTML="<tr><th scope='col'>Nombre Archivo</th><th scope='col'>Archivo en hexadecimal</th></tr>";
		}else{
			document.getElementById("tablavehiculo").tBodies[0].innerHTML="<tr><th scope='col'>Nombre Archivo</th><th scope='col'>Archivo en hexadecimal</th></tr>";;
		}
		
		
		for (var j=0;j<select.options.length;j++){
			if (select.options[j].selected)
			
				for (var i=0; i<respuesta.length;i++){
				if(respuesta[i].nombre.trim()==select.options[j].value.trim()){
			      		if(respuesta[i].tipo=="C"){
				      		tablaconductor.style.display="block";
				      		fila=tablaconductor.tBodies[0].rows.length;
				      		tablaconductor.tBodies[0].insertRow(fila);
				      		tablaconductor.tBodies[0].rows[fila].insertCell(0);
				      		tablaconductor.tBodies[0].rows[fila].insertCell(1);
				      		tablaconductor.tBodies[0].rows[fila].cells[0].innerHTML=respuesta[i].nombre;
				      		texto=document.createTextNode(respuesta[i].datosInterpretados);
				      		div=document.createElement("div");
				      		div.style.overflow="auto";
				      		div.style.height="200px";
				      		div.appendChild(texto);
				      		tablaconductor.tBodies[0].rows[fila].cells[1].appendChild(div);		
				      	}else{
				      		tablavehiculo.style.display="block";
				      		fila=tablavehiculo.tBodies[0].rows.length;
				      		tablavehiculo.tBodies[0].insertRow(fila);
				      		tablavehiculo.tBodies[0].rows[fila].insertCell(0);
				      		tablavehiculo.tBodies[0].rows[fila].insertCell(1);
				      		texto=document.createTextNode(respuesta[i].datosInterpretados);
				      		div=document.createElement("div");
				      		div.style.overflow="auto";
				      		div.style.height="200px";
				      		div.appendChild(texto);
				      		tablavehiculo.tBodies[0].rows[fila].cells[0].innerHTML=respuesta[i].nombre;
				      		tablavehiculo.tBodies[0].rows[fila].cells[1].appendChild(div);
				      	}
			      }
		      }
	      		
		}
	
	}
		
	
	function seleccionado(){ 

		var archivos = document.getElementById("archivos");//Damos el valor del input tipo file
		var archivo = archivos.files; //Obtenemos el valor del input (los arcchivos) en modo de arreglo

		var data = new FormData();

		//Como no sabemos cuantos archivos subira el usuario, iteramos la variable y al
		//objeto de FormData con el metodo "append" le pasamos calve/valor, usamos el indice "i" para
		//que no se repita, si no lo usamos solo tendra el valor de la ultima iteración
		var fin=4;
		if (archivo.length<4)
			fin=archivo.length;
		for(var i=0; i<fin; i++){
			
		   data.append('archivo'+i,archivo[i]);
		}
		//url, funcion, funcionError, metodo, parametros, contentType OJO CONTENTYPE A FALSE para poder subir mas de un archivo
		var cargador = new net.CargadorContenidos("controlador?accion=anonimo", muestraarchivo,null,"POST",data, false);
		
		
		}
		function muestraarchivo(){
			var respuesta=eval(this.req.responseText);
			archivos=respuesta;
			var tabla=document.getElementById("tablainfo");
			tabla.tBodies[0].innerHTML="";
			for (var i in respuesta){
		     		fila=tabla.tBodies[0].rows.length;
		     		tabla.tBodies[0].insertRow(fila);
		     		tabla.tBodies[0].rows[fila].insertCell(0);
		      		tabla.tBodies[0].rows[fila].insertCell(1);
		      		tabla.tBodies[0].rows[fila].cells[0].innerHTML=respuesta[i].nombre;
		      		if (respuesta[i].mensaje=="true"){
		      			texto=document.createTextNode("archivo con formato correcto");
		      		}else{
		      			texto=document.createTextNode(respuesta[i].mensaje);
		      		}
		      		
		      		div=document.createElement("div");
		      		div.style.overflow="auto";
		      		div.appendChild(texto);
		      		tabla.tBodies[0].rows[fila].cells[1].appendChild(div);	
		      		if 	(respuesta[i].mensaje!="true")
		      			tabla.tBodies[0].rows[fila].cells[1].style.color="red";
		     
		     }
		     muestralistaarchivo();
			}


</script>

<title>PrincipalAnonimo</title>
</head>

<body >
<div id="sesion.error" style="display:none"> <span style="color:red" >sesion invalida, inicie sesion  <a href="index.jsp">aqui</a></span></div>
<div id="contenido" style="display:block">
	<h1>Sesion iniciada por : Anonimo</h1>
	<div id="izq">
		<fieldset>
			<legend>Archivos de conductor(multiselect tecla ctrl)</legend>
			<select id="listaArchivoconductor" name="listaArchivoconductor[]" multiple  onchange="selectArchivo(this)" style="height: 197px; width: 248px; ">	</select>
		</fieldset>
		<fieldset>
			<legend>Archivos de vehiculo (multiselect tecla ctrl)</legend>
			<select id="listaArchivovehiculo" name="listaArchivovehiculo[]" multiple  onchange="selectArchivo(this)" style="height: 197px; width: 248px; ">	</select>
		</fieldset>
		
		
		<br>
		<button onclick="accion(this)" name="subir">Subir</button><br>
		
		<div id="subir"  style="display:none" name="submenu">
				Subir archivo(maximo 5):<input id="archivos" type="file" name="archivos[]" multiple="multiple" onchange="seleccionado();"  accept="application/TGD" max="3"/>
				<input type="submit" value="subir archivos"/>
		</div>
		<div id="interpretar"  style="display:none" name="submenu">
		</div>
	</div>
	
	<div id="der">
	<!-- ********************************************** Cuadro de archivos interpretados ******************************** -->
		<div id="interpretados" >
				<table name="tablaconductor" id="tablaconductor" class="gridtable" style="display:none">
					<caption><h2>Archivos de conductores</h2></caption>
					<tbody></tbody>
				</table>
			
				<table name="tablavehiculo" id="tablavehiculo" class="gridtable" style="display:none">
					<caption><h2>Archivos de vehiculos</h2></caption>
					<tbody></tbody>
				</table>
		</div>
		<div id="informacion">
			<table name="tablainfo" id="tablainfo" class="gridtable">
					<caption>Informacion archivos subidos</caption>
					<tbody></tbody>
				</table>
		</div>
	</div>
</div>

</body>
</html>