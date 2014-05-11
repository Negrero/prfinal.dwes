<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ejemplo AJAX con MVC</title>
<script language="javascript">
        var xhr;
        function validaUsuario(){
            if(window.ActiveXObject){
                xhr=new ActiveXObject("Microsoft.XMLHttp");      
            }
            else if((window.XMLHttpRequest) || (typeof XMLHttpRequest)!=undefined){
                xhr=new XMLHttpRequest();
            }
            else{
                alert("Su navegador no tiene soporte para AJAX");
                return;
            }
            enviaPeticion();
        }
        function enviaPeticion(){
            xhr.open("POST",document.forms[0].action,true);
            xhr.onreadystatechange=procesaResultado;
            //definiciÃ³n del tipo de contenido del cuerpo 
            //para formularios HTML
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            var datos=obtenerDatos();
            //se envÃ­an los datos en el cuerpo de la peticiÃ³n
            xhr.send(datos);
        }
        
        function obtenerDatos(){
            //genera una cadena con los datos de los controles
            //del formulario para enviarlos en la peticiÃ³n
            var controles=document.forms[0].elements;
            var datos=new Array();
            var cad="";
            for(var i=0;i<controles.length;i++){
                cad=encodeURIComponent(controles[i].name)+"=";
                cad+=encodeURIComponent(controles[i].value);
                datos.push(cad);
            }
            //se forma la cadena con el mÃ©todo join() del array
            //para evitar mÃºltiples concatenaciones
            cad=datos.join("&");
            return cad;
        }
        function procesaResultado(){
        	
            if(xhr.readyState==4){  
           		
                var result=xhr.responseText;
           		result=result.trim();
                
                if(result=="loginincorrecto"){
                    document.getElementById("registro").style.display="block";         
                }else{
                	if (result=="hayconcurso"){
                		//alert ("hay concurso"+result);
                		window.document.location="concurso.jsp"; 
                	}else{
                		//alert ("no hay concurso     "+result);
                		document.getElementById("informacion").innerHTML="No hay disponible mas fotogramas ya has agotado tus fotogramas"
                	}
                    
                }
            }
        }
    </script> 
</head>
<body>
<center>
<form action="controlador?accion=validar" method="post">
Introduzca sus datos si ya es usuario registrado<br>
Usuario : <input name="login" id="login" type="text" value="" size="30"/><br>
Clave : <input name="clave" type="password" size="30" value="" /><br>
<br>
<input type="button" value="Entrar" onclick="validaUsuario();" name="botEntrar"/>
<br/>
<br/>
<div id="informacion"></div>
<div id="registro" style="display:none">
	<br/><br/>
	<h3>Datos incorrectos</h3>
	<a href="controlador?accion=solicitud">Regístrate</a>
</div>
</form>
</center>
</body>
</html>