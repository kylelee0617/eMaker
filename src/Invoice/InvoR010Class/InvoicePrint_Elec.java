package Invoice.InvoR010Class;
import javax.swing.*;

import Invoice.utils.InvoicePrintUtil;
import Invoice.vo.*;
import jcx.jform.sproc;
import java.io.*;
import java.util.*;
import jcx.util.*;
import jcx.html.*;
import jcx.db.*; 

public class InvoicePrint_Elec extends sproc{
  public String getDefaultValue(String value)throws Throwable{
    System.out.println("-------------------InvoicePrintTest2----------------------S") ;
    
//    String PRINTURL = "http://172.16.5.77:8081/InvoicePrintService/services/";
//    String PRINTURL = "http://172.16.5.77:8081/InvoicePrintService/services";
    String PRINTURL = "http://172.22.10.51:8080/InvoiceService/services";
    String CompanyNo = getValue("CompanyNo");
    String InvoiceDate = getValue("InvoiceDate");
    String [][] table = getTableData("table1");
    String PrintUserNo = getUser();
    StringBuffer choose = new StringBuffer();
    int cv=0;
    for(int x = 0 ; x<table.length ; x++){
      if(table[x][0].trim().equals("Y")){
        if(choose.length()==0) choose.append("'"+table[x][2].trim()+"'"); else choose.append(",'"+table[x][2].trim()+"'");
        cv++;
      }
    }
    if(cv==0){
      messagebox("�Цܤ֤Ŀ�@�����");
      return value;
    }
    
    System.out.println("choose>>>" + choose);
    
    talk t = getTalk("Invoice");
    talk t1 = getTalk("Sale");
    talk tFE5D = getTalk("FE5D");
    
    StringBuffer sql = new StringBuffer();
    sql.append(" select InvoiceNo ");
    sql.append(" from InvoM030");
    sql.append(" where DELYes='Y'");
    if(CompanyNo.length()!=0) sql.append(" and CompanyNo='" + CompanyNo + "'");
    if(InvoiceDate.length()!=0) sql.append(" and SUBSTRING(InvoiceDate,1,4)+SUBSTRING(InvoiceDate,6,2)+SUBSTRING(InvoiceDate,9,2) between "+InvoiceDate.replaceAll("/","")+" ");
    if(choose.length()!=0) sql.append(" and InvoiceNo IN ("+choose.toString()+") ORDER BY InvoiceNo");
    String [][] M030 = t.queryFromPool(sql.toString());
    
    System.out.println("-----------------------------------------11111") ;
    HashMap hM030 = new HashMap();
    for(int x=0 ; x<M030.length ; x++){
      hM030.put(M030[x][0].trim(),M030[x][0].trim());
    }
    StringBuffer vM030 = new StringBuffer();
    for(int x = 0 ; x<table.length ; x++){
      if(hM030. containsKey(table[x][2].trim()) && table[x][0].trim().equals("Y")){
        if(vM030.length()==0) vM030.append("�o�����X�@�o�A���I��O�w�A�˵��@�o�o���M��\r\n");
        vM030.append("�o�����X : "+table[x][2].trim()+"\r\n");
      }
    }
    
    String PrintDateTime = datetime.getToday("YYYY/mm/dd") + " " + datetime.getTime("h:m:s");
    setValue("PrintDateTime",PrintDateTime);
    String PrintStatus = "���͵o��";
    
    //�D��
    HashMap hM030_1 = new HashMap();
    // 0.�o�����X 1.�o����� 2.�R���H�W�� 3.��~�|����
    sql = new StringBuffer(" select a.InvoiceNo, a.InvoiceDate, a.CustomName, a.TaxKind ");
    // 4.�קO 5.��O6.���� 7.�o�����|���B 8.�o���|�B 9.�o���`���B
    sql.append(",'', a.HuBei, a.HuBei, isnull(a.InvoiceMoney,'0'), isnull(a.InvoiceTax,'0'), isnull(a.InvoiceTotalMoney, '0') ");
    // 10.�C�L���� 11.�o���p��(2/3) 12.�~�W�N�� 13.�R���H�νs 14.�O�_�R�� 15 �o������ 16 �ȪA��O
    sql.append(",isnull(a.PrintTimes,'0'), InvoiceKind, PointNo,CustomNo, isnull(DELYes,''), ProcessInvoiceNo, isnull(OBJECT_CD, '' )");
    // 17. �H���X
    sql.append(",a.RandomCode ,a.CompanyNo ");
    sql.append("from InvoM030 a ");
    sql.append("where 1=1 ");
    // sql.append("and isnull(a.RandomCode,'') != '' "); //�u��q�l�o��
    if (choose.length() != 0) sql.append(" and InvoiceNo in (" + choose.toString() + ")");
    String[][] InvoM030 = t.queryFromPool(sql.toString());
    for (int x = 0; x < InvoM030.length; x++) {
      hM030_1.put(InvoM030[x][0].trim(), InvoM030[x]);
      StringBuilder tmpsb = new StringBuilder();
      for(int i=0 ; i<InvoM030[x].length ; i++) {
        tmpsb.append(i).append("-").append(InvoM030[x][i].trim()).append(" ; ");
      }
      System.out.println("InvoM030>>>" + tmpsb.toString());
    }
    
    //�קO�N�� - �W��
    HashMap hM0D0 = new HashMap();
    sql = new StringBuffer(" select ProjectNo,ProjectName from InvoM0D0 ");
    String[][] InvoM0D0 = t.queryFromPool(sql.toString());
    for (int x = 0; x < InvoM0D0.length; x++) {
      hM0D0.put(InvoM0D0[x][0].trim(), InvoM0D0[x][1].trim());
    }

    //��P�Ȥ�m�W�a�}
    HashMap hCustom = new HashMap();
    sql = new StringBuffer(" select DISTINCT "
        + "RTRIM(ISNULL(a.City,'')) + RTRIM(ISNULL(a.Town,'')) + RTRIM(a.Address)"  //0
        + ", a.CustomName"  //1
        + ", RTRIM(c.InvoiceNo) + RTRIM(a.CustomNo)"  //2
        + ", RTRIM(ISNULL(a.ZIP,'')) ");  //3
    sql.append(" from Sale05M091 a,Sale05M086 b,Sale05M087 c ");
    sql.append("  where b.DocNo=c.DocNo and a.OrderNo=b.OrderNo");
    if (choose.length() != 0) sql.append(" and c.InvoiceNo in (" + choose.toString() + ")");
    String[][] Custom = t1.queryFromPool(sql.toString());
    for (int x = 0; x < Custom.length; x++) {
      hCustom.put(Custom[x][2].trim(), Custom[x]);
    }

    //�~���N�� - �W��
    HashMap hM010 = new HashMap();
    sql = new StringBuffer("select PointNo,PointName from InvoM010");
    String[][] InvoM010 = t.queryFromPool(sql.toString());
    for (int x = 0; x < InvoM010.length; x++) {
      hM010.put(InvoM010[x][0].trim(), InvoM010[x][1].trim());
    }

    //����
    HashMap hM031 = new HashMap();
    sql = new StringBuffer("select UPPER(InvoiceNo),RTRIM(Detailitem)+RTRIM(Remark)+'�F' from InvoM031");
    if (choose.length() != 0) sql.append(" WHERE InvoiceNo in (" + choose.toString() + ")");
    String[][] InvoM031 = t.queryFromPool(sql.toString());
    for (int x = 0; x < InvoM031.length; x++) {
      String temp[] = { "", "" };
      if (hM031.containsKey(InvoM031[x][0])) {
        String Invo_temp = (String) hM031.get(InvoM031[x][0].trim());
        temp[0] = InvoM031[x][0].trim();
        temp[1] += Invo_temp + InvoM031[x][1].trim();
        hM031.put( temp[0].trim() , temp[1].trim() );
      } else {
        hM031.put( InvoM031[x][0].trim() , InvoM031[x][1].trim() );
      }
    }

    // �}�l�ո��
    Hashtable result = new Hashtable();
    for (int x = 0; x < table.length; x++) {
      if (table[x][0].trim().equals("Y")) {
        String[] Invo_temp = (String[]) hM030_1.get( table[x][2].trim() );
        String InvoiceNo = Invo_temp[0].trim();        // �o�����X
        
        //�L�o
        if (Invo_temp == null) continue;        //�S�D��
        if (!PrintStatus.equals("���ͧ@�o�p") && Invo_temp[14].trim().equals("Y")) continue; //�w�@�o(�R��)���B�z
        
        InvoiceDate = Invo_temp[1].trim();             // �o�����
        String CustomName = Invo_temp[2].trim();       // �Ȥ�W��
        String ProjectNo = table[x][4].trim();  // �קO
        String HuBei = Invo_temp[5].trim();            // ��O
        String Detailltem = hM031.get( table[x][2].trim() )!=null? (String)hM031.get( table[x][2].trim() ):""  ; // ���ڦW��
        String InvoiceMoney = Invo_temp[7].trim();     // �o�����|���B
        String InvoiceTax = Invo_temp[8].trim();       // �o���|�B
        String InvoiceTotalMoney = Invo_temp[9].trim();// �o���`���B
        int PrintTime = Integer.parseInt(Invo_temp[10].trim());       //�C�L�ɶ�
        String PointNo = Invo_temp[12].trim();         //�~���N��
        String PointName = (String) hM010.get(PointNo);
        String randomCode = Invo_temp[17].trim();      //�H���X
        CompanyNo = Invo_temp[18].trim();
        String CustomNo = Invo_temp[13].trim();        //�Ȥ�ID
        String ProcessInvoiceNo = Invo_temp[15].trim();
        String OBJECT_CD = Invo_temp[16].trim();
        String buyerZip = "";
        String address = "";
        String[] CustomInfo = (String[]) hCustom.get(table[x][2].trim() + CustomNo.trim()); //�Ȥ��� (�o�����X+�Ȥ�ID)
        if (CustomInfo != null) {
          buyerZip = CustomInfo[3].trim();
          address += CustomInfo[0].trim();
        }
        
        // 2012-06-18 ���q�W��... �ץ�
        String companyInvoNo = "";
        String Company_Name = "";
        String[][] FED1023A = t.queryFromPool("SELECT "
            + "AdminNo "
            + ",Company_Name "
            + ",CompanyInvoiceNo "
            + "FROM FED1023A "
            + "WHERE "
            + "Company_CD = '" + CompanyNo + "' "
            + "AND ProjectNo = '" + ProjectNo + "' ");
        if (FED1023A.length > 0) {
          Company_Name = FED1023A[0][1].trim();
          companyInvoNo = FED1023A[0][2].trim();
        }
        
        // �ȪA�Ȥ���
        if (ProcessInvoiceNo.equals("2")) {
          if (ProjectNo.trim().equals("H38")) ProjectNo = "H38A";
          String stringSQL = "SELECT OBJECT_FULL_NAME, MAIL_ADDR FROM FE5D05 " 
              + "WHERE RTRIM(DEPT_CD)+RTRIM(DEPT_CD_1) = '" + ProjectNo + "' AND OBJECT_CD = '" + OBJECT_CD + "'";
          String[][] AFE5D05 = tFE5D.queryFromPool(stringSQL);
          if (AFE5D05.length > 0) {
            address = AFE5D05[0][1].trim();
            CustomName = AFE5D05[0][0].trim();
          }
          stringSQL = "SELECT "
              + "OBJECT_FULL_NAME " 
              + "FROM InvoM0C0 " 
              + "WHERE CustomNo = '" + CustomNo + "' " 
              + "AND LEN(OBJECT_FULL_NAME) > 0 ";
          String[][] AInvoM0C0 = t.queryFromPool(stringSQL);
          if (AInvoM0C0.length > 0) {
            if (AInvoM0C0[0][0].length() > 0) {
              CustomName = AInvoM0C0[0][0].trim();
            }
          }
          CustomInfo = new String[3];
        }
        
        //�H�W�S���Ȥ��� �h�q�o���t�Χ�
        if(CustomInfo==null){
          String stringSQL = " SELECT "
              + "ZIPCode "
              + ", RTRIM(ISNULL(City,'')) "
              + ", RTRIM(ISNULL(Town,'')) "
              + ",RTRIM(Address) " 
              + "FROM InvoM0C0 " 
              + "WHERE CustomNo = '" + CustomNo + "' ";
          String [][] AInvoM0C0 = t.queryFromPool(stringSQL);
          if (AInvoM0C0.length > 0){
             if(AInvoM0C0[0][1].trim().equals(AInvoM0C0[0][2].trim())) {
               buyerZip = AInvoM0C0[0][0].trim();
               address = AInvoM0C0[0][1].trim() + AInvoM0C0[0][3].trim();
             }else {
               buyerZip = AInvoM0C0[0][0].trim();
               address = AInvoM0C0[0][1].trim() + AInvoM0C0[0][2].trim() + AInvoM0C0[0][3].trim();
             }
          }
        }
        System.out.println("���͵o��=" + InvoiceNo);
        
//        System.out.println(">>>buyerZip:" + buyerZip);
//        System.out.println(">>>address:" + address);
        
        //���Ӥ��e
        StringBuilder sbDetail = new StringBuilder();
        sbDetail.append("��~�H�νs:").append(companyInvoNo).append(";");
        if(Company_Name.length() > 0) {
//          Company_Name = Company_Name.replaceAll("�ѥ�����", "(��)").replaceAll("��ƳB", "��");
          sbDetail.append("�W��:").append( Company_Name.replaceAll("�ѥ�����", "(��)").replaceAll("����ƳB", "��") ).append(";");
        }
        sbDetail.append("���:").append(PrintDateTime).append(";");
        sbDetail.append("�o�����X:").append(InvoiceNo).append(";");
        sbDetail.append("�R���H:").append(CustomName).append(";");
        sbDetail.append("�צW:").append(ProjectNo).append(";");
        sbDetail.append("�ɼӧO:").append(HuBei).append(";");
        sbDetail.append("�K�n:").append(PointName + "  " + Detailltem).append(";");
        
        InvoicePrintVo vo = new InvoicePrintVo();
        //---����H
        vo.setRecipientPost(buyerZip);
        vo.setRecipientAddr(address);
        vo.setRecipientCompany("");
        vo.setRecipientName(CustomName);
        //----�o�����e
        vo.setInvoiceDate( Integer.toString(Integer.parseInt(InvoiceDate.replaceAll("/", "")) - 19110000) );
        vo.setInvoiceNumber(InvoiceNo);
        vo.setPrintDate(datetime.getToday("YYYYmmdd") + datetime.getTime("hms"));
        vo.setRandomCode(randomCode);
        vo.setSaleAmount(InvoiceMoney);
        vo.setTotal(InvoiceTotalMoney);
        if(CustomNo.length() == 8) {  //�k�H�~�n�ǰe�νs
          vo.setBuyerId(CustomNo);
        }else {
          vo.setBuyerId("");
        }
        vo.setSellerId(companyInvoNo);
        vo.setDetail(sbDetail.toString());
        vo.setPrintCount("" + (PrintTime + 1) );
        vo.setDeptId(PrintUserNo.equals("flife")? "25000":"�h�L25F��");
        vo.setBuyerName(CustomName);
        vo.setTax(InvoiceTax);
        
        InvoicePrintUtil iPrintUtil = new InvoicePrintUtil( PRINTURL );
        String rs = iPrintUtil.doPrint(vo);
        boolean printOK = rs.indexOf("SUCCESS:") == 0;
        if(printOK) {
          StringBuilder sql2 = new StringBuilder();
          sql2.append("update InvoM030 ");
          sql2.append("set printYES='").append("Y").append("' ");
          sql2.append(", printTimes='").append(PrintTime + 1).append("' ");
          sql2.append("where InvoiceNo='").append(InvoiceNo).append("' ");
          t.execFromPool(sql2.toString());
          result.put( InvoiceNo, "�C�L���\��:" + rs.replace("SUCCESS:", "") );
        }else {
          result.put( InvoiceNo, "�o�Ͱ��D:" + rs.replace("ERROR:", "") );
        }
        
      } //if end
    } //for end
    
//    System.out.println(">>>result:" + result);
//    messagebox( "�C�L���G:\n" + result.toString().replaceAll("{" , "").replaceAll(",", "").replaceAll("}", "\n") );
    messagebox( "�C�L���G:\n" + result );
    
    return value;
  }
  public String getInformation(){
    return "---------------button1(�o���M�L).defaultValue()----------------";
  }
}
