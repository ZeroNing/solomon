package com.steven.solomon.param;

import com.steven.solomon.code.TenancyErrorCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TenantInfoUpdateParam implements Serializable {

    /**
     * 主键id
     */
    @NotBlank(message = TenancyErrorCode.ID_NOT_NULL)
    private String id;

    /**
     * 租客名称
     */
    @NotBlank(message = TenancyErrorCode.NAME_NOT_NULL)
    private String name;

    /**
     * 身份证号码
     */
    @NotBlank(message = TenancyErrorCode.IDENTITY_CARD_NOT_NULL)
    private String identityCard;

    /**
     * 手机号码
     */
    @NotBlank(message = TenancyErrorCode.PHONE_NOT_NULL)
    private String phone;

    /**
     * 省份id
     */
    @NotNull(message = TenancyErrorCode.PROVINCE_ID_NOT_NULL)
    private Long provinceId;

    /**
     * 市id
     */
    @NotNull(message = TenancyErrorCode.CITY_ID_NOT_NULL)
    private Long cityId;

    /**
     * 区id
     */
    @NotNull(message = TenancyErrorCode.AREA_ID_NOT_NULL)
    private Long areaId;

    /**
     * 地址
     */
    @NotBlank(message = TenancyErrorCode.ADDRESS_NOT_NULL)
    private String address;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
