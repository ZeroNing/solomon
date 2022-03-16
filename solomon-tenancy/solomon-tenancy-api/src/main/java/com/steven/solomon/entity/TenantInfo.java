package com.steven.solomon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;
@TableName("tenant_info")
public class TenantInfo extends BaseEntity {

    /**
     * 租客名称
     */
    private String name;

    /**
     * 身份证号码
     */
    private String identityCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 省份id
     */
    private Long provinceId;

    /**
     * 市级id
     */
    private Long cityId;

    /**
     * 区级id
     */
    private Long areaId;

    /**
     * 身份证地址
     */
    private String address;

    public TenantInfo(){
        super();
        super.create();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
