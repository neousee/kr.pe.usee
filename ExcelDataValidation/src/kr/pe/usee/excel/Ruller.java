package kr.pe.usee.excel;

public class Ruller {


	/* Rull : 원산지 
    String[] Formula = new String[] { };
    CellRangeAddressList AddressList = new CellRangeAddressList();
    //드롭다운 박스 범위 지정.
    //addCellRangeAddress(int firstRow, int firstCol, int lastRow, int lastCol)
    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(COOFormula);
    HSSFDataValidation DataValidation = new HSSFDataValidation(COOAddressList, COOConstrain);
    //공백무시 옵션 true : 무시, false: 무시안함                 
    COODataValidation.setEmptyCellAllowed(false);
    //cell 선택시 설명메시지 보이기 옵션  true: 표시, false : 표시안함                 
    COODataValidation.setShowPromptBox(true);
    //cell 선택시 드롭다운박스 list 표시여부 설정 true : 안보이게, false : 보이게                 
    COODataValidation.setSuppressDropDownArrow(false);
    //오류메시지 생성. 형식에 맞지 않는 데이터 입력시  createErrorBox(String title,String text)                 
    COODataValidation.createErrorBox("Don't Edit this cell", "원산지 속성값은 입력 불필요");
    //설명메시지 createPromptBox(String title, String text)
    COODataValidation.createPromptBox("Don't change this", "원산지 속성값은 입력 불필요");
    /*오류메시지 스타일(중지,경보,정보).
    HSSFDataValidation.ErrorStyle.STOP : 데이터외의값 허용안함.
    HSSFDataValidation.ErrorStyle.WARNING : 데이터외의값 입력시 경고(선택)창.
    HSSFDataValidation.ErrorStyle.INFO : 데이터외의값 입력시 정보창.
    COODataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
    */
}
