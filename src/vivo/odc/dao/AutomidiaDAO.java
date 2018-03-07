package vivo.odc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;

public class AutomidiaDAO extends OraMaster{

	public boolean isOpen(String request){
	     Connection        conn        = null;  
	 	 PreparedStatement pstmt       = null;
	 	 ResultSet         rs          = null;
	 	 boolean 		   closed      = false;
	 	 
	 	 try{
	        String strSQL = " select count(1) quant from request " +
	                        " where request = ? and closed=0 ";
	        
	        conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, request);
			rs = pstmt.executeQuery();
			if (rs.next()){
				closed = rs.getInt("quant")>0?true:false;
			}
	     }
	     catch (Exception ex){
	    	 ex.printStackTrace();
	    	 Logs.error("PROCESSO CANCELADO. Request:"+request,ex);
	     }
        finally {
           	 close(rs);
           	 close(pstmt);
           	 close(conn);
	     }
	     return closed;  
	 }
	
	public void changeStatus(String request,String statusNovo) {
		Connection        conn = null;
		PreparedStatement psmt = null;
		CallableStatement cs   = null;


		try {
			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.AUTOMIDIA);

			String strSQL = "ALTER SESSION SET nls_date_format = 'yyyy/mm/dd hh24:mi:ss' ";

			psmt = conn.prepareStatement(strSQL);

			psmt.execute();
			psmt.close();

			strSQL = "{ CALL admahd30.gvtpackageautomidiaextends.changestatus(?,?) }";

			cs = conn.prepareCall(strSQL);

			cs.setString(1, request);
			cs.setString(2, statusNovo);
			cs.execute();
		} catch (Exception exception) {
			Logs.error("PROCESSO CANCELADO. Erro ao mudar status. Request:"+request+" Novo Status: "+statusNovo, exception);
		} finally {
			close(cs);
			close(psmt);
			close(conn);
		}
	}
	
	public void fecharChamado(String request, String solucao) {
		Connection conn = null;
		PreparedStatement pstm = null;
		CallableStatement cs = null;

		try {
			String analista = getCurranal(request);

			String strSQL = " ALTER SESSION SET nls_date_format = 'yyyy/mm/dd hh24:mi:ss' ";
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.AUTOMIDIA);			
			pstm = conn.prepareStatement(strSQL);
			pstm.execute();
			close(pstm);

			strSQL = "{ CALL admahd30.spr_runrclosereqparentgvt(?,?,?,?,?) }";

			cs = conn.prepareCall(strSQL);
			cs.setString(1, request);
			cs.setString(2, analista);
			cs.setString(3, "");
			cs.setInt(4, 1);
			cs.setString(5, solucao);

			cs.execute();

		} catch (Exception sqlException) {
			Logs.error(
					"ERROR ao fechar chamado. "
							+ request, sqlException);			
		} finally {
			close(cs);
			close(pstm);
			close(conn);
		}
	}
	
	public String getCurranal(String request) {
		String curranal = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String strSQL = " select CURRANAL from ADMAHD30.REQUEST "
					+ " where request = ? ";

			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.AUTOMIDIA);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, request);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				curranal = rs.getString("CURRANAL");
			}
			close(rs);
			close(pstmt);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO. Request:" + request, ex);			
		} finally {
			close(conn);
		}
		return curranal;
	}
	
}
