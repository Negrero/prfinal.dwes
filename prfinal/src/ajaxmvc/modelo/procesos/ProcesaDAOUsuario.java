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
	
		
		/** 
		 * Devuelve un ArrayList de objetos Ranking que encapsula el listado
		 * de ranking en orden descendente
		 * @return La lista de objetos Ranking en orden descendente
		 */
		public ArrayList<Ranking> getRanking(){
		
				ArrayList<Ranking> ranking = new ArrayList<Ranking>();
				Ranking rnkUsuario = null;
				
				Connection conBD = null;
				Statement st = null;
				ResultSet rs = null;
				String sentenciaSQL = null;
				
				sentenciaSQL = "select login, puntos from ranking order by puntos desc";
				try {
		        	conBD = ds.getConnection();
		        	st = conBD.createStatement();
		        	rs = st.executeQuery(sentenciaSQL);
		
		        	while (rs.next())
		        	{
		        		rnkUsuario = new Ranking();
		        		rnkUsuario.setLogin(rs.getString("login"));
		        		rnkUsuario.setPuntos(rs.getString("puntos"));
		        		//System.out.println("login: "+rs.getString("login") );
		        		//System.out.println("puntos: "+rs.getString("puntos") );
		        		ranking.add(rnkUsuario);
		        	}
		        	//System.out.println("Tamaño del array: "+ranking.size());
		        }
		        catch(Exception excepcion) {
		        	excepcion.printStackTrace();
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
				return ranking;
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
		
			
			/** 
			 * Devuelve true si hay 3 o mas fotogramas del listado total de fotogrmas menos
			 * el total de fotogramas que hayan concursado de un usuario dado
			 */
			public boolean hayFotogramaConcurso(String login){
			
						boolean hay = false;
						Connection conBD = null;
						Statement st = null;
						ResultSet rs = null;
						String sentenciaSQL = null;
					
						sentenciaSQL = "SELECT count(*) as hay FROM fotogramas where idFotogramas not in (select idfotograma from concurso where login='"+login+"' )";
						try {
				        	conBD = ds.getConnection();
				        	st = conBD.createStatement();
				        	rs = st.executeQuery(sentenciaSQL);
				        	rs.next();
				        	if (rs.getInt("hay")>=3){
				        		hay=true;
				        	}
				        
				        }
				        catch(Exception excepcion) {
				        	excepcion.printStackTrace();
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
					
						return hay;	
					 }
		
		
			
			/** 
			 * Devuelve un ArrayList de fotogramas para concursar que no haya ya 
			 * concursado para este usuario
			 */
			public ArrayList<Fotograma> getFotogramasConcurso(String login){
			
						ArrayList<Fotograma> listaFotograma = new ArrayList<Fotograma>();
						Fotograma fotograma = null;
						
						Connection conBD = null;
						Statement st = null;
						ResultSet rs = null;
						String sentenciaSQL = null;
						
						sentenciaSQL = "SELECT * FROM fotogramas where idFotogramas not in (select idfotograma from concurso where login='"+login+"' )";
						System.out.println("login es :"+login);
						System.out.println("sentenciasql: "+sentenciaSQL);
						try {
				        	conBD = ds.getConnection();
				        	st = conBD.createStatement();
			
				        	
				        	rs = st.executeQuery(sentenciaSQL);
				        
				        	while (rs.next())
				        	{
				        		fotograma = new Fotograma();
				        		fotograma.setAnyoEstreno(rs.getInt("anyoEstreno"));
				        		fotograma.setArchivo(rs.getString("archivo"));
				        		fotograma.setDirector(rs.getString("director"));
				        		fotograma.setGenero(rs.getString("genero"));
				        		fotograma.setIdfotograma(rs.getInt("idFotogramas"));
				        		fotograma.setTitPelicula(rs.getString("titpelicula"));
				        		System.out.println("id numero: "+rs.getInt("idFotogramas") );
				        		//System.out.println("puntos: "+rs.getString("puntos") );
				        		listaFotograma.add(fotograma);
				        	}
				        	//System.out.println("Tamaño del array: "+ranking.size());
				        }
				        catch(Exception excepcion) {
				        	excepcion.printStackTrace();
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
						return listaFotograma;
					 }
		
		
			
			/** 
			 * Devuelve un ArrayList de titulos de todos los fotgramas
			 */
			public ArrayList<String> getListaTitulo(){
			
						ArrayList<String> listaTitulo = new ArrayList<String>();
					
						
						Connection conBD = null;
						Statement st = null;
						ResultSet rs = null;
						String sentenciaSQL = null;
						
						sentenciaSQL = "SELECT titpelicula FROM fotogramas ";
						try {
				        	conBD = ds.getConnection();
				        	st = conBD.createStatement();
				        	rs = st.executeQuery(sentenciaSQL);
			
				        	while (rs.next())
				        	{
				        		
				        		listaTitulo.add(rs.getString("titpelicula"));
				        	}
				        	//System.out.println("Tamaño del array: "+ranking.size());
				        }
				        catch(Exception excepcion) {
				        	excepcion.printStackTrace();
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
						return listaTitulo;
					 }
			
				
				/** 
				 * Inserta registro con los datos de la clase concurso y actualiza
				 * la base de datos ranking con el nuevo valor
				 */
				public void setGrabarRanking(Concurso concurso){
				
							
										// TODO Auto-generated method stub
										Connection conBD = null;
										Statement st = null;
										ResultSet rs = null;
										String sentenciaSQL = null;
										float ranking=0;
							
										sentenciaSQL="select * from ranking where login='"+concurso.getLogin()+"'";
										System.out.println("sentenciaSQL: "+sentenciaSQL);
										try {
								        	conBD = ds.getConnection();
								        	st = conBD.createStatement();
								        	rs = st.executeQuery(sentenciaSQL);
								        	rs.next();
								        	ranking=rs.getFloat("puntos");
								        	ranking+= Float.parseFloat(concurso.getPuntos());
								        	
								        	sentenciaSQL="UPDATE `pr07carmonagil`.`ranking` SET `puntos`="+ranking+" WHERE `login`='"+concurso.getLogin()+"'";
								        	st.executeUpdate(sentenciaSQL);
								        	sentenciaSQL="INSERT INTO `pr07carmonagil`.`concurso` (`login`, `idfotograma`, `puntos`) VALUES ('"
								        	+concurso.getLogin()+"',"+concurso.getIdfotograma()+","+concurso.getPuntos()+")";
								        	
								        	st.executeUpdate(sentenciaSQL);
								        }
								        catch(Exception excepcion) {
								        	excepcion.printStackTrace();
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
										
									  }
			
			
		}

