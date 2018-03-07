package vivo.odc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import vivo.odc.vo.upload.ArquivoResposta;
import vivo.odc.vo.upload.Registro;

import com.thoughtworks.xstream.XStream;

public class FileUtil {

	public static void createFileResposta(ArquivoResposta arquivoResposta, Map<String,Registro> hashZipFile ){
		
		String dataEnd = MasterDate.formatter(new Date(), null);
		dataEnd = dataEnd.replaceAll("/", "_");
		dataEnd = dataEnd.replaceAll(":", "_");
		dataEnd = dataEnd.replaceAll(" ", "_");
		String nomeArq = arquivoResposta.getInfo_envio().getNome_prestadora()+"_resposta_"+dataEnd+".xml";
		
        // Criando um objeto XStream          
        XStream xstream = new XStream();
        //forcando o processamento das annotations
 	    xstream.processAnnotations(ArquivoResposta.class);
        
		//File file = new File("W://Crm//Ouvidoria//Focus//Arquivos//Resposta//" + nomeArq);
 	    File file = new File("Z://Anatel//Respostas//A PROCESSAR//" + nomeArq);
 	   //File file = new File("c://TEMP//" + nomeArq);
		
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(file);
			// Passando os dados de Objetos Java para XML
			
			xstream.aliasPackage("", "com.gvt.bean.upload.ArquivoResposta");
			xstream.aliasPackage("", "com.gvt.bean.upload.Registro");
	        String xml = xstream.toXML(arquivoResposta);
	        xml = xml.replaceAll("__", "_");
	        arquivo.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			arquivo.write(xml);
			arquivo.flush();
			arquivo.close();
			//new ZipFiles().createZipFile(hashZipFile, nomeArq, file);
			//file.delete();
		} catch (IOException e) {
			e.printStackTrace();
			Logs.error("Erro ao criar arquivo de resposta. Todo processo será cancelado.",e);
			System.exit(-1);
		}		
	}
}
