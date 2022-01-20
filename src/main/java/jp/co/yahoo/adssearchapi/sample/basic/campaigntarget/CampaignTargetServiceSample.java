/**
 * Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.basic.campaigntarget;

import jp.co.yahoo.adssearchapi.sample.basic.campaign.CampaignServiceSample;
import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v7.model.CampaignServiceType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceDayOfWeek;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceExcludedType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceGetResponse;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceLocationTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceMinuteOfHour;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceMutateResponse;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceNetworkCoverageType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceNetworkTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceOperation;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServicePlatformTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServicePlatformType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceScheduleTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceSelector;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceTarget;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceTargetType;
import jp.co.yahoo.adssearchapi.v7.model.CampaignTargetServiceValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * example CampaignTargetService operation and Utility method collection.
 */
public class CampaignTargetServiceSample {

  private static final String SERVICE_NAME = "CampaignTargetService";

  /**
   * main method for CampaignTargetServiceSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {

    // =================================================================
    // Setting
    // =================================================================
    ValuesHolder valuesHolder = new ValuesHolder();
    long accountId = ApiUtils.ACCOUNT_ID;

    try {
      // =================================================================
      // check & create upper service object.
      // =================================================================
      valuesHolder = setup();
      ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);
      Long campaignId = valuesRepositoryFacade.getCampaignValuesRepository().findCampaignId(CampaignServiceType.STANDARD);

      // =================================================================
      // CampaignTargetService::ADD
      // =================================================================
      // create request.
      CampaignTargetServiceOperation addCampaignTargetOperation = buildExampleMutateRequest(accountId, new ArrayList<CampaignTarget>() {{
        // Schedule Target
        add(createExampleScheduleTarget(accountId, campaignId));
        // Location Target
        add(createExampleLocationTarget(accountId, campaignId));
        // Network Target
        add(createExampleNetworkTarget(accountId, campaignId));
      }});

      // run
      List<CampaignTargetServiceValue> addCampaignTargetValues = mutate(addCampaignTargetOperation, "add");

      List<CampaignTarget> campaignTargets = new ArrayList<>();
      for (CampaignTargetServiceValue campaignTargetValues: addCampaignTargetValues) {
        campaignTargets.add(campaignTargetValues.getCampaignTarget());
      }

      // =================================================================
      // CampaignTargetService::SET
      // =================================================================
      // create request.
      CampaignTargetServiceOperation setCampaignTargetOperation = buildExampleMutateRequest(accountId, createExampleSetRequest(campaignTargets));

      // run
      mutate(setCampaignTargetOperation, "set");

      // =================================================================
      // CampaignTargetService::GET
      // =================================================================
      // create request.
      CampaignTargetServiceSelector campaignTargetSelector = buildExampleGetRequest(accountId, campaignTargets);

      // run
      get(campaignTargetSelector, "get");

      // =================================================================
      // CampaignTargetService::REMOVE
      // =================================================================
      // create request.
      CampaignTargetServiceOperation removeCampaignTargetOperation = buildExampleMutateRequest(accountId, campaignTargets);

      // run
      mutate(removeCampaignTargetOperation, "remove");

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      cleanup(valuesHolder);
    }
  }

  /**
   * example mutate request.
   */
  public static CampaignTargetServiceOperation buildExampleMutateRequest(long accountId, List<CampaignTarget> operand) {
    CampaignTargetServiceOperation operation = new CampaignTargetServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example Schedule Target request.
   *
   * @param accountId long
   * @param campaignId long
   * @return CampaignTarget
   */
  public static CampaignTarget createExampleScheduleTarget(long accountId, long campaignId) {

    // target
    CampaignTargetServiceScheduleTarget scheduleTarget = new CampaignTargetServiceScheduleTarget();
    scheduleTarget.setDayOfWeek(CampaignTargetServiceDayOfWeek.MONDAY);
    scheduleTarget.setStartHour(10);
    scheduleTarget.setStartMinute(CampaignTargetServiceMinuteOfHour.ZERO);
    scheduleTarget.setEndHour(11);
    scheduleTarget.setEndMinute(CampaignTargetServiceMinuteOfHour.ZERO);

    CampaignTargetServiceTarget target = new CampaignTargetServiceTarget();
    target.setTargetType(CampaignTargetServiceTargetType.SCHEDULE);
    target.setScheduleTarget(scheduleTarget);

    CampaignTarget campaignTarget = new CampaignTarget();
    campaignTarget.setAccountId(accountId);
    campaignTarget.setCampaignId(campaignId);
    campaignTarget.setBidMultiplier(1.0);
    campaignTarget.setTarget(target);

    return campaignTarget;
  }

  /**
   * example Location Target request.
   *
   * @param accountId long
   * @param campaignId long
   * @return CampaignTarget
   */
  public static CampaignTarget createExampleLocationTarget(long accountId, long campaignId) {

    // target
    CampaignTargetServiceLocationTarget locationTarget = new CampaignTargetServiceLocationTarget();
    locationTarget.setExcludedType(CampaignTargetServiceExcludedType.INCLUDED);

    CampaignTargetServiceTarget target = new CampaignTargetServiceTarget();
    target.setTargetType(CampaignTargetServiceTargetType.LOCATION);
    target.setTargetId("JP-13-0048");
    target.setLocationTarget(locationTarget);

    CampaignTarget campaignTarget = new CampaignTarget();
    campaignTarget.setAccountId(accountId);
    campaignTarget.setCampaignId(campaignId);
    campaignTarget.setBidMultiplier(0.95);
    campaignTarget.setTarget(target);

    return campaignTarget;
  }

  /**
   * example Network Target request.
   *
   * @param accountId long
   * @param campaignId long
   * @return CampaignTarget
   */
  public static CampaignTarget createExampleNetworkTarget(long accountId, long campaignId) {

    // target
    CampaignTargetServiceNetworkTarget networkTarget = new CampaignTargetServiceNetworkTarget();
    networkTarget.setNetworkCoverageType(CampaignTargetServiceNetworkCoverageType.YAHOO_SEARCH);

    CampaignTargetServiceTarget target = new CampaignTargetServiceTarget();
    target.setTargetType(CampaignTargetServiceTargetType.NETWORK);
    target.setNetworkTarget(networkTarget);

    CampaignTarget campaignTarget = new CampaignTarget();
    campaignTarget.setAccountId(accountId);
    campaignTarget.setCampaignId(campaignId);
    campaignTarget.setTarget(target);

    return campaignTarget;
  }

  /**
   * example mutate campaignTargets.
   *
   * @param operation CampaignTargetOperation
   * @return CampaignTargetValues
   */
  public static List<CampaignTargetServiceValue> mutate(CampaignTargetServiceOperation operation, String action) throws Exception {

    CampaignTargetServiceMutateResponse response = ApiUtils.execute(SERVICE_NAME, action, operation, CampaignTargetServiceMutateResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example campaigns set request.
   *
   * @param campaignTargets List<CampaignTarget>
   * @return List<CampaignTarget>
   */
  public static List<CampaignTarget> createExampleSetRequest(List<CampaignTarget> campaignTargets) {
    // create operands
    List<CampaignTarget> operands = new ArrayList<>();

    for (CampaignTarget campaignTarget : campaignTargets) {
      // set target
      CampaignTargetServiceTarget target = new CampaignTargetServiceTarget();
      target.setTargetType(campaignTarget.getTarget().getTargetType());
      target.setTargetId(campaignTarget.getTarget().getTargetId());

      CampaignTarget scheduleOperand = new CampaignTarget();
      scheduleOperand.setAccountId(campaignTarget.getAccountId());
      scheduleOperand.setCampaignId(campaignTarget.getCampaignId());
      scheduleOperand.setBidMultiplier(0.5);
      scheduleOperand.setTarget(target);

      operands.add(scheduleOperand);
      break;
    }

    // set PlatformTarget for SMART_PHONE
    CampaignTargetServicePlatformTarget smartPhone = new CampaignTargetServicePlatformTarget();
    smartPhone.setPlatformType(CampaignTargetServicePlatformType.SMART_PHONE);

    CampaignTargetServiceTarget targetSmartPhone = new CampaignTargetServiceTarget();
    targetSmartPhone.setTargetType(CampaignTargetServiceTargetType.PLATFORM);
    targetSmartPhone.setPlatformTarget(smartPhone);

    CampaignTarget smartPhoneOprand = new CampaignTarget();
    smartPhoneOprand.setAccountId(campaignTargets.get(0).getAccountId());
    smartPhoneOprand.setCampaignId(campaignTargets.get(0).getCampaignId());
    smartPhoneOprand.setBidMultiplier(0.1);
    smartPhoneOprand.setTarget(targetSmartPhone);

    operands.add(smartPhoneOprand);

    // set PlatformTarget for TABLET
    CampaignTargetServicePlatformTarget tablet = new CampaignTargetServicePlatformTarget();
    smartPhone.setPlatformType(CampaignTargetServicePlatformType.TABLET);

    CampaignTargetServiceTarget targetTablet = new CampaignTargetServiceTarget();
    targetTablet.setTargetType(CampaignTargetServiceTargetType.PLATFORM);
    targetTablet.setPlatformTarget(tablet);

    CampaignTarget tabletOperand = new CampaignTarget();
    tabletOperand.setAccountId(campaignTargets.get(0).getAccountId());
    tabletOperand.setCampaignId(campaignTargets.get(0).getCampaignId());
    tabletOperand.setBidMultiplier(0.1);
    tabletOperand.setTarget(targetTablet);

    operands.add(tabletOperand);

    // set PlatformTarget for DESKTOP
    CampaignTargetServicePlatformTarget desktop = new CampaignTargetServicePlatformTarget();
    smartPhone.setPlatformType(CampaignTargetServicePlatformType.DESKTOP);

    CampaignTargetServiceTarget targetDesktop = new CampaignTargetServiceTarget();
    targetDesktop.setTargetType(CampaignTargetServiceTargetType.PLATFORM);
    targetDesktop.setPlatformTarget(desktop);

    CampaignTarget desktopOperand = new CampaignTarget();
    desktopOperand.setAccountId(campaignTargets.get(0).getAccountId());
    desktopOperand.setCampaignId(campaignTargets.get(0).getCampaignId());
    desktopOperand.setBidMultiplier(0.1);
    desktopOperand.setTarget(targetDesktop);

    operands.add(desktopOperand);

    return operands;
  }

  /**
   * Sample Program for CampaignTargetService GET.
   *
   * @param selector CampaignTargetSelector
   * @return CampaignTargetValues
   */
  public static List<CampaignTargetServiceValue> get(CampaignTargetServiceSelector selector, String action) throws Exception {

    CampaignTargetServiceGetResponse response = ApiUtils.execute(SERVICE_NAME, action, selector, CampaignTargetServiceGetResponse.class);

    // Response
    return response.getRval().getValues();
  }

  /**
   * example get request.
   *
   * @param accountId long
   * @param campaignTargets CampaignTarget
   * @return CampaignTargetSelector
   */
  public static CampaignTargetServiceSelector buildExampleGetRequest(long accountId, List<CampaignTarget> campaignTargets) {
    // Set Selector
    CampaignTargetServiceSelector selector = new CampaignTargetServiceSelector();
    selector.setAccountId(accountId);

    List<Long> campaignIds = new ArrayList<>();
    List<String> targetIds = new ArrayList<>();
    for (CampaignTarget campaignTarget : campaignTargets) {
      campaignIds.add(campaignTarget.getCampaignId());
      targetIds.add(campaignTarget.getTarget().getTargetId());
    }
    selector.setCampaignIds(campaignIds);
    selector.setTargetIds(targetIds);

    selector.setTargetTypes(Arrays.asList(//
      CampaignTargetServiceTargetType.LOCATION, //
      CampaignTargetServiceTargetType.SCHEDULE, //
      CampaignTargetServiceTargetType.NETWORK, //
      CampaignTargetServiceTargetType.PLATFORM //
    ));
    selector.setExcludedType(CampaignTargetServiceExcludedType.INCLUDED);
    selector.setPlatformTypes(Arrays.asList( //
      CampaignTargetServicePlatformType.SMART_PHONE, //
      CampaignTargetServicePlatformType.TABLET, //
      CampaignTargetServicePlatformType.DESKTOP //
    ));

    // Set Paging
    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }

  /**
   * check & create upper service object.
   *
   * @return ValuesHolder
   * @throws Exception throw exception
   */
  private static ValuesHolder setup() throws Exception {
    return CampaignServiceSample.create();
  }

  /**
   * cleanup service object.
   *
   * @param valuesHolder ValuesHolder
   * @throws Exception throw exception
   */
  public static void cleanup(ValuesHolder valuesHolder) throws Exception {
    CampaignServiceSample.cleanup(valuesHolder);
  }

}
