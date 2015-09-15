package kr.pe.usee.ls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author Administrator
 *
 */
public class SqlFileHandler implements Serializable {

	public final static String SQL_DIR = "Z:\\60000.이행\\60100.IT공통\\30.MigrationSQL";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static void main(String[] args) {
		SqlFileHandler sfh = new SqlFileHandler();
	}
	
	/**
	 * DIR 수행
	 * @param d : directory
	 * @param teamCd : 팀명 코드
	 */
	public void getDir(File d, String teamCd) {
		
		for(File file : d.listFiles()) {
			System.out.println(file.getAbsolutePath());
		}
	}
	

	public static void makeSqlFile(String fileName, String sql, String teamCd) throws IOException {
		File dir = new File(SQL_DIR,teamCd);
		File file = new File(dir,fileName);
		//FileOutputStream fos = new FileOutputStream(file);
		FileWriter fw = new FileWriter(file);
		fw.write(sql);
		fw.flush();
		fw.close();
	}
}
