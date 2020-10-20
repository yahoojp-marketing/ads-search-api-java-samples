/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v3.model.AdGroupAd;
import jp.co.yahoo.adssearchapi.v3.model.AdGroupAdServiceAdType;
import jp.co.yahoo.adssearchapi.v3.model.AdGroupAdServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method collection for Java Sample Program.
 */
public class AdGroupAdValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * AdGroupAdValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public AdGroupAdValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return AdGroupAds
   */
  public List<AdGroupAd> getAdGroupAds(){
    List<AdGroupAd> adGroupAds = new ArrayList<>();
    if (this.valuesHolder.getAdGroupAdServiceValueList().size() == 0) {
      return adGroupAds;
    }
    for (AdGroupAdServiceValue value : this.valuesHolder.getAdGroupAdServiceValueList()) {
      adGroupAds.add(value.getAdGroupAd());
    }
    return adGroupAds;
  }

  /**
   * @return AdIds
   */
  public List<Long> getAdIds(){
    List<Long> adIds = new ArrayList<>();
    if (this.valuesHolder.getAdGroupAdServiceValueList().size() == 0) {
      return adIds;
    }
    for (AdGroupAdServiceValue value : this.valuesHolder.getAdGroupAdServiceValueList()) {
      adIds.add(value.getAdGroupAd().getAdId());
    }
    return adIds;
  }

  /**
   * @param campaignId CampaignId
   * @param adGroupId AdGroupId
   * @param adType AdType
   * @return Long|null
   */
  public Long findAdId(Long campaignId, Long adGroupId, AdGroupAdServiceAdType adType){
    if (this.valuesHolder.getAdGroupAdServiceValueList().size() == 0) {
      return null;
    }
    for (AdGroupAdServiceValue value : this.valuesHolder.getAdGroupAdServiceValueList()) {
      if(value.getAdGroupAd().getCampaignId() == campaignId
        && value.getAdGroupAd().getAdGroupId() == adGroupId
        && value.getAdGroupAd().getAd().getAdType().equals(adType)){
        return value.getAdGroupAd().getAdId();
      }
    }
    return null;
  }
}
