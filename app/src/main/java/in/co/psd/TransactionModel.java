package in.co.psd;

public class TransactionModel {

    String transaction_id, transaction_user, transaction_amount, transaction_payOn, transaction_payId, transaction_img, transaction_stat,
            transaction_date, transaction_type, transaction_purpose;

    public TransactionModel(String transaction_id, String transaction_user, String transaction_amount, String transaction_payOn,
                            String transaction_payId, String transaction_img, String transaction_stat, String transaction_date,
                            String transaction_type, String transaction_purpose) {

        this.transaction_id = transaction_id;
        this.transaction_user = transaction_user;
        this.transaction_amount = transaction_amount;
        this.transaction_payOn = transaction_payOn;
        this.transaction_payId = transaction_payId;
        this.transaction_img = transaction_img;
        this.transaction_stat = transaction_stat;
        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
        this.transaction_purpose = transaction_purpose;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_user() {
        return transaction_user;
    }

    public void setTransaction_user(String transaction_user) {
        this.transaction_user = transaction_user;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_payOn() {
        return transaction_payOn;
    }

    public void setTransaction_payOn(String transaction_payOn) {
        this.transaction_payOn = transaction_payOn;
    }

    public String getTransaction_payId() {
        return transaction_payId;
    }

    public void setTransaction_payId(String transaction_payId) {
        this.transaction_payId = transaction_payId;
    }

    public String getTransaction_img() {
        return transaction_img;
    }

    public void setTransaction_img(String transaction_img) {
        this.transaction_img = transaction_img;
    }

    public String getTransaction_stat() {
        return transaction_stat;
    }

    public void setTransaction_stat(String transaction_stat) {
        this.transaction_stat = transaction_stat;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_purpose() {
        return transaction_purpose;
    }

    public void setTransaction_purpose(String transaction_purpose) {
        this.transaction_purpose = transaction_purpose;
    }
}
