package ajaxmvc.modelo.procesos;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;
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
		public ProcesaDAOUsuario(DataSource ds){
		
				this.ds = ds;
			 }
		
		/** 
		 * Constructor que recibe el datasource y el nombre del archivo
		 * properties con las sentencias sql
		 */
		public ProcesaDAOUsuario(DataSource ds, String archivoSql)	throws FileNotFoundException, IOException {
		
				this.ds = ds;
				this.sql= new Properties();
				
				this.sql.load(new FileInputStream(archivoSql));
			 }
		
			
			/** 
			 * Comprueba si existe un usuario. En caso de login correcto, 
			 * se devuelve true, en caso contrario, false.
			 * @param login Login del usuario a localizar
			 * @param clave Clave del usuario a localizar
			 * @return
			 * @throws BeanError
			 */
			public boolean existeUsr(String login)	throws BeanError {
			
					
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
		
		/** 
		 * Comprueba si existe un usuario y se valida su clave. En caso de login y clave correctos, 
		 * se devuelve true, en caso contrario, false.
		 */
		public boolean existeUsr(String login, String clave)	throws BeanError {
		
				boolean existe = false;
				Connection conBD = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				
				try {
		        	conBD = ds.getConnection();
		        	ps = conBD.prepareStatement(this.sql.getProperty("existeloginclave"));
		        	
		        	ps.setString(1, login);
		        	ps.setString(2, clave);
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
		public boolean registrarUsuario(Usuario user)	throws BeanError {
		
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
		            	@SuppressWarnings("unused")
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
		
			
			
				
				/** 
				 * Realiza el proceso de grabar los archivos que haya seleccionado un usuario
				 */
				public java.lang.String setGrabaArchivo(String login, Archivo archivo)	throws BeanError {
				
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
			
			
				
				/** 
				 * devuelve un arraylist de Archivo de un usuario
				 */
				@SuppressWarnings("rawtypes")
				public ArrayList getListaArchivo(String login)	throws BeanError {
				
								Connection conBD = null;
								PreparedStatement ps=null;
								ArrayList<Archivo> lista = new ArrayList<Archivo>();
								
								try {
						        	conBD = ds.getConnection();
						        	// select nombre from archivos where login=?
						        	ps = conBD.prepareStatement(this.sql.getProperty("listaarchivo"));
						        	
						        		ps.setString(1, login);
						            	ResultSet rs = ps.executeQuery();
						           
						            	while (rs.next()){
						            		
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
				
				/** 
				 * devuelve un ArrayList de Archivo de un usuario de la lista que haya seleccionado
				 */
				@SuppressWarnings("rawtypes")
				public ArrayList getListaArchivoSelect(String login, String[] listaselec)	throws BeanError {
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

