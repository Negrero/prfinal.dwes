<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript" src="js/coreAJAX.js"></script>
<script language="javascript" src="js/md5.js"></script>
<script language="javascript" src="js/utf8_encode.js"></script>
<script language="javascript" src="js/md5_file.js"></script>
<script language="javascript" src="js/file_get_contents.js"></script>
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

    /**
     * read text input
     */
    var cadena;
	function readText(archivo) {
		var output = ""; //placeholder for text output
		var lista=document.getElementById("list")
		lista.innerHTML="";
		
		
		if (archivo.files && archivo.files[0]) {
			for (i = 0; i < archivo.files.length; i++) {
				var reader = new FileReader();
		
				reader.onload = function(e) {
					output = e.target.result;
					
					displayContents(output);
				};//end onload()
				
				reader.readAsText(archivo.files[i]);
			}

		}//end if html5 filelist support
		else if (window.ActiveXObject && filePath) { //fallback to IE 6-8 support via ActiveX
			try {
				reader = new ActiveXObject("Scripting.FileSystemObject");
				var file = reader.OpenTextFile(filePath, 1); //ActiveX File Object
				output = file.ReadAll(); //text contents of file
				file.Close(); //close file "input stream"
				displayContents(output);
			} catch (e) {
				if (e.number == -2146827859) {
					alert('Unable to access local files due to browser security settings. '
							+ 'To overcome this, go to Tools->Internet Options->Security->Custom Level. '
							+ 'Find the setting for "Initialize and script ActiveX controls not marked as safe" and change it to "Enable" or "Prompt"');
				}
			}
		} else { //this is where you could fallback to Java Applet, Flash or similar
			return false;
		}
		return true;
	}

	/**
	 * display content using a basic HTML replacement
	 */
	function displayContents(txt,file) {
		var el = document.getElementById('list');
		
		console.log(md5(txt));
		
		el.appendChild(document.createElement("br"));
		el.appendChild(document.createTextNode(md5(txt)));
		
	}
</script>
</head>
<body >
	<form action="#" method="post" onsubmit="return false" id="formulario">
		<button name="archivo" onclick="accion(this)">Interpretar archivo</button>
		<button name="login" onclick="accion(this)">login</button>
		<button name="registrar" onclick="accion(this)">registrarse</button>
	</form>
	<div id="registrar"  style="display:none" name="submenu">
		<jsp:include page="registrar.html"></jsp:include>
	</div>
	<div id="login" style="display:none" name="submenu">
		<jsp:include page="login.html" ></jsp:include>
	</div>
	<div id="archivo" style="display:none" name="submenu">
		<jsp:include page="archivo.html" ></jsp:include>
		
	</div>
	
	<div id="list"></div>
	
</body>
</html>