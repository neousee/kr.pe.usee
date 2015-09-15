package kr.pe.usee.ls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public class SqlMaker implements Serializable {

	SqlFileHandler sqlFile;
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String LF = "\r\n";
	public static final String UNIONALL = "UNION ALL";
	
	public SqlMaker() {
		sqlFile = new SqlFileHandler();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException {
		SqlMaker sm = new SqlMaker();
		MigListReader reader = new MigListReader();
    	reader.setSource(new File("D:\\myworkspace\\All_20150617.xlsx"),args);
		sm.getSql(reader);
	}
	
	public void getSql(MigListReader reader) throws IOException {
		String sql, fileName, teamCd;
		for(ListRow listRow:reader.getList()) {
			
			sql = makeSqlFile(listRow);
			fileName = "50-" + listRow.getTobeTable().getTable() + "-01.sql";
			teamCd = getTeamCd(listRow);
			SqlFileHandler.makeSqlFile(fileName, sql, teamCd);
		}
		
	}
	
	public String getTeamCd(ListRow row) {
		String teamCd = "";
		if( "POS" .equals( row.getTobeTable().getApCode() ) ) teamCd = "PO";
		if( "고객".equals( row.getTobeTable().getApCode() ) ) teamCd = "CU";
		if( "대금".equals( row.getTobeTable().getApCode() ) ) teamCd = "PR";
		if( "매입".equals( row.getTobeTable().getApCode() ) ) teamCd = "PU";
		if( "매출".equals( row.getTobeTable().getApCode() ) ) teamCd = "SA";
		if( "물류".equals( row.getTobeTable().getApCode() ) ) teamCd = "LO";
		if( "발주".equals( row.getTobeTable().getApCode() ) ) teamCd = "OR";
		if( "재고".equals( row.getTobeTable().getApCode() ) ) teamCd = "IN";
		if( "코드".equals( row.getTobeTable().getApCode() ) ) teamCd = "CG";
		return teamCd;
	}
	
	public String makeSqlFile(ListRow row) {

		StringBuffer sb = new StringBuffer();
		TobeTable tobe;
		int insertCnt;
		int selectCnt;
		
		tobe = row.getTobeTable();
		
		// INSERT begin
		sb.append("INSERT INTO "); sb.append(tobe.getSchema()+"."+tobe.getTable()+" (\r\n");
		insertCnt = 0;
		for( MappingRow mappingRow : row.getMappingRows() ) {
			if(insertCnt++!=0) sb.append(", "); else sb.append("  ");
			sb.append( mappingRow.getTobeCol().getColumn() + SqlMaker.SPACE +"/*"+mappingRow.getTobeCol().getComment() + "*/" + SqlMaker.LF);
		}
		sb.append(SqlMaker.LF+")"+SqlMaker.LF);
		//INSERT end
		
		// SELECT begin
		int idx = 0;
		for( AsisTable asisTable : row.getAsisTables() ) {
			sb.append("SELECT"+SqlMaker.LF);
			selectCnt = 0;
			for(MappingRow mapping:row.getMappingRows()) {
				if(selectCnt++!=0) sb.append("      , "); else sb.append("       ");
				for(AsisColumn as:mapping.asisColumns) {
					if(as.getTable()!=null&&as.getTable().equals(asisTable.getTable())) {
						sb.append(as.getColumn() + SqlMaker.SPACE +"/*"+as.getComment() + "*/" + SqlMaker.LF);
					}
				}
			}
			sb.append("  FROM"+SqlMaker.LF);
			sb.append("       "+asisTable.getSchema()+SqlMaker.DOT+asisTable.getTable()+SqlMaker.LF);
			sb.append(SqlMaker.UNIONALL+SqlMaker.LF);
		}
		sb.delete(sb.length()-SqlMaker.UNIONALL.length()-SqlMaker.LF.length()-1,sb.length()-1);
		sb.append(SqlMaker.LF+";");
		System.out.println(sb.toString()+SqlMaker.LF);
		
		return sb.toString();
	}

}
