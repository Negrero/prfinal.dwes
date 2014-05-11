var xmlHttpRequest;
function ObjetoAJAX(){
    if(window.ActiveXObject){
         xmlHttpRequest=new ActiveXObject("Microsoft.XMLHttp");      
    }
    else if((window.XMLHttpRequest) || (typeof XMLHttpRequest)!=undefined){
         xmlHttpRequest=new XMLHttpRequest();
    }
    else{
         xmlHttpRequest=null;
    }
    this.enviar=m_enviar;
    this.respuestaTexto=m_texto;
    this.respuestaXML=m_XML;
    this.obtenerEncabezados=m_encabezados;
    this.estado=m_estado;
    this.textoEstado=m_textoEstado;

    function m_enviar(url, method, funcionRetorno, objForm){
        var dataSend=null;
        if(method.toLowerCase()=="post"&&objForm!=null){
            dataSend=obtenerDatos(objForm);
        }
        else if(method.toLowerCase()=="get"&&objForm!=null){
            dataSend=obtenerDatos(objForm);
            if(url.indexOf("?")==-1){
                url+="?"+dataSend;
            }
            else{
                url+="&"+dataSend;
            }
        }
        //Petición en modo asíncrono
        alert(url);
        xmlHttpRequest.open(method,url,true);
        xmlHttpRequest.onreadystatechange=function(){
            if(xmlHttpRequest.readyState==4){
                eval(funcionRetorno+"("+")");
            }
        };
        if(method.toLowerCase()=="post"&&objForm!=null){
        	xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        xmlHttpRequest.send(dataSend);
    }
    function m_texto(){
        return xmlHttpRequest.responseText;
    }
    function m_XML(){
        return xmlHttpRequest.responseXML;
    }
    function m_encabezados(){
        return xmlHttpRequest.getAllResponseHeaders();
    }
    function m_estado(){
        return xmlHttpRequest.status;
    }
    function m_textoEstado(){
        return xmlHttpRequest.statusText;
    }
    function obtenerDatos(objForm){
        var controles=objForm.elements;
        var datos=new Array();
        var cad="";
        for(var i=0;i<controles.length;i++){
             cad=encodeURIComponent(controles[i].name)+"=";
             cad+=encodeURIComponent(controles[i].value);
             datos.push(cad);
        }
        cad=datos.join("&");
        return cad;
     }
}