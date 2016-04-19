package kr.pe.usee.excel;
import java.io.File;
import java.io.FileOutputStream;

/**
원본 : http://www.nabble.com/sample-code--to-read-excel-listbox-values-td23921169.html 
**/

public class ExcelListDemo{
    /**
     * @param args
     */
    public static void main(String[] args) {
        // New Workbook.
        File outputFile = new File("C:/temp.xls");
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            HSSFWorkbook workbook = new HSSFWorkbook();

   //sheet 생성.
            HSSFSheet sheet = workbook.createSheet("List Sheet");

   //드롭다운 박스 값 지정.
            String[] strFormula = new String[] { "100", "200", "300", "400", "500" };

            CellRangeAddressList addressList = new CellRangeAddressList();

   //드롭다운 박스 범위 지정.
   //addCellRangeAddress(int firstRow, int firstCol, int lastRow, int lastCol)
   addressList.addCellRangeAddress(0, 0, 1, 2);

            DVConstraint constraing = DVConstraint.createExplicitListConstraint(strFormula);
            HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, constraing);

   //공백무시 옵션 true : 무시, false: 무시안함
            dataValidation.setEmptyCellAllowed(false);

   //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함
            dataValidation.setShowPromptBox(true);

   //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게
            dataValidation.setSuppressDropDownArrow(false);

   //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)
            dataValidation.createErrorBox("Invalid input !", "Something is wrong. check condition!");
    
   //설명메시지 createPromptBox(String title, String text)
   dataValidation.createPromptBox("promptBox", "Not Korean");
    

   /** 한글안됨. createErrorBox,createPromptBox */

   /*오류메시지 스타일(중지,경보,정보).
   HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
   HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
   HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.*/
   dataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
   
            sheet.addValidationData(dataValidation);
            workbook.write(fos);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
