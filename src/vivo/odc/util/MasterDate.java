package vivo.odc.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import vivo.odc.dao.OraHolidaysDAO;

public class MasterDate {
	
	public static void main(String[] args) {
		Date ultimoDiaUtil = MasterDate.incDay(new Date(), -1);
		System.out.println(MasterDate.formatter(new Date(), "dd/MM/yyyy"));
		while (true){
			if (MasterDate.isSaturday(ultimoDiaUtil) || MasterDate.isSunday(ultimoDiaUtil) || new OraHolidaysDAO().isHoliday(MasterDate.formatter(ultimoDiaUtil,"dd/MM/yyyy"))){
				ultimoDiaUtil = MasterDate.incDay(ultimoDiaUtil, -1);
			}
			else {
				break;//achou ultimo dia util
			}
		}
	}	
	
	public static String getSaudacao(){
		String retorno = "Boa noite";
		int hora = extractHora(new Date());
		if (hora>=0 && hora<=11){
			retorno = "Bom dia";
		}
		else if (hora>=12 && hora<=17){
			retorno = "Boa tarde";
		}
		return retorno;
	}
	
	public static String formatter(Date date,String formatter){
		SimpleDateFormat format = null;
		if (formatter==null){
			format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		}
		else{
			format = new SimpleDateFormat(formatter);
		}
		if(date == null){
			return "";
		}else{
			return format.format(date);	
		}
		
	}
	
	public static Date createDate(int dia, int mes, int ano) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(ano, mes - 1, dia);
		return calendar.getTime();
	}
	
	public static Date createDate(int dia, int mes, int ano,int hora,int minutos,int segundos) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(ano, mes - 1, dia,hora,minutos,segundos);
		return calendar.getTime();
	}
	
	public static java.sql.Date timeStampToSqlDate(Timestamp data) {
		java.sql.Date d = new java.sql.Date(data.getTime());
		return d;
	}
	
	public static Timestamp dateToTimeStamp(Date data) {
		Timestamp d = new java.sql.Timestamp(data.getTime()); ;
		return d;
	}
	
	public static Timestamp createDateHora(String dataHora) {
		String dataHoraSplit[] = dataHora.split(" ");
		String data[] = dataHoraSplit[0].split("/");
		String hora[] = dataHoraSplit[1].split(":");
		Date date = createDate(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(hora[0]),Integer.parseInt(hora[1]),Integer.parseInt(hora[2]));
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp createDateHora2(String dataHora) {		
		if("".equals(dataHora)){
			return null;
		}else{
			String dataHoraSplit[] = dataHora.split(" ");
			String data[] = dataHoraSplit[0].split("/");
			String hora[] = dataHoraSplit[2].split(":");
			Date date = createDate(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(hora[0]),Integer.parseInt(hora[1]),Integer.parseInt(hora[2]));
			return new Timestamp(date.getTime());	
		}
	}
	
	public static Date incDay(Date data, int incr){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		c.add(Calendar.DAY_OF_YEAR, incr);
		return c.getTime();
	}
	
	public static boolean isMonday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY;
	}
	
	public static boolean isFriday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY;
	}
	
	public static boolean isSaturday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY;
	}
	
	public static boolean isSunday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
	}
	
	public static int extractHora(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/** 
	 * <b>asDateHoraMaxima</b><br><br>
	 * public static Date <b>asDateHoraMaxima</b>()<br><br>
	 * Converte a hora de uma determinada data para 23:59:59.
	 * @param data - Data no qual se quer modificar a hora.
	 * @return Data do sistema e hora com o valor 23:59:59.
	 * */
	public static Date asDateHoraMaxima(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		return calendar.getTime();
	}
	
	/** 
	 * <b>asDateZeroHora</b><br><br>
	 * public static Date <b>asDateZeroHora</b>()<br><br>
	 * Converte a hora de uma determinada data para 00:00:00.
	 * @param data - Data no qual se quer modificar a hora.
	 * @return Data do sistema e hora com o valor 00:00:00.
	 * */
	public static Date asDateZeroHora(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date incMonth(Date data, int incr){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		c.add(Calendar.MONTH, incr);
		return c.getTime();
	}
	
	public String createLastYearMonth(int meses){
		Date now = new Date();
		String retorno = "";
		for (int i=1;i<=meses;i++){
			retorno +="'"+MasterDate.formatter(now, "yyyy/MM")+"',";
			now = incMonth(now, -1);
		}
		retorno = retorno.substring(0, retorno.length()-1);
		return retorno;
	}
	
	public static Date createDate(String data){
		String data1Split[] = data.split("/");
		Date dataFinal = MasterDate.asDateZeroHora(MasterDate.createDate(Integer.parseInt(data1Split[0]), Integer.parseInt(data1Split[1]), Integer.parseInt(data1Split[2])));
		return dataFinal;
	}
	
	public static Timestamp createDate2(String data){
		String data1Split[] = data.split("/");
		Date dataFinal = MasterDate.asDateZeroHora(MasterDate.createDate(Integer.parseInt(data1Split[0]), Integer.parseInt(data1Split[1]), Integer.parseInt(data1Split[2])));
		return new Timestamp(dataFinal.getTime());
	}
	
	public static Date ultimoDiaDoMes(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH);
		int ano = c.get(Calendar.YEAR);
		return createDate(dia, mes+1, ano);
	}
	
	public static int extractAno(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.YEAR);
	}
	
	public static int extractMes(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return (c.get(Calendar.MONTH) + 1);
	}
	
	public static int extractDia(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
}
