package kr.pe.usee.excel;

public class Ruller {


	/* Rull : ������ 
    String[] Formula = new String[] { };
    CellRangeAddressList AddressList = new CellRangeAddressList();
    //��Ӵٿ� �ڽ� ���� ����.
    //addCellRangeAddress(int firstRow, int firstCol, int lastRow, int lastCol)
    DVConstraint Constrain = DVConstraint.createExplicitListConstraint(COOFormula);
    HSSFDataValidation DataValidation = new HSSFDataValidation(COOAddressList, COOConstrain);
    //���鹫�� �ɼ� true : ����, false: ���þ���                 
    COODataValidation.setEmptyCellAllowed(false);
    //cell ���ý� ����޽��� ���̱� �ɼ�  true: ǥ��, false : ǥ�þ���                 
    COODataValidation.setShowPromptBox(true);
    //cell ���ý� ��Ӵٿ�ڽ� list ǥ�ÿ��� ���� true : �Ⱥ��̰�, false : ���̰�                 
    COODataValidation.setSuppressDropDownArrow(false);
    //�����޽��� ����. ���Ŀ� ���� �ʴ� ������ �Է½�  createErrorBox(String title,String text)                 
    COODataValidation.createErrorBox("Don't Edit this cell", "������ �Ӽ����� �Է� ���ʿ�");
    //����޽��� createPromptBox(String title, String text)
    COODataValidation.createPromptBox("Don't change this", "������ �Ӽ����� �Է� ���ʿ�");
    /*�����޽��� ��Ÿ��(����,�溸,����).
    HSSFDataValidation.ErrorStyle.STOP : �����Ϳ��ǰ� ������.
    HSSFDataValidation.ErrorStyle.WARNING : �����Ϳ��ǰ� �Է½� ���(����)â.
    HSSFDataValidation.ErrorStyle.INFO : �����Ϳ��ǰ� �Է½� ����â.
    COODataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
    */
}
