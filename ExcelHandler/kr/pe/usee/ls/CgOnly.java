package kr.pe.usee.ls;

import java.io.IOException;

public class CgOnly {
	
	ExcelHandler eh;
	public static String t;

	public static void main(String[] args) throws IOException {
		//ExcelHandler eh = new ExcelHandler("D:\\myworkspace\\���൥���͸������Ǽ�_�ڵ�_�������_v0.2_20150701_ext.xlsx");
		ExcelHandler eh = new ExcelHandler("D:\\myworkspace\\���纻 ���൥���͸������Ǽ�_���_v1.9(�����۾�)_ext.xlsx");
		//ExcelHandler eh = new ExcelHandler("D:\\myworkspace\\���൥���͸������Ǽ�_�ڵ�_������_v0.2_20150701_ext.xlsx");
		eh.read();
		//eh.read2();
		SqlMaker sqlMaker = new SqlMaker();
		String teamCd = "PR";
		String fileName = null;
		for(ListRow listRow : eh.getListRow() ) {
			fileName = "50-"+listRow.getTobeTable().getTable()+"-01.sql";
			t = sqlMaker.makeSqlFile(listRow);
			SqlFileHandler.makeSqlFile(fileName, t, "PR");
		}
	}
	
	public CgOnly() {
		eh = new ExcelHandler();
	}
	
	public CgOnly(String filePath) {
		eh = new ExcelHandler(filePath);
	}
	
	public ListRow[] getList() {
		ListRow[] list = null;
		return list;
	}
	
	

}
