package com.ftouchcustomer.Orders;


import java.io.Serializable;
import java.util.List;

public class ClsLayerItemMaster implements Serializable {


    String TAX_SLAB_ID = "";
    String ITEM_CODE = "";
    String first_letter = "";
    String TAX_TYPE = "";
    String AUTO_GENERATE_ITEMCODE = "";
    String HSN_SAC_CODE = "";
    String ITEM_NAME = "", REMARK = "", UNIT_CODE = "", TAGS = "", ACTIVE = "", TAX_APPLY = "", SLAB_NAME = "";
    String LAYERITEM_ID = "";

    int DISPLAY_ORDER = 0;

    boolean isHeader = false;

    List<String> TagList;

    List<String> Images_name_list;

    double lastPurchasePrice = 0.0;
    double WHOLESALE_RATE = 0.0;
    double _saleRateIncludingTax = 0.0;
    double _wholesaleRateIncludingTax = 0.0;
    double _AmountWithTax = 0.0;
    double _AmountWithoutTax = 0.0;
    double _TotalTaxAmount = 0.0;
    double RATE_PER_UNIT = 0.0, MIN_STOCK = 0.0, MAX_STOCK = 0.0, SGST = 0.0, CGST = 0.0, IGST = 0.0,
            Opening_Stock = 0.0, IN = 0.0, OUT = 0.0, AverageSaleRate = 0.0;
    String MerchantName = "";

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }



    public List<String> getImages_name_list() {
        return Images_name_list;
    }

    public void setImages_name_list(List<String> images_name_list) {
        Images_name_list = images_name_list;
    }


    public double getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Double getIN() {
        return IN;
    }

    public void setIN(Double IN) {
        this.IN = IN;
    }

    public Double getOUT() {
        return OUT;
    }

    public void setOUT(Double OUT) {
        this.OUT = OUT;
    }

    public Double getAverageSaleRate() {
        return AverageSaleRate;
    }

    public void setAverageSaleRate(Double averageSaleRate) {
        AverageSaleRate = averageSaleRate;
    }

    public String getHSN_SAC_CODE() {
        return HSN_SAC_CODE;
    }

    public void setHSN_SAC_CODE(String HSN_SAC_CODE) {
        this.HSN_SAC_CODE = HSN_SAC_CODE;
    }

    public Double getOpening_Stock() {
        return Opening_Stock;
    }

    public void setOpening_Stock(Double opening_Stock) {
        Opening_Stock = opening_Stock;
    }

    public ClsLayerItemMaster(String first_letter) {
        this.first_letter = first_letter;
    }

    public List<String> getTagList() {
        return TagList;
    }

    public void setTagList(List<String> tagList) {
        TagList = tagList;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getSLAB_NAME() {
        return SLAB_NAME;
    }

    public void setSLAB_NAME(String SLAB_NAME) {
        this.SLAB_NAME = SLAB_NAME;
    }

    public Double getSGST() {
        return SGST;
    }

    public void setSGST(Double SGST) {
        this.SGST = SGST;
    }

    public Double getCGST() {
        return CGST;
    }

    public void setCGST(Double CGST) {
        this.CGST = CGST;
    }

    public Double getIGST() {
        return IGST;
    }

    public void setIGST(Double IGST) {
        this.IGST = IGST;
    }

    public String getTAX_SLAB_ID() {
        return TAX_SLAB_ID;
    }

    public void setTAX_SLAB_ID(String TAX_SLAB_ID) {
        this.TAX_SLAB_ID = TAX_SLAB_ID;
    }

    public String getTAX_APPLY() {
        return TAX_APPLY;
    }

    public void setTAX_APPLY(String TAX_APPLY) {
        this.TAX_APPLY = TAX_APPLY;
    }

    public String getLAYERITEM_ID() {
        return LAYERITEM_ID;
    }

    public void setLAYERITEM_ID(String LAYERITEM_ID) {
        this.LAYERITEM_ID = LAYERITEM_ID;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public Double getMIN_STOCK() {
        return MIN_STOCK;
    }

    public void setMIN_STOCK(Double MIN_STOCK) {
        this.MIN_STOCK = MIN_STOCK;
    }

    public Double getMAX_STOCK() {
        return MAX_STOCK;
    }

    public void setMAX_STOCK(Double MAX_STOCK) {
        this.MAX_STOCK = MAX_STOCK;
    }

    public int getDISPLAY_ORDER() {
        return DISPLAY_ORDER;
    }

    public void setDISPLAY_ORDER(int DISPLAY_ORDER) {
        this.DISPLAY_ORDER = DISPLAY_ORDER;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String UNIT_CODE) {
        this.UNIT_CODE = UNIT_CODE;
    }

    public String getTAGS() {
        return TAGS;
    }

    public void setTAGS(String TAGS) {
        this.TAGS = TAGS;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public Double getRATE_PER_UNIT() {
        return RATE_PER_UNIT;
    }

    public void setRATE_PER_UNIT(Double RATE_PER_UNIT) {
        this.RATE_PER_UNIT = RATE_PER_UNIT;
    }


    public String getAUTO_GENERATE_ITEMCODE() {
        return AUTO_GENERATE_ITEMCODE;
    }

    public void setAUTO_GENERATE_ITEMCODE(String AUTO_GENERATE_ITEMCODE) {
        this.AUTO_GENERATE_ITEMCODE = AUTO_GENERATE_ITEMCODE;
    }

    public Double get_AmountWithTax() {
        return _AmountWithTax;
    }

    public void set_AmountWithTax(Double _AmountWithTax) {
        this._AmountWithTax = _AmountWithTax;
    }

    public Double get_AmountWithoutTax() {
        return _AmountWithoutTax;
    }

    public void set_AmountWithoutTax(Double _AmountWithoutTax) {
        this._AmountWithoutTax = _AmountWithoutTax;
    }

    public Double get_TotalTaxAmount() {
        return _TotalTaxAmount;
    }

    public void set_TotalTaxAmount(Double _TotalTaxAmount) {
        this._TotalTaxAmount = _TotalTaxAmount;
    }

    public Double get_saleRateIncludingTax() {
        return _saleRateIncludingTax;
    }

    public void set_saleRateIncludingTax(Double _saleRateIncludingTax) {
        this._saleRateIncludingTax = _saleRateIncludingTax;
    }

    public Double get_wholesaleRateIncludingTax() {
        return _wholesaleRateIncludingTax;
    }

    public void set_wholesaleRateIncludingTax(Double _wholesaleRateIncludingTax) {
        this._wholesaleRateIncludingTax = _wholesaleRateIncludingTax;
    }

    public Double getWHOLESALE_RATE() {
        return WHOLESALE_RATE;
    }

    public void setWHOLESALE_RATE(Double WHOLESALE_RATE) {
        this.WHOLESALE_RATE = WHOLESALE_RATE;
    }

    public String getTAX_TYPE() {
        return TAX_TYPE;
    }

    public void setTAX_TYPE(String TAX_TYPE) {
        this.TAX_TYPE = TAX_TYPE;
    }


}
