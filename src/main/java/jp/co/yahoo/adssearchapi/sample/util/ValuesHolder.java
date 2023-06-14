/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.util;

import jp.co.yahoo.adssearchapi.v11.model.AccountSharedServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.AdGroupAdServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.AdGroupCriterionServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.AdGroupServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.BiddingStrategyServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.CampaignServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.FeedItemServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.FeedServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.LabelServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.PageFeedAssetSetServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.ReportDefinitionServiceValue;
import jp.co.yahoo.adssearchapi.v11.model.RetargetingListServiceValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method collection for Java Sample Program.
 */
public class ValuesHolder {

  private List<AccountSharedServiceValue> accountSharedServiceValueList = new ArrayList<>();

  /**
   * @return AccountSharedServiceValueList
   */
  public List<AccountSharedServiceValue> getAccountSharedServiceValueList() {
    return accountSharedServiceValueList;
  }

  /**
   * @param accountSharedValuesList AccountSharedServiceValueList
   */
  public void setAccountSharedServiceValueList(List<AccountSharedServiceValue> accountSharedValuesList) {
    if (this.accountSharedServiceValueList.size() == 0) {
      this.accountSharedServiceValueList = accountSharedValuesList;
    } else {
      this.accountSharedServiceValueList.addAll(accountSharedValuesList);
    }
  }

  private List<RetargetingListServiceValue> retargetingListServiceValueList = new ArrayList<>();

  /**
   * @return RetargetingListServiceValueList
   */
  public List<RetargetingListServiceValue> getRetargetingListServiceValueList() {
    return retargetingListServiceValueList;
  }

  /**
   * @param retargetingListValuesList RetargetingListServiceValueList
   */
  public void setRetargetingListServiceValueList(List<RetargetingListServiceValue> retargetingListValuesList) {
    if (this.retargetingListServiceValueList.size() == 0) {
      this.retargetingListServiceValueList = retargetingListValuesList;
    } else {
      this.retargetingListServiceValueList.addAll(retargetingListValuesList);
    }
  }

  private List<FeedServiceValue> feedServiceValueList = new ArrayList<>();

  /**
   * @return FeedServiceValueList
   */
  public List<FeedServiceValue> getFeedServiceValueList() {
    return feedServiceValueList;
  }

  /**
   * @param feedFolderValuesList FeedServiceValueList
   */
  public void setFeedServiceValueList(List<FeedServiceValue> feedFolderValuesList) {
    if (this.feedServiceValueList.size() == 0) {
      this.feedServiceValueList = feedFolderValuesList;
    } else {
      this.feedServiceValueList.addAll(feedFolderValuesList);
    }
  }

  private List<LabelServiceValue> labelServiceValueList = new ArrayList<>();

  /**
   * @return LabelValuesList
   */
  public List<LabelServiceValue> getLabelServiceValueList() {
    return labelServiceValueList;
  }

  /**
   * @param labelValuesList LabelServiceValueList
   */
  public void setLabelServiceValueList(List<LabelServiceValue> labelValuesList) {
    if (this.labelServiceValueList.size() == 0) {
      this.labelServiceValueList = labelValuesList;
    } else {
      this.labelServiceValueList.addAll(labelValuesList);
    }
  }

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

  private List<FeedItemServiceValue> feedItemServiceValueList = new ArrayList<>();

  /**
   * @return FeedItemServiceValueList
   */
  public List<FeedItemServiceValue> getFeedItemServiceValueList() {
    return feedItemServiceValueList;
  }

  /**
   * @param feedItemValuesList FeedItemServiceValueList
   */
  public void setFeedItemServiceValueList(List<FeedItemServiceValue> feedItemValuesList) {
    if (this.feedItemServiceValueList.size() == 0) {
      this.feedItemServiceValueList = feedItemValuesList;
    } else {
      this.feedItemServiceValueList.addAll(feedItemValuesList);
    }
  }

  private List<ReportDefinitionServiceValue> reportDefinitionServiceValueList = new ArrayList<>();

  /**
   * @return ReportDefinitionServiceValueList
   */
  public List<ReportDefinitionServiceValue> getReportDefinitionServiceValueList() {
    return reportDefinitionServiceValueList;
  }

  /**
   * @param reportDefinitionValuesList FeedItemValuesList
   */
  public void setReportDefinitionServiceValueList(List<ReportDefinitionServiceValue> reportDefinitionValuesList) {
    if (this.reportDefinitionServiceValueList.size() == 0) {
      this.reportDefinitionServiceValueList = reportDefinitionValuesList;
    } else {
      this.reportDefinitionServiceValueList.addAll(reportDefinitionValuesList);
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
