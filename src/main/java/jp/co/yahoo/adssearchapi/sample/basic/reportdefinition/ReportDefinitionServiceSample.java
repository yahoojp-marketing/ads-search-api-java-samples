/**
 * Copyright (C) 2022 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.reportdefinition;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.v9.api.ReportDefinitionServiceApi;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinition;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceDownloadSelector;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceGetReportFields;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceOperation;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportCompressType;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportDateRangeType;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportDownloadEncode;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportDownloadFormat;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportIncludeDeleted;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportLanguage;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportSortField;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportSortType;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceReportType;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceSelector;
import jp.co.yahoo.adssearchapi.v9.model.ReportDefinitionServiceValue;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

/**
 * example ReportDefinitionService operation and Utility method collection.
 */
public class ReportDefinitionServiceSample {

  private static final ReportDefinitionServiceApi reportDefinitionService = new ReportDefinitionServiceApi(ApiUtils.getYahooJapanAdsApiClient());

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
      "NETWORK", //
      "CLICK_TYPE", //
      "DEVICE", //
      "DAY", //
      "DAY_OF_WEEK", //
      "QUARTER", //
      "YEAR", //
      "MONTH", //
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
      reportDefinitionService.reportDefinitionServiceGetReportFieldsPost(getReportFieldsRequest);

      // =================================================================
      // ReportDefinitionService ADD
      // =================================================================
      // create request.
      ReportDefinitionServiceOperation addRequest = buildExampleMutateRequest( //
          accountId, Collections.singletonList(createExampleReportDefinition()) //
      );

      // run
      List<ReportDefinitionServiceValue> addResponse = reportDefinitionService.reportDefinitionServiceAddPost(addRequest).getRval().getValues();
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
      List<ReportDefinitionServiceValue> getResponse = reportDefinitionService.reportDefinitionServiceGetPost(selector).getRval().getValues();

      long getJobIds = 0;
      for (ReportDefinitionServiceValue values : getResponse) {
        getJobIds = values.getReportDefinition().getReportJobId();
      }

      // =================================================================
      // ReportDefinitionService download (http request)
      // =================================================================
      ReportDefinitionServiceDownloadSelector downloadSelector = buildExampleDownloadRequest(accountId, getJobIds);
      Resource report = reportDefinitionService.reportDefinitionServiceDownloadPost(downloadSelector);
      System.out.println("### reportString=\n" + StreamUtils.copyToString(report.getInputStream(), StandardCharsets.UTF_8));

      // =================================================================
      // ReportDefinitionService REMOVE
      // =================================================================
      // create request.
      ReportDefinitionServiceOperation removeRequest = buildExampleMutateRequest(accountId, reportDefinitionList);

      // run
      reportDefinitionService.reportDefinitionServiceRemovePost(removeRequest);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
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
    reportDefinition.setReportIncludeDeleted(ReportDefinitionServiceReportIncludeDeleted.TRUE);

    return reportDefinition;
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
      List<ReportDefinitionServiceValue> reportDefinitionValues = reportDefinitionService.reportDefinitionServiceGetPost(selector).getRval().getValues();

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
