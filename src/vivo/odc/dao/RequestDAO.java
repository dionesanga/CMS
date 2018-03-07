package vivo.odc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;

public class RequestDAO extends OraMaster{

	public boolean chamadoIsOpen(String request){
		 Connection           conn           = null;  
	 	 PreparedStatement    pstmt          = null;
	 	 ResultSet            rs             = null;
	 	 boolean              flag           = false;
	 	 String strSQL =" SELECT COUNT(1) QTDE FROM REQUEST WHERE REQUEST = ?";
	 	try{
	 		conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
	 		pstmt = conn.prepareStatement(strSQL);
	 		pstmt.setString(1, request);
			rs = pstmt.executeQuery();
			if (rs.next()){
				flag = rs.getInt("qtde")>=1?true:false;
			}
	 	}
	    catch (Exception ex){
	    	 ex.printStackTrace();
	    	 Logs.error("PROCESSO CANCELADO",ex);
	     }
		 finally {
			close(rs);
		 	close(pstmt);
		    close(conn);
		 }
		 return flag;
	}
	
	public String getCloseDate(String request){
		String            closeDate     =  "";
		Connection        conn        = null;  
		PreparedStatement pstmt       = null;
		ResultSet         rs          = null;

		try{
			String strSQL = " select to_char(closedate,'dd/MM/RRRR  HH24:MI:SS') closedate " +
							" from REQUEST r " +
							" where request = ? ";
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, request);
			rs = pstmt.executeQuery();
			if (rs.next()){
				closeDate = rs.getString("closedate");
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO.Request:"+request,ex);
		}
		finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return closeDate;  
	}
	
	public String getUltimoChamado(String codSolicitacao){
		Connection        	conn        = null;
		PreparedStatement 	psmt        = null;
		ResultSet         	rs          = null;
		String            	request     = null;
		int					idSolic		= this.getidSolicByCodSolic(codSolicitacao);
		
		String sql =    " SELECT REQUEST FROM PLANCRM_OWNER.OUV_FOCUS_REQUEST  " +
						" WHERE ID_SOLIC = ?  " +
						" AND ID_REQUEST = (  " +
						"                   SELECT MAX(ID_REQUEST)  " +
						"                   FROM PLANCRM_OWNER.OUV_FOCUS_REQUEST  " +
						"                   WHERE ID_SOLIC = ?  " +
						"                 ) ";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, idSolic);
			psmt.setInt(2, idSolic);
			rs = psmt.executeQuery();
			if (rs.next()){
				request = rs.getString("REQUEST");
			}
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado. Ocorreu um erro!ID_SOLICITACAO:"+codSolicitacao, e);
		} finally{
			close(rs);
			close(psmt);
			close(conn);
		}
		return request;
	}
	
	public boolean isOpen(String request){
		boolean			  isOpen		= false;
		Connection        conn        	= null;
		PreparedStatement psmt        	= null;
		ResultSet         rs          	= null;		
		
		String sql =" SELECT count(1) qtd  " +
					" FROM REQUEST  WHERE REQUEST = ? AND CLOSED = 0";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, request);
			rs = psmt.executeQuery();
			if (rs.next()){
				isOpen = rs.getInt("qtd") > 0 ? true : false;
			}
		}
		catch (Exception e){
			Logs.error("Todo o processo será cancelado - Ocorreu um erro!ID_SOLICITACAO:"+request, e);
        	System.exit(-1);
		} finally{
			close(rs);
			close(psmt);
			close(conn);
		}
		return isOpen;
	} 
	
	public int getidSolicByCodSolic(String codSolicitacao){
		Connection        	conn        = null;
		PreparedStatement 	psmt        = null;
		ResultSet         	rs          = null;
		int            		idSolic     = 0;
		
		String sql =    " SELECT ID_SOLIC FROM PLANCRM_OWNER.OUV_FOCUS_SOLIC WHERE COD_SOLICITACAO = ?";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, codSolicitacao);
			rs = psmt.executeQuery();
			if (rs.next()){
				idSolic = rs.getInt("ID_SOLIC");
			}
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado. Ocorreu um erro!ID_SOLICITACAO:"+idSolic, e);
		} finally{
			close(rs);
			close(psmt);
			close(conn);
		}
		return idSolic;
	}
}
