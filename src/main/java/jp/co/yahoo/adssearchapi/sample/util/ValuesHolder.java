/**
 * Copyright (C) 2023 LY Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.util;

import jp.co.yahoo.adssearchapi.v17.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v17.model.AdGroupCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v17.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v17.model.BiddingStrategyServiceValue;
import jp.co.yahoo.adssearchapi.v17.model.CampaignServiceValue;
import jp.co.yahoo.adssearchapi.v17.model.PageFeedAssetSetServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method collection for Java Sample Program.
 */
public class ValuesHolder {

  private List<BiddingStrategyServiceValue> biddingStrategyServiceValueList = new ArrayList<>();

  /**
   * @return BiddingStrategyServiceValueList
   */
  public List<BiddingStrategyServiceValue> getBiddingStrategyServiceValueList() {
    return biddingStrategyServiceValueList;
  }

  /**
   * @param biddingStrategyValuesList BiddingStrategyServiceValueList
   */
  public void setBiddingStrategyServiceValueList(List<BiddingStrategyServiceValue> biddingStrategyValuesList) {
    if (this.biddingStrategyServiceValueList.size() == 0) {
      this.biddingStrategyServiceValueList = biddingStrategyValuesList;
    } else {
      this.biddingStrategyServiceValueList.addAll(biddingStrategyValuesList);
    }
  }

  private List<CampaignServiceValue> campaignServiceValueList = new ArrayList<>();

  /**
   * @return CampaignServiceValueList
   */
  public List<CampaignServiceValue> getCampaignServiceValueList() {
    return campaignServiceValueList;
  }

  /**
   * @param campaignValuesList CampaignValueList
   */
  public void setCampaignServiceValueList(List<CampaignServiceValue> campaignValuesList) {
    if (this.campaignServiceValueList.size() == 0) {
      this.campaignServiceValueList = campaignValuesList;
    } else {
      if (campaignValuesList.size() > 0) {
        this.campaignServiceValueList.addAll(campaignValuesList);
      } else {
        this.campaignServiceValueList = campaignValuesList;
      }
    }
  }

  private List<AdGroupServiceValue> adGroupServiceValueList = new ArrayList<>();

  /**
   * @return AdGroupServiceValueList
   */
  public List<AdGroupServiceValue> getAdGroupServiceValueList() {
    return adGroupServiceValueList;
  }

  /**
   * @param adGroupValuesList AdGroupServiceValueList
   */
  public void setAdGroupServiceValueList(List<AdGroupServiceValue> adGroupValuesList) {
    if (this.adGroupServiceValueList.size() == 0) {
      this.adGroupServiceValueList = adGroupValuesList;
    } else {
      this.adGroupServiceValueList.addAll(adGroupValuesList);
    }
  }

  private List<AdGroupAdServiceValue> adGroupAdServiceValueList = new ArrayList<>();

  /**
   * @return AdGroupAdServiceValueList
   */
  public List<AdGroupAdServiceValue> getAdGroupAdServiceValueList() {
    return adGroupAdServiceValueList;
  }

  /**
   * @param adGroupAdValuesList AdGroupAdServiceValueList
   */
  public void setAdGroupAdServiceValueList(List<AdGroupAdServiceValue> adGroupAdValuesList) {
    if (this.adGroupAdServiceValueList.size() == 0) {
      this.adGroupAdServiceValueList = adGroupAdValuesList;
    } else {
      this.adGroupAdServiceValueList.addAll(adGroupAdValuesList);
    }
  }

  private List<AdGroupCriterionServiceValue> adGroupCriterionServiceValueList = new ArrayList<>();

  /**
   * @return AdGroupCriterionServiceValueList
   */
  public List<AdGroupCriterionServiceValue> getAdGroupCriterionServiceValueList() {
    return adGroupCriterionServiceValueList;
  }

  /**
   * @param adGroupCriterionValuesList AdGroupCriterionServiceValueList
   */
  public void setAdGroupCriterionServiceValueList(List<AdGroupCriterionServiceValue> adGroupCriterionValuesList) {
    if (this.adGroupCriterionServiceValueList.size() == 0) {
      this.adGroupCriterionServiceValueList = adGroupCriterionValuesList;
    } else {
      this.adGroupCriterionServiceValueList.addAll(adGroupCriterionValuesList);
    }
  }

  private List<PageFeedAssetSetServiceValue> pageFeedAssetSetServiceValueList = new ArrayList<>();

  /**
   * @return PageFeedAssetSetServiceValueList
   */
  public List<PageFeedAssetSetServiceValue> getPageFeedAssetSetServiceValueList() {
    return pageFeedAssetSetServiceValueList;
  }

  /**
   * @param pageFeedAssetSetValuesList PageFeedAssetSetServiceValueList
   */
  public void setPageFeedAssetSetServiceValueList(List<PageFeedAssetSetServiceValue> pageFeedAssetSetValuesList) {
    if (this.pageFeedAssetSetServiceValueList.size() == 0) {
      this.pageFeedAssetSetServiceValueList = pageFeedAssetSetValuesList;
    } else {
      this.pageFeedAssetSetServiceValueList.addAll(pageFeedAssetSetValuesList);
    }
  }
}
