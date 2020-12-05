package Invoice.vo;

/**
 * �o���C�L������
 * @author B04391
 *
 */

public class InvoicePrintVo {
  private String title = "�����H��";                            // �o�����Y
  
  private String sendName = "";                                 // �H��H�W��
  private String sendPost = "11073";                            // �H��H�l���ϸ�
  private String sendAddr = "�x�_���Q����1��28��";              // �H��H�a�}
  private String sendCompany = "�����H�ثO�I�Ʒ~�ѥ��������q";  // �H��H���q
  private int DETAIL_LENGTH = 4;                                // ���Ӧ��

  //����H
  private String recipientPost = "";        // ����H�l���ϸ�
  private String recipientAddr = "";        // ����H�a�}
  private String recipientCompany = "";     // ����H���q
  private String recipientName = "";        // ����H�m�W

  //�~���B���B
  private String invoiceDate = "";          // �o�����ͤ��
  private String invoiceNumber = "";        // �o���s��
  private String printDate = "";            // �o���C�L���
  private String randomCode = "";           // �H���X
  private String saleAmount = "";           // �P���B
  private String total = "";                // �`�p
  private String buyerId = "";              // �R��
  private String sellerId = "";             // ���
  private String mark1 = "";                // �o���U����O1
  private String mark2 = "";                // �o���U����O 2
  private String detail = "";               // ���ӲӶ��H,�@�����j��� ;�����j����
  private String printCount = "1";          // �C�L����;�j��1���|�ܸɦL
  private String deptId = "2200";           // �C�L�̩��ݳ��
  private String buyerName = "";            // �R���H
  private String tax = "";                  // �|�B
  
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getSendPost() {
    return sendPost;
  }
  public void setSendPost(String sendPost) {
    this.sendPost = sendPost;
  }
  public String getSendAddr() {
    return sendAddr;
  }
  public void setSendAddr(String sendAddr) {
    this.sendAddr = sendAddr;
  }
  public String getSendCompany() {
    return sendCompany;
  }
  public void setSendCompany(String sendCompany) {
    this.sendCompany = sendCompany;
  }
  public int getDETAIL_LENGTH() {
    return DETAIL_LENGTH;
  }
  public void setDETAIL_LENGTH(int dETAIL_LENGTH) {
    DETAIL_LENGTH = dETAIL_LENGTH;
  }
  public String getSendName() {
    return sendName;
  }
  public void setSendName(String sendName) {
    this.sendName = sendName;
  }
  public String getRecipientPost() {
    return recipientPost;
  }
  public void setRecipientPost(String recipientPost) {
    this.recipientPost = recipientPost;
  }
  public String getRecipientAddr() {
    return recipientAddr;
  }
  public void setRecipientAddr(String recipientAddr) {
    this.recipientAddr = recipientAddr;
  }
  public String getRecipientCompany() {
    return recipientCompany;
  }
  public void setRecipientCompany(String recipientCompany) {
    this.recipientCompany = recipientCompany;
  }
  public String getRecipientName() {
    return recipientName;
  }
  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }
  public String getInvoiceDate() {
    return invoiceDate;
  }
  public void setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;
  }
  public String getInvoiceNumber() {
    return invoiceNumber;
  }
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }
  public String getPrintDate() {
    return printDate;
  }
  public void setPrintDate(String printDate) {
    this.printDate = printDate;
  }
  public String getRandomCode() {
    return randomCode;
  }
  public void setRandomCode(String randomCode) {
    this.randomCode = randomCode;
  }
  public String getSaleAmount() {
    return saleAmount;
  }
  public void setSaleAmount(String saleAmount) {
    this.saleAmount = saleAmount;
  }
  public String getTotal() {
    return total;
  }
  public void setTotal(String total) {
    this.total = total;
  }
  public String getBuyerId() {
    return buyerId;
  }
  public void setBuyerId(String buyerId) {
    this.buyerId = buyerId;
  }
  public String getSellerId() {
    return sellerId;
  }
  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }
  public String getMark1() {
    return mark1;
  }
  public void setMark1(String mark1) {
    this.mark1 = mark1;
  }
  public String getMark2() {
    return mark2;
  }
  public void setMark2(String mark2) {
    this.mark2 = mark2;
  }
  public String getDetail() {
    return detail;
  }
  public void setDetail(String detail) {
    this.detail = detail;
  }
  public String getPrintCount() {
    return printCount;
  }
  public void setPrintCount(String printCount) {
    this.printCount = printCount;
  }
  public String getDeptId() {
    return deptId;
  }
  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }
  public String getBuyerName() {
    return buyerName;
  }
  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }
  public String getTax() {
    return tax;
  }
  public void setTax(String tax) {
    this.tax = tax;
  }
  
}
