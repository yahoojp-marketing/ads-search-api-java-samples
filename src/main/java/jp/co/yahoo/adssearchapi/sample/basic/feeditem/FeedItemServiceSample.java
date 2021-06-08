/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.feeditem;

import jp.co.yahoo.adssearchapi.sample.basic.adgroup.AdGroupServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v5.model.FeedItem;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceApprovalStatus;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceAttribute;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceCriterionTypeFeedItem;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceCustomParameter;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceCustomParameters;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceDayOfWeek;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceDevicePreference;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceFeedAttributeValue;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceGeoRestriction;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceGetResponse;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceIsRemove;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceKeywordMatchType;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceLocation;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceMinuteOfHour;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceMultipleFeedItemAttribute;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceOperation;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServicePlaceholderField;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServicePlaceholderType;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceSchedule;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceScheduling;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceSelector;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceSimpleFeedItemAttribute;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceTargetingAdGroup;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceTargetingCampaign;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceTargetingKeyword;
import jp.co.yahoo.adssearchapi.v5.model.FeedItemServiceValue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * example FeedItemService operation and Utility method collection.
 */
public class FeedItemServiceSample {

  private static final String SERVICE_NAME = "FeedItemService";

  /**
   * main method for FeedItemServiceSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {

    // =================================================================
    // Setting
    // =================================================================
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(new ValuesHolder());
    long accountId = ApiUtils.ACCOUNT_ID;

    try {
      // =================================================================
      // FeedItemService ADD (QUICKLINK)
      // =================================================================
      // create request.
      FeedItemServiceOperation addRequestQuicklink = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.QUICKLINK, new ArrayList<FeedItem>() {{
        add(createExampleQuicklink());
      }});

      // run
      List<FeedItemServiceValue> addResponseQuicklink = mutate(addRequestQuicklink, "add");
      valuesRepositoryFacade.getValuesHolder().setFeedItemServiceValueList(addResponseQuicklink);

      // =================================================================
      // FeedItemService ADD (CALLEXTENSION)
      // =================================================================
      // create request.
      FeedItemServiceOperation addRequestCallextension = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLEXTENSION, new ArrayList<FeedItem>() {{
        add(createExampleCallextension());
      }});

      // run
      List<FeedItemServiceValue> addResponseCallextension = mutate(addRequestCallextension, "add");
      valuesRepositoryFacade.getValuesHolder().setFeedItemServiceValueList(addResponseCallextension);

      // =================================================================
      // FeedItemService ADD (CALLOUT)
      // =================================================================
      // create request.
      FeedItemServiceOperation addRequestCallout = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLOUT, new ArrayList<FeedItem>() {{
        add(createExampleCallout());
      }});

      // run
      List<FeedItemServiceValue> addResponseCallout = mutate(addRequestCallout, "add");
      valuesRepositoryFacade.getValuesHolder().setFeedItemServiceValueList(addResponseCallout);

      //=================================================================
      // FeedItemService SET (QUICKLINK)
      //=================================================================
      // create request.
      List<FeedItem> feedItemsQuicklink = new ArrayList<>();
      feedItemsQuicklink.add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.QUICKLINK));
      FeedItemServiceOperation setRequestQuicklink = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.QUICKLINK, createExampleSetRequest(feedItemsQuicklink));

      // run
      mutate(setRequestQuicklink, "set");

      //=================================================================
      // FeedItemService SET (CALLEXTENSION)
      //=================================================================
      // create request.
      List<FeedItem> feedItemsCallextension = new ArrayList<>();
      feedItemsCallextension.add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.CALLEXTENSION));
      FeedItemServiceOperation setRequestCallextension = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLEXTENSION, createExampleSetRequest(feedItemsCallextension));

      // run
      mutate(setRequestCallextension, "set");

      //=================================================================
      // FeedItemService SET (CALLOUT)
      //=================================================================
      // create request.
      List<FeedItem> feedItemsCallout = new ArrayList<>();
      feedItemsCallout.add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.CALLOUT));
      FeedItemServiceOperation setRequestCallout = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLOUT, createExampleSetRequest(feedItemsCallout));

      // run
      mutate(setRequestCallout, "set");

      // =================================================================
      // FeedItemService GET
      // =================================================================
      // create request.
      FeedItemServiceSelector feedItemSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItemIds());

      // run
      get(feedItemSelector, "get");

      // =================================================================
      // FeedItemService REMOVE (QUICKLINK)
      // =================================================================
      // create request.
      FeedItemServiceOperation removeRequestQuicklink = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.QUICKLINK, new ArrayList<FeedItem>() {{
        add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.QUICKLINK));
      }});

      // run
      mutate(removeRequestQuicklink, "remove");

      // =================================================================
      // FeedItemService REMOVE (CALLEXTENSION)
      // =================================================================
      // create request.
      FeedItemServiceOperation removeRequestCallextension = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLEXTENSION, new ArrayList<FeedItem>() {{
        add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.CALLEXTENSION));
      }});

      // run
      mutate(removeRequestCallextension, "remove");

      // =================================================================
      // FeedItemService REMOVE (CALLOUT)
      // =================================================================
      // create request.
      FeedItemServiceOperation removeRequestCallout = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.CALLOUT, new ArrayList<FeedItem>() {{
        add(valuesRepositoryFacade.getFeedItemValuesRepository().findFeedItem(FeedItemServicePlaceholderType.CALLOUT));
      }});

      // run
      mutate(removeRequestCallout, "remove");

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * example mutate request.
   */
  public static FeedItemServiceOperation buildExampleMutateRequest(long accountId, FeedItemServicePlaceholderType feedItemPlaceholderType, List<FeedItem> operand) {
    FeedItemServiceOperation operation = new FeedItemServiceOperation();
    operation.setAccountId(accountId);
    operation.setPlaceholderType(feedItemPlaceholderType);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example AdCustomizer FeedItem request.
   *
   * @param campaignId       long
   * @param adGroupId        long
   * @param feedId     long
   * @param feedAttributeIds Map<String, Long>
   * @return FeedItem
   */
  public static FeedItem createExampleAdCustomizer(long campaignId, long adGroupId, long feedId, Map<String, Long> feedAttributeIds) {

    // set feedItem
    FeedItem feedItem = new FeedItem();
    feedItem.setFeedId(feedId);
    feedItem.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    feedItem.setEndDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusMonths(1L)));

    // set feedItemAttribute
    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeInteger = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeInteger.setFeedAttributeValue("1234567890");

    FeedItemServiceAttribute integer = new FeedItemServiceAttribute();
    integer.setFeedAttributeId(feedAttributeIds.get("AD_CUSTOMIZER_INTEGER"));

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributePrice = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributePrice.setFeedAttributeValue("9,999,999.99");

    FeedItemServiceAttribute price = new FeedItemServiceAttribute();
    price.setFeedAttributeId(feedAttributeIds.get("AD_CUSTOMIZER_PRICE"));

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeDate = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeDate.setFeedAttributeValue(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss").format(LocalDateTime.now().plusWeeks(1L)));

    FeedItemServiceAttribute date = new FeedItemServiceAttribute();
    date.setFeedAttributeId(feedAttributeIds.get("AD_CUSTOMIZER_DATE"));

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeString = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeString.setFeedAttributeValue("sample Value");

    FeedItemServiceAttribute string = new FeedItemServiceAttribute();
    string.setFeedAttributeId(feedAttributeIds.get("AD_CUSTOMIZER_STRING"));

    feedItem.setFeedItemAttribute(Arrays.asList( //
      integer, //
      price, //
      date, //
      string //
    ));

    // set scheduling
    FeedItemServiceSchedule schedule1 = new FeedItemServiceSchedule();
    schedule1.setDayOfWeek(FeedItemServiceDayOfWeek.SUNDAY);
    schedule1.setStartHour(14L);
    schedule1.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule1.setEndHour(15L);
    schedule1.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceSchedule schedule2 = new FeedItemServiceSchedule();
    schedule2.setDayOfWeek(FeedItemServiceDayOfWeek.MONDAY);
    schedule2.setStartHour(14L);
    schedule2.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule2.setEndHour(15L);
    schedule2.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceScheduling scheduling = new FeedItemServiceScheduling();
    scheduling.setSchedules(Arrays.asList(schedule1, schedule2));

    feedItem.setScheduling(scheduling);

    // set targetCampaign
    FeedItemServiceTargetingCampaign targetingCampaign = new FeedItemServiceTargetingCampaign();
    targetingCampaign.setTargetingCampaignId(campaignId);
    feedItem.setTargetingCampaign(targetingCampaign);

    // set targetAdGroup
    FeedItemServiceTargetingAdGroup targetingAdGroup = new FeedItemServiceTargetingAdGroup();
    targetingAdGroup.setTargetingAdGroupId(adGroupId);
    feedItem.setTargetingAdGroup(targetingAdGroup);

    // set targetKeyword
    FeedItemServiceTargetingKeyword targetingKeyword = new FeedItemServiceTargetingKeyword();
    targetingKeyword.setText("sample keyword");
    targetingKeyword.setKeywordMatchType(FeedItemServiceKeywordMatchType.EXACT);
    feedItem.setTargetingKeyword(targetingKeyword);

    // set geoTargeting
    FeedItemServiceLocation location = new FeedItemServiceLocation();
    location.setCriterionTypeFeedItem(FeedItemServiceCriterionTypeFeedItem.LOCATION);
    location.setGeoRestriction(FeedItemServiceGeoRestriction.LOCATION_OF_PRESENCE);
    location.setTargetId("JP-01-0010");
    feedItem.setLocation(location);

    return feedItem;
  }


  /**
   * example Quicklink FeedItem request.
   *
   * @return FeedItem
   */
  public static FeedItem createExampleQuicklink() {

    // feedItemAttribute
    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeLinkText = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeLinkText.setFeedAttributeValue("samplelink");
    FeedItemServiceAttribute linkText = new FeedItemServiceAttribute();
    linkText.setPlaceholderField(FeedItemServicePlaceholderField.LINK_TEXT);
    linkText.setSimpleFeedItemAttribute(feedItemAttributeLinkText);

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeAdvancedUrl = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeAdvancedUrl.setFeedAttributeValue("http://www.quicklink.sample.co.jp");
    FeedItemServiceAttribute advancedUrl = new FeedItemServiceAttribute();
    advancedUrl.setPlaceholderField(FeedItemServicePlaceholderField.ADVANCED_URL);
    advancedUrl.setSimpleFeedItemAttribute(feedItemAttributeAdvancedUrl);

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeAdvancedMobileUrl = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeAdvancedMobileUrl.setFeedAttributeValue("http://www.quicklink.sample.co.jp/mobile");
    FeedItemServiceAttribute advancedMobileUrl = new FeedItemServiceAttribute();
    advancedMobileUrl.setPlaceholderField(FeedItemServicePlaceholderField.ADVANCED_MOBILE_URL);
    advancedMobileUrl.setSimpleFeedItemAttribute(feedItemAttributeAdvancedMobileUrl);

    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeTrackingUrl = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeTrackingUrl.setFeedAttributeValue("http://www.quicklink.sample.co.jp?url={lpurl}&amp;pid={_id1}");
    FeedItemServiceAttribute trackingUrl = new FeedItemServiceAttribute();
    trackingUrl.setPlaceholderField(FeedItemServicePlaceholderField.TRACKING_URL);
    trackingUrl.setSimpleFeedItemAttribute(feedItemAttributeTrackingUrl);

    FeedItemServiceMultipleFeedItemAttribute feedItemAttributeAdditionalAdvancedUrls = new FeedItemServiceMultipleFeedItemAttribute();
    FeedItemServiceFeedAttributeValue feedAttributeValue1 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue1.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced1/");
    FeedItemServiceFeedAttributeValue feedAttributeValue2 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue2.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced2/");
    FeedItemServiceFeedAttributeValue feedAttributeValue3 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue3.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced3/");
    feedItemAttributeAdditionalAdvancedUrls.setFeedAttributeValues(Arrays.asList(feedAttributeValue1, feedAttributeValue2, feedAttributeValue3));
    FeedItemServiceAttribute additionalAdvancedUrls = new FeedItemServiceAttribute();
    additionalAdvancedUrls.setPlaceholderField(FeedItemServicePlaceholderField.ADDITIONAL_ADVANCED_URLS);
    additionalAdvancedUrls.setMultipleFeedItemAttribute(feedItemAttributeAdditionalAdvancedUrls);

    FeedItemServiceMultipleFeedItemAttribute feedItemAttributeAdditionalAdvancedMobileUrls = new FeedItemServiceMultipleFeedItemAttribute();
    FeedItemServiceFeedAttributeValue feedAttributeValue4 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue4.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced1/mobile");
    FeedItemServiceFeedAttributeValue feedAttributeValue5 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue5.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced2/mobile");
    FeedItemServiceFeedAttributeValue feedAttributeValue6 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue6.setFeedAttributeValue("http://www.quicklink.sample.co.jp/AdditionalAdvanced3/mobile");
    feedItemAttributeAdditionalAdvancedMobileUrls.setFeedAttributeValues(Arrays.asList(feedAttributeValue4, feedAttributeValue5, feedAttributeValue6));
    FeedItemServiceAttribute additionalAdvancedMobileUrls = new FeedItemServiceAttribute();
    additionalAdvancedMobileUrls.setPlaceholderField(FeedItemServicePlaceholderField.ADDITIONAL_ADVANCED_MOBILE_URLS);
    additionalAdvancedMobileUrls.setMultipleFeedItemAttribute(feedItemAttributeAdditionalAdvancedMobileUrls);

    // scheduling
    FeedItemServiceSchedule schedule1 = new FeedItemServiceSchedule();
    schedule1.setDayOfWeek(FeedItemServiceDayOfWeek.SUNDAY);
    schedule1.setStartHour(14L);
    schedule1.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule1.setEndHour(15L);
    schedule1.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceSchedule schedule2 = new FeedItemServiceSchedule();
    schedule2.setDayOfWeek(FeedItemServiceDayOfWeek.MONDAY);
    schedule2.setStartHour(14L);
    schedule2.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule2.setEndHour(15L);
    schedule2.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceScheduling scheduling = new FeedItemServiceScheduling();
    scheduling.setSchedules(Arrays.asList(schedule1, schedule2));

    // CustomParameters
    FeedItemServiceCustomParameter customParameter = new FeedItemServiceCustomParameter();
    customParameter.setKey("1d1");
    customParameter.setValue("1234");
    FeedItemServiceCustomParameters customParameters = new FeedItemServiceCustomParameters();
    customParameters.setParameters(Collections.singletonList(customParameter));


    FeedItem feedItem = new FeedItem();
    feedItem.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    feedItem.setEndDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusMonths(1L)));
    feedItem.setDevicePreference(FeedItemServiceDevicePreference.SMART_PHONE);
    feedItem.setFeedItemAttribute(Arrays.asList( //
      linkText, //
      advancedUrl, //
      advancedMobileUrl, //
      trackingUrl, //
      additionalAdvancedUrls, //
      additionalAdvancedMobileUrls //
    ));
    feedItem.setScheduling(scheduling);
    feedItem.setCustomParameters(customParameters);

    return feedItem;
  }

  /**
   * example Callextension FeedItem request.
   *
   * @return FeedItem
   */
  private static FeedItem createExampleCallextension() {

    // feedItemAttribute
    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeCallPhoneNumber = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeCallPhoneNumber.setFeedAttributeValue("0120-123-456");

    FeedItemServiceAttribute callPhoneNumber = new FeedItemServiceAttribute();
    callPhoneNumber.setPlaceholderField(FeedItemServicePlaceholderField.CALL_PHONE_NUMBER);
    callPhoneNumber.setSimpleFeedItemAttribute(feedItemAttributeCallPhoneNumber);

    // scheduling
    FeedItemServiceSchedule schedule1 = new FeedItemServiceSchedule();
    schedule1.setDayOfWeek(FeedItemServiceDayOfWeek.SUNDAY);
    schedule1.setStartHour(14L);
    schedule1.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule1.setEndHour(15L);
    schedule1.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceSchedule schedule2 = new FeedItemServiceSchedule();
    schedule2.setDayOfWeek(FeedItemServiceDayOfWeek.MONDAY);
    schedule2.setStartHour(14L);
    schedule2.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule2.setEndHour(15L);
    schedule2.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceScheduling scheduling = new FeedItemServiceScheduling();
    scheduling.setSchedules(Arrays.asList(schedule1, schedule2));

    FeedItem feedItem = new FeedItem();
    feedItem.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    feedItem.setEndDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusMonths(1L)));
    feedItem.setDevicePreference(FeedItemServiceDevicePreference.SMART_PHONE);
    feedItem.setFeedItemAttribute(Collections.singletonList(callPhoneNumber));
    feedItem.setScheduling(scheduling);

    return feedItem;
  }

  /**
   * example Callout FeedItem request.
   *
   * @return FeedItem
   */
  private static FeedItem createExampleCallout() {

    // feedItemAttribute
    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeCalloutText = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeCalloutText.setFeedAttributeValue("sample callout text");

    FeedItemServiceAttribute calloutText = new FeedItemServiceAttribute();
    calloutText.setPlaceholderField(FeedItemServicePlaceholderField.CALLOUT_TEXT);
    calloutText.setSimpleFeedItemAttribute(feedItemAttributeCalloutText);

    // scheduling
    FeedItemServiceSchedule schedule1 = new FeedItemServiceSchedule();
    schedule1.setDayOfWeek(FeedItemServiceDayOfWeek.SUNDAY);
    schedule1.setStartHour(14L);
    schedule1.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule1.setEndHour(15L);
    schedule1.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceSchedule schedule2 = new FeedItemServiceSchedule();
    schedule2.setDayOfWeek(FeedItemServiceDayOfWeek.MONDAY);
    schedule2.setStartHour(14L);
    schedule2.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule2.setEndHour(15L);
    schedule2.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceScheduling scheduling = new FeedItemServiceScheduling();
    scheduling.setSchedules(Arrays.asList(schedule1, schedule2));

    FeedItem feedItem = new FeedItem();
    feedItem.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    feedItem.setEndDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusMonths(1L)));
    feedItem.setDevicePreference(FeedItemServiceDevicePreference.SMART_PHONE);
    feedItem.setFeedItemAttribute(Collections.singletonList(calloutText));
    feedItem.setScheduling(scheduling);

    return feedItem;
  }

  /**
   * example StructuredSnippet FeedItem request.
   *
   * @return FeedItem
   */
  public static FeedItem createExampleStructuredSnippet() {
    // set feedItem
    FeedItem feedItem = new FeedItem();
    feedItem.setStartDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))));
    feedItem.setEndDate(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusMonths(1L)));
    feedItem.setDevicePreference(FeedItemServiceDevicePreference.SMART_PHONE);

    // set feedItemAttribute
    FeedItemServiceSimpleFeedItemAttribute feedItemAttributeHeader = new FeedItemServiceSimpleFeedItemAttribute();
    feedItemAttributeHeader.setFeedAttributeValue("ブランド");

    FeedItemServiceAttribute header = new FeedItemServiceAttribute();
    header.setPlaceholderField(FeedItemServicePlaceholderField.STRUCTURED_SNIPPET_HEADER);

    FeedItemServiceAttribute values = new FeedItemServiceAttribute();
    values.setPlaceholderField(FeedItemServicePlaceholderField.STRUCTURED_SNIPPET_VALUES);
    FeedItemServiceFeedAttributeValue feedAttributeValue1 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue1.setFeedAttributeValue("SampleBrand1");
    FeedItemServiceFeedAttributeValue feedAttributeValue2 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue2.setFeedAttributeValue("SampleBrand2");
    FeedItemServiceFeedAttributeValue feedAttributeValue3 = new FeedItemServiceFeedAttributeValue();
    feedAttributeValue3.setFeedAttributeValue("SampleBrand3");

    FeedItemServiceMultipleFeedItemAttribute feedItemAttributeValues = new FeedItemServiceMultipleFeedItemAttribute();
    feedItemAttributeValues.setFeedAttributeValues(Arrays.asList(feedAttributeValue1, feedAttributeValue2, feedAttributeValue3));

    feedItem.setFeedItemAttribute(Arrays.asList(header, values));

    // set scheduling
    FeedItemServiceSchedule schedule1 = new FeedItemServiceSchedule();
    schedule1.setDayOfWeek(FeedItemServiceDayOfWeek.SUNDAY);
    schedule1.setStartHour(14L);
    schedule1.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule1.setEndHour(15L);
    schedule1.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceSchedule schedule2 = new FeedItemServiceSchedule();
    schedule2.setDayOfWeek(FeedItemServiceDayOfWeek.MONDAY);
    schedule2.setStartHour(14L);
    schedule2.setStartMinute(FeedItemServiceMinuteOfHour.ZERO);
    schedule2.setEndHour(15L);
    schedule2.setEndMinute(FeedItemServiceMinuteOfHour.THIRTY);
    FeedItemServiceScheduling scheduling = new FeedItemServiceScheduling();
    scheduling.setSchedules(Arrays.asList(schedule1, schedule2));
    feedItem.setScheduling(scheduling);

    return feedItem;
  }

  /**
   * example feedItem set request.
   *
   * @param feedItems FeedItem
   * @return FeedItems
   */
  public static List<FeedItem> createExampleSetRequest(List<FeedItem> feedItems) {

    List<FeedItem> operands = new ArrayList<>();

    for (FeedItem feedItem : feedItems) {
      FeedItem operand = new FeedItem();
      operand.setFeedItemId(feedItem.getFeedItemId());

      // clear date
      operand.setStartDate("");
      operand.setEndDate("");

      // clear scheduling
      operand.setScheduling(new FeedItemServiceScheduling());

      // clear getTargeting
      if (feedItem.getPlaceholderType().equals(FeedItemServicePlaceholderType.AD_CUSTOMIZER)) {
        FeedItemServiceLocation location = new FeedItemServiceLocation();
        location.setIsRemove(FeedItemServiceIsRemove.TRUE);
        operand.setLocation(location);
      }
      operands.add(operand);
    }
    return operands;
  }

  /**
   * example get request.
   *
   * @param accountId   long
   * @param feedItemIds Long
   * @return FeedItemSelector
   */
  public static FeedItemServiceSelector buildExampleGetRequest(long accountId, List<Long> feedItemIds) {

    // Set Selector
    FeedItemServiceSelector selector = new FeedItemServiceSelector();
    selector.setAccountId(accountId);

    if (feedItemIds.size() > 0) {
      selector.setFeedItemIds(feedItemIds);
    }

    selector.setPlaceholderTypes(Arrays.asList( //
      FeedItemServicePlaceholderType.QUICKLINK, //
      FeedItemServicePlaceholderType.CALLEXTENSION, //
      FeedItemServicePlaceholderType.AD_CUSTOMIZER, //
      FeedItemServicePlaceholderType.CALLOUT, //
      FeedItemServicePlaceholderType.STRUCTURED_SNIPPET //
    ));

    selector.setApprovalStatuses(Arrays.asList( //
      FeedItemServiceApprovalStatus.APPROVED, //
      FeedItemServiceApprovalStatus.APPROVED_WITH_REVIEW, //
      FeedItemServiceApprovalStatus.REVIEW, //
      FeedItemServiceApprovalStatus.PRE_DISAPPROVED, //
      FeedItemServiceApprovalStatus.POST_DISAPPROVED //
    ));

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

  /**
   * example mutate campaignTargets.
   *
   * @param operation CampaignTargetOperation
   * @return CampaignTargetValues
   */
  public static List<FeedItemServiceValue> mutate(FeedItemServiceOperation operation, String action) throws Exception {

    FeedItemServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, FeedItemServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * Sample Program for FeedItemService GET.
   *
   * @param selector FeedItemSelector
   * @return FeedItemValues
   * @throws Exception throw exception
   */
  public static List<FeedItemServiceValue> get(FeedItemServiceSelector selector, String action) throws Exception {

    FeedItemServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, FeedItemServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * check & create upper service object.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  private static ValuesHolder setup() throws Exception {
    return AdGroupServiceSample.create();
  }


  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {
    if (valuesHolder.getFeedItemServiceValueList().size() > 0) {
      ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);
      FeedItemServiceOperation removeRequest =
        buildExampleMutateRequest(ApiUtils.ACCOUNT_ID, FeedItemServicePlaceholderType.QUICKLINK, valuesRepositoryFacade.getFeedItemValuesRepository().getFeedItems());
      mutate(removeRequest, "remove");
    }
    AdGroupServiceSample.cleanup(valuesHolder);
  }

  /**
   * create basic FeedItem.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  public static ValuesHolder create() throws Exception {

    ValuesHolder parentValuesHolder = setup();
    long accountId = ApiUtils.ACCOUNT_ID;

    FeedItemServiceOperation addRequestQuicklink = buildExampleMutateRequest(accountId, FeedItemServicePlaceholderType.QUICKLINK, new ArrayList<FeedItem>() {{
      add(createExampleQuicklink());
    }});

    List<FeedItemServiceValue> addResponseQuicklink = mutate(addRequestQuicklink,"add");

    ValuesHolder selfValuesHolder = new ValuesHolder();
    selfValuesHolder.setBiddingStrategyServiceValueList(parentValuesHolder.getBiddingStrategyServiceValueList());
    selfValuesHolder.setFeedServiceValueList(parentValuesHolder.getFeedServiceValueList());
    selfValuesHolder.setCampaignServiceValueList(parentValuesHolder.getCampaignServiceValueList());
    selfValuesHolder.setAdGroupServiceValueList(parentValuesHolder.getAdGroupServiceValueList());
    selfValuesHolder.setFeedItemServiceValueList(addResponseQuicklink);

    return selfValuesHolder;
  }

}
