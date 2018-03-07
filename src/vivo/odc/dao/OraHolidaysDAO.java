package vivo.odc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;

public class OraHolidaysDAO extends OraMaster{
	
	public boolean isHoliday(String data){
		Connection        conn        = null;
		PreparedStatement psmt        = null;
		ResultSet         rs          = null;
		boolean           retorno     = false;  
		
		String sql = " select count(1) holiday from PLANCRM_OWNER.CKP_HOLIDAYS " +
				     " where hdate = to_date(?,'DD/MM/RRRR') ";
				    
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, data);
			rs = psmt.executeQuery();
			if (rs.next()){
				if (rs.getInt("holiday")>=1){
					retorno=true;
				}
			}
			close(rs);
			close(psmt);
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado", e);
			System.exit(-1);
		} finally{
			close(conn);
		}
		return retorno;
	}
}
