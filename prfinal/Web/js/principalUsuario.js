var login;
	// muestra un menu dependiendo del boton que pulsamos ocultando el resto de menus
	/**
	 * muestra los elemento del menu del boton que hemos pulsado
	 * @param id
	 */
	function accion(id){
		botones=document.getElementsByName("submenu");
		for(var i=0;i<botones.length;i++){
			botones[i].style.display="none";
		}
		document.getElementById(id.name).style.display="block";
	}
	// funcion si hay el atributo login en sesion para mostrar un menu o una advertencia
	// para volver al menu anterior
	/**
	 * Cuando se carga la pagina ve si hay un login en la sesion 
	 * sino lo hay se mostrar un error y un enlace para ir a la pagina de entrada
	 */
	function iniciar(log){
		login=log;
		var loginsesion=login;
		if (loginsesion=="null"){
			document.getElementById("sesion.error").style.display="block";
			document.getElementById("contenido").style.display="none";
		}else{
			document.getElementById("sesion.error").style.display="none";
			document.getElementById("contenido").style.display="block";
			listaarchivo();
		}
	}
	/**
	 * Lista los ficheros en el select de un usuario
	 */
	function listaarchivo(){
		var parametros="accion=listaarchivo&login="+login;
		var cargador = new net.CargadorContenidos("controlador", muestralistaarchivo,null,"POST",parametros, "application/x-www-form-urlencoded");
	}
	
	// 
	function muestralistaarchivo(){
		var listaconductor=document.getElementById("listaArchivoconductor");
		var listavehiculo=document.getElementById("listaArchivovehiculo");
		listaconductor.innerHTML="";
		listavehiculo.innerHTML="";
		//lista.innerHTML=" ";
		 var respuesta=eval(this.req.responseText);
	      //respuesta=respuesta.trim();
	      
	      for(var i=0;i<respuesta.length;i++){
		      
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
	/**
	 * muesta el nombre del fichero y el fichero interpretado de los elementos seleccionados del select
	 * @param select
	 */
	function selectArchivo(select){
		
		var data="";
		for (var i=0;i<select.options.length;i++){
			if (select.options[i].selected)
				data+="&nombrearchivo="+select[i].value;		
		}
		if (select.id=="listaArchivoconductor"){
			document.getElementById("tablaconductor").tBodies[0].innerHTML="<tr><th scope='col'>Nombre Archivo</th><th scope='col'>Archivo en hexadecimal</th></tr>";
		}else{
			document.getElementById("tablavehiculo").tBodies[0].innerHTML="<tr><th scope='col'>Nombre Archivo</th><th scope='col'>Archivo en hexadecimal</th></tr>";;
		}
		//(url, funcion, funcionError, metodo, parametros, contentType )
		var cargador = new net.CargadorContenidos("controlador?accion=interpretar", mostrarSelect,null,"POST",data, "application/x-www-form-urlencoded");
	}
	
	
	function mostrarSelect(){
		var respuesta=eval(this.req.responseText);
	      //Datos json devuelto por el servidor:
	      //[{nombre:'C_E34055105V000000_E_20080401_1710.TGD,tipo:'C',datosInterpretados:'.....................'}]
	      tablaconductor=document.getElementById("tablaconductor");
	      tablavehiculo=document.getElementById("tablavehiculo");
	      var fila=0;
	      for(var i=0;i<respuesta.length;i++){	
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
	      listaarchivo();
	}
	
	/**
	 * Para enviar los ficheros seleccionados al servidor para el proceso de grabacion
	 */
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
		//url, funcion, funcionError, metodo, parametros, contentType OJO CONTENTYPE A FALSE para poder subir mas de un archivo
		var cargador = new net.CargadorContenidos("controlador?accion=grabararchivosusuario", muestraarchivo,null,"POST",data, false);
		
		
		}
	// muestra informacion del archivo subido nombre+mensaje 
		function muestraarchivo(){
			var respuesta=eval(this.req.responseText);
			var tabla=document.getElementById("tablainfo");
			for (var i in respuesta){
		     		fila=tabla.tBodies[0].rows.length;
		     		tabla.tBodies[0].insertRow(fila);
		     		tabla.tBodies[0].rows[fila].insertCell(0);
		      		tabla.tBodies[0].rows[fila].insertCell(1);
		      		tabla.tBodies[0].rows[fila].cells[0].innerHTML=respuesta[i].nombre;
		      		texto=document.createTextNode(respuesta[i].mensaje);
		      		div=document.createElement("div");
		      		div.style.overflow="auto";
		      		div.appendChild(texto);
		      		tabla.tBodies[0].rows[fila].cells[1].appendChild(div);	
		      		if 	(respuesta[i].mensaje!="correcto")
		      			tabla.tBodies[0].rows[fila].cells[1].style.color="red";
		     
		     }
			listaarchivo()
			}
