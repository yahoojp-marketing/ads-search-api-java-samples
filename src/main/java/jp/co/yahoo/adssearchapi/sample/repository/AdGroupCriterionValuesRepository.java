/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.repository;

import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v5.model.AdGroupCriterion;
import jp.co.yahoo.adssearchapi.v5.model.AdGroupCriterionServiceUse;
import jp.co.yahoo.adssearchapi.v5.model.AdGroupCriterionServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method collection for Java Sample Program.
 */
public class AdGroupCriterionValuesRepository {

  private ValuesHolder valuesHolder;

  /**
   * AdGroupCriterionValuesRepository constructor.
   *
   * @param valuesHolder ValuesHolder
   */
  public AdGroupCriterionValuesRepository(ValuesHolder valuesHolder) {
    this.valuesHolder = valuesHolder;
  }

  /**
   * @return AdGroupCriterions
   */
  public List<AdGroupCriterion> getAdGroupCriterions(){
    List<AdGroupCriterion> adGroupCriterions = new ArrayList<>();
    if (this.valuesHolder.getAdGroupCriterionServiceValueList().size() == 0) {
      return adGroupCriterions;
    }
    for (AdGroupCriterionServiceValue value : this.valuesHolder.getAdGroupCriterionServiceValueList()) {
      adGroupCriterions.add(value.getAdGroupCriterion());
    }
    return adGroupCriterions;
  }

  /**
   * @return CriterionIds
   */
  public List<Long> getCriterionIds(){
    List<Long> criterionIds = new ArrayList<>();
    if (this.valuesHolder.getAdGroupCriterionServiceValueList().size() == 0) {
      return criterionIds;
    }
    for (AdGroupCriterionServiceValue value : this.valuesHolder.getAdGroupCriterionServiceValueList()) {
      criterionIds.add(value.getAdGroupCriterion().getCriterion().getCriterionId());
    }
    return criterionIds;
  }

  /**
   * @param campaignId CampaignId
   * @param adGroupId AdGroupId
   * @param adGroupCriterionUse AdGroupCriterionUse
   * @return CriterionId
   */
  public Long findCriterionId(Long campaignId, Long adGroupId, AdGroupCriterionServiceUse adGroupCriterionUse){
    if (this.valuesHolder.getAdGroupCriterionServiceValueList().size() == 0) {
      return null;
    }
    for (AdGroupCriterionServiceValue value : this.valuesHolder.getAdGroupCriterionServiceValueList()) {
      if(value.getAdGroupCriterion().getCampaignId().equals(campaignId)
        && value.getAdGroupCriterion().getAdGroupId().equals(adGroupId)
        && value.getAdGroupCriterion().getUse() == adGroupCriterionUse){
        return value.getAdGroupCriterion().getCriterion().getCriterionId();
      }
    }
    return null;
  }
}
