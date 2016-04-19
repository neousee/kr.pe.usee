package kr.pe.usee.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import kr.pe.usee.db.mysql.MysqlDb;
import kr.pe.usee.db.mysql.ProdInfo2;
import kr.pe.usee.io.ExcelReadHandler;

public class ExcelTemplateMaker {
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		ExcelReadHandler kbfSource = new ExcelReadHandler("D:\\source\\KBFWorkingReview_ext.xlsx");
		kbfSource.getKbfData();
		Hashtable<String,PogRow> pogList;
		//ExcelReadHandler pogProdSource = new ExcelReadHandler("D:\\source\\PogListCate.xls");
		//pogProdSource.getPogData();
		
		
		MysqlDb db = new MysqlDb();
		/* SELECT `prodCd`, `nm`, `tl1`, `tl2`, `tl3`, `tl4` FROM `poglistcate` */
		pogList = db.selectPogList();
		Hashtable<String,PogRed> pogRed = db.getPogRed();
		Hashtable<String,KbfAttrValueList> attrValueListHashtable = db.selectAttrValueSetList();
		
		// 대분류 하나
        Workbook wb;
        ArrayList<GrpResultSet> grpResultSet;        

        for(String lvl:level1) 
        {
        
        	if("16.생활잡화".equals(lvl))
        	{	
			System.out.println(lvl);
			String file = "D:\\source\\"+lvl+".KBFWorkingTemplate.xls";
			wb = new HSSFWorkbook();

	        Map<String, CellStyle> styles = createStyles(wb);
			//if(wb instanceof XSSFWorkbook) file += "x";
			
	        // 중분류별로 만든다.
	        grpResultSet = db.getGrpResultSet(lvl.substring(3));
	        ArrayList<ProdInfo2> listProdInfo;
	        
	        for(GrpResultSet grpResult:grpResultSet) {
	        	System.out.println(grpResult.tl2);
	        	System.out.println(grpResult.getInStr());
	        	listProdInfo = db.getMoreGrpResult(grpResult);
	        	
	        	Sheet sheet = wb.createSheet(grpResult.tl2);
	        	setSheetStyle(sheet);
	            
	        	// create header
	        	Row headRow = sheet.createRow(0);
	        	Cell hCellLvl3 = headRow.createCell(0);
	        	hCellLvl3.setCellValue("소분류");
	        	hCellLvl3.setCellStyle(styles.get("header"));
	        	Cell hCellProdCd = headRow.createCell(1);
	        	hCellProdCd.setCellValue("상품코드");
	        	hCellProdCd.setCellStyle(styles.get("header"));
	        	Cell hCellProdName = headRow.createCell(2);
	        	hCellProdName.setCellValue("상품명");
	        	hCellProdName.setCellStyle(styles.get("header"));
	        	
	        	
	        	Set<String> set1 = db.getSelectedList(grpResult);
	        	/**
	        	 * SELECT `l1`, `l2`, `l3`, `l4`, `attrlist`, `at1`, `at2`, `at3`, `at4` FROM `selectedList` WHERE l1 = ? AND l2 = ?
	        	 */
	        	String[] headerAttrList = new String[set1.size()];
	        	set1.toArray(headerAttrList);
	        	Cell hAttr;
	        	for(int x=3;x-3<headerAttrList.length;x++) {
		        		hAttr = headRow.createCell(x);
		        		hAttr.setCellValue(headerAttrList[x-3]);
			        	hAttr.setCellStyle(styles.get("header"));	
	        	}
	        	// 시트별로 상품을 나열한다.
	        	ProdInfo2 prodInfo;
	        	
	        	/**
	        	 * 상품별로 Loop
	        	 */
	        	Cell cellLvl3;
	        	Cell cellProdCd;
	        	Cell cellProdName;
	        	HSSFDataValidation hSSFDataValidation;
	        	PogRow pogRow;
	        	for(int i=0;i<listProdInfo.size();i++) {
	        		prodInfo = listProdInfo.get(i);
		        	Row row = sheet.createRow(i+1);
		        	/*
					for(int j=0;j<level1.length;j++) {
		        		Cell cell = row.createCell(j);
		        		cell.setCellValue(level1[j]);
		        	}
		        	*/
		        	pogRow = pogList.get(prodInfo.getProdCd());
		        	KbfRow prodDefinedList = kbfSource.getKbfList().get(pogRow.getL1()+":"+pogRow.getL2()+":"+pogRow.getL3());
		        	cellLvl3 = row.createCell(0);
		        	cellLvl3.setCellValue(pogRow.getL3());
		        	cellLvl3.setCellStyle(styles.get("c_header"));
		        	cellProdCd = row.createCell(1);
		        	cellProdCd.setCellValue(prodInfo.getProdCd());
		        	cellProdCd.setCellStyle(styles.get("c_header"));
		        	cellProdName = row.createCell(2);
		        	cellProdName.setCellValue(prodInfo.getNm());
		        	cellProdName.setCellStyle(styles.get("c_header"));
		        	Cell attrCell;
		        	ProdInfo pi = new ProdInfo(prodInfo.getProdCd()+"@"+prodInfo.getNm());
		        	Hashtable<String, KbfAttrValueList> hashtable = db.getAttrValList(pi);
		        	String headerAttrName;
		        	boolean isMatch;
		        	for(int j=3;j-3<headerAttrList.length;j++)
		        	{
		        		headerAttrName = headerAttrList[j-3];
		        		//정의된 속성이 그룹 속성에 있다면
		        		try {
		        		if(prodDefinedList.hasAttr(headerAttrName)) {
		        			//System.out.println("정의된 속성이 그룹 속성에 있다면 : YES");
		        			isMatch = true;
		        		} else {
		        			//System.out.println("정의된 속성이 그룹 속성에 있다면 : NO");
		        			isMatch = false;
		        		}
		        		attrCell = row.createCell(j);
		        		
		        		//attrCell.setCellValue(headerAttrList[j-3]);
		        		KbfAttrValueList kavl = hashtable.get(headerAttrList[j-3]);
		        		
		        		hSSFDataValidation = makeDataValidationList(pi,pogRed,headerAttrName,kavl,i+1,j,attrCell,styles,isMatch);
		        		if(hSSFDataValidation != null) sheet.addValidationData(hSSFDataValidation);
		        		} catch(NullPointerException nep) {
		        			System.out.println(nep.getMessage());
		        		}
		        	}
	        	}	        	
	        	/**
	        	 * 상품별로 Loop End
	        	 */
	        }
                //sheet.addValidationData(brandDataValidation);
                //sheet.addValidationData(COODataValidation);
	        

	        FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
        	}
		}
	}
		//XSSFSheet aaaa = template.getSheetData("aaaa");
		//System.out.println(aa);
		// 대분류별로 파일을 만든다.
		
		// 중분류별로 시트를 만든다.
		// make excel

        //turn off gridlines
    
	private static HSSFDataValidation makeDataValidationList(ProdInfo pi, Hashtable<String, PogRed> pogRed, String attrName, KbfAttrValueList kavl, int row, int col, Cell cell,Map<String, CellStyle> styles,boolean isMatch) {
		HSSFDataValidation DataValidation = null;
		if(isMatch) {
		if(kavl != null) {
			if("단위규격".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_light_orange"));
//		        String[] Formula = new String[]{};
//			    CellRangeAddressList AddressList = new CellRangeAddressList();
//			    //드롭다운 박스 범위 지정.
//			    AddressList.addCellRangeAddress(row, col, row, col);
//			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
//			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
//			    //공백무시 옵션 true : 무시, false: 무시안함                 
//			    DataValidation.setEmptyCellAllowed(false);
//			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
//			    DataValidation.setShowPromptBox(true);
//			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
//			    DataValidation.setSuppressDropDownArrow(false);
//			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
//			    DataValidation.createErrorBox("Don't Edit this cell", "원산지 속성값은 입력 불필요");
//			    //설명메시지 createPromptBox(String title, String text)
//			    DataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
//			    /*오류메시지 스타일(중지,경보,정보).
//			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
//			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
//			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
//			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("브랜드".equals(attrName)) {
				
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getBrnd_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "브랜드 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("제조사".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getVen_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "제조사 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("원산지".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getOrgnp_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("가격".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("맛가미여부".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{"Yes","No"};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "원산지 속성값은 입력 불필요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Yes or No", "Yes No 중 선택하세요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else {
		        String[] Formula = kavl.toStringArray();
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Select Only One", "리스트중 하나를 골라야 합니다.");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Only One", "리스트에서 하나를 골라주세요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);

				cell.setCellStyle(styles.get("cell_lemon"));
				
			}
		} else {
			
			if("단위규격".equals(attrName)) {

//		        String[] Formula = new String[]{};
//			    CellRangeAddressList AddressList = new CellRangeAddressList();
//			    //드롭다운 박스 범위 지정.
//			    AddressList.addCellRangeAddress(row, col, row, col);
//			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
//			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
//			    //공백무시 옵션 true : 무시, false: 무시안함                 
//			    DataValidation.setEmptyCellAllowed(false);
//			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
//			    DataValidation.setShowPromptBox(true);
//			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
//			    DataValidation.setSuppressDropDownArrow(false);
//			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
//			    DataValidation.createErrorBox("Don't Edit this cell", "원산지 속성값은 입력 불필요");
//			    //설명메시지 createPromptBox(String title, String text)
//			    DataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
//			    /*오류메시지 스타일(중지,경보,정보).
//			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
//			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
//			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
//			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("브랜드".equals(attrName)) {
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getBrnd_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "브랜드 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("원산지".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getOrgnp_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("제조사".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getVen_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "입력하지마세요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "제조사 속성값은 입력 불필요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("맛가미여부".equals(attrName)) {
		        String[] Formula = new String[]{"Yes","No"};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //드롭다운 박스 범위 지정.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //공백무시 옵션 true : 무시, false: 무시안함                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
			    DataValidation.setShowPromptBox(true);
			    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "원산지 속성값은 입력 불필요");
			    //설명메시지 createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Yes or No", "Yes No 중 선택하세요");
			    /*오류메시지 스타일(중지,경보,정보).
			    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
			    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
			    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else {
				
			}
				
		}
		} else { // not match
			cell.setCellValue("");
			cell.setCellStyle(styles.get("cell_bg"));
		}
		return DataValidation;
	}

	private static void setSheetStyle(Sheet sheet) {
    
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        //the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);
	}
	/*
        Cell cell;
        //the header row: centered text in 48pt font
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < titles.length; i++) {
            cell = headerRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(styles.get("header"));
        }
        //columns for 11 weeks starting from 9-Jul
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        //calendar.setTime(fmt.parse("9-Jul"));
        calendar.set(Calendar.YEAR, year);
        for (int i = 0; i < 11; i++) {
            cell = headerRow.createCell(titles.length + i);
            cell.setCellValue(calendar);
            cell.setCellStyle(styles.get("header_date"));
            calendar.roll(Calendar.WEEK_OF_YEAR, true);
        }
        //freeze the first row
        sheet.createFreezePane(0, 1);
        Row row;
        int rownum = 1;
        for (int i = 0; i < data.length; i++, rownum++) {
            row = sheet.createRow(rownum);
            if(data[i] == null) continue;

            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(j);
                String styleName;
                boolean isHeader = i == 0 || data[i-1] == null;
                switch(j){
                    case 0:
                        if(isHeader) {
                            styleName = "cell_b";
                            cell.setCellValue(Double.parseDouble(data[i][j]));
                        } else {
                            styleName = "cell_normal";
                            cell.setCellValue(data[i][j]);
                        }
                        break;
                    case 1:
                        if(isHeader) {
                            styleName = i == 0 ? "cell_h" : "cell_bb";
                        } else {
                            styleName = "cell_indented";
                        }
                        cell.setCellValue(data[i][j]);
                        break;
                    case 2:
                        styleName = isHeader ? "cell_b" : "cell_normal";
                        cell.setCellValue(data[i][j]);
                        break;
                    case 3:
                        styleName = isHeader ? "cell_b_centered" : "cell_normal_centered";
                        cell.setCellValue(Integer.parseInt(data[i][j]));
                        break;
                    case 4: {
                       // calendar.setTime(fmt.parse(data[i][j]));
                        calendar.set(Calendar.YEAR, year);
                        cell.setCellValue(calendar);
                        styleName = isHeader ? "cell_b_date" : "cell_normal_date";
                        break;
                    }
                    case 5: {
                        int r = rownum + 1;
                        String fmla = "IF(AND(D"+r+",E"+r+"),E"+r+"+D"+r+",\"\")";
                        cell.setCellFormula(fmla);
                        styleName = isHeader ? "cell_bg" : "cell_g";
                        break;
                    }
                    default:
                        styleName = data[i][j] != null ? "cell_blue" : "cell_normal";
                }

                cell.setCellStyle(styles.get(styleName));
            }
        }

        //group rows for each phase, row numbers are 0-based
        sheet.groupRow(4, 6);
        sheet.groupRow(9, 13);
        sheet.groupRow(16, 18);

        //set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 256*6);
        sheet.setColumnWidth(1, 256*33);
        sheet.setColumnWidth(2, 256*20);
        sheet.setZoom(3, 4);


        // Write the output to a file
        String file = "D:\\source\\KBFWorkingTemplate.xlsx";
        if(wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
	}

    /**
     * create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT .getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.AQUA .getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_aqua", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_lavender", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_coral", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON .getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_lemon", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_light_orange", style);

        return styles;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
    

    private static SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM");

    private static final String[] level1 = {"01.곡물","02.과일","03.채소","04.수산","05.축산","06.조리식품","07.낙농","08.냉장","09.냉동","10.일반식품","11.조미료류","12.면과자","13.음료","14.주류","15.일상용품","16.생활잡화","17.MS"};
    private static final String[] titles = {"SKU","과일","채소","수산","축산","조리식품","낙농","냉장","냉동","일반식품","조미료류","면과자","음료","주류","일상용품","생활잡화","MS"};

    //sample data to fill the sheet.
    private static final String[][] data = {
            {"1.0", "Marketing Research Tactical Plan", "J. Dow", "70", "9-Jul", null,
                "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
            null,
            {"1.1", "Scope Definition Phase", "J. Dow", "10", "9-Jul", null,
                "x", "x", null, null,  null, null, null, null, null, null, null},
            {"1.1.1", "Define research objectives", "J. Dow", "3", "9-Jul", null,
                    "x", null, null, null,  null, null, null, null, null, null, null},
            {"1.1.2", "Define research requirements", "S. Jones", "7", "10-Jul", null,
                "x", "x", null, null,  null, null, null, null, null, null, null},
            {"1.1.3", "Determine in-house resource or hire vendor", "J. Dow", "2", "15-Jul", null,
                "x", "x", null, null,  null, null, null, null, null, null, null},
            null,
            {"1.2", "Vendor Selection Phase", "J. Dow", "19", "19-Jul", null,
                null, "x", "x", "x",  "x", null, null, null, null, null, null},
            {"1.2.1", "Define vendor selection criteria", "J. Dow", "3", "19-Jul", null,
                null, "x", null, null,  null, null, null, null, null, null, null},
            {"1.2.2", "Develop vendor selection questionnaire", "S. Jones, T. Wates", "2", "22-Jul", null,
                null, "x", "x", null,  null, null, null, null, null, null, null},
            {"1.2.3", "Develop Statement of Work", "S. Jones", "4", "26-Jul", null,
                null, null, "x", "x",  null, null, null, null, null, null, null},
            {"1.2.4", "Evaluate proposal", "J. Dow, S. Jones", "4", "2-Aug", null,
                null, null, null, "x",  "x", null, null, null, null, null, null},
            {"1.2.5", "Select vendor", "J. Dow", "1", "6-Aug", null,
                null, null, null, null,  "x", null, null, null, null, null, null},
            null,
            {"1.3", "Research Phase", "G. Lee", "47", "9-Aug", null,
                null, null, null, null,  "x", "x", "x", "x", "x", "x", "x"},
            {"1.3.1", "Develop market research information needs questionnaire", "G. Lee", "2", "9-Aug", null,
                null, null, null, null,  "x", null, null, null, null, null, null},
            {"1.3.2", "Interview marketing group for market research needs", "G. Lee", "2", "11-Aug", null,
                null, null, null, null,  "x", "x", null, null, null, null, null},
            {"1.3.3", "Document information needs", "G. Lee, S. Jones", "1", "13-Aug", null,
                null, null, null, null,  null, "x", null, null, null, null, null},
    };
}
