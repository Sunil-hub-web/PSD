package in.co.psd;

public class ProductModelClass {

    String product_id,product_title,product_desc,product_amt,product_lockDays,product_purchaseLimit,product_owner,
            product_banner,product_catelog,product_stat, income, productBook_start_date, productBook_end_date;

    public ProductModelClass(String product_id, String product_title, String product_desc, String product_amt, String product_lockDays,
                             String product_purchaseLimit, String product_owner, String product_banner, String product_catelog,
                             String product_stat, String income, String productBook_start_date, String productBook_end_date) {

        this.product_id = product_id;
        this.product_title = product_title;
        this.product_desc = product_desc;
        this.product_amt = product_amt;
        this.product_lockDays = product_lockDays;
        this.product_purchaseLimit = product_purchaseLimit;
        this.product_owner = product_owner;
        this.product_banner = product_banner;
        this.product_catelog = product_catelog;
        this.product_stat = product_stat;
        this.income = income;
        this.productBook_start_date = productBook_start_date;
        this.productBook_end_date = productBook_end_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_amt() {
        return product_amt;
    }

    public void setProduct_amt(String product_amt) {
        this.product_amt = product_amt;
    }

    public String getProduct_lockDays() {
        return product_lockDays;
    }

    public void setProduct_lockDays(String product_lockDays) {
        this.product_lockDays = product_lockDays;
    }

    public String getProduct_purchaseLimit() {
        return product_purchaseLimit;
    }

    public void setProduct_purchaseLimit(String product_purchaseLimit) {
        this.product_purchaseLimit = product_purchaseLimit;
    }

    public String getProduct_owner() {
        return product_owner;
    }

    public void setProduct_owner(String product_owner) {
        this.product_owner = product_owner;
    }

    public String getProduct_banner() {
        return product_banner;
    }

    public void setProduct_banner(String product_banner) {
        this.product_banner = product_banner;
    }

    public String getProduct_catelog() {
        return product_catelog;
    }

    public void setProduct_catelog(String product_catelog) {
        this.product_catelog = product_catelog;
    }

    public String getProduct_stat() {
        return product_stat;
    }

    public void setProduct_stat(String product_stat) {
        this.product_stat = product_stat;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getProductBook_start_date() {
        return productBook_start_date;
    }

    public void setProductBook_start_date(String productBook_start_date) {
        this.productBook_start_date = productBook_start_date;
    }

    public String getProductBook_end_date() {
        return productBook_end_date;
    }

    public void setProductBook_end_date(String productBook_end_date) {
        this.productBook_end_date = productBook_end_date;
    }
}
