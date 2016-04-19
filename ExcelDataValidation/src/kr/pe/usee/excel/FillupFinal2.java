package kr.pe.usee.excel;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kr.pe.usee.db.excel.KbfAttr;
import kr.pe.usee.db.excel.KbfAttrValueList2;
import kr.pe.usee.db.excel.Prod2;
import kr.pe.usee.db.mysql.MysqlDb;
import kr.pe.usee.io.ExcelReadHandler;

public class FillupFinal2 {
	
	private static final String[] level1 = {"001.과일","002.곡물","003.채소","004.수산","005.축산","006.조리식품","007.낙농","008.냉장","009.냉동","010.MS","011.음료","012.주류","013.일반식품","014.조미료류","015.면과자","016.일상용품","017.생활잡화"};
	//private static final String[] aa = {"국산","국내산","수입산","원양산","원양산(남대서양)","원양산(대서양)","원양산(라스팔마스)","원양산(북태평양)","원양산(인도양)","원양산(태평양)","별도표기","아르헨티나","오스트리아","호주","벨기에","브라질","캐나다","칠레","중국","독일","덴마크","에콰도르","스페인","프랑스","기니","과테말라","헝가리","인도네시아","이스라엘","인도","이란","일본","북한","모로코","미얀마","모리타니아","멕시코","말레이시아","네덜란드","노르웨이","뉴질랜드","페루","필리핀","파키스탄","포르투갈","러시아","사우디아라비아","세네갈","태국","통가","터키","대만","미국","우즈베키스탄","베트남","남아프리카","기타"};
    
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		ExcelReadHandler kbfSource = new ExcelReadHandler("D:\\source\\KBFWorkingReview_ext.xlsx");
		BufferedReader in = new BufferedReader(new FileReader("D:\\work\\KBF_reply_0225.txt"));
	    String s;
		MysqlDb db = new MysqlDb();
	    Hashtable<String, SelectedData> selectedTable = db.selectOldKbf();
	    /*
	    Hashtable<String, SelectedData> selectedTable = new Hashtable<String, SelectedData>(); 
	    SelectedData tData = new SelectedData();
	    while ((s = in.readLine()) != null) {
	    	String[] ts = s.split(":");
	    	if(ts != null && ts.length == 5) {
	    	tData = new SelectedData(s);
	    	selectedTable.put(tData.getProd_cd()+":"+tData.getKbfAttrNm(), tData);
	    	}
	    }
	    */
	    in.close();
		kbfSource.getKbfData();
		
		
		// 대분류 하나
        Workbook wb;      

    	// Total KBF 리스트
		Hashtable<String, ArrayList<KbfAttrValueList2>> attrValueListHashtable = db.selectKbfAttrValueSetList();
		
        for(String l1Cd:level1) 
        {        
        	{	
			System.out.println(l1Cd);
			String file = "D:\\source\\"+l1Cd+".KBFWorkingTemplate0226.xls";
			wb = new HSSFWorkbook();

	        Map<String, CellStyle> styles = createStyles(wb);
	        
			
	        // 중분류별로 만든다.
	        Hashtable<String,String> l2List = db.getL2List(l1Cd.substring(0,3));
	        String l2Cd = "";

	        Enumeration<String> keys = l2List.keys();
		    for(int i=0;i<l2List.size();i++) {
		    	if( keys.hasMoreElements() )
	        	l2Cd = keys.nextElement();
	        	System.out.println(l2Cd);
	        	
	        	Sheet sheet = wb.createSheet(l2List.get(l2Cd));
	        	//sheet.protectSheet("password");	        	
	        	//setSheetStyle(sheet);
	            
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
	        	Cell hCellProdKbfName = headRow.createCell(3);
	        	hCellProdKbfName.setCellValue("KBF속성명");
	        	hCellProdKbfName.setCellStyle(styles.get("header"));
	        	Cell hCellProdKbfValList = headRow.createCell(4);
	        	hCellProdKbfValList.setCellValue("KBF속성값");
	        	hCellProdKbfValList.setCellStyle(styles.get("header"));
	        	Cell hCellPrevKbfVal = headRow.createCell(5);
	        	hCellPrevKbfVal.setCellValue("참고값");
	        	hCellPrevKbfVal.setCellStyle(styles.get("header"));
	        	Cell hCellNonExist = headRow.createCell(6);
	        	hCellNonExist.setCellValue("선택값 없음");
	        	hCellNonExist.setCellStyle(styles.get("header"));
	        	
	        	// 중분류별 상품 목록
	    		ArrayList<Prod2> prodList;
	    		prodList = db.getProdList2(l1Cd.substring(0,3),l2Cd);
	        	// 시트별로 상품을 나열한다.
	        	Prod2 prodInfo;
	        	
	        	/**
	        	 * 상품별로 Loop
	        	 */
	        	Cell cellLvl3;
	        	Cell cellProdCd;
	        	Cell cellProdName;
	        	Cell kbfAttrName;
	        	Cell kbfAttrSet;
	        	for(int ii=0 ; ii<prodList.size(); ii++) {
	        		prodInfo = prodList.get(ii);
		        	Row row = sheet.createRow(ii+1);
		        	
		        	cellLvl3 = row.createCell(0);
		        	cellLvl3.setCellValue(prodInfo.getL3_NM());
		        	cellLvl3.setCellStyle(styles.get("cell_lemon"));
		        	
		        	cellProdCd = row.createCell(1);
		        	cellProdCd.setCellValue(prodInfo.getPROD_CD());
		        	cellProdCd.setCellStyle(styles.get("cell_lemon"));
		        	
		        	cellProdName = row.createCell(2);
		        	cellProdName.setCellValue(prodInfo.getPROD_NM());
		        	cellProdName.setCellStyle(styles.get("cell_lemon"));
		        	
		        	kbfAttrName = row.createCell(3);
		        	kbfAttrName.setCellValue(prodInfo.getKBF_ATTR_NM());
		        	kbfAttrName.setCellStyle(styles.get("cell_lemon"));
		        	
		        	kbfAttrSet = row.createCell(4);
		        	if(	selectedTable.containsKey(prodInfo.getPROD_CD()+":"+prodInfo.getKBF_ATTR_NM()) ) 
		        	{
		        		kbfAttrSet.setCellStyle(styles.get("cell_coral"));	
		        	} else { 
		        		kbfAttrSet.setCellStyle(styles.get("cell_light_orange"));	
		        	}
		        		        	
		        	if(prodInfo.getKBF_ATTR_NM().equals("단위규격") || prodInfo.getKBF_ATTR_NM().equals("브랜드") || prodInfo.getKBF_ATTR_NM().equals("원산지")) {
		        		
		        	} else {
		        		String[] Formula = getArraySet(attrValueListHashtable.get(prodInfo.getKBF_ATTR_ID()));
			        
				    CellRangeAddressList AddressList = new CellRangeAddressList();
				    //드롭다운 박스 범위 지정.
				    AddressList.addCellRangeAddress(ii+1, 4, ii+1, 4);
				    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
				    HSSFDataValidation DataValidation = new HSSFDataValidation(AddressList, Constrain);
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
				    
		        	sheet.addValidationData(DataValidation);
		        	}

		        	if(	selectedTable.containsKey(prodInfo.getPROD_CD()+":"+prodInfo.getKBF_ATTR_NM()) ) 
		        	{
			        	kbfAttrName = row.createCell(5);
			        	kbfAttrName.setCellValue(selectedTable.get(prodInfo.getPROD_CD()+":"+prodInfo.getKBF_ATTR_NM()).getKbfAttrValue());
			        	kbfAttrName.setCellStyle(styles.get("cell_lemon"));	
		        	} 
	        	}	    
	        }
	        FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
        	}
		}
	}

	private static String[] getArraySet(ArrayList<KbfAttrValueList2> arrayList) {
		if(arrayList == null) return new String[0];
		String[] t = new String[arrayList.size()];
		for(int i=0;i<arrayList.size();i++) {
			KbfAttrValueList2 KbfAttrValue = arrayList.get(i);
			t[i] = KbfAttrValue.getKBF_ATTR_VAL_NM();
		}
		return t;
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
        style.setLocked(true);
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
    
    
	private static HSSFDataValidation makeDataValidationList(ProdInfo pi, Hashtable<String, PogRed> pogRed, String attrName, KbfAttrValueList2 kavl, int row, int col, Cell cell,Map<String, CellStyle> styles,boolean isMatch) {
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
		        //String[] Formula = kavl.toStringArray();
		        String[] Formula = {"1","2"}; //kavl.toStringArray();
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

}
