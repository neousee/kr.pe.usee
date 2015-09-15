package kr.pe.usee.sql;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.pe.usee.sql.parser.ReservedWord;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlParser;

public class TableCatcher {


	File dir;
	File[] list;
	public static void main(String[] args) throws IOException, JSQLParserException {
		//TableCatcher tableCatcher = new TableCatcher("Z:\\60000.ÀÌÇà\\60100.IT°øÅë\\30.MigrationSQL");
		TableCatcher tableCatcher = new TableCatcher();
	}

	public File[] getDirList() {
		return list;
	}

	public TableCatcher(File inDir) throws IOException, JSQLParserException {
		this.dir = inDir;
		list = dir.listFiles();
		for(File file:list) {
			System.out.println(file);
			parseSql(file);
		}
		
	}
	
	public TableCatcher(String path) throws IOException, JSQLParserException {
		dir = new File(path);
		list = dir.listFiles();
		for(File file:list) {
			System.out.println(file);
			new TableCatcher(file);
		}
	}
	
	public TableCatcher() {
		parse();
	}

	public void parseSql(File sqlFile) throws IOException, JSQLParserException {
		//findTable("");
		String sql = "SELECT '001' AS APRV_TASK_CD ,1 AS APRV_1_SEQ ,1 AS APRV_2_SEQ ,'678' AS APRV_JOB_CD ,1 AS APRV_TYP_DIV_CD ,1 AS SRL_PRL_DIV_CD ,'N' AS EAPRV_TRGT_YN ,'MIGUSR_CG' AS REG_EMP_NO ,SYSDATE AS REG_DT ,'MIGUSR_CG' AS CHG_EMP_NO ,SYSDATE AS CHG_DT FROM DUAL ";
		sql += "UNION ALL SELECT '001', 2, 1, '738', 2, 2, 'N', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		/*sql += "SELECT '001', 2, 2, '708', 2, 2, 'N', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '001', 3, 1, '677', 1, 1, 'Y', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '001', 4, 1, '661', 1, 1, 'Y', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '002', 1, 1, '688', 1, 1, 'N', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '002', 2, 1, '738', 2, 2, 'N', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '002', 2, 2, '708', 2, 2, 'N', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '002', 3, 1, '687', 1, 1, 'Y', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		sql += "SELECT '002', 4, 1, '661', 1, 1, 'Y', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ;";
		*/
		sql = "SELECT '001' APRV_TASK_CD ,'' APRV_TASK_NM 			,'' MENU_ID 			,'MIGUSR_CG' REG_EMP_NO 			,SYSDATE REG_DT 			,'MIGUSR_CG' CHG_EMP_NO 			,SYSDATE CHG_DT 	FROM dual UNION ALL select ,'' '', 'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE from dual ; ";
		FileReader fr = null;
		String arr[];
		boolean isSelect = false;
		boolean isComment = false;
		StringBuffer sbAll = new StringBuffer();
		StringBuffer sb;
		List list = null;
		try {
			fr = new FileReader(sqlFile);
			
			BufferedReader br = new BufferedReader(fr);
			String s;
			
			while((s = br.readLine()) != null) { 
				System.out.println(s);
				arr = s.split(" ");
				sb = new StringBuffer();
				if(isSelect) {
					for(int i=0;i<arr.length;i++) {
						if(isComment) {
							if(arr[i].endsWith("*/")) {
								isComment = false;
							}
						}
						else if(arr[i].toUpperCase().equals("UNION") && !arr[i].toUpperCase().equals("ALL")){
							sb.append(arr[i]+" ALL ");
						} else if(arr[i].startsWith("AS")) {

						} else if(arr[i].startsWith("/*")) {
							isComment = true;
						} else if( arr[i].matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*") ) {
							if(arr[i].startsWith(",")) sb.append(", '' ");
							else sb.append(" ABC AAA, ");
						} else {
							if(arr[i].matches(".*[@].*")) {
								sb.append(arr[i].substring(0, arr[i].indexOf("@"))+" ");
							}
							else
							sb.append(arr[i]+" ");

						}
					}
				} else {
					if(s.trim().toUpperCase().startsWith("SELECT")) {
						isSelect = true; 
						arr = s.split(" ");
						sb = new StringBuffer();
						for(int i=0;i<arr.length;i++) {
							if(isComment) {
								if(arr[i].endsWith("*/")) {
									isComment = false;
								}
							}
							else if(arr[i].toUpperCase().equals("UNION") &&  !arr[i].toUpperCase().equals("ALL")){
								sb.append(arr[i]+" ALL ");
							} else if(arr[i].startsWith("AS")) {

							} else if(arr[i].startsWith("/*")) {
								isComment = true;
							} else if( arr[i].matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*") ) {

							} else {
								sb.append(arr[i]+" ");
							}
						}
					}
				}
				sbAll.append(sb.toString().toUpperCase());
			} 
			System.out.println(sbAll.toString());
	//		list = findTable3(sbAll.toString());
			//list = findTable3(sql);

			System.out.println(list.toString()); 
			fr.close();
		} catch (java.io.IOException ie) {
			System.out.println(ie.getMessage());
		}/* catch (JSQLParserException jpe) {
			System.out.println(jpe.getMessage());
		} catch (net.sf.jsqlparser.parser.TokenMgrError tme) {
			System.out.println(tme.getMessage());
		} catch (NullPointerException npe) {
			System.out.println(npe.getMessage());
		}*/
		
		parse();
	}
	
	private void parse() {
		
		String sample = "";
		
		sample += "INSERT INTO CG_APRV_LINE (";
		sample += "			 APRV_TASK_CD      /*½ÂÀÎ¾÷¹«ÄÚµå     */ ";
		sample += "			,APRV_1_SEQ        /*½ÂÀÎ1¼ø¹ø        */ ";
		sample += "			,APRV_2_SEQ        /*½ÂÀÎ2¼ø¹ø        */ ";
		sample += "			,APRV_JOB_CD       /*½ÂÀÎÁ÷¹«ÄÚµå     */ ";
		sample += "			,APRV_TYP_DIV_CD   /*½ÂÀÎÀ¯Çü±¸ºÐÄÚµå */ ";
		sample += "			,SRL_PRL_DIV_CD    /*Á÷·Äº´·Ä±¸ºÐÄÚµå */ ";
		sample += "			,EAPRV_TRGT_YN     /*ÀüÀÚ½ÂÀÎ´ë»ó¿©ºÎ */ ";
		sample += "			,REG_EMP_NO        /*µî·Ï»ç¿ø¹øÈ£     */ ";
		sample += "			,REG_DT            /*µî·ÏÀÏ½Ã         */ ";
		sample += "			,CHG_EMP_NO        /*º¯°æ»ç¿ø¹øÈ£     */ ";
		sample += "			,CHG_DT            /*º¯°æÀÏ½Ã         */ ";
		sample += ") ";
		sample += "SELECT '001'          AS APRV_TASK_CD      /*½ÂÀÎ¾÷¹«ÄÚµå     */  ";
		sample += "			,1              AS APRV_1_SEQ        /*½ÂÀÎ1¼ø¹ø        */";
		sample += "			,1              AS APRV_2_SEQ        /*½ÂÀÎ2¼ø¹ø        */";
		sample += "			,'678'          AS APRV_JOB_CD       /*½ÂÀÎÁ÷¹«ÄÚµå     */";
		sample += "			,1              AS APRV_TYP_DIV_CD   /*½ÂÀÎÀ¯Çü±¸ºÐÄÚµå */";
		sample += "			,1              AS SRL_PRL_DIV_CD    /*Á÷·Äº´·Ä±¸ºÐÄÚµå */";
		sample += "			,'N'            AS EAPRV_TRGT_YN     /*ÀüÀÚ½ÂÀÎ´ë»ó¿©ºÎ */";
		sample += "			,'MIGUSR_CG'    AS REG_EMP_NO        /*µî·Ï»ç¿ø¹øÈ£     */";
		sample += "			,SYSDATE        AS REG_DT            /*µî·ÏÀÏ½Ã         */";
		sample += "			,'MIGUSR_CG'    AS CHG_EMP_NO        /*º¯°æ»ç¿ø¹øÈ£     */";
		sample += "			,SYSDATE        AS CHG_DT            /*º¯°æÀÏ½Ã         */";
		sample += "	FROM DUAL";
		sample += "UNION";
		sample += "SELECT '001',	2,	1,	'738',	2,	2,	'N',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '001',	2,	2,	'708',	2,	2,	'N',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '001',	3,	1,	'677',	1,	1,	'Y',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '001',	4,	1,	'661',	1,	1,	'Y',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '002',	1,	1,	'688',	1,	1,	'N',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '002',	2,	1,	'738',	2,	2,	'N',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '002',	2,	2,	'708',	2,	2,	'N',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL UNION ";
		sample += "SELECT '002',	3,	1,	'687',	1,	1,	'Y',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL; COMMIT;";
		sample += "SELECT '002',	4,	1,	'661',	1,	1,	'Y',	'MIGUSR_CG', SYSDATE, 'MIGUSR_CG', SYSDATE FROM DUAL ";
		sample += ";";

		
		tokenize(sample);
		
	}
	
	private void tokenize(String sample) {
		sample = sample.toUpperCase();
		sample = removeEscape(sample);
		//System.out.println(sample);
		String[] stmt = sample.split(";");
		System.out.println("Statement : "+stmt.length);
		
		for(String query:stmt) {
			parseQuery(query);
		}
		
	}

	private void parseQuery(String query) {
		String[] t;
		if(query.trim().startsWith(ReservedWord.KWD_SELECT)) {
			System.out.println("SELECT Statement");
			parseSelect(query);
		} else if(query.trim().startsWith(ReservedWord.KWD_INSERT)) {
			System.out.println("INSERT Statement");
			t = parseInsert(query);
			print(t);
			if(t!=null) {
				parseSelect(t);
			}
		} else if(query.trim().startsWith(ReservedWord.KWD_COMMIT)) {
			System.out.println("COMMIT Statement");
		}
		System.out.println("Statement : "+query);
		
	}

	private void print(String[] t) {
		System.out.println("----------------------------");
		for(String a:t) {
			System.out.print(a+" ");
		}
		System.out.println("\r\n----------------------------");
		
	}

	private String[] parseInsert(String query) {
		// TODO Auto-generated method stub
		String[] parsedQuery = query.split(" ");
		ArrayList<String> t = new ArrayList<String>();
		String[] tt = null;
		boolean isSelect = false;
		for(String tk:parsedQuery) {
			if(!isSelect) {
				if(tk.trim().startsWith(ReservedWord.KWD_SELECT)) {
					isSelect = true;
					t.add(tk);
				} else {
					
				}
			} else {
				t.add(tk);
			}
		}
		if(t.size()!=0) {
			tt = new String[t.size()];
			t.toArray(tt);
		}
		return tt;
	}

	private String[] parseSelect(String[] tk) {
		String[] rt = null;
		
		ArrayList<String[]> selectQueryList = new ArrayList<String[]>();
		getSelectList(tk,selectQueryList);
		
		for(String[] selectTk:selectQueryList) {
			parseSelectSingle(selectTk);
		}
		return rt;		
	}
	
	private void parseSelectSingle(String[] tk) {
		if(tk[0].trim().startsWith("(")) {
			tk = removeBracket(tk);
		} 
		String[] fromTk = findFrom(tk);
		
		if(fromTk[0].trim().startsWith(ReservedWord.LEFT_PARA)) {
			
		} else {
			findTables(fromTk);
		}
	}
	
	private void findTables(String[] fromTk) {
		System.out.println(fromTk);
		
	}

	private String[] findFrom(String[] tk) {
		int idxFrom = 0;
		int idxWhere = 0;
		String rt[];
		for(int i=0;i<tk.length;i++) {
			if(tk[i].trim().equals(ReservedWord.KWD_FROM)) {
				idxFrom = i;
			} else if(tk[i].trim().equals(ReservedWord.KWD_WHERE)) {
				idxWhere = i;
			}
		}
		int end=0;
		if(idxWhere==0) {
			end = tk.length;
		} else {
			end = idxWhere;
		}
		rt = new String[end-idxFrom];
		for(int i = idxFrom;i<end;i++) {
			System.out.println(tk[i]);
			rt[i-idxFrom] = tk[i];
		}
		return rt;
	}

	private String[] removeBracket(String[] tk) {
		// TODO Auto-generated method stub
		return tk;
	}

	private void getSelectList(String[] rt,ArrayList<String[]> arrayList) {
		int fromIdx = 0;
		String[][] rtArray = new String[2][];

		for(int i=rt.length-1;i>-1;i--) {
			/*
			if(rt[i].startsWith(ReservedWord.KWD_FROM)) {
				fromIdx = i;
				selectTokens = new String[i+1];
				al.toArray(selectTokens);
				al = new ArrayList<String>();
				break;
			} else if(rt[i].startsWith(ReservedWord.KWD_GROUP_BY)) {
				
			} else */
			
			if(rt[i].startsWith(ReservedWord.KWD_UNION)) {
				System.out.println(ReservedWord.KWD_UNION);
				rtArray = splitArray(rt,i);
				break;
			} else if(rt[i].startsWith(ReservedWord.KWD_MINUS)) {
				break;
			} else if(rt[i].startsWith(ReservedWord.KWD_EXCEPT)) {
				break;
			} else if(rt[i].startsWith(ReservedWord.KWD_INTERSECT)) {
				break;
			} 
			/*
			else if(rt[i].startsWith(ReservedWord.KWD_LIMIT)) {
				
			} else if(rt[i].startsWith(ReservedWord.KWD_FOR_UPDATE)) {
				
			}*/ else {
				//parseSingleSelect(rt);
			}			
		}
		
		arrayList.add(rtArray[1]);
		if(rtArray[0]==null)
			return;
		else {
			getSelectList(rtArray[0],arrayList);
		}

	}

	private String[][] splitArray(String[] rt, int index) {
		String[] al1 = new String[index];
		String[] al2 = new String[rt.length-index];
		for(int i=0;i<index;i++) {
			al1[i] = rt[i];
		}
		
		for(int i=index;i<rt.length;i++) {
			al2[i-index] = rt[i];
		}
		
		String[][] rtArray = new String[2][];
		rtArray[0] = al1;
		rtArray[1] = al2;
		
		return rtArray;
	}
	
	private String[] parseSelect(String query) {
		String[] rt;
		String[] parsedQuery = query.split(" ");
		rt = parseSelect(parsedQuery);
		return rt;
	}

	/**
	 * @param sample
	 * @return
	 */
	private String removeEscape(String sample) {
		String t = sample;
		while(t.contains("\t"))
			t = t.replaceAll("\t", " ");
		while(t.contains("/***/"))
			t = t.replaceAll("/***/", "/*AAAA*/ ");
		while(t.contains("  "))
			t = t.replaceAll("  ", " ");
		
		//System.out.println(t);
		return t;
	}

	private void findTable4(String s) throws ParseException {
		ZqlParser p = null;
		p = new ZqlParser(new ByteArrayInputStream(s.getBytes()));
		ZStatement stmt = p.readStatement();		
	}

	private List findTable3(String s) throws JSQLParserException {
		System.out.println(s);
		Statement statement = CCJSqlParserUtil.parse(s);
		Select selectStatement = (Select) statement;
		//Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		return tableList;
	}


	private List findTable2(Reader reader) throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse(reader);
		Select selectStatement = (Select) statement;
		//Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		return tableList;
	}

	private List findTable(String s) throws JSQLParserException {
		
		CCJSqlParserManager pm = new CCJSqlParserManager();
		String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * /* ÇÑ±Û Å×½ºÆ® */ FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 "+
		" WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) AND ID2 IN (SELECT * FROM MY_TABLE6)" ;
		net.sf.jsqlparser.statement.Statement statement = pm.parse(new StringReader(sql));
		/* 
		now you should use a class that implements StatementVisitor to decide what to do
		based on the kind of the statement, that is SELECT or INSERT etc. but here we are only
		interested in SELECTS
		*/
		List tableList = null;
		if (statement instanceof Select) {
			Select selectStatement = (Select) statement;
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			tableList = tablesNamesFinder.getTableList(selectStatement);
			for (Iterator iter = tableList.iterator(); iter.hasNext();) {
				System.out.println(iter.next());
			}
		}	
		return tableList;
	}
}
