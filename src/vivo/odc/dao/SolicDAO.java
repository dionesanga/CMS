package vivo.odc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;

public class SolicDAO extends OraMaster{

	public boolean existIdInMis(String codSolicitacao){
		Connection        	conn        = null;  
		PreparedStatement 	pstmt       = null;
		ResultSet         	rs          = null;
		boolean 		  	solic       = false;
		String				auxIntID	= codSolicitacao.replace(".", "");		

		try{
			String strSQL = " SELECT COUNT(1) QTD FROM PLANCRM_OWNER.MIS_ANATEL_FOCUS WHERE SCDSOLICITACAO = ?";

			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1, auxIntID);
			rs = pstmt.executeQuery();
			if (rs.next()){
				solic = rs.getInt("QTD") > 0 ? true : false;
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO. Request:"+codSolicitacao,ex);
		}
		finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return solic;  
	 }
}
