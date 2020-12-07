/**
 * 20200602 Kyle : �~���˺A�d�ߥ\��
 * 20200603 Kyle : �������ӭn��2X�ؼ˺A���g�i�ӡA���S�ɶ��ҥH�u�g�F�����PEPS�A�Ʊ�H�ᦳ�ɶ��ɻ�
 * 
 * �i��ϥγB�]�w�@AMLCODE�}�C����
 * select * from saleRY773 order by AMLno asc
 * AML 001  �P�@�Ȥ�P�@��~�餺2��(�t)�H�W�]�t�{���B�״ڡB�H�Υd�B�䲼����A�B�C���Ҥ���s�x��450,000~499,999���A�t���ˮֹwĵ�C
 * AML 002  �P�@�Ȥ�3����~�餺�A��2��H�{���ζ״ڹF450,000~499,999��, �t���ˮִ��ܳq���C
 * AML 003  �P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 004  �P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C
 * AML 005  �Nú�ڤH�P�ʶR�H���Y���D�G���ˤ���/�ÿˡA�t���ˮִ��ܳq���C
 * AML 006  �P�@�Ȥᤣ�ʲ��R��Añ���e�h�q�����ʶR�A���ˮ֨�X�z�ʡC
 * AML 007  �P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 008  ���ʲ��P��ѲĤT��N�z��ú�ڡA�t���ˮִ��ܳq���C
 * AML 009  �Ȥ�Y�ӦۥD�޾����Ҥ��i����~���P�����ꮣ���Y���ʥ�����a�Φa�ϡA�Ψ�L�����`�Υ��R�����`����a�Φa�ϡA���ˮ֨�X�z�ʡC
 * AML 010  �ۥD�޾����Ҥ��i����~���P������U���ƥ��l���Y���ʥ�����a�Φa�ϡB�Ψ�L�����`�Υ��R�����`����a�Φa�϶פJ������ڶ��A���ˮ֨�X�z�ʡC
 * AML 011  ����̲ר��q�H�Υ���H���D�޾������i�����Ƥ��l�ι���F�ΰ�ڻ{�w�ΰl�d�����Ʋ�´�F�Υ������æ��P���Ʋ�´�����p�̡A���̸ꮣ����k�i������@�~�C
 * AML 012  �Ȥ�n�D�N���ʲ��v�Q�n�O���ĤT�H�A���ണ�X�������p�Ωڵ����������`���p�C
 * AML 013  �Ȥ��I���ʲ�������ڶ��A�H�{�r��I�q���H�~�U�����ڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 014  �Ȥ��ñ���e���e�I�M�۳ƴڡA�B�L�X�z��������ӷ��A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 015  �n�D���q�}�ߨ����T��I�������䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 016  �n�D���q�}�ߺM�P����u(�������u)�䲼�@�����I�覡�A���ˮ֬O�_�ŦX�æ��~��������x�C
 * AML 017  �Ȥᬰ���ަW���H�A�а���[�j���Ȥ��¾�f�d�ę̀���~�������q���@�~��z�C
 * AML 018  �Ȥᬰ���ަW���H������W��A�T�����ýШ̨���~�����q���@�~�|��k���ǡC
 * AML 019  �Ȥᬰ���q�Q�`���t�H�A�ݨ̫O�I�~�P�Q�`���Y�H�q�Ʃ�ڥH�~����L����޲z��k����C
 * AML 020  �Ȥᬰ�æ��¦W���H�A���ЮֽT�{��A�A�i��������C
 * AML 021  �Ȥ�Ψ���q�H�B�a�x�����Φ��K�����Y���H�A���{���B�����ꤺ�~�F���ΰ�ڲ�´���n�F�v��¾�ȡA�Х[�j�Ȥ��¾�լd�C
 */
package Farglory.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jcx.db.talk;
import jcx.jform.bvalidate;

/**
 * �~���U�A�ˬd�� 1. �쪩(�A��18 & �A��21)�٨S��eclipse�}�o�A�ϥ�bean�˸��ܼƤ���K�A�G����map�C 2.
 * �ĤG���]����beclipse���}�o�F�A�G�ϥ�AMLBean���Ѽƶǻ��B�z�C 3. ����کΫ�⦳�ɶ���쪩���Ҧ����ܤ@�U�C
 * 
 * @author B04391
 *
 */

public class AMLTools extends bvalidate {
  // DB
  talk db400 = null;
  talk dbSale = null;
  talk dbEIP = null;
  talk dbPW0D = null;

  KUtils kutil = new KUtils();

  // param �ǤJ��
  StringBuilder sbRsMsg = null;
  String strOrderNo = ""; // �ʫ��ҩ���s��
  String strProjectID1 = ""; // �קO�N�X
  String strOrderDate = ""; // �ʫ��ҩ�����
  String strActionName = "�s��"; // �sSale05M070�ϥ�
  String strActionNo = ""; // �sSale05M070�ϥ�
  String funcName = ""; // ���ڳ�ɷ|���ǤJ

  int intRecordNo = 1; // �sSale05M070�ϥ�
  Map mapAMLMsg = null;
  String strNowDate = "";
  String strNowDate2 = "";
  String rocNowDate = "";
  String strNowTime = "";
  String strNowTime2 = "";

  // ���u�s��
  String userNo = "";
  String empNo = "";

  AMLBean aml;
  String[][] payByCustoms;
  String[][] payByOrders;

  public AMLTools() throws Throwable {
  }

  public AMLTools(AMLBean aml) throws Throwable {
    db400 = getTalk("400CRM");
    dbSale = getTalk("Sale");
    dbEIP = getTalk("EIP");
    dbPW0D = getTalk("pw0d");

    this.aml = aml;
    sbRsMsg = new StringBuilder();
    strOrderNo = aml.getOrderNo();
    strProjectID1 = aml.getProjectID1();
    strOrderDate = aml.getTrxDate();
    strActionName = aml.getActionName();
    funcName = aml.getFuncName();

    // LOG���,�ɶ�
    this.getDateTime();

    // ���u�s�� & EIPNO
    userNo = getUser().toUpperCase().trim();
    this.getEmpNo();

    // �Ǹ�
    this.getRecordNo070();

    // actionNo
    this.getActionNo();

    // ���oAML�A�ˤ��廡��
    this.getAML();

    // ���ڮ�
//    if ("����".equals(aml.getFuncName())) {
//
//    }
  }

  public AMLTools(Map cons) throws Throwable {
    db400 = getTalk("400CRM");
    dbSale = getTalk("Sale");
    dbEIP = getTalk("EIP");
    dbPW0D = getTalk("pw0d");

    sbRsMsg = new StringBuilder();
    strOrderNo = cons.get("OrderNo") != null ? cons.get("OrderNo").toString().trim() : "";
    strProjectID1 = cons.get("ProjectID1") != null ? cons.get("ProjectID1").toString().trim() : "";
    strOrderDate = cons.get("TrxDate") != null ? cons.get("TrxDate").toString().trim() : "";
    strActionName = cons.get("ActionName") != null ? cons.get("ActionName").toString().trim() : "";
    funcName = cons.get("funcName") != null ? cons.get("funcName").toString().trim() : "";

    // LOG���,�ɶ�
    this.getDateTime();

    // ���u�s�� & EIPNO
    userNo = getUser().toUpperCase().trim();
    this.getEmpNo();

    // �Ǹ�
    this.getRecordNo070();

    // actionNo
    this.getActionNo();

    // ���oAML�A�ˤ��廡��
    this.getAML();
  }

  public String getAML() throws Throwable {
    String rs = "getAML Error";
    String sql = "select * from saleRY773 where AMLType = 'AML' order by AMLNo asc";
    String[][] retAML = dbSale.queryFromPool(sql);
    mapAMLMsg = new HashMap();
    for (int i = 0; i < retAML.length; i++) {
      String[] retAML1 = retAML[i];
      mapAMLMsg.put(retAML1[1], retAML1[2]);
    }
    rs = "";
    return rs;
  }

  public void getActionNo() throws Throwable {
    String ram = "";
    Random random = new Random();
    for (int i = 0; i < 4; i++) {
      ram += String.valueOf(random.nextInt(10));
    }
    strActionNo = strNowDate + strNowTime + ram;
    System.out.println("strActionNo=====>" + strActionNo);
  }

  public void getRecordNo070() throws Throwable {
    String[][] ret05M070;
    String stringSQL = "SELECT MAX(RecordNo) AS MaxNo FROM Sale05M070 WHERE OrderNo ='" + this.strOrderNo + "' ";
    ret05M070 = dbSale.queryFromPool(stringSQL);
    if (!"".equals(ret05M070[0][0].trim())) {
      intRecordNo = Integer.parseInt(ret05M070[0][0].trim()) + 1;
    }
    System.out.println("intRecordNo=====>" + intRecordNo);
  }

  public void getDateTime() throws Throwable {
    Date now = new Date();
    SimpleDateFormat nowsdf = new SimpleDateFormat("yyyyMMdd");
    strNowDate = nowsdf.format(now);

    String tempROCYear = "" + (Integer.parseInt(strNowDate.substring(0, strNowDate.length() - 4)) - 1911);
    rocNowDate = tempROCYear + strNowDate.substring(strNowDate.length() - 4, strNowDate.length());

    SimpleDateFormat nowTimeSdf = new SimpleDateFormat("HHmmss");
    strNowTime = nowTimeSdf.format(now);

    SimpleDateFormat nowsdf2 = new SimpleDateFormat("yyyy-MM-dd");
    strNowDate2 = nowsdf2.format(now);
    SimpleDateFormat nowTimeSdf2 = new SimpleDateFormat("HH:mm:ss");
    strNowTime2 = nowTimeSdf2.format(now);

    System.out.println("RocNowDate=====>" + rocNowDate);
    System.out.println("strNowTime=====>" + strNowTime);
  }

  public void getEmpNo() throws Throwable {
    // ���u�X
    String[][] retEip = null;
    String stringSQL = "SELECT EMPNO FROM FGEMPMAP where FGEMPNO ='" + this.userNo + "'";
    retEip = dbEIP.queryFromPool(stringSQL);
    if (retEip.length > 0) {
      empNo = retEip[0][0];
    } else {
      System.out.println(">>>None EmpNo<<<");
    }
  }

  public boolean check(String value) throws Throwable {
    return false;
  }
  
  /**
   * AML 001  �P�@�Ȥ�P�@��~�餺2��(�t)�H�W�]�t�{���B�״ڡB�H�Υd�B�䲼����A�B�C���Ҥ���s�x��450,000~499,999���A�t���ˮֹwĵ�C(���ڥ���Ҧ�ú��)
   * Ĳ�o����: ���wĲ�o
   * @param aml
   * @return
   * @throws Throwable
   */
  public Result chkAML001(AMLBean aml) throws Throwable {
    Result rs = new Result();
    StringBuilder sbMsg = new StringBuilder();
    aml.setAMLNo("001");

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(aml.getAMLNo()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rs.setRsStat(EnumRsStatus.NODATA_AMLMSG);
      return rs;
    }

    System.out.println("AML01 start>>>");
    // �q��
    String shareCondition = "and a.EDate = '" + aml.getTrxDate() + "' and c.OrderNo in (" + aml.getOrderNos() + ") ";
    StringBuilder sbSQL = new StringBuilder();
    sbSQL.append("select distinct a.EDate , c.OrderNo , a.DocNo ");
    sbSQL.append(", 'cash' , 'cash no' as prrofNo , STUFF((SELECT ',' + aa.CustomName FROM Sale05M084 aa WHERE aa.docNo = a.docNo FOR XML PATH('')), 1, 1, '') ");
    sbSQL.append(", ISNULL(a.CashMoney , 0) as money  ");
    sbSQL.append(", ISNULL(a.CashMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as oneMoney ");
    sbSQL.append("from Sale05M080 a , sale05m086 c ");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and (ISNULL(a.CashMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and 499999) ");
    sbSQL.append(shareCondition);
    sbSQL.append("UNION ");
    sbSQL.append("select distinct a.EDate , c.OrderNo , a.DocNo ");
    sbSQL.append(", 'credit' , b.CreditCardNo as prrofNo , STUFF((SELECT ',' + aa.CustomName FROM Sale05M084 aa WHERE aa.docNo = a.docNo FOR XML PATH('')), 1, 1, '') ");
    sbSQL.append(", ISNULL(b.CreditCardMoney, 0) as money ");
    sbSQL.append(", ISNULL(b.CreditCardMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo)  as oneMoney ");
    sbSQL.append("from Sale05M080 a  , Sale05M083 b , sale05m086 c ");
    sbSQL.append("where b.DocNo = a.DocNo and a.DocNo=c.DocNo ");
    sbSQL.append("and (ISNULL(b.CreditCardMoney, 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo)  BETWEEN 450000 and 499999) ");
    sbSQL.append(shareCondition);
    sbSQL.append("UNION ");
    sbSQL.append("select distinct a.EDate , c.OrderNo , a.DocNo ");
    sbSQL.append(", 'bank' , b.BankNo as prrofNo , STUFF((SELECT ',' + aa.CustomName FROM Sale05M084 aa WHERE aa.docNo = a.docNo FOR XML PATH('')), 1, 1, '') ");
    sbSQL.append(", ISNULL(b.BankMoney, 0) as money ");
    sbSQL.append(", ISNULL(b.BankMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo)  as oneMoney ");
    sbSQL.append("from Sale05M080 a  , Sale05M328 b , sale05m086 c ");
    sbSQL.append("where b.DocNo = a.DocNo and a.DocNo=c.DocNo ");
    sbSQL.append("and (ISNULL(b.BankMoney, 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and  499999) ");
    sbSQL.append(shareCondition);
    sbSQL.append("UNION ");
    sbSQL.append("select distinct a.EDate , c.OrderNo , a.DocNo ");
    sbSQL.append(", 'check' , b.CheckNo as prrofNo , STUFF((SELECT ',' + aa.CustomName FROM Sale05M084 aa WHERE aa.docNo = a.docNo FOR XML PATH('')), 1, 1, '') ");
    sbSQL.append(", ISNULL(b.CheckMoney, 0) as money ");
    sbSQL.append(", ISNULL(b.CheckMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo)  as oneMoney ");
    sbSQL.append("from Sale05M080 a  , Sale05M082 b , sale05m086 c ");
    sbSQL.append("where b.DocNo = a.DocNo and a.DocNo=c.DocNo ");
    sbSQL.append("and (ISNULL(b.CheckMoney, 0)/(select count(e.OrderNo) from Sale05M086 e where e.DocNo = a.DocNo group by e.DocNo) BETWEEN 450000 and 499999) ");
    sbSQL.append(shareCondition);
    sbSQL.append("order by a.EDate desc, a.DocNo desc ");
    String[][] retAML0011 = dbSale.queryFromPool(sbSQL.toString(), 300);
    int count1 = 0;
    String lastKey1 = "";
    for (int i = 0; i < retAML0011.length; i++) {
      String thisKey1 = retAML0011[i][1].trim();
      if (lastKey1.equals(thisKey1)) { // �ۦP�Y���ܲĤG��
        if (count1 >= 1)
          continue; // �T���@���N�n
        sbMsg.append(amlDesc.replaceAll("<customName>", aml.getCustomName())).append("\n");
        count1++;
      }
      lastKey1 = thisKey1;
    }

    // �Ȥ᳡��
    shareCondition = "and a.EDate = '" + aml.getTrxDate() + "' and c.CustomNo in (" + aml.getCustomNos() + ") ";
    String sql = "select distinct a.EDate , c.CustomNo , c.CustomName , 'cash' " + ", ISNULL(a.CashMoney , 0) as money "
        + ", (select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo) "
        + ", ISNULL(a.CashMoney , 0)*(select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo)/100 " + "from Sale05M080 a , sale05m084 c  "
        + "where 1=1 and a.DocNo=c.DocNo  "
        + "and (ISNULL(a.CashMoney , 0)*(select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo)/100 BETWEEN 450000 and 499999) "
        + shareCondition + "UNION " + "select distinct a.EDate , c.CustomNo , c.CustomName , 'credit' " + ", ISNULL(b.CreditCardMoney, 0) as money "
        + ", (select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo) "
        + ", ISNULL(a.CreditCardMoney , 0)*(select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo)/100 "
        + "from Sale05M080 a  , Sale05M083 b , sale05m084 c " + "where b.DocNo = a.DocNo and a.DocNo=c.DocNo "
        + "and (ISNULL(b.CreditCardMoney, 0)/(select count(e.OrderNo) from Sale05M086 e where e.DocNo = a.DocNo group by e.DocNo) BETWEEN 450000 and 499999) " + shareCondition
        + "UNION " + "select distinct a.EDate , c.CustomNo , c.CustomName , 'bank' " + ", ISNULL(b.BankMoney, 0) as money "
        + ", (select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo) "
        + ", ISNULL(a.BankMoney , 0)*(select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo)/100 "
        + "from Sale05M080 a  , Sale05M328 b , sale05m084 c " + "where b.DocNo = a.DocNo and a.DocNo=c.DocNo "
        + "and (ISNULL(b.BankMoney, 0)/(select count(e.OrderNo) from Sale05M086 e where e.DocNo = a.DocNo group by e.DocNo) BETWEEN 450000 and  499999) " + shareCondition
        + "UNION " + "select distinct a.EDate , c.CustomNo , c.CustomName , 'check' " + ", ISNULL(b.CheckMoney, 0) as money "
        + ", (select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo) "
        + ", ISNULL(a.CheckMoney , 0)*(select top 1 Percentage from Sale05M084 e where e.DocNo=a.DocNo and e.CustomNo=c.CustomNo)/100 "
        + "from Sale05M080 a  , Sale05M082 b , sale05m084 c " + "where b.DocNo = a.DocNo and a.DocNo=c.DocNo "
        + "and (ISNULL(b.CheckMoney, 0)/(select count(e.OrderNo) from Sale05M086 e where e.DocNo = a.DocNo group by e.DocNo) BETWEEN 450000 and 499999) " + shareCondition
        + "order by a.EDate desc , c.CustomNo desc ";
    String[][] retAML0012 = dbSale.queryFromPool(sbSQL.toString(), 300);
    int count2 = 0;
    String lastKey2 = "";
    for (int i = 0; i < retAML0012.length; i++) {
      String thisKey2 = retAML0012[i][1].trim();
      if (lastKey2.equals(thisKey2)) { // �ۦP�Y���ܲĤG��
        if (count2 >= 1)
          continue; // �T���@���N�n
        sbMsg.append(amlDesc.replaceAll("<customName>", retAML0012[i][2].trim())).append("\n");
        count2++;
      }
      lastKey2 = thisKey2;
    }

    rs.setRsStat(EnumRsStatus.SUCCESS);
    rs.setData(sbMsg.toString());
    return rs;
  }
  
  
  /**
   * AML 002 �P�@�Ȥ�3����~�餺�A��2��H�{���ζ״ڹF450,000~499,999��,
   * Ĳ�o����: ���ڳ楻���R���@��45~49�U�ݰ����ˮ֡C
   * �d�߫e���A�R���@���Y���ͺA�ˡC
   * @param aml
   * @return
   * @throws Throwable
   */
  public Result chkAML002(AMLBean aml) throws Throwable {
    Result rs = new Result();
    StringBuilder sbMsg = new StringBuilder();
    aml.setAMLNo("002");

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(aml.getAMLNo()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rs.setRsStat(EnumRsStatus.NODATA_AMLMSG);
      return rs;
    }

    // �]���Ĥ@���R�����ӬO���ڳ楻���A�G�u�n��e��ѧY�i
    String startEDate = kutil.getDateAfterNDays(aml.getTrxDate().trim(), "/", -2);
    String endEDate = kutil.getDateAfterNDays(aml.getTrxDate().trim(), "/", -1);

    System.out.println("AML02 start>>>");
    // �q��
    StringBuilder sbSQL = new StringBuilder();
    sbSQL.append("SELECT a.EDate ,c.OrderNo ");
    sbSQL.append(", SUM(a.CashMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CashMoney ");
    sbSQL.append(", SUM(a.CreditCardMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CreditCardMoney ");
    sbSQL.append(", SUM(a.BankMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as BankMoney ");
    sbSQL.append(", SUM(a.CheckMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CheckMoney ");
    sbSQL.append("FROM Sale05M080 a , sale05m086 c ");
    sbSQL.append("WHERE 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and a.EDate BETWEEN '" + startEDate + "' AND '" + endEDate + "' and c.OrderNo in ('" + aml.getOrderNos() + "') ");
    sbSQL.append("GROUP BY c.OrderNo ,a.EDate , a.DocNo ,a.CashMoney ,a.CreditCardMoney ,a.BankMoney ,a.CheckMoney ");
    sbSQL.append("HAVING ( (ISNULL(a.CashMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.CreditCardMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.BankMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.CheckMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) BETWEEN 450000 and 499999) ) ");
    sbSQL.append("ORDER BY a.EDate ,c.OrderNo ");
    String[][] retAML0021 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey1 = "";
    for (int i = 0; i < retAML0021.length; i++) {
      String thisKey1 = retAML0021[i][1].trim();
      if (lastKey1.equals(thisKey1))
        continue; // ���ƪ����n
      sbMsg.append(amlDesc.replaceAll("<customName>", aml.getCustomName())).append("\n");
      lastKey1 = thisKey1;
    }

    // �Ȥ�
    sbSQL = new StringBuilder();
    sbSQL.append("select a.EDate , c.CustomNo ,c.CustomName ");
    sbSQL.append(", SUM(a.CashMoney)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 as CashMoney ");
    sbSQL.append(", SUM(a.CreditCardMoney)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 as CreditCardMoney ");
    sbSQL.append(", SUM(a.BankMoney)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 as BankMoney ");
    sbSQL.append(", SUM(a.CheckMoney)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 as CheckMoney ");
    sbSQL.append("from Sale05M080 a , sale05m084 c ");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and a.EDate BETWEEN '" + startEDate + "' AND '" + endEDate + "' and c.CustomNo in (" + aml.getCustomNos() + ") ");
    sbSQL.append("GROUP BY a.DocNo ,a.EDate , c.CustomNo ,c.CustomName ,a.CashMoney ,a.CreditCardMoney ,a.BankMoney ,a.CheckMoney ");
    sbSQL.append("HAVING ((ISNULL(a.CashMoney , 0)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.CreditCardMoney , 0)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.BankMoney , 0)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 BETWEEN 450000 and 499999) ");
    sbSQL.append("or (ISNULL(a.CheckMoney , 0)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 BETWEEN 450000 and 499999) ) ");
    sbSQL.append("ORDER BY a.EDate ,c.CustomNo ");
    String[][] retAML0022 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey2 = "";
    for (int i = 0; i < retAML0022.length; i++) {
      String thisKey2 = retAML0022[i][1].trim();
      if (lastKey2.equals(thisKey2))
        continue; // ���ƪ����n
      sbMsg.append(amlDesc.replaceAll("<customName>", retAML0022[i][2].trim())).append("\n");
      lastKey2 = thisKey2;
    }

    rs.setRsStat(EnumRsStatus.SUCCESS);
    rs.setData(sbMsg.toString());
    return rs;
  }

  
  /**
   * AML 003  �P�@�Ȥ�P�@��~��{��ú�ǲ֭p�F50�U��(�t)�H�W�A���ˮ֬O�_�ŦX�æ��~��������x�C
   * Ĳ�o���� : ���ڳ���U�����{�����ˮ�
   * �d�߭q��γ�@�Ȥ���馬�ڡA�W�L50�U���ͺA��
   * @param aml
   * @return
   * @throws Throwable
   */
  public Result chkAML003(AMLBean aml) throws Throwable {
    Result rs = new Result();
    StringBuilder sbMsg = new StringBuilder();
    aml.setAMLNo("003");

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(aml.getAMLNo()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rs.setRsStat(EnumRsStatus.NODATA_AMLMSG);
      return rs;
    }

    System.out.println("AML03 start>>>");
    // �q��
    StringBuilder sbSQL = new StringBuilder();
    sbSQL.append("select c.OrderNo ");
    sbSQL.append(", SUM(a.CashMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CashMoney");
    sbSQL.append("from Sale05M080 a , sale05m086 c");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo");
    sbSQL.append("and a.EDate = '" + aml.getTrxDate() + "' and c.OrderNo in ('" + aml.getOrderNos() + "') ");
    sbSQL.append("GROUP BY c.OrderNo , a.DocNo ,a.CashMoney");
    sbSQL.append("HAVING ( (ISNULL(a.CashMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) >= 500000) )");
    sbSQL.append("ORDER BY c.OrderNo DESC ");
    String[][] retAML0031 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey1 = "";
    for (int i = 0; i < retAML0031.length; i++) {
      String thisKey1 = retAML0031[i][1].trim();
      if (lastKey1.equals(thisKey1))
        continue; // ���ƪ����n
      sbMsg.append(amlDesc.replaceAll("<customName>", aml.getCustomName())).append("\n");
      lastKey1 = thisKey1;
    }

    // �Ȥ�
    sbSQL = new StringBuilder();
    sbSQL.append("select c.CustomNo ,c.CustomName ");
    sbSQL.append(", SUM(a.CashMoney)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 as CashMoney ");
    sbSQL.append("from Sale05M080 a , sale05m084 c ");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and a.EDate BETWEEN '" + aml.getTrxDate() + "' and c.CustomNo in ('" + aml.getCustomNos() +"') ");
    sbSQL.append("GROUP BY a.DocNo ,c.CustomNo ,c.CustomName ,a.CashMoney ");
    sbSQL.append("HAVING ( (ISNULL(a.CashMoney , 0)*(select top 1 cc.Percentage from Sale05M084 cc where cc.DocNo=a.DocNo and cc.CustomNo=c.CustomNo)/100 >= 500000) ) ");
    sbSQL.append("ORDER BY c.CustomNo DESC ");
    String[][] retAML0032 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey2 = "";
    for (int i = 0; i < retAML0032.length; i++) {
      String thisKey2 = retAML0032[i][0].trim();
      if (lastKey2.equals(thisKey2))
        continue; // ���ƪ����n
      sbMsg.append(amlDesc.replaceAll("<customName>", retAML0032[i][1].trim())).append("\n");
      lastKey2 = thisKey2;
    }

    rs.setRsStat(EnumRsStatus.SUCCESS);
    rs.setData(sbMsg.toString());
    return rs;
  }
  
  
  /**
   * AML 004  �P�@�Ȥ�3����~�餺�A�֭pú��{���W�L50�U��, �t���ˮִ��ܳq���C
   * Ĳ�o���� : ���ڳ���U�����{���A�B�Q��B�e�饼�R���j�B(AML003)
   * �d�߭q��γ�@�Ȥ�T�餺���ڲ֭p�A�W�L50�U���ͺA��
   * @param aml
   * @return
   * @throws Throwable
   */
  public Result chkAML004(AMLBean aml) throws Throwable {
    Result rs = new Result();
    StringBuilder sbMsg = new StringBuilder();
    aml.setAMLNo("003");

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(aml.getAMLNo()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rs.setRsStat(EnumRsStatus.NODATA_AMLMSG);
      return rs;
    }

    String startEDate = kutil.getDateAfterNDays(aml.getTrxDate().trim(), "/", -2);
    String endEDate = aml.getTrxDate().trim();

    System.out.println("AML03 start>>>");
    // �q��
    StringBuilder sbSQL = new StringBuilder();
    sbSQL.append("select a.EDate ,c.OrderNo , a.DocNo "); 
    sbSQL.append(", SUM(a.CashMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CashMoney ");
    sbSQL.append("from Sale05M080 a , sale05m086 c ");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and a.EDate BETWEEN '" + startEDate + "' AND '" + endEDate + "' and c.OrderNo in ('" + aml.getOrderNos() + "') ");
    sbSQL.append("GROUP BY c.OrderNo , a.DocNo ,a.EDate ,a.CashMoney ");
    sbSQL.append("HAVING ( (ISNULL(a.CashMoney , 0)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) >= 500000) ) ");
    sbSQL.append("ORDER BY a.edate DESC ,c.OrderNo DESC ");
    String[][] retAML0041 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey1 = "";
    String passKey1 = "";
    int totalMoney1 = 0;
    for (int i = 0; i < retAML0041.length; i++) {
      String thisKey1 = retAML0041[i][0].trim() + retAML0041[i][1].trim();
      int thisMoney = Integer.parseInt(retAML0041[i][3].trim());
      
      if (passKey1.equals(thisKey1)) {
        continue;  //�w���L��key(���W���L�j�B or �w�B�z�L) 
      }
      
      if(thisMoney >= 500000) {
        passKey1 = thisKey1;  //�o�Ѥj�B pass �o�ӭq��
        continue;
      }
      
      if(thisKey1.equals(lastKey1)) {   //�P�渹�֭p�A���渹��s 
        totalMoney1 += thisMoney;
      }else {
        totalMoney1 = thisMoney;
      }
      
      if( thisKey1.equals(lastKey1) && totalMoney1 > 500000 ) {   //�@���W�L�зǡA���ͺA�� & 
        sbMsg.append(amlDesc.replaceAll("<customName>", aml.getCustomName())).append("\n");
        passKey1 = thisKey1;
      }
      
      lastKey1 = thisKey1;
    }

    // �Ȥ�
    sbSQL = new StringBuilder();
    sbSQL.append("select a.EDate ,c.CustomNo ,c.CustomName , a.DocNo "); 
    sbSQL.append(", SUM(a.CashMoney)/(select COUNT(cc.OrderNo) from Sale05M086 cc where cc.DocNo=a.DocNo group by cc.DocNo) as CashMoney ");
    sbSQL.append("from Sale05M080 a , sale05m084 c ");
    sbSQL.append("where 1=1 and a.DocNo=c.DocNo ");
    sbSQL.append("and a.EDate BETWEEN '" + startEDate + "' AND '" + endEDate + "' and c.CustomNo in (" + aml.getCustomNos() + ") ");
    sbSQL.append("GROUP BY c.CustomNo ,c.CustomName , a.DocNo ,a.EDate ,a.CashMoney ");
    sbSQL.append("ORDER BY a.edate DESC ,c.CustomNo DESC ,c.CustomName DESC ");
    String[][] retAML0042 = dbSale.queryFromPool(sbSQL.toString(), 300);
    String lastKey2 = "";
    String passKey2 = "";
    int totalMoney2 = 0;
    for (int i = 0; i < retAML0042.length; i++) {
      String thisKey = retAML0042[i][0].trim() + retAML0042[i][1].trim();
      int thisMoney = Integer.parseInt(retAML0042[i][3].trim());
      
      if (passKey2.equals(thisKey)) {
        continue;  //�w���L��key(���W���L�j�B or �w�B�z�L) 
      }
      
      if(thisMoney >= 500000) {
        passKey2 = thisKey;  //�o�Ѥj�B pass �o�ӤH
        continue;
      }
      
      if(thisKey.equals(lastKey2)) {   //�P�H�֭p�A���渹��s 
        totalMoney2 += thisMoney;
      }else {
        totalMoney2 = thisMoney;
      }
      
      if( thisKey.equals(lastKey2) && totalMoney2 > 500000 ) {   //�@���W�L�зǡA���ͺA�� & �аO��pass 
        sbMsg.append(amlDesc.replaceAll("<customName>", retAML0042[i][2].trim())).append("\n");
        passKey2 = thisKey;
      }
      
      lastKey2 = thisKey;
    }

    rs.setRsStat(EnumRsStatus.SUCCESS);
    rs.setData(sbMsg.toString());
    return rs;
  }
  

  // 21. �F�vPEPS X171
  public String chkX171_PEPS(Map cons) throws Throwable {
    cons.put("AMLNo", "021");
    String rsMsg = "";

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(cons.get("AMLNo").toString().trim()) == null ? "" : mapAMLMsg.get(cons.get("AMLNo").toString().trim()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rsMsg = "[Error : �d�L���˺A����]";
      return rsMsg;
    }
    // System.out.println("amlDesc>>>" + amlDesc);

    String sql = "SELECT * FROM CRCLNAPF " + "WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L "
        + "WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE " + "AND L.CONTROLCLASSIFICATIONCODE ='X171' " + "AND C.REMOVEDDATE >= '" + strNowDate2 + " " + strNowTime2
        + "' ) " + "AND ISREMOVE = 'N'  " + "AND CUSTOMERID ='" + cons.get("customId") + "' ";
    String[][] retQuery = db400.queryFromPool(sql);

    if (retQuery.length > 0) {
      // �ŦX�˺A
      String[] retQuery1 = retQuery[0];
      String cusId = retQuery1[3].toString().trim();
      String cusName = retQuery1[5].toString().trim();
      String customTitle = cons.get("customTitle").toString();
      System.out.println("cusName>>>" + cusName);
      System.out.println("customTitle>>>" + customTitle);
      rsMsg += (amlDesc.replaceAll("<customTitle>", customTitle).replaceAll("<customName>", cusName) + "<br>");
      cons.put("errMsg", rsMsg);

      this.insSale070(cons);
      this.insCR400(cons);
    } else {
      // ���ŦX�˺A
      cons.put("errMsg", "���ŦX");
      this.insSale070(cons);
    }

    return rsMsg;
  }

  // 18. ����W�� X181
  public String chkX181_Sanctions(Map cons) throws Throwable {
    cons.put("AMLNo", "018");
    String rsMsg = "";

    // �����o�˺A����
    String amlDesc = mapAMLMsg.get(cons.get("AMLNo").toString().trim()) == null ? "" : mapAMLMsg.get(cons.get("AMLNo").toString().trim()).toString().trim(); // �˺A��ĵ��r
    if ("".equals(amlDesc)) {
      rsMsg = "[Error : �d�L���˺A����]";
      return rsMsg;
    }

    String sql = "SELECT * FROM CRCLNAPF " + "WHERE CONTROLLISTNAMECODE IN (SELECT DISTINCT C.CONTROLLISTNAMECODE FROM CRCLNCPF C,CRCLCLPF L "
        + "WHERE C.CONTROLCLASSIFICATIONCODE=L.CONTROLCLASSIFICATIONCODE " + "AND L.CONTROLCLASSIFICATIONCODE ='X181' " + "AND C.REMOVEDDATE >= '" + strNowDate2 + " " + strNowTime2
        + "' ) " + "AND ISREMOVE = 'N'  " + "AND CUSTOMERID ='" + cons.get("customId") + "' ";
    String[][] retQuery = db400.queryFromPool(sql);

    if (retQuery.length > 0) {
      // �ŦX�˺A
      String[] retQuery1 = retQuery[0];
      String cusId = retQuery1[3].toString().trim();
      String cusName = retQuery1[5].toString().trim();
      String customTitle = cons.get("customTitle").toString();
      rsMsg += (amlDesc.replaceAll("<customTitle>", customTitle).replaceAll("<customName>", cusName) + "<br>");
      cons.put("errMsg", rsMsg);

      this.insSale070(cons);
      this.insCR400(cons);
    } else {
      // ���ŦX�˺A
      cons.put("errMsg", "���ŦX");
      this.insSale070(cons);
    }

    return rsMsg;
  }

  /**
   * funcName(Func) : �\�ඵ EX ���W�B�ʫ��ҩ��� funcName2(RecordType) : �\�ඵ2 EX �Ȥ��ơB�N�z�H���
   * ActionName(ActionName) : �s�W�B�ק�B�R�� errMsg : �ŦX���˺A���e�A�ά�"���A��"�B"���ŦX" AMLNo :
   * AML�˺A�s��
   */
  public String insSale070(Map cons) throws Throwable {
    String rsMsg = "";
    String sql = "INSERT INTO Sale05M070 "
        + "(OrderNo,ProjectID1,RecordNo,ActionNo,Func,RecordType,ActionName,RecordDesc,CustomID,CustomName,OrderDate,SHB00,SHB06A,SHB06B,SHB06,SHB97,SHB98,SHB99) " + "VALUES "
        + "('" + strOrderNo + "','" + strProjectID1 + "','" + intRecordNo + "','" + strActionNo + "', " + "'" + cons.get("funcName") + "','" + cons.get("funcName2") + "','"
        + strActionName + "','" + cons.get("errMsg") + "', " + "'" + cons.get("customId") + "','" + cons.get("customName") + "','" + strOrderDate + "','RY','773','"
        + cons.get("AMLNo") + "','" + cons.get("errMsg") + "', " + "'" + empNo + "', '" + rocNowDate + "', '" + strNowTime + "') ";

    try {
      dbSale.execFromPool(sql);
    } catch (Exception ex) {
      rsMsg = "[Error : ins070 error]";
      System.out.println(">>>ins070 Error : " + cons);
      ex.printStackTrace();
    }

    return rsMsg;
  }

  public String insCR400(Map cons) throws Throwable {
    String rsMsg = "";
    String sql = "INSERT INTO PSHBPF " + "(SHB00, SHB01, SHB03, SHB04, SHB05, SHB06A, SHB06B, SHB06, SHB97, SHB98, SHB99) " + "VALUES ('RY', '" + strOrderNo + "', '" + rocNowDate
        + "', '" + cons.get("customId") + "', '" + cons.get("customName") + "', '773', '" + cons.get("AMLNo") + "', " + "'" + cons.get("errMsg") + "','" + empNo + "','"
        + rocNowDate + "','" + strNowTime + "') ";

    try {
      db400.execFromPool(sql);
    } catch (Exception ex) {
      rsMsg = "[Error : ins400 error]";
      System.out.println(">>>ins400 Error : " + cons);
      ex.printStackTrace();
    }

    return rsMsg;
  }

  // ���oAML�A�˻���
  public Map getAMLDesc() {
    return mapAMLMsg;
  }

}