package org.cn.google.mode;

public class SkuDetailModel {

    private String productId;//"33",
    private String type;//"inapp",
    private String price;//"US$9.99",
    private Long price_amount_micros;//9990000,
    private String price_currency_code;//"USD",
    private String title;//"1月 (PandaVPN Pro - 做全球最快&最具隐私安全的VPN)",
    private String description;//"1月",
    private String skuDetailsToken;//"AEuhp4JETPBUZ_cFerokDcWQETsG35Dbp_LZX6hD8A-5Ga4K1719bkxP3BSgaLEILPo2"

    @Override
    public String toString() {
        return "SkuDetailModel{" +
                "productId='" + productId + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", price_amount_micros=" + price_amount_micros +
                ", price_currency_code='" + price_currency_code + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", skuDetailsToken='" + skuDetailsToken + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getPrice_amount_micros() {
        return price_amount_micros;
    }

    public void setPrice_amount_micros(Long price_amount_micros) {
        this.price_amount_micros = price_amount_micros;
    }

    public String getPrice_currency_code() {
        return price_currency_code;
    }

    public void setPrice_currency_code(String price_currency_code) {
        this.price_currency_code = price_currency_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkuDetailsToken() {
        return skuDetailsToken;
    }

    public void setSkuDetailsToken(String skuDetailsToken) {
        this.skuDetailsToken = skuDetailsToken;
    }
}
