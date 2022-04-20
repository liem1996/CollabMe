package com.example.collabme.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Payment {
    @SerializedName("CardNo")
    @Expose
    private String CardNo;
    @SerializedName("ExpDay")
    @Expose
    private String ExpDay;
    @SerializedName("Cvv")
    @Expose
    private String Cvv;
    @SerializedName("IdPerson")
    @Expose
    private String IdPerson;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("OfferId")
    @Expose
    private String OfferId;
    @SerializedName("BankAcount")
    @Expose
    private String BankAcount;

    public Payment(String cardNo, String expDay, String cvv, String idPerson, String name, String offerId, String bankAcount) {
        this.CardNo = cardNo;
        this.ExpDay = expDay;
        this.Cvv = cvv;
        this.IdPerson = idPerson;
        this.Name = name;
        this.OfferId = offerId;
        this.BankAcount = bankAcount;
    }

    /**
     *
     * @return
     * The CardNo
     */
    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    /**
     *
     * @return
     * The ExpDay
     */
    public String getExpDay() {
        return ExpDay;
    }

    public void setExpDay(String expDay) {
        ExpDay = expDay;
    }

    /**
     *
     * @return
     * The Cvv
     */
    public String getCvv() {
        return Cvv;
    }

    public void setCvv(String cvv) {
        Cvv = cvv;
    }

    /**
     *
     * @return
     * The IdPerson
     */
    public String getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(String idPerson) {
        IdPerson = idPerson;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    /**
     *
     * @return
     * The OfferId
     */
    public String getOfferId() {
        return OfferId;
    }


    public void setOfferId(String offerId) {
        OfferId = offerId;
    }

    /**
     *
     * @return
     * The getBankAcount
     */
    public String getBankAcount() {
        return BankAcount;
    }

    public void setBankAcount(String bankAcount) {
        BankAcount = bankAcount;
    }


    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("CardNo",CardNo);
        json.put("ExpDay",ExpDay);
        json.put("Cvv",Cvv);
        json.put("IdPerson",IdPerson);
        json.put("Name", Name);
        json.put("OfferId", OfferId);
        json.put("BankAcount", BankAcount);
        return json;
    }
    public static Payment create(Map<String, Object> json) {
        String CardNo = (String) json.get("CardNo");
        String ExpDay = (String) json.get("ExpDay");
        String Cvv = (String) json.get("Cvv");
        String IdPerson = (String) json.get("IdPerson");
        String Name = (String) json.get("Name");
        String OfferId = (String) json.get("OfferId");
        String BankAcount = (String) json.get("BankAcount");

        Payment payment = new Payment(CardNo,ExpDay,Cvv,IdPerson,Name,OfferId,BankAcount);
        return payment;
    }


}
