/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import java.util.ArrayList;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.Campaign;
import jp.co.yahoo.adssearchapi.v7.model.CampaignServiceAppStore;
import jp.co.yahoo.adssearchapi.v7.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignServiceValue;

/**
 * Utility method collection for Java Sample Program.
 */
public class CampaignValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * CampaignValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public CampaignValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return Campaigns
   */
  public List<Campaign> getCampaigns(){
    List<Campaign> campaigns = new ArrayList<>();
    if (this.valuesHolder.getCampaignServiceValueList().size() == 0) {
      return campaigns;
    }
    for (CampaignServiceValue value : this.valuesHolder.getCampaignServiceValueList()) {
      campaigns.add(value.getCampaign());
    }
    return campaigns;
  }

  /**
   * @return CampaignIds
   */
  public List<Long> getCampaignIds(){
    List<Long> campaignIds = new ArrayList<>();
    if (this.valuesHolder.getCampaignServiceValueList().size() == 0) {
      return campaignIds;
    }
    for (CampaignServiceValue value : this.valuesHolder.getCampaignServiceValueList()) {
      campaignIds.add(value.getCampaign().getCampaignId());
    }
    return campaignIds;
  }

  /**
   * @param campaignType CampaignType
   * @return Long|null
   */
  public Long findCampaignId(CampaignServiceType campaignType) {
    if (this.valuesHolder.getCampaignServiceValueList().size() == 0) {
      return null;
    }
    for (CampaignServiceValue value : this.valuesHolder.getCampaignServiceValueList()) {
      if (value.getCampaign().getType().equals(campaignType)) {
        return value.getCampaign().getCampaignId();
      }
    }
    return null;
  }

  /**
   * @param appStore AppStore
   * @return Long|null
   */
  public Long findCampaignId(CampaignServiceAppStore appStore) {
    if (this.valuesHolder.getCampaignServiceValueList().size() == 0) {
      return null;
    }
    for (CampaignServiceValue value : this.valuesHolder.getCampaignServiceValueList()) {
      if (value.getCampaign().getType().equals(CampaignServiceType.MOBILE_APP) && value.getCampaign().getAppStore().equals(appStore)) {
        return value.getCampaign().getCampaignId();
      }
    }
    return null;
  }

  /**
   * @param campaignId Long
   * @return String|null
   */
  public String findAppId(Long campaignId) {
    if (this.valuesHolder.getCampaignServiceValueList().size() == 0) {
      return null;
    }
    for (CampaignServiceValue value : this.valuesHolder.getCampaignServiceValueList()) {
      if (value.getCampaign().getCampaignId().equals(campaignId)) {
        return value.getCampaign().getAppId();
      }
    }
    return null;
  }
}
