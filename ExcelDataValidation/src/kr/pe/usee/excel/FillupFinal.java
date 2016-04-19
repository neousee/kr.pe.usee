package kr.pe.usee.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import kr.pe.usee.db.excel.KbfAttr;
import kr.pe.usee.db.mysql.MysqlDb;
import kr.pe.usee.db.mysql.ProdInfo2;
import kr.pe.usee.io.ExcelReadHandler;

public class FillupFinal {
	
	private static final String[] level1 = {"001.����","002.�","003.ä��","004.����","005.���","006.������ǰ","007.����","008.����","009.�õ�","010.MS","011.����","012.�ַ�","013.�Ϲݽ�ǰ","014.���̷��","015.�����","016.�ϻ��ǰ","017.��Ȱ��ȭ"};
    
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		ExcelReadHandler kbfSource = new ExcelReadHandler("D:\\source\\KBFWorkingReview_ext.xlsx");
		
		kbfSource.getKbfData();
		Hashtable<String, Prod> prodList;
		//ExcelReadHandler pogProdSource = new ExcelReadHandler("D:\\source\\PogListCate.xls");
		//pogProdSource.getPogData();
		
		
		MysqlDb db = new MysqlDb();
		/* SELECT `prodCd`, `nm`, `tl1`, `tl2`, `tl3`, `tl4` FROM `poglistcate` */
		prodList = db.getProdList();
		//Hashtable<String,PogRed> pogRed = db.getPogRed();
		Hashtable<String,PogRed> pogRed = new Hashtable<String, PogRed>();
		
		//Hashtable<String,KbfAttrValueList> attrValueListHashtable = db.selectAttrValueSetList();
		
		// ��з� �ϳ�
        Workbook wb;
        ArrayList<GrpResultSet> grpResultSet;        

        for(String lvl:level1) 
        {
        
        //	if("16.��Ȱ��ȭ".equals(lvl))
        	{	
			System.out.println(lvl);
			String file = "D:\\source\\"+lvl+".KBFWorkingTemplate0223.xls";
			wb = new HSSFWorkbook();

	        Map<String, CellStyle> styles = createStyles(wb);
			//if(wb instanceof XSSFWorkbook) file += "x";
			
	        // �ߺз����� �����.
	        //grpResultSet = db.getL2ProdList(lvl.substring(0,3));
	        Hashtable<String,String> l2List = db.getL2List(lvl.substring(0,3));
	        Hashtable<String,Prod> listProdInfo;
	        String l2Cd;
	        
	        for( Enumeration<String> keys = l2List.keys();keys.hasMoreElements();) {
	        	l2Cd = keys.nextElement();
	        	System.out.println(l2Cd);
	        	//System.out.println(grpResult.getInStr());
	        	
	        	Sheet sheet = wb.createSheet(l2List.get(l2Cd));
	        	setSheetStyle(sheet);
	            
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
	        	
	        	// �ߺз��� KBF ����Ʈ
	        	ArrayList<KbfAttr> kbfList = db.getKbfList(l2Cd);
	        	String[] headerAttrList = getAttrValueList(kbfList);
	        	ArrayList<String[]> headerAttrCodeList = db.getAttrCodeList(l2Cd);
	        	System.out.println(kbfList.size()+":"+headerAttrList.length+":"+headerAttrCodeList.size());
	        	Hashtable<String,ArrayList<KbfAttrList>> kbfAttrList = db.getKbfAtrrList(headerAttrCodeList);
	        	listProdInfo = db.getProdListL2(l2Cd);
	        	ArrayList<Prod> orderedProdList = db.getProdOrderListL2(l2Cd);
	        	
	        	/**
	        	 * SELECT `l1`, `l2`, `l3`, `l4`, `attrlist`, `at1`, `at2`, `at3`, `at4` FROM `selectedList` WHERE l1 = ? AND l2 = ?
	        	 */
	        	//set1.toArray(headerAttrList);
	        	Cell hAttr;
	        	for(int x=3;x-3<headerAttrCodeList.size();x++) {
		        		hAttr = headRow.createCell(x);
		        		hAttr.setCellValue(headerAttrCodeList.get(x-3)[1]);
			        	hAttr.setCellStyle(styles.get("header"));	
	        	}
	        	// ��Ʈ���� ��ǰ�� �����Ѵ�.
	        	Prod prodInfo;
	        	
	        	/**
	        	 * ��ǰ���� Loop
	        	 */
	        	Cell cellLvl3;
	        	Cell cellProdCd;
	        	Cell cellProdName;
	        	HSSFDataValidation hSSFDataValidation;
	        	for(int i=0 ; i<orderedProdList.size(); i++) {
	        		prodInfo = orderedProdList.get(i);
		        	Row row = sheet.createRow(i+1);
					for(int j=0;j<level1.length;j++) {
		        		Cell cell = row.createCell(j);
		        		cell.setCellValue(level1[j]);
		        	}
		        	//pogRow = pogList.get(prod.getProdCd());
		        	KbfRow prodDefinedList = kbfSource.getKbfList().get(prodInfo.getL1_CD()+":"+prodInfo.getL2_CD()+":"+prodInfo.getL3_CD());
		        	cellLvl3 = row.createCell(0);
		        	cellLvl3.setCellValue(prodInfo.getL3_NM());
		        	cellLvl3.setCellStyle(styles.get("c_header"));
		        	cellProdCd = row.createCell(1);
		        	cellProdCd.setCellValue(prodInfo.getPROD_CD());
		        	cellProdCd.setCellStyle(styles.get("c_header"));
		        	cellProdName = row.createCell(2);
		        	cellProdName.setCellValue(prodInfo.getPROD_NM());
		        	cellProdName.setCellStyle(styles.get("c_header"));
		        	Cell attrCell;
		        	ProdInfo pi = new ProdInfo(prodInfo.getPROD_CD()+"@"+prodInfo.getPROD_NM());
		        	ArrayList<KbfAttr> selectedkbfList =  getKbfListFromL3(prodInfo.getL3_CD(),kbfList);
		        	Hashtable<String, ArrayList<KbfAttrList>> selectedKbfAttrList = getKbfAttrListFromKbf(selectedkbfList,kbfAttrList);
		        	String headerAttrName;
		        	String headerAttrId;
		        	boolean isMatch;
		        	for(int j=3;j-3<headerAttrCodeList.size();j++)
		        	{
		        		headerAttrName = headerAttrCodeList.get(j-3)[1];
		        		headerAttrId = headerAttrCodeList.get(j-3)[0];
		        		//���ǵ� �Ӽ��� �׷� �Ӽ��� �ִٸ�
		        		try {
		        		if(selectedKbfAttrList.containsKey(headerAttrId)) {
		        			//System.out.println("���ǵ� �Ӽ��� �׷� �Ӽ��� �ִٸ� : YES");
		        			isMatch = true;
		        		} else {
		        			//System.out.println("���ǵ� �Ӽ��� �׷� �Ӽ��� �ִٸ� : NO");
		        			isMatch = false;
		        		}
		        		attrCell = row.createCell(j);
		        		String t ="";
		        		ArrayList<KbfAttrList> al = selectedKbfAttrList.get(headerAttrId);
		        		for(KbfAttrList a:al) {
		        			t += a.getKBF_ATTR_VAL_NM();
		        		}
		        		
		        		//attrCell.setCellValue(t);
		        		ArrayList<KbfAttrList> all = kbfAttrList.get(headerAttrId);
		        		//ArrayList<KbfAttrList> kavl = selectedKbfAttrList.get(headerAttrCodeList[j-3]);
		        		KbfAttrValueList kavl = new KbfAttrValueList(all,headerAttrId);
		        		
		        		hSSFDataValidation = makeDataValidationList(pi,pogRed,headerAttrName,kavl,i+1,j,attrCell,styles,isMatch);
		        		if(hSSFDataValidation != null) sheet.addValidationData(hSSFDataValidation);
		        		} catch(NullPointerException nep) {
		        			System.out.println(nep.getMessage());
		        		}
		        	}
		        	
	        	}	    
	        }
                //sheet.addValidationData(brandDataValidation);
                //sheet.addValidationData(COODataValidation);
	        

	        FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
        	}
		}
	}

	private static Hashtable<String, ArrayList<KbfAttrList>> getKbfAttrListFromKbf(ArrayList<KbfAttr> selectedkbfList, 
			Hashtable<String, ArrayList<KbfAttrList>> kbfAttrList) {
		Hashtable<String, ArrayList<KbfAttrList>> t = new Hashtable<String, ArrayList<KbfAttrList>>();
		for(KbfAttr kbfAttr:selectedkbfList) {
			t.put(kbfAttr.getKBF_ATTR_ID(), kbfAttrList.get(kbfAttr.getKBF_ATTR_ID()));
		}
		return t;
	}

	private static ArrayList<KbfAttr> getKbfListFromL3(String l3_CD, ArrayList<KbfAttr> kbfList) {
		ArrayList<KbfAttr> arrayList = new ArrayList<KbfAttr>();
		for(KbfAttr kbfAttr : kbfList) {
			if(kbfAttr.getL3_CD().equals(l3_CD)) arrayList.add(kbfAttr);
		}
		return arrayList;
	}

	private static String[] getAttrValueList(ArrayList<KbfAttr> kbfList) {
		java.util.ArrayList<String> set = new ArrayList<String>();
		for(KbfAttr kbfAttr:kbfList) {
			if(!set.contains(kbfAttr.getKBF_ATTR_NM())) set.add(kbfAttr.getKBF_ATTR_NM());			
		}
		String[] t = new String[set.size()];
		set.toArray(t);
		return t;
	}

	private static String[] getAttrCodeList(ArrayList<KbfAttr> kbfList) {
		java.util.ArrayList<String> set = new ArrayList<String>();
		for(KbfAttr kbfAttr:kbfList) {
			System.out.println(kbfAttr.getKBF_ATTR_ID());
			if(!set.contains(kbfAttr.getKBF_ATTR_ID())) set.add(kbfAttr.getKBF_ATTR_ID());			
		}
		String[] t = new String[set.size()];
		set.toArray(t);
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
    
    
	private static HSSFDataValidation makeDataValidationList(ProdInfo pi, Hashtable<String, PogRed> pogRed, String attrName, KbfAttrValueList kavl, int row, int col, Cell cell,Map<String, CellStyle> styles,boolean isMatch) {
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
		        String[] Formula = kavl.toStringArray();
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
