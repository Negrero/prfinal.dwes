package ajaxmvc.modelo.procesos;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.json.*;
import javax.sql.DataSource;
import java.util.Collection;

import ajaxmvc.modelo.beans.*;

/** 
 * Se encarga de proporcionar el servicio de acceso a BD
 * @author Andres Carmona Gil
 */
public class ProcesaDAOUsuario {
	
	/**
	 * Datasource
	 */
	DataSource ds = null;
	
	Properties sql=null;
	/**
	 * Constructor que recibe el datasource
	 * @param ds El datasource para el acceso a la base de datos
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public ProcesaDAOUsuario(DataSource ds)  {
		this.ds = ds;
	}
	public ProcesaDAOUsuario(DataSource ds, String archivoSql) throws FileNotFoundException, IOException{
		// TODO Auto-generated constructor stub
		this.ds = ds;
		this.sql= new Properties();
		
		this.sql.load(new FileInputStream(archivoSql));
	}
	/**
	 * Comprueba si existe un usuario y se valida su clave. En caso de login y clave correctos, 
	 * se devuelve true, en caso contrario, false.
	 * @param login Login del usuario a localizar
	 * @param clave Clave del usuario a localizar
	 * @return
	 * @throws BeanError 
	 */
	public boolean existeUsr(String login) throws BeanError {
		boolean existe = false;
		Connection conBD = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
        	conBD = ds.getConnection();
        	ps = conBD.prepareStatement(this.sql.getProperty("existelogin"));
        	
        	ps.setString(1, login);
        	rs = ps.executeQuery();

        	if (rs.next())	{
        			existe = true;
        	}
		}
        catch(SQLException sqle) {
        	System.out.println("SE HA PRODUCIDO UN ERROR sql");
        	throw new BeanError(1,sqle.getMessage()+" en clase"+sql.getClass().getCanonicalName(),sqle);
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					throw new BeanError(1,sqle.getMessage(),sqle);
				}
		}		
		return existe;
	}
	public boolean existeUsr(String login,String Clave) throws BeanError {
		boolean existe = false;
		Connection conBD = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
        	conBD = ds.getConnection();
        	ps = conBD.prepareStatement(this.sql.getProperty("existeloginclave"));
        	
        	ps.setString(1, login);
        	ps.setString(2, Clave);
        	rs = ps.executeQuery();

        	if (rs.next())	{
        			existe = true;
        	}
		}
        catch(SQLException sqle) {
        	System.out.println("SE HA PRODUCIDO UN ERROR sql");
        	throw new BeanError(1,sqle.getMessage()+" en clase"+sql.getClass().getCanonicalName(),sqle);
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					throw new BeanError(1,sqle.getMessage(),sqle);
				}
		}		
		return existe;
	}
		
	/**
	 * Realiza el proceso de registro de un usuario. Si el proceso se completa correctamente
	 * se devolverá true, en caso contrario, false.
	 * @param user Objeto que encapsula login y clave del usuario a registrar.
	 * @return true si se ha podido registrar al usuario, false en caso contrario.
	 * @throws BeanError 
	 */
	public boolean registrarUsuario(Usuario user) throws BeanError{
		boolean registrado = false;
		Connection conBD = null;
		PreparedStatement ps=null;
		try {
        	conBD = ds.getConnection();
        	//resgistrarusuario = insert into usuario(login,clave,email) values(?,?,?)
        	ps = conBD.prepareStatement(this.sql.getProperty("registrarusuario"));
        	
        	if (!this.existeUsr(user.getLogin()))
        	{
        		ps.setString(1, user.getLogin());
            	ps.setString(2, user.getClave());
            	ps.setString(3, user.getEmail());
            	int rs = ps.executeUpdate();
        		registrado=true;
        	}     	

        	
        }
        catch(SQLException sqle) {
    
        	throw new BeanError(1,sqle.getMessage()+" en clase: "+this.getClass().getSimpleName()+
        			" y metodo: registrarUsuario",sqle);
        	
        }
		finally {
			if (conBD!=null)
				try {
					conBD.close();
				}
				catch(SQLException sqle)
				{
					System.out.println(sqle);
				}
		}	
		return registrado;
	}
		
			
			
			public String setGrabaArchivo(String login,Archivo archivo) throws BeanError {
				// TODO Auto-generated method stub
				
				boolean registrado = false;
				Connection conBD = null;
				PreparedStatement ps=null;
				String msgerror="correcto";
				try {
		        	conBD = ds.getConnection();
		        	// select * from archivos where login=? and nombre=?
		        	ps = conBD.prepareStatement(this.sql.getProperty("selectarchivo"));
		        	
		        		ps.setString(1, login);
		        		ps.setString(2, archivo.getNombre_fichero());
		            	ResultSet rs = ps.executeQuery();
		            	if (rs.next()){
		            		msgerror="archivo ya esta grabado en base de datos";
		            	}else{
		            		//resgistrarusuario = insert into usuario(login,clave,email) values(?,?,?)
			        		ps = conBD.prepareStatement(this.sql.getProperty("grabararchivo"));
			        		ps.setString(1, login);
			            	ps.setBlob(2, archivo.getFichero());
			            	ps.setString(3, archivo.getNombre_fichero());
			            	int rs1 = ps.executeUpdate();
			            	if (rs1<1){
			            		msgerror="error en base de datos no se ha podido grabar el fichero";
			            		throw new BeanError(1,"error al introducir al grabar archivo en base de datos");
			            	}
		            	}
	
		        }
		        catch(SQLException sqle) {
		    
		        	throw new BeanError(1,sqle.getMessage());
		        	
		        }
				finally {
					if (conBD!=null)
						try {
							conBD.close();
						}
						catch(SQLException sqle)
						{
							System.out.println(sqle);
						}
				}	
				return msgerror;
			}
			
			
			public ArrayList getListaArchivo(String attribute) throws BeanError {
				// TODO Auto-generated method stub
				Connection conBD = null;
				PreparedStatement ps=null;
				ArrayList<Archivo> lista = new ArrayList<Archivo>();
				
				try {
		        	conBD = ds.getConnection();
		        	// select nombre from archivos where login=?
		        	ps = conBD.prepareStatement(this.sql.getProperty("listaarchivo"));
		        	
		        		ps.setString(1, attribute);
		            	ResultSet rs = ps.executeQuery();
		           
		            	while (rs.next()){
		            		String cadena=rs.getString("nombre");
		            		Archivo archivo=new Archivo(rs.getString("nombre"),rs.getBlob("archivo").getBinaryStream());
		            		lista.add(archivo);
		            	}
		        	    

		        	
		        }
		        catch(SQLException sqle) {
		    
		        	throw new BeanError(1,sqle.getMessage()+" en clase: "+this.getClass().getSimpleName()+
		        			" y metodo: registrarUsuario",sqle);
		        	
		        }
				finally {
					if (conBD!=null)
						try {
							conBD.close();
						}
						catch(SQLException sqle)
						{
							System.out.println(sqle);
						}
				}	
				return lista;
			}
			public ArrayList<Archivo> getListaArchivoSelect(String login,
					String[] listaselec) throws BeanError {
				// TODO Auto-generated method stub
				Connection conBD = null;
				PreparedStatement ps=null;
				ArrayList<Archivo> lista = new ArrayList<Archivo>();
				
				try {
		        	conBD = ds.getConnection();
		        	// select * from archivos where login=? and nombre=?
		        	ps = conBD.prepareStatement(this.sql.getProperty("selectarchivo"));
		        	for (String nombre:listaselec){
		        		ps.setString(1, login);
		        		ps.setString(2, nombre);
		            	ResultSet rs = ps.executeQuery();
		            	if (rs.next()){
		            		Archivo archivo=new Archivo(rs.getString("nombre"),rs.getBlob("archivo").getBinaryStream());
		            		lista.add(archivo);
		            	}
					}
		        	    

		        	
		        }
		        catch(SQLException sqle) {
		    
		        	throw new BeanError(1,sqle.getMessage()+" en clase: "+this.getClass().getSimpleName()+
		        			" y metodo: registrarUsuario",sqle);
		        	
		        }
				finally {
					if (conBD!=null)
						try {
							conBD.close();
						}
						catch(SQLException sqle)
						{
							System.out.println(sqle);
						}
				}	
				return lista;
			}
			
		
		
			
			
			
		}

