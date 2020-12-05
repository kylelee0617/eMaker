package Sale.AML;
import javax.swing.*;
import jcx.jform.bproc;
import cLabel;
import Farglory.util.*;
import jcx.jform.bNotify;
import jcx.jform.bBase;
import java.io.*;
import java.util.*;
import jcx.util.*;
import jcx.html.*;
import jcx.db.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckAML2 extends bproc{
	public String getDefaultValue(String value)throws Throwable{
		//20191107 �~���θꮣ���I�޲z�F���B�z�{�ǧ@�~+����~���Υ����ꮣ���I�����B�z�{�ǧ@�~+�~���θꮣ�W����B�z�{�ǧ@�~
		System.out.println("===========AML============S");
		talk  dbSale =  getTalk("Sale") ;
		talk  db400CRM =  getTalk("400CRM") ;
		talk  dbPW0D =  getTalk("pw0d") ;
		talk  dbJGENLIB  =  getTalk("JGENLIB") ;
		talk  dbEIP  =  getTalk("EIP") ;
		String strSaleSql = "";
		String str400CRMSql = "";
		String strPW0DSql = "";
		String strJGENLIBSql = "";
		String strEIPSql = "";
		String strBDaysql = "";
		String str400sql = "";
		String stringSQL = "";
		String strPW0Dsql = "";
		String[][]   ret080Table;//�{��
		String[][]   ret083Table;//�H�Υd
		String[][]   ret328Table;//�Ȧ�
		String[][]   ret082Table;//����
		String[][]  ret070Table;
		String[][] retPDCZPFTable;
		String[][] retQueryLog;
		String[][] retCList;
		//���e����
		String strActionName =  getValue("actionName").trim() ;//�@�ʦW��
		String strCreditCardMoney  =  getValue("CreditCardMoney").trim() ;//�H�Υd
		String strCashMoney  =  getValue("CashMoney").trim() ;//�{��
		String strBankMoney  =  getValue("BankMoney").trim() ;//�Ȧ�
		String strCheckMoney  =  getValue("CheckMoney").trim() ;//����
		String strReceiveMoney = getValue("ReceiveMoney").trim() ;//�����`�B
		String strProjectID1 =  getValue("field2").trim() ;//�קO�N�X
		String strEDate =  getValue("field3").trim() ;//���ڤ��
		String strDocNo =  getValue("field4").trim() ;//�s��
		if("".equals(strCreditCardMoney)){
			strCreditCardMoney = "0";
		}
		if("".equals(strCashMoney)||"0.0".equals(strCashMoney)){
			strCashMoney = "0";
		}
		if("".equals(strBankMoney)||"0.0".equals(strBankMoney)){
			strBankMoney = "0";
		}
		if("".equals(strCheckMoney)){
			strCheckMoney = "0";
		}
		//�Nú�H����
		String strDeputy=getValue("PaymentDeputy").trim();
		String strDeputyName = getValue("DeputyName").trim();
		String strDeputyID=getValue("DeputyID").trim();
		String strDeputyRelationship = getValue("DeputyRelationship").trim();
		String bStatus=getValue("B_STATUS").trim();
		String cStatus=getValue("C_STATUS").trim();
		String rStatus=getValue("R_STATUS").trim();
		//�ʶR�H�m�W
		String allOrderID = "";
		String allOrderName = "";
		String percentage = ""; 
		String[][] orderCustomTable =  getTableData("table3");
		for (int g = 0; g < orderCustomTable.length; g++) {
			if("".equals(allOrderName)){
				allOrderID =  orderCustomTable[g][3].trim();
				allOrderName =  orderCustomTable[g][4].trim();
				percentage = orderCustomTable[g][5].trim();
			}else{
				allOrderID = allOrderID+"�B"+ orderCustomTable[g][3].trim();
				allOrderName = allOrderName+"�B"+ orderCustomTable[g][4].trim();
				percentage = percentage+"�B"+ orderCustomTable[g][5].trim();
			}
		}
		//13,14
		String rule13=getValue("Rule13").trim();
		String rule14=getValue("Rule14").trim();
		//�@��
		String errMsg="";
		String allCustomName = allOrderName;
		String allCustomID = allOrderID;
		//���ڤ������榡
		String[] tempEDate = strEDate.split("/");
		String rocDate = "";
		String year = tempEDate[0];
		int intYear = Integer.parseInt(year) - 1911;
		rocDate = Integer.toString(intYear)+ tempEDate[1]+ tempEDate[2];
		//LOG NOW DATE
		Date now = new Date();
		SimpleDateFormat nowsdf = new SimpleDateFormat("yyyyMMdd");
		String strNowDate = nowsdf.format(now);
		String tempROCYear=""+(Integer.parseInt(strNowDate.substring(0,strNowDate.length()-4))-1911);
		String RocNowDate = tempROCYear+strNowDate.substring(strNowDate.length()-4,strNowDate.length());
		SimpleDateFormat nowTimeSdf = new SimpleDateFormat("HHmmss");
		String strNowTime = nowTimeSdf.format(now);
		SimpleDateFormat nowTimestampSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNowTimestamp =  nowTimestampSdf.format(now);
		//���s
		String userNo = getUser().toUpperCase().trim();
		String empNo="";
		String [][] retEip=null;
		strEIPSql="SELECT EMPNO FROM FGEMPMAP where FGEMPNO ='" + userNo + "'" ;
		retEip = dbEIP.queryFromPool(strEIPSql);
		if(retEip.length>0){
			empNo=retEip[0][0] ;
		}
		//�ʪ��ҩ��渹
		String strOrderNo = "";
		String orderNos = "";
		String[][] orderNoTable =  getTableData("table4");
		strOrderNo=orderNoTable[0][2].trim();
		for (int g = 0; g < orderNoTable.length; g++) {
      if("".equals(orderNos)){
        orderNos =  orderNoTable[g][2].trim();
      }else{
        orderNos += "�B"+ orderNoTable[g][2].trim();
      }
    }
		//�~���l�ܬy����
		int intRecordNo =1;
		strSaleSql = "SELECT MAX(RecordNo) AS MaxNo FROM Sale05M070 WHERE OrderNo ='"+strOrderNo+"'";
		ret070Table = dbSale.queryFromPool(strSaleSql);
		if(!"".equals(ret070Table[0][0].trim())){
			intRecordNo = Integer.parseInt(ret070Table[0][0].trim())+1;
		}
		//actionNo
		String ram = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			ram += String.valueOf(random.nextInt(10));
		}
		String actionNo =strNowDate+ strNowTime+ram;
		
		
		//start of �˺A1~4  Kyle
		//1�P�@�Ȥ�P�@��~�餺2��(�t)�H�W�]�t�{���B�״ڡB�H�Υd�B�䲼����A�B�C���Ҥ���s�x��450,000~499,999���A�t���ˮֹwĵ�C
		//2�P�@�Ȥ�3����~�餺�A��2��H�{���ζ״ڹF450,000~499,999��, �t���ˮִ��ܳq���C
		//3�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C
		//4�P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if(strCashMoney == null || "".equals(strCashMoney)) { //�{���`�B
		  strCashMoney = "0";
		}
		if(strCreditCardMoney == null || "".equals(strCreditCardMoney)) { //�H�Υd�`�B
		  strCreditCardMoney = "0";
    }
		if(strBankMoney == null || "".equals(strBankMoney)) { //�Ȧ��`�B
		  strBankMoney = "0";
    }
		if(strCheckMoney == null || "".equals(strCheckMoney)) { //�����`�B
		  strCheckMoney = "0";
    }
		if(strReceiveMoney == null || "".equals(strReceiveMoney)) { //���ڳ��`�B
		  strReceiveMoney = "0";
    }
		double dCashMoney = Double.parseDouble(strCashMoney);
		double dCheckMoney = Double.parseDouble(strCheckMoney);
		double dBankMoney = Double.parseDouble(strBankMoney);
		double dReceiveMoney = Double.parseDouble(strReceiveMoney);
		String[] orderNoss = orderNos.split("�B");
		String[] customNos = allCustomID.split("�B");
		String[] percentages = percentage.split("�B");
		
		KUtils kutil = new KUtils();
		String tempMsg = "";
		AMLBean aml = new AMLBean();
		aml.setProjectID1(strProjectID1);
		aml.setFuncName("����");
		aml.setActionName(strActionName);
		aml.setCustomTitle("�Ȥ�");
		aml.setTrxDate(strEDate);
		aml.setOrderNos(kutil.genQueryInString(orderNoss));
		aml.setCustomNos(kutil.genQueryInString(customNos));
		aml.setCustomNames(allCustomName);
		AMLTools amlTool = new AMLTools(aml);
		
		//TODO: �A��1
		tempMsg = amlTool.chkAML001(aml).getData().toString();
		errMsg += tempMsg; 
		
		//TODO: �A��2
		//����Y���@���{���ζ״ڤ���45~49�h�ˬd�e���
		//Tips: �q���Ȥ�n���}�B�z
		if(dCashMoney > 0 || dBankMoney > 0) {
		  if( (dCashMoney >= 450000 && dCashMoney <= 499999) || (dBankMoney >= 450000 && dBankMoney <= 499999) ) {  //�q��
	      tempMsg = amlTool.chkAML002(aml , "order").getData().toString();
	      errMsg += tempMsg;
	    }
	    for(int g=0 ; g<customNos.length ; g++) {
	      if( (dCashMoney*Double.parseDouble(percentages[g].trim())/100 >= 450000 && dCashMoney*Double.parseDouble(percentages[g].trim())/100 <= 499999) 
	          || (dBankMoney*Double.parseDouble(percentages[g].trim())/100 >= 450000 && dBankMoney*Double.parseDouble(percentages[g].trim())/100 <= 499999) ) {  //�Ȥ�
	        aml.setCustomId( customNos[g].trim() );
	        tempMsg = amlTool.chkAML002(aml , "custom").getData().toString();
	        errMsg += tempMsg;
	      }
	    }
		}
		
		//TODO: �A��3
		if(dCashMoney > 0) {
		  tempMsg = amlTool.chkAML003(aml).getData().toString();
	    errMsg += tempMsg;
		}
		
		//TODO: �A��4
		if(dCashMoney > 0) {
		  tempMsg = amlTool.chkAML004(aml).getData().toString();
	    errMsg += tempMsg;
		}
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//End of �A��1~4 Kyle
		
		
		//Pattern5,8,9,10,11,17~20
		//�H�Υd
		ret083Table  =  getTableData("table5");
		if(ret083Table.length > 0) {
			for(int e=0;e<ret083Table.length;e++){
				String str083Deputy = ret083Table[e][7].trim();//���Hú��
				String str083DeputyName=ret083Table[e][8].trim();//�m�W
				String str083DeputyId=ret083Table[e][9].trim();//�����Ҹ�
				String str083Rlatsh=ret083Table[e][10].trim();//���Y
				String str083Bstatus=ret083Table[e][12].trim();//�¦W��
				String str083Cstatus=ret083Table[e][13].trim();//���ަW��
				String str083Rstatus=ret083Table[e][14].trim();//�Q���H
		System.out.println("str083Deputy=====>"+str083Deputy);
		System.out.println("str083DeputyName=====>"+str083DeputyName);
		System.out.println("str083DeputyId=====>"+str083DeputyId);
		System.out.println("str083Rlatsh=====>"+str083Rlatsh);
		System.out.println("str083Bstatus=====>"+str083Bstatus);
		System.out.println("str083Cstatus=====>"+str083Cstatus);
		System.out.println("str083Rstatus=====>"+str083Rstatus);
				//���A��LOG_2,3,4,6,7,9,10,11,12,15,16
				//2
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','002','�P�@�Ȥ�3����~�餺�A��2��H�{���ζ״ڹF450,000~499,999��, �t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//3
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','003','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//4
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','004','�P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//6
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','006','�P�@�Ȥᤣ�ʲ��R��Añ���e�h�q�����ʶR�A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//7
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','007','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//9
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','009','�Ȥ�Y�ӦۥD�޾����Ҥ��i����~���P�����ꮣ���Y���ʥ�����a�Φa�ϡA�Ψ�L����`�Υ��R����`����a�Φa�ϡA���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//10
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','010','�ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//11
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','011','����̲ר��q�H�Υ���H���D�޾������i�����Ƥ��l�ι���F�ΰ�ڻ{�w�ΰl�d�����Ʋ�´�F�Υ������æ��P���Ʋ�´�����p�̡A���̸ꮣ����k�i������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//12
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','012','�Ȥ�n�D�N���ʲ��v�Q�n�O���ĤT�H�A���ണ�X�������p�Ωڵ����������`���p�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//15
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','015','�n�D���q�}�ߨ����T��I�������䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//16
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','016','�n�D���q�}�ߺM�P����u(�������u)�䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				
				if("Y".equals(str083Deputy)){//���Nú�H
					//18����W��
					//Query_Log ���ͤ�
					strPW0Dsql = "SELECT BIRTHDAY FROM QUERY_LOG WHERE PROJECT_ID = '"+strProjectID1+"' AND QUERY_ID = '"+str083DeputyId+"' AND NAME = '"+str083DeputyName+"'";
					retQueryLog = dbPW0D.queryFromPool(strPW0Dsql);
					if(retQueryLog.length > 0) {
						System.out.println("BIRTHDAY====>"+retQueryLog[0][0].trim().replace("/","-")) ;
						strBDaysql = "AND ( CUSTOMERNAME='"+str083DeputyName+"' AND BIRTHDAY = '"+retQueryLog[0][0].trim().replace("/","-")+"' )";
					}else{
						strBDaysql = "AND CUSTOMERNAME='"+str083DeputyName+"'";
					}
					System.out.println("strBDaysql====>"+strBDaysql) ;
					//AS400
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X181' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str083DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '018', '�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}else{
							errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}
						*/
						errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���ŦX','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str083DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//X171
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X171' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str083DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '021', '�ӫȤ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str083DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���ŦX','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str083DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡC�Ш̬~������@�~��z
					if("�B��".equals(str083Rlatsh) || "��L".equals(str083Rlatsh)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '005', '�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���ŦX','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str083DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C
					//Sale05M070
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','008','�Nú�ڤH"+str083DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '008', '���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}else{
						errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}
					*/
					errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C\n";
					//�Ȥᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C
					//�Ȥᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C
					if("Y".equals(str083Bstatus) || "Y".equals(str083Cstatus)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName ,RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '020', '�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}else{
							errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}
						*/
						errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���ŦX','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str083DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//�Ȥᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C
					if("Y".equals(str083Rstatus)){
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str083DeputyId+"', '"+str083DeputyName+"', '773', '019', '�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�H�Υd�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}else{
							errMsg =errMsg+"\n�H�Υd�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}
						*/
						errMsg =errMsg+"�H�Υd�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�H�Υd���','"+strActionName+"', '���ŦX','"+str083DeputyId+"','"+str083DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str083DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
				}else{
					//���Hú��(���A��5,8,17,19,20)
					//5
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','005','�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//8
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','008','���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//17
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','017','�ӫȤᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//17
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','018','�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//19
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','019','�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//20
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�H�Υd���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','020','�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}		
			}
		}
		//�{��(�u���@��)
		System.out.println("strDeputy=====>"+strDeputy);
		System.out.println("strDeputyName=====>"+strDeputyName);
		System.out.println("strDeputyID=====>"+strDeputyID);
		System.out.println("strDeputyRelationship=====>"+strDeputyRelationship);
		System.out.println("bStatus=====>"+bStatus);
		System.out.println("cStatus=====>"+cStatus);
		System.out.println("rStatus=====>"+rStatus);
		System.out.println("strCashMoney=====>"+strCashMoney);
		//���A��LOG_6,9,10,11,12,15,16
		//6
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','006','�P�@�Ȥᤣ�ʲ��R��Añ���e�h�q�����ʶR�A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//9
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','009','�Ȥ�Y�ӦۥD�޾����Ҥ��i����~���P�����ꮣ���Y���ʥ�����a�Φa�ϡA�Ψ�L����`�Υ��R����`����a�Φa�ϡA���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//10
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','010','�ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//11
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','011','����̲ר��q�H�Υ���H���D�޾������i�����Ƥ��l�ι���F�ΰ�ڻ{�w�ΰl�d�����Ʋ�´�F�Υ������æ��P���Ʋ�´�����p�̡A���̸ꮣ����k�i������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//12
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','012','�Ȥ�n�D�N���ʲ��v�Q�n�O���ĤT�H�A���ണ�X�������p�Ωڵ����������`���p�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//15
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','015','�n�D���q�}�ߨ����T��I�������䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//16
		strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','016','�n�D���q�}�ߺM�P����u(�������u)�䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
		dbSale.execFromPool(strSaleSql);
		intRecordNo++;
		//5, 8, 17,18,19,20,21
		if(Double.parseDouble(strCashMoney) != 0){//���{��ú�O
			if("Y".equals(strDeputy)){//���Nú�H
				//18����W��
				//Query_Log ���ͤ�
				strPW0DSql = "SELECT BIRTHDAY FROM QUERY_LOG WHERE PROJECT_ID = '"+strProjectID1+"' AND QUERY_ID = '"+strDeputyID+"' AND NAME = '"+strDeputyName+"'";
				retQueryLog = dbPW0D.queryFromPool(strPW0DSql);
				if(retQueryLog.length > 0) {
					System.out.println("BIRTHDAY====>"+retQueryLog[0][0].trim().replace("/","-")) ;
					strBDaysql = "AND ( CUSTOMERNAME='"+strDeputyName+"' AND BIRTHDAY = '"+retQueryLog[0][0].trim().replace("/","-")+"' )";
				}else{
					strBDaysql = "AND CUSTOMERNAME='"+strDeputyName+"'";
				}
				System.out.println("strBDaysql====>"+strBDaysql) ;
				//AS400
				str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X181' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+strDeputyID+"' "+strBDaysql ;
				retCList = db400CRM.queryFromPool(str400sql);
				if(retCList.length > 0) {
					//400 LOG
					stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '018', '�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(stringSQL);	
					//SALE LOG
					stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) "+
									" VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '�Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(stringSQL);
					intRecordNo++;
					/*
					if("".equals(errMsg)){
						errMsg ="�{���Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
					}else{
						errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
					}
					*/
					errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC\n";
				}else{
					//���ŦX
					stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) "+
									" VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���ŦX','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+strDeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(stringSQL);
					intRecordNo++;
				}
				//X171
				str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X171' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+strDeputyID+"' "+strBDaysql ;
				retCList = db400CRM.queryFromPool(str400sql);
				if(retCList.length > 0) {
					//400 LOG
					stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '021', '�Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(stringSQL);	
					//SALE LOG
					stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) "+
									" VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '�Nú�ڤH"+strDeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+strDeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(stringSQL);
					intRecordNo++;
					/*
					if("".equals(errMsg)){
						errMsg ="�{���Nú�ڤH"+strDeputyName+"�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
					}else{
						errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
					}
					*/
					errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C\n";
				}else{
					//���ŦX
					stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) "+
									" VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���ŦX','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+strDeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(stringSQL);
					intRecordNo++;
				}
				//�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡC�Ш̬~������@�~��z
				if("�B��".equals(strDeputyRelationship) || "��L".equals(strDeputyRelationship)){
					//Sale05M070
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '�Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '005', '�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="�{���Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
					}else{
						errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
					}
					*/
					errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C\n";
				}else{
					//���ŦX
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���ŦX','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+strDeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}
				//���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C
				//Sale05M070
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"','�Nú�ڤH"+strDeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','008','�Nú�ڤH"+strDeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//AS400
				strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '008', '���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbJGENLIB.execFromPool(strJGENLIBSql);
				/*
				if("".equals(errMsg)){
					errMsg ="�{���Nú�ڤH"+strDeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
				}else{
					errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
				}
				*/
				errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C\n";
				//�Ȥᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C
				//�Ȥᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C
				if("Y".equals(bStatus) || "Y".equals(cStatus)){
					//Sale05M070
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '�Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '020', '�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="�{���Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
					}else{
						errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
					}
					*/
					errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C\n";
				}else{
					//���ŦX
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '���ŦX','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+strDeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}
				//�Ȥᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C
				if("Y".equals(rStatus)){
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '�Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '019', '�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="�{���Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
					}else{
						errMsg =errMsg+"\n�{���Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
					}
					*/
					errMsg =errMsg+"�{���Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C\n";
				}else{
					//���ŦX
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '���ŦX','"+strDeputyID+"','"+strDeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+strDeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}
			}else{//�{�����Hú��
				//���A��5,8,17,18,19,20
				//5
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo, Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','005','�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//8
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','008','���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//17
				strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','017','�ӫȤᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//18
				strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','018','�Ȥᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//19
				strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','019','�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//20
				strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�{�����','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','020','�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
			}
		}
		//�Ȧ�
		ret328Table  =  getTableData("table9");
		if(ret328Table.length > 0) {
			for(int f=0;f<ret328Table.length;f++){
				String str328Deputy = ret328Table[f][5].trim();//���Hú��
				String str328DeputyName=ret328Table[f][6].trim();//�m�W
				String str328DeputyId=ret328Table[f][7].trim();
				String str328ExPlace=ret328Table[f][8].trim();
				String str328Rlatsh=ret328Table[f][9].trim();
				String str328bStatus=ret328Table[f][11].trim();
				String str328cStatus=ret328Table[f][12].trim();
				String str328rStatus=ret328Table[f][13].trim();		
		System.out.println("str328Deputy=====>"+str328Deputy);
		System.out.println("str328DeputyName=====>"+str328DeputyName);
		System.out.println("str328DeputyId=====>"+str328DeputyId);
		System.out.println("str328ExPlace=====>"+str328ExPlace);
		System.out.println("str328Rlatsh=====>"+str328Rlatsh);
		System.out.println("str328bStatus=====>"+str328bStatus);
		System.out.println("str328cStatus=====>"+str328cStatus);
		System.out.println("str328rStatus=====>"+str328rStatus);
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','007','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//9
				//���A��3,4,6,7,9,11,12,15,16
				//3
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','003','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//4
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','004','�P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//6
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','006','�P�@�Ȥᤣ�ʲ��R��Añ���e�h�q�����ʶR�A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//7
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','009','�Ȥ�Y�ӦۥD�޾����Ҥ��i����~���P�����ꮣ���Y���ʥ�����a�Φa�ϡA�Ψ�L����`�Υ��R����`����a�Φa�ϡA���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//11
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','011','����̲ר��q�H�Υ���H���D�޾������i�����Ƥ��l�ι���F�ΰ�ڻ{�w�ΰl�d�����Ʋ�´�F�Υ������æ��P���Ʋ�´�����p�̡A���̸ꮣ����k�i������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//12
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','012','�Ȥ�n�D�N���ʲ��v�Q�n�O���ĤT�H�A���ണ�X�������p�Ωڵ����������`���p�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//15
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','015','�n�D���q�}�ߨ����T��I�������䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//16
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','016','�n�D���q�}�ߺM�P����u(�������u)�䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//�۪��ĺʷ��޲z�e���|�����ڨ���~����´�Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`��ڨ���~����´��ĳ����a�Φa�϶פJ������ڶ��C
				strJGENLIBSql =  "SELECT CZ07 FROM PDCZPF WHERE CZ01='NATIONCODE' AND CZ09 = '" + str328ExPlace + "'";
				retPDCZPFTable = dbJGENLIB.queryFromPool(strJGENLIBSql);
				if(retPDCZPFTable.length > 0){
					String strCZ07 =retPDCZPFTable[0][0].trim();
					if("�u���k��".equals(strCZ07)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '�Nú�ڤH"+str328DeputyName+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','010','�Nú�ڤH"+str328DeputyName+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '010', '�ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						
						String strTempMsg = "";
						if("Y".equals(str328Deputy)){
							strTempMsg = "�Ȧ�Nú�ڤH"+str328DeputyName;
						}else{
							strTempMsg = "�Ȥ�"+allOrderName;
						}
						/*			
						if("".equals(errMsg)){
							errMsg =strTempMsg+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n"+strTempMsg+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+strTempMsg+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���ŦX','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','010','�Nú�ڤH"+str328DeputyName+"�Y�Ӧ۬~���θꮣ����Y���ʥ��B����`�Υ��R����`����a�Φa�϶פJ���ڶ��A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
				}
				if("Y".equals(str328Deputy)){//���Nú�H
					//����W��
					System.out.println("�Ȧ����W��====>") ;
					//Query_Log ���ͤ�
					strPW0Dsql = "SELECT BIRTHDAY FROM QUERY_LOG WHERE PROJECT_ID = '"+strProjectID1+"' AND QUERY_ID = '"+str328DeputyId+"' AND NAME = '"+str328DeputyName+"'";
					retQueryLog = dbPW0D.queryFromPool(strPW0Dsql);
					if(retQueryLog.length > 0) {
						System.out.println("BIRTHDAY====>"+retQueryLog[0][0].trim().replace("/","-")) ;
						strBDaysql = "AND ( CUSTOMERNAME='"+str328DeputyName+"' AND BIRTHDAY = '"+retQueryLog[0][0].trim().replace("/","-")+"' )";
					}else{
						strBDaysql = "AND CUSTOMERNAME='"+str328DeputyName+"'";
					}
					System.out.println("strBDaysql====>"+strBDaysql) ;
					//AS400
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X181' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str328DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '018', '�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName,RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}else{
							errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}
						*/
						errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName,RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���ŦX','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str328DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//X171
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X171' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str328DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '021', '�Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName,RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','�Nú�ڤH"+str328DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str328DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName,RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���ŦX','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str328DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡC�Ш̬~������@�~��z
					if("�B��".equals(str328Rlatsh) || "��L".equals(str328Rlatsh)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName, RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '005', '�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName, RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���ŦX','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str328DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C
					//Sale05M070
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','�Nú�ڤH"+str328DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','008','�Nú�ڤH"+str328DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '008', '���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}else{
						errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}
					*/
					errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C\n";
					//�Ȥᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C
					//�Ȥᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C
					if("Y".equals(str328bStatus) || "Y".equals(str328cStatus)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '020', '�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}else{
							errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}
						*/
						errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���ŦX','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str328DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//�Ȥᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C
					if("Y".equals(str328rStatus)){
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"', '�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str328DeputyId+"', '"+str328DeputyName+"', '773', '019', '�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="�Ȧ�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}else{
							errMsg =errMsg+"\n�Ȧ�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}
						*/
						errMsg =errMsg+"�Ȧ�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"', '���ŦX','"+str328DeputyId+"','"+str328DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str328DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
				}else{
					//���Hú��(���A��)10,5,8,17,19,20
					//5
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName, RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','005','�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//8
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','008','���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//10
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','010','�ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//17
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','017','�ӫȤᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//18
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','018','�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//19
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"', '���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','019','�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//20
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','020','�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//21
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType,ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȧ���','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','021','�Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}	
			}
		}
		//����
		ret082Table  =  getTableData("table2");
		if(ret082Table.length > 0) {
			for(int g=0;g<ret082Table.length;g++){
				String str082Deputy = ret082Table[g][8].trim();//���Hú��
				String str082DeputyName=ret082Table[g][9].trim();//�m�W
				String str082DeputyId=ret082Table[g][10].trim();//�����Ҹ�
				String str082Rlatsh=ret082Table[g][11].trim();//���Y
				String str082Bstatus=ret082Table[g][13].trim();//�¦W��
				String str082Cstatus=ret082Table[g][14].trim();//���ަW��
				String str082Rstatus=ret082Table[g][15].trim();//�Q���H
		System.out.println("str082Deputy=====>"+str082Deputy);
		System.out.println("str082DeputyName=====>"+str082DeputyName);
		System.out.println("str082DeputyId=====>"+str082DeputyId);
		System.out.println("str082Rlatsh=====>"+str082Rlatsh);
		System.out.println("str082Bstatus=====>"+str082Bstatus);
		System.out.println("str082Cstatus=====>"+str082Cstatus);
		System.out.println("str082Rstatus=====>"+str082Rstatus);
				//���A��2,3,4,6,7,9,10,11,12,15,16
				//2
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','002','�P�@�Ȥ�3����~�餺�A��2��H�{���ζ״ڹF450,000~499,999��, �t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//3
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','003','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//4
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','004','�P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//6
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','006','�P�@�Ȥᤣ�ʲ��R��Añ���e�h�q�����ʶR�A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//7
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','007','�P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//9
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','009','�Ȥ�Y�ӦۥD�޾����Ҥ��i����~���P�����ꮣ���Y���ʥ�����a�Φa�ϡA�Ψ�L����`�Υ��R����`����a�Φa�ϡA���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//10
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','010','�ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L����`�Υ��R����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//11
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','011','����̲ר��q�H�Υ���H���D�޾������i�����Ƥ��l�ι���F�ΰ�ڻ{�w�ΰl�d�����Ʋ�´�F�Υ������æ��P���Ʋ�´�����p�̡A���̸ꮣ����k�i������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//12
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','012','�Ȥ�n�D�N���ʲ��v�Q�n�O���ĤT�H�A���ണ�X�������p�Ωڵ����������`���p�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//15
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','015','�n�D���q�}�ߨ����T��I�������䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
				//16
				strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','016','�n�D���q�}�ߺM�P����u(�������u)�䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
				dbSale.execFromPool(strSaleSql);
				intRecordNo++;
			
				if("Y".equals(str082Deputy)){//���Nú�H
					System.out.println("���ڨ���W��====>") ;
					//����W��
					//Query_Log ���ͤ�
					strPW0Dsql = "SELECT BIRTHDAY FROM QUERY_LOG WHERE PROJECT_ID = '"+strProjectID1+"' AND QUERY_ID = '"+str082DeputyId+"' AND NAME = '"+str082DeputyName+"'";
					retQueryLog = dbPW0D.queryFromPool(strPW0Dsql);
					if(retQueryLog.length > 0) {
						System.out.println("BIRTHDAY====>"+retQueryLog[0][0].trim().replace("/","-")) ;
						strBDaysql = "AND ( CUSTOMERNAME='"+str082DeputyName+"' AND BIRTHDAY = '"+retQueryLog[0][0].trim().replace("/","-")+"' )";
					}else{
						strBDaysql = "AND CUSTOMERNAME='"+str082DeputyName+"'";
					}
					System.out.println("strBDaysql====>"+strBDaysql) ;
					//AS400
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X181' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str082DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '018', '�ӫȤᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"', '�Nú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="���ڥNú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}else{
							errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC";
						}
						*/
						errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"', '���ŦX','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','018','�Nú�ڤH"+str082DeputyName+"�����ޤ�����W���H�A�иT�����A�è̬~��������q���@�~�e�e�k��ǡC','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//X171
					str400sql = "SELECT * FROM CRCLNAPF WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE AND L.CONTROLCLASSIFICATIONCODE ='X171' AND C.REMOVEDDATE >= '"+strNowTimestamp+"' ) AND ISREMOVE = 'N'  AND CUSTOMERID = '"+str082DeputyId+"' "+strBDaysql ;
					retCList = db400CRM.queryFromPool(str400sql);
					if(retCList.length > 0) {
						//400 LOG
						stringSQL = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '021', '�Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(stringSQL);	
						//SALE LOG
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"', '�Nú�ڤH"+str082DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str082DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
						/*
						if("".equals(errMsg)){
							errMsg ="���ڥNú�ڤH"+str082DeputyName+"�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						stringSQL = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"', '���ŦX','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','021','�Nú�ڤH"+str082DeputyName+"�B�a�x�����Φ��K�����Y���H�A�����n�F�v��¾�ȤH�h�A�Х[�j�Ȥ��¾�լd�A�è̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(stringSQL);
						intRecordNo++;
					}
					//�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡC�Ш̬~������@�~��z
					if("�B��".equals(str082Rlatsh) || "��L".equals(str082Rlatsh)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','�Nú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '005', '�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="���ڥNú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}else{
							errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C";
						}
						*/
						errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���ŦX','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','005','�Nú�ڤH"+str082DeputyName+"�P�Ȥ�"+allOrderName+"�D�G�˵����������Y�A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C
					//Sale05M070
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','�Nú�ڤH"+str082DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','008','�Nú�ڤH"+str082DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//AS400
					strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '008', '���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbJGENLIB.execFromPool(strJGENLIBSql);
					/*
					if("".equals(errMsg)){
						errMsg ="���ڥNú�ڤH"+str082DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}else{
						errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C";
					}
					*/
					errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"�N����z���ʲ�����A�Ш̬~���θꮣ����@�~��z�C\n";
					//�Ȥᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C
					//�Ȥᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C
					if("Y".equals(str082Bstatus) || "Y".equals(str082Cstatus)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','�Nú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '020', '�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="���ڥNú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}else{
							errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C";
						}
						*/
						errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���ŦX','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','020','�Nú�ڤH"+str082DeputyName+"���æ��¦W���H�A���ЮֽT�{��A�A�i������������@�~�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
					//�Ȥᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C
					if("Y".equals(str082Rstatus)){
						//Sale05M070
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','�Nú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
						//AS400
						strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+str082DeputyId+"', '"+str082DeputyName+"', '773', '019', '�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbJGENLIB.execFromPool(strJGENLIBSql);
						/*
						if("".equals(errMsg)){
							errMsg ="���ڥNú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}else{
							errMsg =errMsg+"\n���ڥNú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C";
						}
						*/
						errMsg =errMsg+"���ڥNú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C\n";
					}else{
						//���ŦX
						strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���ŦX','"+str082DeputyId+"','"+str082DeputyName+"','"+strEDate+"','RY','773','019','�Nú�ڤH"+str082DeputyName+"�����q�Q�`���t�H�A�Ш̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
						dbSale.execFromPool(strSaleSql);
						intRecordNo++;
					}
				}else{//���Hú��
					//���A��5,8,17,19,20
					//5
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','005','�Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//8
					strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','008','���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//17
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo, Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"', '���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','017','�ӫȤᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//19
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo, ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','019','�ӫȤᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//20
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','020','�ӫȤᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
					//21
					strSaleSql = "INSERT INTO Sale05M070 (DocNo, OrderNo, ProjectID1, RecordNo,ActionNo,  Func, RecordType, ActionName, RecordDesc, CustomID, CustomName, EDate, SHB00, SHB06A, SHB06B, SHB06,SHB97,SHB98,SHB99)  VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','���ڸ��','"+strActionName+"','���A��','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','021','�Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�A�Ш̬~������@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
					dbSale.execFromPool(strSaleSql);
					intRecordNo++;
				}		
			}
		}
		//13.�Ȥ��I���ʲ�������ڶ��A�H�{�r��I�q���H�~�U�����ڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~�������x�C
		if("Y".equals(rule13)){
			//Sale05M070
			strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȥ���','"+strActionName+"','�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','013','�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbSale.execFromPool(strSaleSql);
			intRecordNo++;
			//AS400
			strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '013', '�Ȥ��I���ʲ�������ڶ��A�H�{�r��I�q���H�~�U�����ڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbJGENLIB.execFromPool(strJGENLIBSql);
			/*
			if("".equals(errMsg)){
				errMsg ="�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C";
			}else{
				errMsg =errMsg+"\n�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C";
			}
			*/
			errMsg =errMsg+"�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C\n";
		}else{
			//���ŦX
			strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȥ���','"+strActionName+"','���ŦX','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','013','�Ȥ�"+allCustomName+"�H�{�r��I�q���H�~�U�����ʲ�������ڡA�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbSale.execFromPool(strSaleSql);
			intRecordNo++;
		}
		//14.�Ȥ��ñ���e���e�I�M�۳ƴڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~�������x�C
		if("Y".equals(rule14)){
			//Sale05M070
			strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȥ���','"+strActionName+"','�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','014','�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbSale.execFromPool(strSaleSql);
			intRecordNo++;
			//AS400
			strJGENLIBSql = "INSERT INTO PSHBPF (SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) VALUES ('RY', '"+strDocNo+"', '"+RocNowDate+"', '"+strDeputyID+"', '"+strDeputyName+"', '773', '014', '�Ȥ��ñ���e���e�I�M�۳ƴڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~�������x�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbJGENLIB.execFromPool(strJGENLIBSql);
			/*
			if("".equals(errMsg)){
				errMsg ="�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C";
			}else{
				errMsg =errMsg+"\n�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C";
			}
			*/
			errMsg =errMsg+"�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C\n";
		}else{
			//���ŦX
			strSaleSql = "INSERT INTO Sale05M070 (DocNo,OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,EDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) VALUES ('"+strDocNo+"','"+strOrderNo+"','"+strProjectID1+"','"+intRecordNo+"','"+actionNo+"','���ڳ�','�Ȥ���','"+strActionName+"','���ŦX','"+allCustomID+"','"+allCustomName+"','"+strEDate+"','RY','773','014','�Ȥ�"+allCustomName+"ñ���e(�t���)���e�I�M�۳ƴڡA�Ш̬~���θꮣ����@�~��z�C','"+empNo+"','"+RocNowDate+"','"+strNowTime+"')";
			dbSale.execFromPool(strSaleSql);
			intRecordNo++;
		}
		if(!"".equals(errMsg)){
			setValue("errMsgBoxText",errMsg);	
			getButton("errMsgBoxBtn").doClick();
			getButton("sendMail").doClick();
		}
		System.out.println("value=====>"+value);
		System.out.println("===========AML============E");
		return value;
	}
	public String getInformation(){
		return "---------------AML(\u6d17\u9322\u9632\u5236).defaultValue()----------------";
	}
}
