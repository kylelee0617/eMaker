package Invoice.InvoM030.func;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import Farglory.util.KUtils;
import jcx.db.talk;
import jcx.jform.bTransaction;

/**
 * Tip:
 * �o��}���O��P���o��( ����ProcessInvoiceNo=1 )
 * 
 * @author B04391
 *
 */

public class InvoM030New_2020 extends bTransaction{
  public boolean action(String value)throws Throwable{
    // �^�ǭȬ� true ��ܰ��汵�U�Ӫ���Ʈw���ʩάd��
    // �^�ǭȬ� false ��ܱ��U�Ӥ����������O
    // �ǤJ�� value �� "�s�W","�d��","�ק�","�R��","�C�L","PRINT" (�C�L�w�����C�L���s),"PRINTALL" (�C�L�w���������C�L���s) �䤤���@
    message("");
    
    talk dbInvoice = getTalk(""+get("put_dbInvoice"));
    talk as400 = getTalk("AS400");
    talk sale = getTalk("Sale");
    dbInvoice = getTalk("Invoice");
    KUtils kUtil = new KUtils();
    
    //�B�z����
    String stringSQL = " SELECT TOP 1 DepartNo " +
                      " FROM InvoProcessDepartNo " +
                    " WHERE DepartNo = '" + getValue("DepartNo").trim() + "'" +
                          " AND EmployeeNo = '" + getUser() + "'" ;
    String retInvoProcessDepartNo[][] = dbInvoice.queryFromPool(stringSQL);
    if(retInvoProcessDepartNo.length == 0){
      //message("���i�B�z �������o��");
      //return false;
    }
    
    //�o���p��
    stringSQL = "SELECT Nationality, CustomName FROM InvoM0C0 WHERE CustomNo = '" +  getValue("CustomNo").trim() + "'" ;
    String retInvoM0C0[][] = dbInvoice.queryFromPool(stringSQL);
    String customName = "";
    if(retInvoM0C0.length > 0){
      String stringNationality =  retInvoM0C0[0][0];
      customName = retInvoM0C0[0][1];
      if (stringNationality.equals("1")){
        if (getValue("CustomNo").length() == 8 && getValue("InvoiceKind").equals("2")){
          setValue("InvoiceKind","3");
          message("�o���w�j��אּ �T�p !");
          //return false;
        }
        if (getValue("CustomNo").length() == 10 && getValue("InvoiceKind").equals("3")){
          setValue("InvoiceKind","2");
          message("�o���w�j��אּ �G�p !");
          //return false;
        }     
      }
      if (stringNationality.equals("2")){
        if (getValue("CustomNo").length() == 10 && getValue("InvoiceKind").equals("3")){
          setValue("InvoiceKind","2");    
          message("�o���w�j��אּ �G�p !");
          return false;
        }     
      }   
    } 
    
//    dbInvoice = getTalk("Invoice");
    /*
    //�o���p��
    Farglory.util.FargloryUtil  exeUtil  =  new  Farglory.util.FargloryUtil() ;
    if (getValue("CustomNo").length()==8 && exeUtil.isDigitNum (getValue("CustomNo")))
      setValue("InvoiceKind","3");
    else
      setValue("InvoiceKind","2");  
    */  
      
    //���o�����X
    String stringInvoiceDate = getValue("InvoiceDate").trim();
    stringInvoiceDate = stringInvoiceDate.substring(0,7);
    stringSQL = "SELECT TOP 1 InvoiceYYYYMM," +
                                    " FSChar," +
                                    " StartNo," +
                                    " InvoiceBook," +
                                    " InvoiceStartNo," +
                                    " InvoiceEndNo," +
                                    " MaxInvoiceNo, " +
                    " SUBSTRING(MaxInvoiceNo,3,10)+1" +
                               " FROM InvoM022 " +
                              " WHERE CompanyNo = '" + getValue("CompanyNo").trim() + "'" +
                                " AND DepartNo = '" + getValue("DepartNo").trim() + "'" +
                                " AND ProjectNo = '" + getValue("ProjectNo").trim() + "'" +
                                " AND InvoiceKind = '" + getValue("InvoiceKind").trim() + "'" +
                                " AND UseYYYYMM = '" + stringInvoiceDate + "'" +
                                " AND (MaxInvoiceDate <= '" + getValue("InvoiceDate").trim() + "' OR MaxInvoiceDate IS NULL OR LEN(MaxInvoiceDate) = 0)" +
                                " AND ENDYES = 'N' " +
                                " AND CloseYes = 'N' " +
                                " AND ProcessInvoiceNo = '1'";
    if  (getValue("ProjectNo").trim().equals("E2AII") || getValue("ProjectNo").trim().equals("H802A")){
      stringSQL = "SELECT TOP 1 InvoiceYYYYMM," +
                      " FSChar," +
                      " StartNo," +
                      " InvoiceBook," +
                      " InvoiceStartNo," +
                      " InvoiceEndNo," +
                      " MaxInvoiceNo, " +
                      " SUBSTRING(MaxInvoiceNo,3,10)+1" +
                     " FROM InvoM022 " +
                    " WHERE CompanyNo = '" + getValue("CompanyNo").trim() + "'" +
                    " AND DepartNo = '" + getValue("DepartNo").trim() + "'" +
                    " AND ProjectNo = '" + getValue("ProjectNo").trim() + "'" +
                    " AND InvoiceKind = '" + getValue("InvoiceKind").trim() + "'" +
                    " AND UseYYYYMM = '" + stringInvoiceDate + "'" +
                    " AND (MaxInvoiceDate <= '" + getValue("InvoiceDate").trim() + "' OR MaxInvoiceDate IS NULL OR LEN(MaxInvoiceDate) = 0)" +
                    " AND ENDYES = 'N' " +
                    " AND CloseYes = 'N' " +
                    " AND ProcessInvoiceNo = '1'" +
                    " AND CompanyNo+BranchNo IN( " +  
                                                " SELECT  CompanyNo+BranchNo " +
                                                 " FROM Invom025" +
                                                 " WHERE ProjectNo = '" + getValue("ProjectNo").trim() + "'" +
                                                   " AND HuBei = '" + getValue("HuBei").trim() + "'" +  
                                                ")";     
    }             
    String retInvoM022[][] = dbInvoice.queryFromPool(stringSQL);
    String stringInvoiceYYYYMM = "";
    String stringFSChar = "";
    String stringStartNo = "";
    String stringInvoiceBook = "";
    String stringInvoiceStartNo = "";
    String stringInvoiceEndNo = "";
    String stringMaxInvoiceNo = "";
    String stringMaxInvoiceNo1 = "";
    String stringNowInvoiceNo = "";
    String stringEndYes = "N";
    for(int i=0;i<retInvoM022.length;i++){    //�Ntop 1 �F�A�]��j����A�٬O�Aı�otop 1 ���|�u���@��?
      stringInvoiceYYYYMM = retInvoM022[i][0];
      stringFSChar = retInvoM022[i][1];
      stringStartNo = retInvoM022[i][2];
      stringInvoiceBook = retInvoM022[i][3];
      stringInvoiceStartNo = retInvoM022[i][4];
      stringInvoiceEndNo = retInvoM022[i][5];
      stringMaxInvoiceNo = retInvoM022[i][6].trim();
      stringMaxInvoiceNo1 = retInvoM022[i][7].trim();
      if (stringMaxInvoiceNo1.length() == 7) stringMaxInvoiceNo1 = "0" + stringMaxInvoiceNo1;
      if (stringMaxInvoiceNo1.length() == 6) stringMaxInvoiceNo1 = "00" + stringMaxInvoiceNo1;  
      if (stringMaxInvoiceNo1.length() == 5) stringMaxInvoiceNo1 = "000" + stringMaxInvoiceNo1;   
      if (stringMaxInvoiceNo1.length() == 4) stringMaxInvoiceNo1 = "0000" + stringMaxInvoiceNo1;      
      if (stringMaxInvoiceNo1.length() == 3) stringMaxInvoiceNo1 = "00000" + stringMaxInvoiceNo1;       
      if (stringMaxInvoiceNo1.length() == 2) stringMaxInvoiceNo1 = "000000" + stringMaxInvoiceNo1;
    System.out.println("stringMaxInvoiceNo="  + stringMaxInvoiceNo);
    System.out.println("stringMaxInvoiceNo1="  + stringMaxInvoiceNo1);
    System.out.println("stringMaxInvoiceNo.length()="  + stringMaxInvoiceNo.length());                                        
      if (stringMaxInvoiceNo.length()==0){
        stringNowInvoiceNo = stringInvoiceStartNo;
      }else{
        stringNowInvoiceNo = stringFSChar + stringMaxInvoiceNo1;  
      }
      if (stringNowInvoiceNo.equals(stringInvoiceEndNo)) stringEndYes ="Y";
    }
//    System.out.println("���o�o�����X>>>" + stringNowInvoiceNo);
    if (stringNowInvoiceNo.length() < 10){
      message("�q���o���w�Χ� �Ь��]�ȫǻ��!");
      return false;
    }
    
    message("���o�o�����X>>>" + stringNowInvoiceNo);
    
    //����
    String [][] A_table = getTableData("table1");
    if (A_table.length ==0){
       message("���ӥ����ܤ֦��@��");
       return false;
    }
    String stringUserkey = "";
    Calendar cal= Calendar.getInstance();//Current time
    stringUserkey = getUser() + "_T" + ""+( (cal.get(Calendar.HOUR_OF_DAY)*10000) + (cal.get(Calendar.MINUTE)*100) + cal.get(Calendar.SECOND) );
    message(stringUserkey);
    stringSQL = "DELETE FROM InvoM030TempBody WHERE UseKey = '" + stringUserkey + "'";
    dbInvoice.execFromPool(stringSQL);
    for(int i=0;i<A_table.length;i++){
      stringSQL =  " INSERT INTO InvoM030TempBody" +
                        "(" +
                        " UseKey," +
                        " RecordNo," +
                        " DetailItem," +
                        " Remark" +
                        " ) VALUES (" +
                                    "'" + stringUserkey +  "'," +
                                    A_table[i][1] + "," +
                                    "'" +  A_table[i][2] +  "'," +
                                    "'" +  A_table[i][3]+  "'" +                
                                  ")";
      dbInvoice.execFromPool(stringSQL);
    }
    //
    String retSystemDateTime[][] = dbInvoice.queryFromPool("spInvoSystemDateTime  'Admin'");
    String stringSystemDateTime ="";
    stringSystemDateTime = retSystemDateTime[0][0].replace("-","/");
    stringSystemDateTime = stringSystemDateTime.substring(0,19);
    message(stringSystemDateTime);
    getButton("buttonMoney").doClick() ;
    
    /**
     * 1. insert InvoM030
     * 2. insert GLEAPFUF
     * 3. check GLEDPFUF
     * 4. insert GLEDPFUF
     * 5. update InvoM022
     */
    try {
      Random r1 = new Random();
      StringBuilder sbSQL = new StringBuilder();
      
      //insert InvoM030
      sbSQL = new StringBuilder();
      sbSQL.append("INSERT  INTO  InvoM030 ");
      sbSQL.append("(InvoiceNo, InvoiceDate, InvoiceKind, CompanyNo, DepartNo, ProjectNo, InvoiceWay, Hubei, CustomNo, PointNo, ");
      sbSQL.append("InvoiceMoney, InvoiceTax, InvoiceTotalMoney, TaxKind, DisCountMoney,DisCountTimes, PrintYes, PrintTimes, DELYes, LuChangYes, ");
      sbSQL.append("ProcessInvoiceNo, Transfer, CreateUserNo, CreateDateTime, LastUserNo, LastDateTime, RandomCode, CustomName ) ");
      sbSQL.append("values ");
      sbSQL.append("( ");
      sbSQL.append("'").append(stringNowInvoiceNo).append("', ");
      sbSQL.append("'").append(getValue("InvoiceDate").trim()).append("', ");
      sbSQL.append("'").append(getValue("InvoiceKind").trim()).append("', ");
      sbSQL.append("'").append(getValue("CompanyNo").trim()).append("', ");
      sbSQL.append("'").append(getValue("DepartNo").trim()).append("', ");
      sbSQL.append("'").append(getValue("ProjectNo").trim()).append("', ");
      sbSQL.append("'").append(getValue("InvoiceWay").trim()).append("', ");
      sbSQL.append("'").append(getValue("HuBei").trim()).append("', ");
      sbSQL.append("'").append(getValue("CustomNo").trim()).append("', ");
      sbSQL.append("'").append(getValue("PointNo").trim()).append("', ");
      sbSQL.append("").append(getValue("InvoiceMoney").trim()).append(", ");      //���|
      sbSQL.append("").append(getValue("InvoiceTax").trim()).append(", ");        //�|�B
      sbSQL.append("").append(getValue("InvoiceTotalMoney").trim()).append(", "); //�t�|
      sbSQL.append("'").append(getValue("TaxKind").trim()).append("', ");         //�|�O
      sbSQL.append("").append(0).append(", ");                                    //�w�������B
      sbSQL.append("").append(0).append(", ");                                    //�w��������
      sbSQL.append("'").append("N").append("', ");                                //�w�C�LYN
      sbSQL.append("").append(0).append(", ");                                    //�ɦL����
      sbSQL.append("'").append("N").append("', ");                                //�@�oYN
      sbSQL.append("'").append("N").append("', ");                                //�J�bYN
      sbSQL.append("'").append("1").append("', ");                                //ProcessInvoiceNo
      sbSQL.append(null + ", ");                                                  //Transfer
      sbSQL.append("'").append(getUser().trim()).append("', ");                   //CreateUserNo
      sbSQL.append("'").append(stringSystemDateTime).append("', ");               //CreateDateTime
      sbSQL.append("'").append(getUser().trim()).append("', ");                   //LastUserNo
      sbSQL.append("'").append(stringSystemDateTime).append("', ");                //LastDateTime
      sbSQL.append("'").append(kUtil.add0(r1.nextInt(9999), 4, "F")).append("', ");  //RandomCode
      sbSQL.append("'").append(customName).append("' ");                         //�Ȥ�m�W
      sbSQL.append(") ");
      dbInvoice.execFromPool(sbSQL.toString());
      
      //��sInvoM022
      sbSQL = new StringBuilder();
      sbSQL.append("update InvoM022 ");
      sbSQL.append("set MaxInvoiceNo='").append(stringNowInvoiceNo).append("' ");
      sbSQL.append(",MaxInvoiceDate='").append(getValue("InvoiceDate").trim()).append("' ");
      sbSQL.append("where 1=1 ");
      sbSQL.append("and CompanyNo='").append(getValue("CompanyNo").trim()).append("' ");
      sbSQL.append("and DepartNo='").append(getValue("DepartNo").trim()).append("' ");
      sbSQL.append("and ProjectNo='").append(getValue("ProjectNo").trim()).append("' ");
      sbSQL.append("and InvoiceKind='").append(getValue("InvoiceKind").trim()).append("' ");
      sbSQL.append("and UseYYYYMM='").append(stringInvoiceDate).append("' ");
      sbSQL.append("and ENDYES='").append("N").append("' ");
      sbSQL.append("and CloseYes='").append("N").append("' ");
      sbSQL.append("AND (MaxInvoiceDate <='").append(getValue("InvoiceDate").trim()).append("' or MaxInvoiceDate IS NULL OR LEN(MaxInvoiceDate) = 0) ");
      sbSQL.append("and ProcessInvoiceNo='").append("1").append("' ");
      dbInvoice.execFromPool(sbSQL.toString());
      
      //�g�JAS400
      Vector vectorSql = new Vector();
      //A : �D��
      sbSQL = new StringBuilder();
      sbSQL.append("insert into GLEAPFUF ");
      sbSQL.append("(EA01U, EA02U, EA03U, EA04U, EA05U, EA06U, EA07U, EA08U, EA09U, EA10U, EA11U, EA12U, EA13U, EA14U, EA15U, EA16U, EA17U, EA18U, EA19U, EA20U, EA21U, EA22U) ");
      sbSQL.append("values ");
      sbSQL.append("(");
      sbSQL.append("'").append(stringNowInvoiceNo).append("', ");                 //�o�����X
      sbSQL.append("'").append(getValue("InvoiceDate").trim()).append("', ");     //�o�����
      sbSQL.append("'").append(getValue("InvoiceKind").trim()).append("', ");     //�o���p��
      sbSQL.append("'").append(getValue("CompanyNo").trim()).append("', ");       //���q�N�X
      sbSQL.append("'").append(getValue("DepartNo").trim()).append("', ");        //�����N�X
      sbSQL.append("'").append(getValue("ProjectNo").trim()).append("', ");       //�קO�N�X
      sbSQL.append("'").append(getValue("InvoiceWay").trim()).append("', ");      //Invoice Way
      sbSQL.append("'").append(getValue("HuBei").trim()).append("', ");           //��O�N��
      sbSQL.append("'").append(getValue("CustomNo").trim()).append("', ");        //�Ȥ�N��
      sbSQL.append("'").append(getValue("PointNo").trim()).append("', ");         //�K�n
      sbSQL.append("").append(getValue("InvoiceMoney").trim()).append(", ");      //���|
      sbSQL.append("").append(getValue("InvoiceTax").trim()).append(", ");        //�|�B
      sbSQL.append("").append(getValue("InvoiceTotalMoney").trim()).append(", "); //�t�|
      sbSQL.append("'").append(getValue("TaxKind").trim()).append("', ");         //�|�O
      sbSQL.append("").append(0).append(", ");             //�w�������B
      sbSQL.append("").append(0).append(", ");             //�w��������
      sbSQL.append("'").append("N").append("', ");         //�w�C�LYN
      sbSQL.append("").append(0).append(", ");             //�ɦL����
      sbSQL.append("'").append("N").append("', ");         //�@�oYN
      sbSQL.append("'").append("N").append("', ");         //�J�bYN
      sbSQL.append("'").append("").append("', ");          //�o���B�z�覡
      sbSQL.append("'").append("����").append("' ");       //����/�ȪA
      sbSQL.append(") ");
      as400.execFromPool(sbSQL.toString());
      
      //D : �Ȥ���
      sbSQL = new StringBuilder();
      sbSQL.append("select ED01U from GLEDPFUF where ED01U = '" +  getValue("CustomNo").trim() + "' ");
      String[][] arrGLEDPFUF = as400.queryFromPool(sbSQL.toString());
      if(arrGLEDPFUF.length == 0 && !"".equals(customName)) {
        sbSQL = new StringBuilder();
        sbSQL.append("insert into GLEDPFUF ");
        sbSQL.append("(ED01U, ED02U) ");
        sbSQL.append("values ");
        sbSQL.append("(");
        sbSQL.append("'").append(getValue("CustomNo").trim()).append("', ");
        sbSQL.append("'").append(customName).append("' ");
        sbSQL.append(") ");
        as400.execFromPool(sbSQL.toString());
      }
//      as400.execFromPool( (String[]) vectorSql.toArray(new String[0]) );
      
      setValue("InvoiceNo",stringNowInvoiceNo);
      message("�w���͵o�� = " +  stringNowInvoiceNo);   
    } catch(Exception ex) {
      message("�o�Ϳ��~1:" + ex);
      return false;
    }
    
    return false;
  }
}
