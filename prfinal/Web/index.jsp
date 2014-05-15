<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="modelo" scope="session" class="ajaxmvc.modelo.beans.ModeloAjax"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript" src="js/coreAJAX.js"></script>
<script type="text/javascript" src="js/validar.js"></script>
<script type="text/javascript">
	
	
	function accion(id){
		botones=document.getElementsByName("submenu");
		for(var i=0;i<botones.length;i++){
			botones[i].style.display="none";
		}
		document.getElementById(id.name).style.display="block";
	}
	
	function existeLoginregistro(login){
		var parametros="accion=existelogin&login="+login.value;
		 var cargador = new net.CargadorContenidos("controlador", muestraloginregistro,null,"POST",parametros, "application/x-www-form-urlencoded");
		// var loader1 = new net.ContentLoader(url,CreateScript,null,"POST",strParams);
		 //new net.CargadorContenidos(url, funcion, funcionError, metodo, parametros, contentType)
	}
	function muestraloginregistro(){
	      var respuesta=this.req.responseText;
	      respuesta=respuesta.trim();
	      if (respuesta=="existelogin"){
		      document.getElementById("loginexiste.error").style.display="block";
		  }else{
			  document.getElementById("loginexiste.error").style.display="none";
			  validar(document.getElementById("loginregistro"));
			}
	      
	}
	function existeLoginentrar(){
		formulario=document.getElementById("loguear");
		
		if (ValidaCampos(formulario)){
			var parametros="accion=login&login="+formulario.loginentrar.value+"&clave="+formulario.claveentrar.value;
			 var cargador = new net.CargadorContenidos("controlador", muestraloginentrar,null,"POST",parametros, "application/x-www-form-urlencoded");
			 
		}
		
		
	}
	function muestraloginentrar(){
			
	      var respuesta=this.req.responseText;
	      respuesta=respuesta.trim();
	     
	      if (respuesta=="principalUsuario.jsp"){
	    	 
			  window.document.location=respuesta;   
		  }else{
			  document.getElementById("entrarlogin.error").style.display="block";
			  
			}
			
	      
	}
	var reader; //GLOBAL File Reader object for demo purpose only

    /**
     * Check for the various File API support.
     */
    window.onload=function checkFileAPI() {
        if (window.File && window.FileReader && window.FileList && window.Blob) {
            reader = new FileReader();
            return true; 
        } else {
            alert('The File APIs are not fully supported by your browser. Fallback required.');
            return false;
        }
    }

    

	
	
		function seleccionado(){ 

			var archivos = document.getElementById("archivos");//Damos el valor del input tipo file
			var archivo = archivos.files; //Obtenemos el valor del input (los arcchivos) en modo de arreglo
			var data = new FormData();
			for(var i=0; i<archivo.length; i++){
				   data.append('archivo'+i,archivo[i]);
				}
			
			//url, funcion, funcionError, metodo, parametros, contentType OJO CONTENTYPE A FALSE para poder subir mas de un archivo
			var cargador = new net.CargadorContenidos("controlador?accion=anonimo", muestraarchivo,null,"POST",data, false);
			
			
			}
			function muestraarchivo(){	
					window.document.location="principalAnonimo.jsp";
				
				}
		
			
</script>
</head>
<body >
	<form action="#" method="post" onsubmit="return false" id="formulario">
		<button name="archivo" onclick="muestraarchivo()">Interpretar archivo</button>
		<button name="login" onclick="accion(this)">login</button>
		<button name="registrar" onclick="accion(this)">registrarse</button>
	</form>
	<div id="registrar"  style="display:none" name="submenu">
		<jsp:include page="registrar.html"></jsp:include>
	</div>
	<div id="login" style="display:none" name="submenu">
		<jsp:include page="login.html" ></jsp:include>
	</div>
	
	
	<div id="list"></div>
	
</body>
</html>