package vivo.odc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vivo.odc.util.Logs;



public class OraMaster {
	
	protected void close(Object obj){
		if (obj!=null){
			try{
				if (obj instanceof ResultSet){
					ResultSet rs = (ResultSet) obj;
					if (rs!=null){
						rs.close();
						rs=null;
					}
				}
				else if (obj instanceof PreparedStatement){
					PreparedStatement ps = (PreparedStatement) obj;
					if (ps!=null){
						ps.close();
						ps=null;
					}
				}
				else if (obj instanceof CallableStatement){
					CallableStatement cstmt = (CallableStatement) obj;
					if (cstmt!=null){
						cstmt.close();
						cstmt=null;
					}
				}
				else if (obj instanceof Connection){
					Connection conn = (Connection) obj;
					if (conn!=null){
						conn.close();
						conn=null;
					}			
				}
				else {
					Logs.error(obj.getClass().getName() +" nao esta mapeado para realizar o fechamento do recurso.");
				}
			}
			catch (Exception e) {
				Logs.error("Erro",e);
			}
		}
	}
}
