/**
 * �o�� - PROD - �s�W���s�{��
 * @author B04391
 *
 */

public class �o��_�����}�� {
  
  public String getDefaultValue(String value) throws Throwable {
 // �^�ǭȬ� true ��ܰ��汵�U�Ӫ���Ʈw���ʩάd��
 // �^�ǭȬ� false ��ܱ��U�Ӥ����������O
 // �ǤJ�� value �� "�s�W","�d��","�ק�","�R��","�C�L","PRINT" (�C�L�w�����C�L���s),"PRINTALL" (�C�L�w���������C�L���s) �䤤���@
 // �^�ǭȬ� true ��ܰ��汵�U�Ӫ���Ʈw���ʩάd��
 // �^�ǭȬ� false ��ܱ��U�Ӥ����������O
 // �ǤJ�� value �� "�s�W","�d��","�ק�","�R��","�C�L","PRINT" (�C�L�w�����C�L���s),"PRINTALL" (�C�L�w���������C�L���s) �䤤���@
 message("");
 String stringDiscountDate = getValue("DiscountDate");
 if(!check.isACDay(stringDiscountDate.replace("/",""))) {
     message("�����������~(YYYY/MM/DD)");
     return false;
 }
 talk dbInvoice = getTalk("Invoice");
 String stringSQL = "";
 String stringUserkey = "";
 //����
 String [][] A_table = getTableData("table1");
 if (A_table.length ==0){
    message("���ӥ����ܤ֦��@��");
    return false;
 }
 Calendar cal= Calendar.getInstance();//Current time
 stringUserkey = getUser() + "_T" + ""+( (cal.get(Calendar.HOUR_OF_DAY)*10000) + (cal.get(Calendar.MINUTE)*100) + cal.get(Calendar.SECOND) );
 System.out.println("stringUserkey=========>>"+stringUserkey);
 stringSQL = "DELETE " +
                       " FROM InvoM041TempBody " +
           " WHERE UseKey = '" + stringUserkey + "'";
 dbInvoice.execFromPool(stringSQL);
 int intBodyCount = 0; 
 String stringCustomNo = "";
 String stringMessage = "";
 int intCustomNo = 0;
 for(int i=0;i<A_table.length;i++){
    if(!A_table[i][8].equals("0")){
        if (!stringCustomNo.equals(A_table[i][18])){
          stringCustomNo = A_table[i][18];
        stringMessage += stringCustomNo+ "�B";
          intCustomNo ++;     
      }
     stringSQL =  " INSERT " +
                 " INTO InvoM041TempBody" +
                       "(" +
                         " UseKey," +
                         " RecordNo," +
                         " ChoiceYES," +
                         " InvoiceNo," +
                         " InvoiceKind," +
                         " InvoiceWay," +
                         " PointNo," +
                         " InvoiceMoney," +
                         " InvoiceTax," +
                         " InvoiceTotalMoney," +
                         " YiDiscountMoney," +
                         " DiscountItemMoney," +
                         " TaxRate," +
                         " TaxKind," +
                         " ProcessInvoiceNo," +
                         " DisCountTimes" +
                       " ) " +
                " VALUES (" +
                   "'" + stringUserkey +  "'," +
                   (intBodyCount + 1) + "," +
                   "'" +  A_table[i][0] +  "'," +                
                   "'" +  A_table[i][2] +  "'," +
                   "'" +  A_table[i][12]+  "'," +                
                   "'" +  A_table[i][13]+  "'," +                
                   "'" +  A_table[i][4]+  "'," +               
                        A_table[i][10]+  "," +               
                        A_table[i][11]+  "," +               
                        A_table[i][6]+  "," +                
                        A_table[i][7]+  "," +                                     
                        A_table[i][8]+  "," +                                                         
                   "'" +  A_table[i][17]+  "'," +                
                   "'" +  A_table[i][14]+  "'," +                                
                   "'" +  A_table[i][15]+  "'," +                                
                   "'" +  A_table[i][16]+  "'" +                                               
                   ")";
     dbInvoice.execFromPool(stringSQL);
     intBodyCount++;
   }
 }
 if(intCustomNo >1){ 
   stringMessage = stringMessage.substring(0,stringMessage.length()-1);
   message("�P�@�i�������i���h���Ȥ᪺�o��!�A�ж}���P��������F" + stringMessage);     
     return false; 
 } 
 /*
 if (stringDiscountDate.equals("9999/01/01")){

 } 
 */
 // �p�ⲣ�ʹX��������(�J��C{n=N_RecordNo%}���@�`���ɨϥ�)
 int intDiscountNoCount = intBodyCount  / 8;
 intDiscountNoCount ++;
 if (intBodyCount % 8 == 0) intDiscountNoCount = intDiscountNoCount -1;
 if(intDiscountNoCount == 0){
   message("�o������ ���i�ť�!");
   return false;
 }
 getButton("button3").doClick();

 //
 String retSystemDateTime[][] = dbInvoice.queryFromPool("spInvoSystemDateTime  'Admin'");
 String stringSystemDateTime ="";
 stringSystemDateTime = retSystemDateTime[0][0].replace("-","/");
 stringSystemDateTime = stringSystemDateTime.substring(0,19);
 //
 stringSQL = "spInvoM040Insert " +
                intDiscountNoCount + "," +
            "'" + getValue("DiscountDate").trim() + "'," +
            "'" + getValue("CompanyNo").trim() + "'," +
            "'" + getValue("DepartNo").trim() + "'," +
            "'" + getValue("ProjectNo").trim() + "'," +           
            "'" + getValue("HuBei").trim() + "'," +                               
            "'" + getValue("CustomNo").trim() + "'," +                              
            "'" + getValue("DiscountWay").trim() + "'," +                     
            "''," +                               
                    getValue("DiscountMoney").trim() + "," +                                        
                    getValue("DiscountTax").trim() + "," +                                        
                    getValue("DiscountTotalMoney").trim() + "," +
            "'1'," +                              
            "'" + getUser() + "'," +
            "'" + stringSystemDateTime  + "'," +
            "'" + stringSystemDateTime  + "'," +          
                "'A'," +
            "'" + stringUserkey + "'," +
            "'" + getValue("Reason").trim() + "'" ;
 dbInvoice.execFromPool(stringSQL);
 stringSQL = "SELECT DiscountNo " +
                    " FROM InvoM040 " +
                    " WHERE EmployeeNo = '" + getUser() + "'" +
                          " AND ModifyDateTime = '" + stringSystemDateTime + "'";
 String retInvoM040[][] = dbInvoice.queryFromPool(stringSQL);
 String stringDiscountNo = "";
 for(int n=0;n<retInvoM040.length;n++){
   stringDiscountNo += retInvoM040[n][0] + ";";
 }
 String cName[][] = dbInvoice.queryFromPool("SELECT CustomName FROM InvoM0C0 WHERE CustomNo='" + getValue("CustomNo").trim() + "'");
 String customerName = "";
 for(int n=0;n<cName.length;n++){
   customerName = cName[n][0];
   break;
 }
 //////////////////////////////////////////
 talk dbEIP = getTalk("EIP");
 talk dbeMail = getTalk("eMail");
 String userNo = getUser().toUpperCase().trim();
 String empNo="";
 String userEmail = "";
 String userEmail2 = "";
 String DPCode="";
 String DPManageemNo="";
 String DPeMail="";
 String DPeMail2="";
 String [][] retEip=null;
 String [][] reteMail = null;
 stringSQL="SELECT EMPNO FROM FGEMPMAP where FGEMPNO ='" + userNo + "'" ;
 retEip = dbEIP.queryFromPool(stringSQL);
 if(retEip.length>0){
   empNo=retEip[0][0] ;
 }
 /////////////////
 System.out.println("empNo===>"+empNo);
 ////////////////
 stringSQL="SELECT DP_CODE,PN_EMAIL1,PN_EMAIL2 FROM PERSONNEL WHERE PN_EMPNO='" + empNo + "'" ;
 reteMail = dbeMail.queryFromPool(stringSQL);
 if(reteMail.length>0){
   DPCode=reteMail[0][0] ;
   if (reteMail[0][1] != null && !reteMail[0][1].equals("")) {
     userEmail= reteMail[0][1] ;
   }
   if ( reteMail[0][2] != null && ! reteMail[0][2].equals("")) {
     userEmail2= reteMail[0][2] ;
   } 
 }
 /////////////////////////////////////////
 System.out.println("DPCode===>"+DPCode);
 System.out.println("userEmail===>"+userEmail);
 System.out.println("userEmail2===>"+userEmail2);
 /////////////////////////////////////////////////
 stringSQL="SELECT DP_MANAGEEMPNO FROM CATEGORY_DEPARTMENT WHERE DP_CODE='" + DPCode + "'" ;
 reteMail = dbeMail.queryFromPool(stringSQL);
 if(reteMail.length>0){
   DPManageemNo=reteMail[0][0] ;
 }
 /////////////////////////////////////////
 System.out.println("DPManageemNo===>"+DPManageemNo);
 /////////////////////////////////////////////////
 stringSQL="SELECT PN_EMAIL1,PN_EMAIL2 FROM PERSONNEL WHERE PN_EMPNO='" + DPManageemNo + "'" ;
 reteMail = dbeMail.queryFromPool(stringSQL);
 if(reteMail.length>0){
   if (reteMail[0][0] != null && !reteMail[0][0].equals("")) {
     DPeMail= reteMail[0][0] ;
   }
   if ( reteMail[0][1] != null && ! reteMail[0][1].equals("")) {
     DPeMail2= reteMail[0][1] ;
   } 
 }
 ////////////////////////////////////
 System.out.println("DPeMail===>"+DPeMail);
 System.out.println("DPeMail2===>"+DPeMail2);
 /////////////////////////////////////////////////////////
 //send email
 String msg ="�קO:" + getValue("ProjectNo").trim() + "�B��O:" + getValue("HuBei").trim() + "�B�Ȥ�:" + customerName + "�B�Ȥᨭ���ҩβνs:" + getValue("CustomNo").trim() + "�B�������:" + getValue("DiscountDate").trim() + "�B�������B:" + getValue("DiscountTotalMoney").trim() + "�A�нT�{�O�_���i��æ��~���q��";
 String subject = "�q���æ��~���@�~";
 String[] arrayUser = {"Justin_Lin@fglife.com.tw",userEmail, DPeMail};
 String  sendRS = sendMailbcc("ex.fglife.com.tw", "Emaker-Invoice@fglife.com.tw", arrayUser, subject, msg, null,  "", "text/html");

 System.out.println("sendRS===>"+sendRS);

 //Email email = new SimpleEmail();
 //email.setHostName("ex.fglife.com.tw");
 //email.setSubject("�q���æ��~���@�~");
 //email.setMsg("�קO:" + getValue("ProjectNo").trim() + "�B��O:" + getValue("HuBei").trim() + "�B�Ȥ�:" + customerName + "�B�Ȥᨭ���ҩβνs:" + getValue("CustomNo").trim() + "�B�������:" + getValue("DiscountDate").trim() + "�B�������B:" + getValue("DiscountTotalMoney").trim() + "�A�нT�{�O�_���i��æ��~���q��");
 //email.setFrom("Emaker-Invoice@fglife.com.tw");
 //if(userEmail!=null && !userEmail.equals("")){
//   email.addTo(userEmail);
 //}else if(userEmail2!=null && !userEmail2.equals("")){
//   email.addTo(userEmail2);
 //}else {
//   message("�d�L�g��H��("+empNo+")�H�c" ); 
 //}
 //if(DPeMail!=null && !DPeMail.equals("")){
//   email.addTo(DPeMail);
 //}else if(DPeMail2!=null && !DPeMail2.equals("")){
//   email.addTo(DPeMail2);
 //}else {
//   message("�d�L�g��H����D��("+DPManageemNo+")�H�c" ); 
 //}
 //email.send();
 ////////////////////////////////////////////
 /////////////////////////////////////////////////////////
 //�W�L���Q�Usend email
 String discountTotalMoney  = getValue("DiscountTotalMoney").trim();
 double dNumber = Double.parseDouble(discountTotalMoney);
 /////////////////////////////////////////////////////////
 System.out.println("dNumber===>"+dNumber);
 /////////////////////////////////////////////////////////
 String over50 = "";
 if(dNumber>=500000){
   String msg2 = "�קO:" + getValue("ProjectNo").trim() + "�B��O:" + getValue("HuBei").trim() + "�B�Ȥ�:" + customerName + "�B�Ȥᨭ���ҩβνs:" + getValue("CustomNo").trim() + "�B�������:" + getValue("DiscountDate").trim() + "�B�������B:" + getValue("DiscountTotalMoney").trim() + "�A�ŦX�P�@�Ȥᤣ�ʲ��R�櫴���_�l��7�Ѥ��A�h�٪��B�F50�U���H�W�A���ˮ֨�X�z��";
   String subject2 = "�q���æ��~���@�~";
   String[] arrayUser2 = {"Justin_Lin@fglife.com.tw",userEmail, DPeMail};
   String  sendRS2 = sendMailbcc("ex.fglife.com.tw", "Emaker-Invoice@fglife.com.tw", arrayUser2, subject2, msg2, null,  "", "text/html");
   
   System.out.println("sendRS2===>"+sendRS2);
   /*
   Email email2 = new SimpleEmail();
   email2.setHostName("ex.fglife.com.tw");
   email2.setSubject("�q���æ��~���@�~");
   email2.setMsg("�קO:" + getValue("ProjectNo").trim() + "�B��O:" + getValue("HuBei").trim() + "�B�Ȥ�:" + customerName + "�B�Ȥᨭ���ҩβνs:" + getValue("CustomNo").trim() + "�B�������:" + getValue("DiscountDate").trim() + "�B�������B:" + getValue("DiscountTotalMoney").trim() + "�A�ŦX�P�@�Ȥᤣ�ʲ��R�櫴���_�l��7�Ѥ��A�h�٪��B�F50�U���H�W�A���ˮ֨�X�z��");
   email2.setFrom("Emaker-Invoice@fglife.com.tw");
   if(userEmail!=null && !userEmail.equals("")){
     email2.addTo(userEmail);
   }else if(userEmail2!=null && !userEmail2.equals("")){
     email2.addTo(userEmail2);
   }else {
     message("�d�L�g��H��("+empNo+")�H�c" ); 
   }
   if(DPeMail!=null && !DPeMail.equals("")){
     email2.addTo(DPeMail);
   }else if(DPeMail2!=null && !DPeMail2.equals("")){
     email2.addTo(DPeMail2);
   }else {
     message("�d�L�g��H����D��("+DPManageemNo+")�H�c" ); 
   }
   
   email2.send();
   */
   //message("�ж�g�æ��~����x�A���ˮ֪�ζi��q���æ��~���@�~!!");    
   over50= "�ж�g�æ��~����x�A���ˮ֪�ζi��q���æ��~���@�~!!";
 } 
 ////////////////////////////////////////////
 action(9);
 if(!over50.equals("")){
   over50 = over50 + "  ";
 }
 messagebox(over50+ "�w���ͧ����� = " + stringDiscountNo);     
 // 2013-12-25 �ƻs��ŶKï      
 Farglory.util.FargloryUtil  exeUtil  =  new  Farglory.util.FargloryUtil() ;
 exeUtil.ClipCopy (exeUtil.doSubstring(stringDiscountNo,  0,  stringDiscountNo.length()-1)) ;
 return false;
  }
  
}
