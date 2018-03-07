/**
 * 
 */
package vivo.odc.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.driver.OracleConnection;

/**
 * @author G0047686
 * Dione Sanga 
 *
 */
public class ConnectionPoolClient {

	private static final long	serialVersionUID  = 414212245730104733L;
	public static final  String PORTAL_OUVIDORIA_DS = "jdbc/PortalOuvidoriaDS";
	public static final  String SIEBEL5 = "jdbc/Siebel5DS";
	public static final  String SIEBEL8 = "jdbc/Siebel8DS";
	public static final  String PSIE8 = "jdbc/psie8";
	public static final  String PBCT1   = "jdbc/pbct1DS";
	public static final  String PBCT2   = "jdbc/pbct2DS";
	public static final  String PGEN	= "jdbc/pgenDS";
	public static final  String AUTOMIDIA = "jdbc/AutomidiaDS";
	public static final  String DGOV		= "jdbc/dsDGOV";
	public static final  String SIEBEL78 = "jdbc/dsSiebel78";
	
	/**
	 * Acesso o DataSource e retorna o objeto de conexao com o banco de dados.
	 * 
	 * @param datasourceJNDI String JNDI
	 * @return java.sql.Connection
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Connection getConnection(String datasourceJNDI) throws Exception
	{
		InitialContext ic = new InitialContext();
		DataSource dataSource = null;
		// Tenta buscar o recurso na arvore JNDI do Oracle IAS.
		dataSource = (DataSource) ic.lookup(datasourceJNDI);
		Connection conection = dataSource.getConnection();
		return conection;
	}
	
	@SuppressWarnings("unchecked")
	public static Connection getConnection2(String datasourceJNDI) throws Exception
	{		
		Connection conn = null;
		DataSource ds = null;
		
		@SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory"); 
		env.put(Context.PROVIDER_URL, "t3://localhost:7001");		
		Context context=new InitialContext( env );
		
		
		ds=(javax.sql.DataSource) context.lookup (datasourceJNDI);
		conn = ds.getConnection();
		
		try{
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.close();
		context = null;
		ds = null;
		return conn;
	}
	
	public static void close(ResultSet rs){
		try{
			if (rs!=null){
				rs.close();
				rs=null;
			}
		}	
		catch (Exception e) {
			Logs.error("Erro",e);
		}
	}
	
	public static void close(PreparedStatement ps){
		try{
			if (ps!=null){
				ps.close();
				ps=null;
			}
		}	
		catch (Exception e) {
			Logs.error("Erro",e);
		}
	}
	
	public static void close(CallableStatement cstmt){
		try{
			if (cstmt!=null){
				cstmt.close();
				cstmt=null;
			}
		}	
		catch (Exception e) {
			Logs.error("Erro",e);
		}
	}
	
	public static void close(Connection conn){
		try{
			if (conn!=null){
				conn.close();
				conn=null;
			}	
		}	
		catch (Exception e) {
			Logs.error("Erro",e);
		}
	}
	
}
