/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import java.util.ArrayList;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v11.model.AdGroup;
import jp.co.yahoo.adssearchapi.v11.model.AdGroupServiceValue;

/**
 * Utility method collection for Java Sample Program.
 */
public class AdGroupValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * AdGroupValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public AdGroupValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return AdGroups
   */
  public List<AdGroup> getAdGroups(){
    List<AdGroup> adGroups = new ArrayList<>();
    if (this.valuesHolder.getAdGroupServiceValueList().size() == 0) {
      return adGroups;
    }
    for (AdGroupServiceValue value : this.valuesHolder.getAdGroupServiceValueList()) {
      adGroups.add(value.getAdGroup());
    }
    return adGroups;
  }

  /**
   * @return AdGroupIds
   */
  public List<Long> getAdGroupIds(){
    List<Long> adGroupIds = new ArrayList<>();
    if (this.valuesHolder.getAdGroupServiceValueList().size() == 0) {
      return adGroupIds;
    }
    for (AdGroupServiceValue value : this.valuesHolder.getAdGroupServiceValueList()) {
      adGroupIds.add(value.getAdGroup().getAdGroupId());
    }
    return adGroupIds;
  }

  /**
   * @param campaignId Long
   * @return Long|null
   */
  public Long findAdGroupId(Long campaignId) {
    if (this.valuesHolder.getAdGroupServiceValueList().size() == 0) {
      return null;
    }
    for (AdGroupServiceValue value : this.valuesHolder.getAdGroupServiceValueList()) {
      if (value.getAdGroup().getCampaignId().equals(campaignId)) {
        return value.getAdGroup().getAdGroupId();
      }
    }
    return null;
  }
}
