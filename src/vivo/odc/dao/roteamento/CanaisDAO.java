package vivo.odc.dao.roteamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vivo.odc.dao.OraMaster;
import vivo.odc.util.ConnectionPoolClient;
import vivo.odc.util.Logs;
import vivo.odc.vo.roteamento.CanaisVO;
import vivo.odc.vo.roteamento.RoteamentoVO;

public class CanaisDAO extends OraMaster{

	public List<CanaisVO> getCanais(){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		ResultSet            	rs             	= null;
		List<CanaisVO> 			list 			= new ArrayList<CanaisVO>();
		CanaisVO				item			= null;
		
		String strSQL =" SELECT ID_CANAL, CANAL, CASE WHEN SITUACAO = 0 THEN 'Ativo' else 'Inativo' END AS SITUACAO FROM PLANCRM_OWNER.OUV_ROTEAMENTO_CANAL";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				item = new CanaisVO();
				item.setIdCanal(rs.getInt("ID_CANAL"));
				item.setCanal(rs.getString("CANAL"));
				item.setSituacao(rs.getString("SITUACAO"));
				
				list.add(item);
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
		return list;
	}
	
	public List<RoteamentoVO> getRoteamentos(Integer idCanal){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		ResultSet            	rs             	= null;
		List<RoteamentoVO>		list 			= new ArrayList<RoteamentoVO>();
		RoteamentoVO			item			= null;
		
		String strSQL =" SELECT ID_ROTEAMENTO, NOME, CONSULTA, FIDELIZACAO, trim(TIPO_DISTRIBUICAO) TIPO_DISTRIBUICAO, FILTER_DATE_BY_FIELD, ID_ROT_TIPIFICACAO, CASE WHEN SITUACAO = 0 THEN 'Ativo' ELSE 'Inativo' END AS SITUACAO FROM PLANCRM_OWNER.OUV_ROTEAMENTOS WHERE ID_CANAL = ?";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setInt(1, idCanal);
			rs = pstmt.executeQuery();
			while(rs.next()){
				item = new RoteamentoVO();
				item.setIdRoteamento(rs.getInt("ID_ROTEAMENTO"));
				item.setNome(rs.getString("NOME"));
				item.setConsulta(rs.getString("CONSULTA"));
				item.setFidelizacao(rs.getString("FIDELIZACAO"));
				item.setTpDistribuicao(rs.getString("TIPO_DISTRIBUICAO"));
				item.setFilterDateByField(rs.getString("FILTER_DATE_BY_FIELD"));
				item.setSituacao(rs.getString("SITUACAO"));
				item.setIdRotTipificacao(rs.getInt("ID_ROT_TIPIFICACAO"));
				
				list.add(item);
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
		return list;
	}
	
	public void gravarNewCanal(CanaisVO canal){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		
		String strSQL = "INSERT INTO PLANCRM_OWNER.OUV_ROTEAMENTO_CANAL (CANAL, SITUACAO) VALUES ( ?, ?)";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			int situacao = canal.getSituacao().endsWith("Ativo") ? 0 : 1; 
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,canal.getCanal());
			pstmt.setInt(2, situacao);
			pstmt.executeUpdate();
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO",ex);
		}
		finally {
			close(pstmt);
			close(conn);
		}
	}
	
	public void updateRoteamento(RoteamentoVO r){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		
		String strSQL = "UPDATE PLANCRM_OWNER.OUV_ROTEAMENTOS SET (CONSULTA, FIDELIZACAO, TIPO_DISTRIBUICAO, FILTER_DATE_BY_FIELD,SITUACAO,ID_ROT_TIPIFICACAO) VALUES (?,?,?,?,?,?) WHERE ID_CANAL = ?";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			
			int situacao	= r.getSituacao().equals("Ativo") ? 0 : 1;
			pstmt.setString(1,r.getConsulta());
			pstmt.setString(2, r.getFidelizacao());
			pstmt.setString(3, r.getTpDistribuicao());
			pstmt.setString(4, r.getFilterDateByField());
			pstmt.setInt(5, r.getIdRotTipificacao());
			pstmt.setInt(6, situacao);
			pstmt.setInt(7, r.getIdCanal());
			pstmt.executeUpdate();
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO",ex);
		}
		finally {
			close(pstmt);
			close(conn);
		}
	}
	
	public void removerCanal(CanaisVO c){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		
		String strSQL = "DELETE PLANCRM_OWNER.OUV_ROTEAMENTO_CANAL WHERE ID_CANAL = ?";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			
			pstmt.setInt(1,c.getIdCanal());
			pstmt.executeUpdate();
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO",ex);
		}
		finally {
			close(pstmt);
			close(conn);
		}
	}
	
	public void removerRoteamento(RoteamentoVO r){
		Connection           	conn           	= null;  
		PreparedStatement    	pstmt          	= null;
		
		String strSQL = "DELETE PLANCRM_OWNER.OUV_ROTEAMENTOS WHERE ID_CANAL = ? AND ID_ROTEAMENTO = ?";
		try{
			conn = ConnectionPoolClient.getConnection(ConnectionPoolClient.PORTAL_OUVIDORIA_DS);
			pstmt = conn.prepareStatement(strSQL);
			
			pstmt.setInt(1,r.getIdCanal());
			pstmt.setInt(2, r.getIdRoteamento());
			pstmt.executeUpdate();
		}
		catch (Exception ex){
			ex.printStackTrace();
			Logs.error("PROCESSO CANCELADO",ex);
		}
		finally {
			close(pstmt);
			close(conn);
		}
	}
}
