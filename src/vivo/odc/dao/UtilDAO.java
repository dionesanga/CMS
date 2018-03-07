package vivo.odc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;
import vivo.odc.vo.ChamadoVO;

public class UtilDAO extends OraMaster{

	public boolean updateTokenProperty(String token,String value){
		Connection        conn        = null;
		PreparedStatement psmt        = null;
		
		String sql = " update PLANCRM_OWNER.OUV_PROPERTIES_CONFIG " +
		             " set value = ? " +
		             " where label = ? ";
		try{
			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, value);
			psmt.setString(2, token);
			psmt.executeUpdate();			
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado.", e);
			close(conn);
		} finally{
			close(psmt);
			close(conn);
		}
		return true;
	}
	
	public void deleteOuv_Gr_Segmento(){
		Connection        	conn        = null;
		PreparedStatement 	psmt        = null;
		
		String sql = "DELETE FROM PLANCRM_OWNER.OUV_GR_SEGMENTO";
		
		try{
			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);			
			psmt.executeUpdate();
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado.", e);			
		} finally{
			close(psmt);
			close(conn);
		}
	}
	
	public void insertIdsCargaAnatel(ChamadoVO[] ids){
		Connection        	conn        = null;
		PreparedStatement 	psmt        = null;
		int 				cont		= 1;
		
		String sql = "INSERT INTO PLANCRM_OWNER.OUV_GR_SEGMENTO (ID_GR_SEGMENTO, NM_SEGMENTO) VALUES (?,?)";
		
		try{
			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			for (ChamadoVO s : ids) {
				psmt.setInt(1, cont++);
				psmt.setString(2, s.getId());
				psmt.executeUpdate();
			}
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado.", e);			
		} finally{
			close(psmt);
			close(conn);
		}
	}
	
	public boolean isExecutantoToken(String token){
		Connection        	conn        = null;
		PreparedStatement 	psmt        = null;
		ResultSet			rs			= null;
		int					qtd			= 1;
		
		String sql = " SELECT COUNT(1) QTD FROM PLANCRM_OWNER.OUV_PROPERTIES_CONFIG WHERE LABEL = ? AND VALUE <> 'LIBERADO'";
		try{
			conn  = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, token);
			rs = psmt.executeQuery();
			
			if(rs.next()){
				qtd = rs.getInt("QTD");
			}
			close(psmt);
		}
		catch (Exception e){
			Logs.error("Todo processo sera cancelado.", e);
			close(conn);
		} finally{
			close(conn);
		}
		return qtd > 0 ? true : false;
	}
}
