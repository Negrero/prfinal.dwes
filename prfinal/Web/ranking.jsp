<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="ajaxmvc.modelo.beans.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consulta de ranking</title>
<script src="utilidades.js" language="javascript"></script>
<script src="http:www.json.org/json2.js"></script>
<script language="javascript">
	
	var peticion=null;
	
	function inicializa_xhr(){
		if(window.XMLHttpRequest){
			return new XMLHttpRequest();
		}else if(window.ActiveXObject){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}
	
	function muestraRanking(){
		if(peticion.readyState==4){
			if (peticion.status==200){
				mostrarTabla(0,5);
				document.getElementById("inicio").innerHTML="0";
				document.getElementById("final").innerHTML="4";
				document.getElementById("inicio").value=0;
				document.getElementById("final").value=5;
			}
		}
	}
	/*
		te muestra la tabla de la lista de fotogramas desde un indice inicial y final,
		este indice es dado segun el combobox 5,10,15 y segun en la posicion de la 
		lista en la que nos encontremos
	*/
	function mostrarTabla(indice_inicio, indice_final){
		var ranking=eval('('+peticion.responseText+')');
		var tabla="<table border='1'><tr><th>Usuario</th><th>Puntos</th></tr>";
	             for(elm = indice_inicio;elm < indice_final;elm++){
	                tabla+="<tr>";
	                tabla+="<td>"+ranking[elm].login+"</td>";
	                tabla+="<td>"+ranking[elm].puntuacion+"</td>";
	                tabla+="</tr>";
	             }
	             tabla+="</table>";         
             document.getElementById("tabla").innerHTML=tabla;
             document.getElementById("inicio").innerHTML=indice_inicio;
		document.getElementById("final").innerHTML=indice_final;
		document.getElementById("inicio").value=indice_inicio;
		document.getElementById("final").value=indice_final;
	}
	/*
		Recupera listado de fotogramas en la  peticion por lo que tenemos en peticion [array fotogramas]
			[{login:'rosa',puntuacion:'23.0'},
			{login:'amarillo',puntuacion:'12.0'},
			{login:'manolo',puntuacion:'11.0'},
			{login:'pardo',puntuacion:'9.0'},{
			login:'yomisma',puntuacion:'8.0'},
			.......]
	*/
	window.onload = function(){
		
		peticion=inicializa_xhr();
		if(peticion){
			peticion.onreadystatechange=muestraRanking;
			peticion.open("GET","controlador?accion=ranking",true);
			peticion.send(null);
		}
	}
	
	
	

	function accionLogin(){
		window.document.location='login.jsp'
	}
	function accionRegistro(){
		window.document.location='controlador?accion=registrar'
	}
	
	/*
		funcion que se mueve hacia adelante en el listado de fotogramas 
		teniendo en cuenta la posicion final del array de fotogramas
		los indices inicio-final los obtenemos o añadimos segun la etique p con id final y inicio
		por lo que siempre sabemos en que posicion de la tabla estamos segun la vista o sea 
		que tenemos seleccionados en el combobox 5,10,15
	 */
	function siguiente(){
		var ranking=eval('('+peticion.responseText+')');
		var indice_inicio=document.getElementById("inicio").value;
		var indice_final=document.getElementById("final").value;
		var opcion=document.getElementById("modo_vista");
		var indice=parseInt(opcion.options[opcion.selectedIndex].value);
		var ultimo=indice_final+indice;
		
		if (ranking.length<ultimo){
			indice_final=ranking.length;
			if(indice_inicio+indice<indice_final)
				indice_inicio+=indice;
		}else{
			indice_inicio=indice_final;
			indice_final+=indice;
		}
		mostrarTabla(indice_inicio,indice_final);
	}
	
	/*
		funcion que se mueve hacia atras en el listado de fotogramas 
		teniendo en cuenta la posicion 0 del lista para no pasarnos
		los indices los guardamos segun la etique p con id final y inicio
		por lo que siempre sabemos en que posicion de la tabla estamos segun la vista o sea 
		que tenemos seleccionados en el combobox 5,10,15
	*/
	function atras(){
		var ranking=eval('('+peticion.responseText+')');
		var indice_inicio=document.getElementById("inicio").value;
		var indice_final=document.getElementById("final").value;
		var opcion=document.getElementById("modo_vista");
		var indice=parseInt(opcion.options[opcion.selectedIndex].value);
		
		if (indice_inicio-indice<0){
			indice_final=indice;
			if(ranking.length<indice_final)
				indice_final=ranking.length;
			indice_inicio=0;
		}else{
			if (ranking.length==indice_final){
				indice_final=indice_inicio;
				indice_inicio-=indice;
		
			}else{
				indice_inicio-=indice;
				indice_final-=indice;
			}
			
		}
		mostrarTabla(indice_inicio,indice_final);
	}
	/*
		cambia la vista es cuando hemos cambiado el combobox de 5,10,15 elementos
		y mostramos la lista con el nuevo indice final y el numeros de elementos
		a mostrar en la tabla 5,10,15
	*/
	function cambiaVista(){
		var ranking=eval('('+peticion.responseText+')');
		var opcion=document.getElementById("modo_vista");
		var indice=parseInt(opcion.options[opcion.selectedIndex].value);
		if (ranking.length<indice)
			indice=ranking.length;
		mostrarTabla(0,indice);
	}
</script>
</head>
<body>
<jsp:useBean id="modelo" class="java.util.ArrayList" scope="request" >
</jsp:useBean>
<center>

<div id="tabla">

</div>
<!-- Estas etiquetas nos sirve para indicarnos el indice dentro de la lista de fotogramas segun 
	el valor del option
 -->
<p id="inicio" value="1">1</p>
<p id="final" value=""></p>
<button id="atras" onclick="atras()"><</button>
<button id="delante" onclick="siguiente()">></button>
<a href="#" onclick="accionLogin()">Login</a>
<a href="#" onclick="accionRegistro()">Registro</a>
	<select id="modo_vista" onchange="cambiaVista()">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="todos">todos</option>
	</select>
</center>
</body>
</html>