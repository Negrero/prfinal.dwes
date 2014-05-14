
/**
 * funcion que recibe un formulario y recorre todos sus input
 * para validarlo
 * @param formulario
 * @returns {Boolean}
 */
function ValidaCampos(formulario){	
	var lista=formulario.getElementsByTagName("input");

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

/**
 * Valida un input y establece la etiqueta con el id ="nombreinput.error" a visible
 * @param elemento
 * @returns {Boolean}
 */
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