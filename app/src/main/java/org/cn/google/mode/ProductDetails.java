package org.cn.google.mode;

public class ProductDetails {

    private String productId;//": '1',
    private String jsonPurchaseInfo;//": {"orderId":"GPA.3315-6563-0607-34401","packageName":"com.igg.android.lordsmobile","productId":"23438","purchaseTime":1616596172087,"purchaseState":0,"purchaseToken":"hnlhndnbjpmaphckfgkipgie.AO-J1OxQ5o2fQvs5QfgtRP2ZPJ8vWupovB8LiAM91Mkas5nCiIl0SZZvjCyFkLa1RQOvof-5Ey8ZZE5bk1yNzB2afp6p6xG0TvyyNuvmarbUo0gB31pmij8","acknowledged":false},
    private String mSignature;//": '1',
    private String price_id;//": '1',

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getJsonPurchaseInfo() {
        return jsonPurchaseInfo;
    }

    public void setJsonPurchaseInfo(String jsonPurchaseInfo) {
        this.jsonPurchaseInfo = jsonPurchaseInfo;
    }

    public String getmSignature() {
        return mSignature;
    }

    public void setmSignature(String mSignature) {
        this.mSignature = mSignature;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }
}
