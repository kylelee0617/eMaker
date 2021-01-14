
import javax.swing.*;
import jcx.jform.bproc;
import java.io.*;
import java.util.*;
import jcx.util.*;
import jcx.html.*;
import jcx.db.*;
import cLabel;

import java.util.LinkedHashMap;
import com.fglife.risk.*;
import com.ibm.as400.access.AS400;
import java.text.SimpleDateFormat;

public class TempButton extends bproc {

  public String getDefaultValue(String value) throws Throwable {

    System.out.println("================Send Mail Star================");
    value = "";
    /*
     * email ���I�W���� 
     * A.��ƾ�z 
     *  a1.��z�Ȥ� 
     *  a2.��z�����q�H 
     *  a3.��z�ĤT�H 
     * B.�̦W��call 400 �쭷�I�� 
     * C.��zemail
     * ���e
     */
    String subject = "���ʲ�ñ���Ȥ᭷�I���ŵ������G�q��(����)";
    talk dbSale = getTalk("Sale");
    talk dbEIP = getTalk("EIP");
    talk dbBen = getTalk("400CRM");
    talk dbEMail = getTalk("eMail");
    talk dbPW0D = getTalk("pw0d");
    String userNo = getUser().toUpperCase().trim();

    // �s��H
    String modifier = "";
    modifier = getUser().toUpperCase().trim();
    String[][] retEip = null;
    String strSQL = "SELECT EMPNO FROM FGEMPMAP where FGEMPNO ='" + modifier + "'";
    retEip = dbEIP.queryFromPool(strSQL);
    if (retEip.length > 0) {
      modifier = retEip[0][0];
    }

    String empNo = "";
    String userEmail = "";
    String userEmail2 = "";
    String DPCode = "";
    String DPManageemNo = "";
    String DPeMail = "";
    String DPeMail2 = "";
    String stringSQL = "";

    String[][] reteMail = null;
    String sysdate = new SimpleDateFormat("yyyyMMdd").format(getDate());
    String systime = new SimpleDateFormat("HHmmss").format(getDate());

    ResourceBundle resource = ResourceBundle.getBundle("sale");
    String as400ip = resource.getString("AS400.IP");
    String as400account = resource.getString("AS400.ACCOUNT");
    String as400password = resource.getString("AS400.PASSWORD");
    String as400init = resource.getString("AS400.INIT");
    String cms00c = resource.getString("CMS00C.LIB");
    String blpc00a = resource.getString("BLPC00A.LIB");
    String psri02 = resource.getString("PSRI02.LIB");

    RPGAS400Interface ra = null;
    RPGAS400Interface rb = null;
    RPGAS400Interface rc = null;

    String sysType = "RYB";// ���ʲ���PB �P��C

    // ���e����
    String strProjectID1 = getValue("ProjectID1").trim();// �קO�N�X
    String strContractDate = getValue("ContractDate").trim();// ñ�����
    String strContractNoDisplay = getValue("ContractNoDisplay").trim();// �X���s��

    String strPosition = ""; // for title
    String strPosition1 = ""; // for context
    String strPosition2 = "";// for context
    String strCustomName = "";
    String strOrderNo = "";
    String strOrderDate = "";
    String strScore = "";
    String strID = "";
    stringSQL = "SELECT Position,CustomName,OrderNo FROM Sale05M275_New WHERE ContractNo = '" + strContractNoDisplay + "' ";
    reteMail = dbSale.queryFromPool(stringSQL);
    if (reteMail.length == 0) {
      // �S���
      value = "M275 �Ȥ��Ƥ��s�b";
      return value;
    }

    strPosition = reteMail[0][0];
    strCustomName = reteMail[0][1];
    strOrderNo = reteMail[0][2];
    // �קO + �ɼӧO + ����O
    subject = strProjectID1 + "��" + strPosition + subject;

    stringSQL = "SELECT OrderDate FROM Sale05M090 WHERE OrderNo = '" + strOrderNo + "' ";
    reteMail = dbSale.queryFromPool(stringSQL);
    strOrderDate = reteMail[0][0];
    ////////////////////////////////////
    System.out.println("strProjectID1===>" + strProjectID1);
    System.out.println("strContractDate===>" + strContractDate);
    System.out.println("strContractNoDisplay===>" + strContractNoDisplay);
    System.out.println("strPosition===>" + strPosition);
    System.out.println("strCustomName===>" + strCustomName);
    System.out.println("strOrderNo===>" + strOrderNo);
    System.out.println("strOrderDate===>" + strOrderDate);
    ArrayList list = new ArrayList();

    try {
      String[][] retBuilding = null;
      stringSQL = "Select HouseCar, Position FROM SALE05M092 WHERE OrderNo = '" + strOrderNo + "' Order By RecordNo ";
      retBuilding = dbSale.queryFromPool(stringSQL);
      int ch = 0;
      int cc = 0;
      if (retBuilding.length == 0) { // �S���
        value = "M092 �ɼӧO��Ƥ��s�b";
        return value;
      }
      for (int i = 0; i < retBuilding.length; i++) {
        String HouseCar = retBuilding[i][0];
        String Building = retBuilding[i][1];
        if (HouseCar.startsWith("House")) {
          if (ch > 0) {
            strPosition1 = strPosition1 + ",";
          }
          strPosition1 = strPosition1 + Building;
          ch++;
        } else if (HouseCar.startsWith("Car")) {
          if (cc > 0) {
            strPosition2 = strPosition2 + ",";
          }
          strPosition2 = strPosition2 + Building;
          cc++;
        }
      }

      // a1��z�Ȥ�
      String[][] retCust = null;
      stringSQL = "SELECT CustomNo, ContractNo FROM SALE05M277 WHERE CONTRACTNO = '" + strContractNoDisplay + "'";
      retCust = dbSale.queryFromPool(stringSQL);
      for (int i = 0; i < retCust.length; i++) {
        ra = new RPGCMS00C(as400ip, as400account, as400password);
        rb = new RPGBLPC00A(as400ip, as400account, as400password);
        rc = new RPGPSRI02(as400ip, as400account, as400password);

        String isManager = "N";
        String type = "N";
        String sex = "";
        String cnyCode = "TWN";
        String birthday = "";
        strID = retCust[i][0];

        System.out.println("strID:" + strID);

        // ���ʫ��ҩ�����o�Ȥ�
        String[][] retCustom = null;
        stringSQL = "SELECT " + "c.CustomName, c.RiskValue  " + "FROM SALE05M091 c Inner Join SALE05M275_new n On c.OrderNo = n.OrderNo " + "WHERE n.ContractNo = '"
            + strContractNoDisplay + "' And c.CustomNo = '" + strID + "'";
        retCustom = dbSale.queryFromPool(stringSQL);

        if (retCustom.length == 0) { // �S���
          value = "M091 �Ȥ��Ƥ��s�b";
          return value;
        }

        for (int idx1 = 0; idx1 < retCustom.length; idx1++) {
          value += "�Ȥ�" + retCustom[idx1][0] + "�~�����I���� :" + retCustom[idx1][1] + "\n";
        }

        // if("".equals(value)){
        // value = "�Ȥ�" + retCustom[0][0] + "�~�����I���� :" + retCustom[0][1];
        // }else{
        // value =value+ "\n�Ȥ�" + retCustom[0][0] + "�~�����I���� :" + retCustom[0][1];
        // }
      }

      // a3.��z�ĤT�H
      System.out.println("!!!!!!a3!!!!!!!");
      stringSQL = "SELECT ContractNo, DesignatedId, DesignatedName, ExportingPlace   FROM sale05m356 WHERE ContractNo = '" + strContractNoDisplay + "'";
      retCust = dbSale.queryFromPool(stringSQL);
      for (int i = 0; i < retCust.length; i++) {
        ra = new RPGCMS00C(as400ip, as400account, as400password);
        rb = new RPGBLPC00A(as400ip, as400account, as400password);
        rc = new RPGPSRI02(as400ip, as400account, as400password);
        System.out.println("3 custno =====> :" + retCust[i][1]);

        // �dQueryLog �ɸ��
        stringSQL = "select top 1 NATIONAL_ID,QUERY_TYPE,NTCODE,NAME,QUERY_ID,BIRTHDAY,SEX,JOB_TYPE,CITY,TOWN,ADDRESS from QUERY_LOG where QUERY_ID = '" + retCust[i][1]
            + "' And PROJECT_ID='" + strProjectID1 + "' Order by QID Desc";
        String retLog[][] = dbPW0D.queryFromPool(stringSQL);
        String isManager = "N";
        String type = "N";
        String sex = "";
        String cnyCode = "TWN";
        String birthday = "0";
        String iad1 = "";
        String iad2 = "";
        String iadd = "";
        String job = "";
        if (retLog.length == 0) {
          if ("".equals(value)) {
            value = "���w�ĤT�H���I�����d�L���";
          } else {
            value = value + "\n���w�ĤT�H���I�����d�L���";
          }
          // return value;
        } else {
          cnyCode = retLog[0][2];
          sex = retLog[0][6];
          birthday = retLog[0][5].replace("/", "");
          iad1 = retLog[0][8];
          iad2 = retLog[0][9];
          iadd = retLog[0][10];
          job = retLog[0][7];

          // ����ID
          if (retCust[i][1].trim().length() == 8) {
            type = "C";// N: �ӤH C: ���q F: �~��H
          } else {
            // �~��H(�H��O�P�_)
            if (!"���إ���".equals(retCust[i][3].trim())) {
              type = "F";
            }
          }

          // ���y��X(�Y�Źw�]TWN)
          System.out.println("retCust[i][3]:" + retCust[i][3]);
          String strCZ09 = "";
          if ("".equals(retCust[i][3].trim())) {
            strCZ09 = "���إ���";
            type = "N";
          } else {
            strCZ09 = retCust[i][3].trim();
          }
          String strSaleSql2 = "SELECT CZ02 FROM PDCZPF WHERE CZ01='NATIONCODE' AND CZ09='" + strCZ09 + "'";
          String retCNYCode2[][] = dbBen.queryFromPool(strSaleSql2);
          if (retCNYCode2.length > 0) {
            cnyCode = retCNYCode2[0][0].trim();
          }
          System.out.println("cnyCode=====> :" + cnyCode);

        }
        LinkedHashMap map = new LinkedHashMap();
        map.put("INAME", retCust[i][2]); // �Ȥ�m�W
        map.put("IDATE", birthday);// �ͤ�
        map.put("ID", retCust[i][1]); // �����Ҹ�
        map.put("IAD1", iad1);// �a�} 1
        map.put("IAD2", iad2);// �a�} 2
        map.put("IAD3", "");// �a�} 3
        map.put("IADD", iadd);// ���a�}
        map.put("IZIP", "");// �l���ϸ�
        map.put("ITELO", "");// ���q�q��
        map.put("ITELH", "");// ��a�q��
        map.put("TYPE", type);// N: �ӤH C: ���q
        map.put("SEX", sex);// �ʧO M,F
        map.put("CNY", cnyCode);// ���y
        map.put("JOB", "");// ¾�~�N�X
        map.put("VOC", "");// ��~�O
        map.put("CUST", " ");// ���@�ŧi
        map.put("IESTD", " "); // �]�w���
        map.put("IEXEC", isManager);// �����޲z�H Y/N
        map.put("CNY2", " ");// ���y 2
        map.put("CNY3", " ");// ���y 3
        map.put("ICHGD", "");// �ܧ�n�O���
        map.put("CHGNO", modifier);// ���ʤH�����s
        map.put("RTCOD", "");// �^�нX
        map.put("INSN", "");// �Ȥ�s��
        System.out.println("cust:" + retCust[i][0]);

        LinkedHashMap mapb = new LinkedHashMap();
        mapb.put("INSID", retCust[i][1]); // �����Ҹ�
        mapb.put("SYSTEM", sysType); // �t�ΧO
        mapb.put("CHGNO", userNo);// ���ʤH�����s
        mapb.put("CAPTION", strContractNoDisplay);// ����
        mapb.put("STRDATE", "0");// �_�l��
        mapb.put("ENDDATE", "0");// �פ��
        mapb.put("RTCOD", "");// �^�нX

        LinkedHashMap mapc = new LinkedHashMap();
        mapc.put("RI0201", retCust[i][1]); // �����Ҹ�
        mapc.put("RI0202", "RY");// �t�ΧO
        mapc.put("RI0203", "Y");
        mapc.put("RIPOLN", "");
        mapc.put("RIFILE", strContractNoDisplay);// �ӷ��׸�
        mapc.put("RI0204", "");//
        mapc.put("RI0205", "");//
        mapc.put("RI0206", "");//
        mapc.put("RI0207", "");//
        mapc.put("RI0208", "");//
        mapc.put("RI0209", "");//
        mapc.put("RO0201", "0");//
        mapc.put("RO0202", "");//
        mapc.put("RO0203", "0");//
        mapc.put("RO0204", "");//
        mapc.put("RO0205", "0");//
        mapc.put("RO0206", "");//
        mapc.put("RO0207", "0");//
        mapc.put("RO0208", "");//
        mapc.put("RO0209", "0");//
        mapc.put("RO0210", "");//
        mapc.put("RO0211", retCust[i][1]);//
        mapc.put("RO0212", "");//
        mapc.put("RO0213", "");//
        mapc.put("RTNR02", "");// �^�нX

        boolean a = ra.invoke(as400init, cms00c, map);
        System.out.println("RTCODE:" + ra.getResult()[22]);
        boolean b = rb.invoke(as400init, blpc00a, mapb);
        System.out.println("RTCODE:" + rb.getResult()[6]);
        boolean c = rc.invoke(as400init, psri02, mapc);
        System.out.println("19�~�����I�� :" + rc.getResult()[19]);
        System.out.println("20�~�����I���� :" + rc.getResult()[20]);
        // �g�^���I��
        // messagebox("���w�ĤT�H�~�����I���� :" + rc.getResult()[20]);
        String sqlWriteRisk = "Update sale05m356 set RiskValue = '" + rc.getResult()[20] + "' Where ContractNo = '" + strContractNoDisplay + "' and DesignatedId = '"
            + retCust[i][1] + "'";
        dbSale.execFromPool(sqlWriteRisk);

        if ("".equals(value)) {
          value = "���w�ĤT�H�~�����I���� :" + rc.getResult()[20];
        } else {
          value = value + "\n���w�ĤT�H�~�����I���� :" + rc.getResult()[20];
        }

        HashMap m = new HashMap();
        m.put("p01", strProjectID1);
        m.put("p02", strPosition1);
        m.put("p025", strPosition2);
        m.put("p03", retCust[i][2]);
        m.put("p035", retCust[i][1]);
        // m.put("p04",strOrderDate);
        m.put("p045", strContractDate);
        m.put("p05", "" + rc.getResult()[19]);
        m.put("p06", (String) rc.getResult()[20]);
        list.add(m);

        ra.disconnect();
        rb.disconnect();
        rc.disconnect();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("!!!!!! send mail!!!!!!!");
    stringSQL = "SELECT EMPNO FROM FGEMPMAP where FGEMPNO ='" + userNo + "'";
    retEip = dbEIP.queryFromPool(stringSQL);
    if (retEip.length > 0) {
      empNo = retEip[0][0];
    }
    /////////////////
    System.out.println("userNo===>" + userNo);
    System.out.println("empNo===>" + empNo);
    ////////////////
    stringSQL = "SELECT DP_CODE,PN_EMAIL1,PN_EMAIL2 FROM PERSONNEL WHERE PN_EMPNO='" + empNo + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    if (reteMail.length > 0) {
      DPCode = reteMail[0][0];
      if (reteMail[0][1] != null && !reteMail[0][1].equals("")) {
        userEmail = reteMail[0][1];
      }
      if (reteMail[0][2] != null && !reteMail[0][2].equals("")) {
        userEmail2 = reteMail[0][2];
      }
    }
    /////////////////////////////////////////
    System.out.println("DPCode===>" + DPCode);
    System.out.println("userEmail===>" + userEmail);
    System.out.println("userEmail2===>" + userEmail2);
    /////////////////////////////////////////////////
    stringSQL = "SELECT DP_MANAGEEMPNO FROM CATEGORY_DEPARTMENT WHERE DP_CODE='" + DPCode + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    if (reteMail.length > 0) {
      DPManageemNo = reteMail[0][0];
    }
    /////////////////////////////////////////
    System.out.println("DPManageemNo===>" + DPManageemNo);
    /////////////////////////////////////////////////
    stringSQL = "SELECT PN_EMAIL1,PN_EMAIL2 FROM PERSONNEL WHERE PN_EMPNO='" + DPManageemNo + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    if (reteMail.length > 0) {
      if (reteMail[0][0] != null && !reteMail[0][0].equals("")) {
        DPeMail = reteMail[0][0];
      }
      if (reteMail[0][1] != null && !reteMail[0][1].equals("")) {
        DPeMail2 = reteMail[0][1];
      }
    }
    ////////////////////////////////////
    System.out.println("DPeMail===>" + DPeMail);
    System.out.println("DPeMail2===>" + DPeMail2);
    /////////////////////////////////////////////////////////
    String PNCode = "";
    String PNManageemNo = "";
    String PNMail = "";

    stringSQL = "SELECT PN_DEPTCODE FROM PERSONNEL WHERE PN_EMPNO='" + empNo + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    PNCode = reteMail[0][0];
    System.out.println("PNCode===>" + PNCode);
    stringSQL = "SELECT DP_MANAGEEMPNO FROM CATEGORY_DEPARTMENT WHERE DP_CODE='" + PNCode + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    PNManageemNo = reteMail[0][0];
    System.out.println("PNManageemNo===>" + PNManageemNo);
    stringSQL = "SELECT PN_EMAIL1 FROM PERSONNEL WHERE PN_EMPNO='" + PNManageemNo + "'";
    reteMail = dbEMail.queryFromPool(stringSQL);
    PNMail = reteMail[0][0];
    System.out.println("PNMail===>" + PNMail);

    // send email
    String ctop = "<html><head><title>�X���|�f�~�����I�q��</title></head><body>";
    String header1 = "(1)�����I�Ȥ�G</br>";
    String header2 = "(2)�C���I�Ȥ�G</br>";
    String table1 = "<table border=1><tr><td align=\"center\">�קO</td><td align=\"center\">�ɼӧO</td><td align=\"center\">����O</td><td align=\"center\">�Ȥ�W��</td><td align=\"center\">ñ�����</td><td align=\"center\">���I��X��</td><td align=\"center\">�Ȥ᭷�I����</td><td align=\"center\">����</td></tr>";
    String tail = "</table>";
    String contextsample = "<tr><td align=\"center\">${p01}</td><td align=\"center\">${p02}</td><td align=\"center\">${p025}</td><td align=\"center\">${p03}</td><td align=\"center\">${p045}</td><td align=\"center\">${p05}</td><td align=\"center\">${p06}</td><td>${p07}</td></tr>";
    String cbottom = "</body></html>";
    String context = strProjectID1 + "��" + strPosition + "���ʲ�ñ���Ȥ᭷�I���ŵ������G�q��<BR>";

    String errMsgText = getValue("errMsgBoxText").trim();
    // String msg
    // ="�קO�N�X�G"+strProjectID1+"\n�ɼӧO�G"+strPosition+"\n�q��m�W�G"+strCustomName+"\n�I�q����G"+strOrderDate+"\nñ������G"+strContractDate+"\n�æ��~���˺A�G\n"+errMsgText;
    // msg = msg.replace("\n","<BR>");
    context = context + table1;
    boolean isHighManager = false;

    for (int i = 0; i < list.size(); i++) {
      HashMap cm = (HashMap) list.get(i);
      String tempPo6 = "" + cm.get("p06");
      tempPo6 = tempPo6.trim();
      if ("�����I".equals(tempPo6)) {
        String l1 = new String(contextsample);
        l1 = l1.replace("${p01}", (String) cm.get("p01"));
        l1 = l1.replace("${p02}", (String) cm.get("p02"));
        l1 = l1.replace("${p025}", (String) cm.get("p025"));
        l1 = l1.replace("${p03}", (String) cm.get("p03"));
        l1 = l1.replace("${p035}", (String) cm.get("p035"));
        // l1 = l1.replace("${p04}",(String) cm.get("p04"));
        l1 = l1.replace("${p045}", (String) cm.get("p045"));
        l1 = l1.replace("${p05}", (String) cm.get("p05"));
        l1 = l1.replace("${p06}", tempPo6.replace("���I", ""));
        l1 = l1.replace("${p07}", "�~���θꮣ���I������" + tempPo6.replace("���I", "") + "���I�Ȥ�A�Ш̬~������@�~�W�w�A����[�j���ޱ����I");
        context = context + l1;
        isHighManager = true;
      }
    }
    if (!isHighManager) { // �����I�D�ޤ~��
      PNMail = userEmail;
    }

    boolean hasRecord = false;
    for (int i = 0; i < list.size(); i++) {
      HashMap cm = (HashMap) list.get(i);
      String tempPo6 = "" + cm.get("p06");
      tempPo6 = tempPo6.trim();
      if (!"�����I".equals(tempPo6)) {
        String l1 = new String(contextsample);
        l1 = l1.replace("${p01}", (String) cm.get("p01"));
        l1 = l1.replace("${p02}", (String) cm.get("p02"));
        l1 = l1.replace("${p025}", (String) cm.get("p025"));
        l1 = l1.replace("${p03}", (String) cm.get("p03"));
        l1 = l1.replace("${p035}", (String) cm.get("p035"));
        // l1 = l1.replace("${p04}",(String) cm.get("p04"));
        l1 = l1.replace("${p045}", (String) cm.get("p045"));
        l1 = l1.replace("${p05}", (String) cm.get("p05"));
        l1 = l1.replace("${p06}", tempPo6.replace("���I", ""));
        l1 = l1.replace("${p07}", "�~���θꮣ���I������" + tempPo6.replace("���I", "") + "���I�Ȥ�");
        context = context + l1;
      }
    }
    context = context + tail + cbottom;

    String[] arrayUser = { "Justin_Lin@fglife.com.tw", userEmail, PNMail };
    String sendRS = sendMailbcc("ex.fglife.com.tw", "Emaker-Invoice@fglife.com.tw", arrayUser, subject, context, null, "", "text/html");
    System.out.println("sendRS===>" + sendRS);
    System.out.println("================Send Mail End================");

    if (!"".equals(value)) {
      messagebox(value);
    }

    return value;

  } // getDefault End

}
