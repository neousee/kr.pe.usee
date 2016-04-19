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
	
	private static final String[] level1 = {"001.����","002.�","003.ä��","004.����","005.���","006.������ǰ","007.����","008.����","009.�õ�","010.MS","011.����","012.�ַ�","013.�Ϲݽ�ǰ","014.���̷��","015.�����","016.�ϻ��ǰ","017.��Ȱ��ȭ"};
	//private static final String[] aa = {"����","������","���Ի�","�����","�����(���뼭��)","�����(�뼭��)","�����(���ȸ���)","�����(�������)","�����(�ε���)","�����(�����)","����ǥ��","�Ƹ���Ƽ��","����Ʈ����","ȣ��","���⿡","�����","ĳ����","ĥ��","�߱�","����","����ũ","���⵵��","������","������","���","���׸���","�밡��","�ε��׽þ�","�̽���","�ε�","�̶�","�Ϻ�","����","�����","�̾Ḷ","��Ÿ�Ͼ�","�߽���","�����̽þ�","�״�����","�븣����","��������","���","�ʸ���","��Ű��ź","��������","���þ�","����ƶ���","���װ�","�±�","�밡","��Ű","�븸","�̱�","���Ű��ź","��Ʈ��","��������ī","��Ÿ"};
    
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
		
		
		// ��з� �ϳ�
        Workbook wb;      

    	// Total KBF ����Ʈ
		Hashtable<String, ArrayList<KbfAttrValueList2>> attrValueListHashtable = db.selectKbfAttrValueSetList();
		
        for(String l1Cd:level1) 
        {        
        	{	
			System.out.println(l1Cd);
			String file = "D:\\source\\"+l1Cd+".KBFWorkingTemplate0226.xls";
			wb = new HSSFWorkbook();

	        Map<String, CellStyle> styles = createStyles(wb);
	        
			
	        // �ߺз����� �����.
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
	        	hCellLvl3.setCellValue("�Һз�");
	        	hCellLvl3.setCellStyle(styles.get("header"));
	        	Cell hCellProdCd = headRow.createCell(1);
	        	hCellProdCd.setCellValue("��ǰ�ڵ�");
	        	hCellProdCd.setCellStyle(styles.get("header"));
	        	Cell hCellProdName = headRow.createCell(2);
	        	hCellProdName.setCellValue("��ǰ��");
	        	hCellProdName.setCellStyle(styles.get("header"));
	        	Cell hCellProdKbfName = headRow.createCell(3);
	        	hCellProdKbfName.setCellValue("KBF�Ӽ���");
	        	hCellProdKbfName.setCellStyle(styles.get("header"));
	        	Cell hCellProdKbfValList = headRow.createCell(4);
	        	hCellProdKbfValList.setCellValue("KBF�Ӽ���");
	        	hCellProdKbfValList.setCellStyle(styles.get("header"));
	        	Cell hCellPrevKbfVal = headRow.createCell(5);
	        	hCellPrevKbfVal.setCellValue("����");
	        	hCellPrevKbfVal.setCellStyle(styles.get("header"));
	        	Cell hCellNonExist = headRow.createCell(6);
	        	hCellNonExist.setCellValue("���ð� ����");
	        	hCellNonExist.setCellStyle(styles.get("header"));
	        	
	        	// �ߺз��� ��ǰ ���
	    		ArrayList<Prod2> prodList;
	    		prodList = db.getProdList2(l1Cd.substring(0,3),l2Cd);
	        	// ��Ʈ���� ��ǰ�� �����Ѵ�.
	        	Prod2 prodInfo;
	        	
	        	/**
	        	 * ��ǰ���� Loop
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
		        		        	
		        	if(prodInfo.getKBF_ATTR_NM().equals("�����԰�") || prodInfo.getKBF_ATTR_NM().equals("�귣��") || prodInfo.getKBF_ATTR_NM().equals("������")) {
		        		
		        	} else {
		        		String[] Formula = getArraySet(attrValueListHashtable.get(prodInfo.getKBF_ATTR_ID()));
			        
				    CellRangeAddressList AddressList = new CellRangeAddressList();
				    //��Ӵٿ� �ڽ� ���� ����.
				    AddressList.addCellRangeAddress(ii+1, 4, ii+1, 4);
				    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
				    HSSFDataValidation DataValidation = new HSSFDataValidation(AddressList, Constrain);
				    //���鹫�� �ɼ� true : ����, false: ���þ���                 
				    DataValidation.setEmptyCellAllowed(false);
				    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
				    DataValidation.setShowPromptBox(true);
				    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
				    DataValidation.setSuppressDropDownArrow(false);
				    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
				    DataValidation.createErrorBox("Select Only One", "����Ʈ�� �ϳ��� ���� �մϴ�.");
				    //����޽��� createPromptBox(String title, String text)
				    DataValidation.createPromptBox("Select Only One", "����Ʈ���� �ϳ��� ����ּ���");
				    /*�����޽��� ��Ÿ��(����,�溸,����).
				    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
				    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
				    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
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
			if("�����԰�".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_light_orange"));
//		        String[] Formula = new String[]{};
//			    CellRangeAddressList AddressList = new CellRangeAddressList();
//			    //��Ӵٿ� �ڽ� ���� ����.
//			    AddressList.addCellRangeAddress(row, col, row, col);
//			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
//			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
//			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
//			    DataValidation.setEmptyCellAllowed(false);
//			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
//			    DataValidation.setShowPromptBox(true);
//			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
//			    DataValidation.setSuppressDropDownArrow(false);
//			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
//			    DataValidation.createErrorBox("Don't Edit this cell", "������ �Ӽ����� �Է� ���ʿ�");
//			    //����޽��� createPromptBox(String title, String text)
//			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
//			    /*�����޽��� ��Ÿ��(����,�溸,����).
//			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
//			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
//			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
//			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("�귣��".equals(attrName)) {
				
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getBrnd_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "�귣�� �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("������".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getVen_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("������".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getOrgnp_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("����".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("�����̿���".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{"Yes","No"};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "������ �Ӽ����� �Է� ���ʿ�");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Yes or No", "Yes No �� �����ϼ���");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else {
		        //String[] Formula = kavl.toStringArray();
		        String[] Formula = {"1","2"}; //kavl.toStringArray();
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Select Only One", "����Ʈ�� �ϳ��� ���� �մϴ�.");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Only One", "����Ʈ���� �ϳ��� ����ּ���");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);

				cell.setCellStyle(styles.get("cell_lemon"));
				
			}
		} else {
			
			if("�����԰�".equals(attrName)) {

//		        String[] Formula = new String[]{};
//			    CellRangeAddressList AddressList = new CellRangeAddressList();
//			    //��Ӵٿ� �ڽ� ���� ����.
//			    AddressList.addCellRangeAddress(row, col, row, col);
//			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
//			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
//			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
//			    DataValidation.setEmptyCellAllowed(false);
//			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
//			    DataValidation.setShowPromptBox(true);
//			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
//			    DataValidation.setSuppressDropDownArrow(false);
//			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
//			    DataValidation.createErrorBox("Don't Edit this cell", "������ �Ӽ����� �Է� ���ʿ�");
//			    //����޽��� createPromptBox(String title, String text)
//			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
//			    /*�����޽��� ��Ÿ��(����,�溸,����).
//			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
//			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
//			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
//			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("�귣��".equals(attrName)) {
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getBrnd_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "�귣�� �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("������".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getOrgnp_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("������".equals(attrName)) {
				cell.setCellStyle(styles.get("cell_coral"));
		        String[] Formula = new String[]{pogRed.get(pi.getCd()).getVen_nm()};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "�Է�����������");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
			    DataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
			} else if("�����̿���".equals(attrName)) {
		        String[] Formula = new String[]{"Yes","No"};
			    CellRangeAddressList AddressList = new CellRangeAddressList();
			    //��Ӵٿ� �ڽ� ���� ����.
			    AddressList.addCellRangeAddress(row, col, row, col);
			    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(Formula);
			    DataValidation = new HSSFDataValidation(AddressList, Constrain);
			    //���鹫�� �ɼ� true : ����, false: ���þ���                 
			    DataValidation.setEmptyCellAllowed(false);
			    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
			    DataValidation.setShowPromptBox(true);
			    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
			    DataValidation.setSuppressDropDownArrow(false);
			    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
			    DataValidation.createErrorBox("Don't Edit this cell", "������ �Ӽ����� �Է� ���ʿ�");
			    //����޽��� createPromptBox(String title, String text)
			    DataValidation.createPromptBox("Select Yes or No", "Yes No �� �����ϼ���");
			    /*�����޽��� ��Ÿ��(����,�溸,����).
			    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
			    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
			    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.*/
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
