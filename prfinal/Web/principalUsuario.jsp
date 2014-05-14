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
<script type="text/javascript" src="js/principalUsuario.js"></script>

<title>PrincipalUsuario</title>
</head>
<body onload=iniciar('<%= request.getSession().getAttribute("login")%>')>
<div id="sesion.error" style="display:none"> <span style="color:red" >sesion invalida, inicie sesion  <a href="index.jsp">aqui</a></span></div>
<div id="contenido" style="display:block">
	<h1>Sesion iniciada por :<%=request.getSession().getAttribute("login") %></h1>
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
				Subir archivo:<input id="archivos" type="file" name="archivos[]" multiple="multiple" onchange="seleccionado();"  accept="application/TGD"/>
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