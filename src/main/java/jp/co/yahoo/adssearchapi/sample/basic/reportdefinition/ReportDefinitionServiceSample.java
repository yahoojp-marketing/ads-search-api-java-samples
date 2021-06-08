/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.reportdefinition;

import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinition;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceDownloadSelector;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceGetReportFields;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceGetReportFieldsResponse;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceGetResponse;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceOperation;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportCompressType;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportDateRangeType;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportDownloadEncode;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportDownloadFormat;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportFieldAttribute;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportIncludeDeleted;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportIncludeZeroImpressions;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportLanguage;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportSortField;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportSortType;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceReportType;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceSelector;
import jp.co.yahoo.adssearchapi.v5.model.ReportDefinitionServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * example ReportDefinitionService operation and Utility method collection.
 */
public class ReportDefinitionServiceSample {

  private static final String SERVICE_NAME = "ReportDefinitionService";

  private static final List<String> CAMPAIGN_REPORT_FIELDS = Arrays.asList( //
    "CAMPAIGN_ID", //
    "CAMPAIGN_NAME", //
    "CAMPAIGN_DISTRIBUTION_SETTINGS", //
    "CAMPAIGN_DISTRIBUTION_STATUS", //
    "DAILY_SPENDING_LIMIT", //
    "CAMPAIGN_START_DATE", //
    "CAMPAIGN_END_DATE", //
    "TRACKING_URL", //
    "CUSTOM_PARAMETERS", //
    "CAMPAIGN_TRACKING_ID", //
    "CONVERSIONS", //
    "CONV_VALUE", //
    "VALUE_PER_CONV", //
    "CAMPAIGN_MOBILE_BID_MODIFIER", //
    "NETWORK", //
    "CLICK_TYPE", //
    "DEVICE", //
    "DAY", //
    "DAY_OF_WEEK", //
    "QUARTER", //
    "YEAR", //
    "MONTH", //
    "MONTH_OF_YEAR", //
    "WEEK", //
    "HOUR_OF_DAY", //
    "OBJECT_OF_CONVERSION_TRACKING", //
    "CONVERSION_NAME", //
    "CAMPAIGN_TYPE" //
  );

  /**
   * main method for ReportDefinitionService
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {

    // =================================================================
    // Setting
    // =================================================================
    long accountId = ApiUtils.ACCOUNT_ID;

    try {
      // =================================================================
      // ReportDefinitionService getReportFields
      // =================================================================

      ReportDefinitionServiceGetReportFields getReportFieldsRequest = new ReportDefinitionServiceGetReportFields();
      getReportFieldsRequest.setReportType(ReportDefinitionServiceReportType.CAMPAIGN);

      // run
      getReportFields(getReportFieldsRequest);

      // =================================================================
      // ReportDefinitionService ADD
      // =================================================================
      // create request.
      ReportDefinitionServiceOperation addRequest = buildExampleMutateRequest( //
        accountId, Collections.singletonList(createExampleReportDefinition()) //
      );

      // run
      List<ReportDefinitionServiceValue> addResponse = mutate(addRequest, "add");
      List<ReportDefinition> reportDefinitionList = new ArrayList<>();
      List<Long> reportJobIds = new ArrayList<>();
      for (ReportDefinitionServiceValue values : addResponse) {
        reportDefinitionList.add(values.getReportDefinition());
        reportJobIds.add(values.getReportDefinition().getReportJobId());
      }

      // =================================================================
      // ReportDefinitionService GET
      // =================================================================
      // check job status
      checkStatus(reportJobIds);

      // create request.
      ReportDefinitionServiceSelector selector = buildExampleGetRequest(accountId, reportJobIds);

      // run
      List<ReportDefinitionServiceValue> getResponse = get(selector);

      long getJobIds = 0;
      for (ReportDefinitionServiceValue values : getResponse) {
        getJobIds = values.getReportDefinition().getReportJobId();
      }

      // =================================================================
      // ReportDefinitionService download (http request)
      // =================================================================
      ReportDefinitionServiceDownloadSelector downloadSelector = buildExampleDownloadRequest(accountId, getJobIds);
      ApiUtils.download(SERVICE_NAME, "download", downloadSelector, "reportDownloadSample.csv");

      // =================================================================
      // ReportDefinitionService REMOVE
      // =================================================================
      // create request.
      ReportDefinitionServiceOperation removeRequest = buildExampleMutateRequest(accountId, reportDefinitionList);

      // run
      mutate(removeRequest, "remove");

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * example getReportFields ReportDefinitions.
   *
   * @param getReportFieldsRequest ReportDefinitionServiceGetReportFields
   * @return ReportFieldAttribute
   */
  private static List<ReportDefinitionServiceReportFieldAttribute> getReportFields(ReportDefinitionServiceGetReportFields getReportFieldsRequest) throws Exception {

    ReportDefinitionServiceGetReportFieldsResponse response = ApiUtils.execute(SERVICE_NAME, "getReportFields", getReportFieldsRequest, ReportDefinitionServiceGetReportFieldsResponse.class);

    return response.getRval().getFields();
  }

  /**
   * example get request.
   *
   * @param accountId long
   * @param reportJobIds List<Long>
   * @return get
   */
  public static ReportDefinitionServiceSelector buildExampleGetRequest(long accountId, List<Long> reportJobIds) {
    ReportDefinitionServiceSelector selector = new ReportDefinitionServiceSelector();
    selector.setAccountId(accountId);

    if (!reportJobIds.isEmpty()) {
      selector.setReportJobIds(reportJobIds);
    }
    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

  /**
   * example mutate request.
   */
  public static ReportDefinitionServiceOperation buildExampleMutateRequest(long accountId, List<ReportDefinition> operand) {
    ReportDefinitionServiceOperation operation = new ReportDefinitionServiceOperation();
    operation.setAccountId(accountId);
    operation.getOperand().addAll(operand);

    return operation;
  }

  private static ReportDefinitionServiceDownloadSelector buildExampleDownloadRequest(long accountId, long reportJobId) {
    ReportDefinitionServiceDownloadSelector selector = new ReportDefinitionServiceDownloadSelector();
    selector.setAccountId(accountId);
    selector.setReportJobId(reportJobId);
    return selector;
  }

  /**
   * example ReportDefinition request.
   *
   * @return ReportDefinition
   */
  private static ReportDefinition createExampleReportDefinition() {

    ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setReportName("sampleReport_"+ ApiUtils.getCurrentTimestamp());
    reportDefinition.setReportType(ReportDefinitionServiceReportType.CAMPAIGN);

    reportDefinition.setReportDateRangeType(ReportDefinitionServiceReportDateRangeType.YESTERDAY);

    reportDefinition.setFields(CAMPAIGN_REPORT_FIELDS);

    ReportDefinitionServiceReportSortField reportSortField = new ReportDefinitionServiceReportSortField();
    reportSortField.setReportSortType(ReportDefinitionServiceReportSortType.ASC);
    reportSortField.setField(CAMPAIGN_REPORT_FIELDS.get(0));
    reportDefinition.setSortFields(Arrays.asList(reportSortField));

    reportDefinition.setReportDownloadFormat(ReportDefinitionServiceReportDownloadFormat.CSV);
    reportDefinition.setReportDownloadEncode(ReportDefinitionServiceReportDownloadEncode.UTF8);
    reportDefinition.setReportLanguage(ReportDefinitionServiceReportLanguage.EN);
    reportDefinition.setReportCompressType(ReportDefinitionServiceReportCompressType.NONE);
    reportDefinition.setReportIncludeZeroImpressions(ReportDefinitionServiceReportIncludeZeroImpressions.TRUE);
    reportDefinition.setReportIncludeDeleted(ReportDefinitionServiceReportIncludeDeleted.TRUE);

    return reportDefinition;
  }

  /**
   * example get reportDefinition.
   *
   * @param selector ReportDefinitionServiceSelector
   * @return List<ReportDefinitionServiceValue>
   */
  public static List<ReportDefinitionServiceValue> get(ReportDefinitionServiceSelector selector) throws Exception {

    ReportDefinitionServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, "get", selector, ReportDefinitionServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example mutate reportDefinition.
   *
   * @param operation ReportDefinitionServiceOperation
   * @return ReportDefinitionServiceValue
   */
  public static List<ReportDefinitionServiceValue> mutate(ReportDefinitionServiceOperation operation, String acrion) throws Exception {

    ReportDefinitionServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, acrion, operation, ReportDefinitionServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example check Report job status.
   *
   * @param reportJobIds    List<Long>
   */
  private static void checkStatus(List<Long> reportJobIds) throws Exception {

    // call 30sec sleep * 30 = 15minute
    for (int i = 0; i < 30; i++) {

      // sleep 30 second.
      System.out.println("***** sleep 30 seconds for Report Job Status Check *****");
      Thread.sleep(30000);

      // get
      ReportDefinitionServiceSelector selector = buildExampleGetRequest(ApiUtils.ACCOUNT_ID, reportJobIds);
      List<ReportDefinitionServiceValue> reportDefinitionValues = get(selector);

      int completedCount = 0;

      // check status
      for (ReportDefinitionServiceValue values : reportDefinitionValues) {
        if (values.getReportDefinition().getReportJobStatus() == null) {
          throw new Exception("Fail to get Report.");
        }
        switch (values.getReportDefinition().getReportJobStatus()) {
          default:
          case WAIT:
          case IN_PROGRESS:
            continue;
          case FAILED:
            throw new Exception("Report Job Status failed.");
          case COMPLETED:
            completedCount++;
        }
      }

      if (reportDefinitionValues.size() == completedCount) {
        return;
      }
    }
    throw new Exception("Fail to get Report.");
  }
}
