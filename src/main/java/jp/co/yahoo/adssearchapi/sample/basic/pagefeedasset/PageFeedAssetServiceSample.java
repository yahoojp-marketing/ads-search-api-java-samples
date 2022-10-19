package jp.co.yahoo.adssearchapi.sample.basic.pagefeedasset;

import jp.co.yahoo.adssearchapi.sample.repository.ValuesRepositoryFacade;
import jp.co.yahoo.adssearchapi.sample.util.ApiUtils;
import jp.co.yahoo.adssearchapi.sample.util.ValuesHolder;
import jp.co.yahoo.adssearchapi.v9.api.PageFeedAssetSetServiceApi;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSet;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSetServiceOperation;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSetServiceSelector;
import jp.co.yahoo.adssearchapi.v9.model.PageFeedAssetSetServiceValue;

import java.util.ArrayList;
import java.util.List;

public class PageFeedAssetServiceSample {
  private static final PageFeedAssetSetServiceApi pageFeedAssetSetService = new PageFeedAssetSetServiceApi(ApiUtils.getYahooJapanAdsApiClient());


  /**
   * main method for PageFeedAssetServiceSample
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
      // PageFeedAssetSetService ADD
      // =================================================================
      // create request.
      PageFeedAssetSetServiceOperation addPageFeedAssetSetServiceOperation = buildExampleMutateRequest(accountId, new ArrayList<PageFeedAssetSet>() {{
        add(createExamplePgeFeedAssetSet(accountId));
      }});

      // run
      List<PageFeedAssetSetServiceValue> pageFeedAssetSetServiceValue = pageFeedAssetSetService.pageFeedAssetSetServiceAddPost(addPageFeedAssetSetServiceOperation).getRval().getValues();
      valuesRepositoryFacade.getValuesHolder().setPageFeedAssetSetServiceValueList(pageFeedAssetSetServiceValue);

      // =================================================================
      // PageFeedAssetSetService SET
      // =================================================================
      // create request.
      PageFeedAssetSetServiceOperation setPageFeedAssetSetServiceOperation = buildExampleMutateRequest(accountId, createExampleSetRequest(valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().getPageFeedAssetSet()));

      // run
      pageFeedAssetSetService.pageFeedAssetSetServiceSetPost(setPageFeedAssetSetServiceOperation);

      // =================================================================
      // PageFeedAssetSetService GET
      // =================================================================
      // create request.
      PageFeedAssetSetServiceSelector pageFeedAssetSetServiceSelector = buildExampleGetRequest(accountId, valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().getFeedIds());

      // run
      pageFeedAssetSetService.pageFeedAssetSetServiceGetPost(pageFeedAssetSetServiceSelector);

      // =================================================================
      // PageFeedAssetSetService REMOVE
      // =================================================================
      // create request.
      PageFeedAssetSetServiceOperation removePageFeedAssetSetServiceOperation = buildExampleMutateRequest(accountId, valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().getPageFeedAssetSet());

      // run
      pageFeedAssetSetService.pageFeedAssetSetServiceRemovePost(removePageFeedAssetSetServiceOperation);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static ValuesHolder create() throws Exception {

    ValuesHolder valuesHolder = new ValuesHolder();
    long accountId = ApiUtils.ACCOUNT_ID;

    PageFeedAssetSetServiceOperation addFeedOperation = buildExampleMutateRequest(accountId, new ArrayList<PageFeedAssetSet>() {{
      add(createExamplePgeFeedAssetSet(accountId));
    }});

    // Run
    List<PageFeedAssetSetServiceValue> pageFeedAssetSetServiceOperationValue = pageFeedAssetSetService.pageFeedAssetSetServiceAddPost(addFeedOperation).getRval().getValues();
    valuesHolder.setPageFeedAssetSetServiceValueList(pageFeedAssetSetServiceOperationValue);
    return valuesHolder;
  }

  public static void cleanup(ValuesHolder valuesHolder) throws Exception {

    long accountId = ApiUtils.ACCOUNT_ID;
    if (valuesHolder.getPageFeedAssetSetServiceValueList().size() == 0) {
      return;
    }
    ValuesRepositoryFacade valuesRepositoryFacade = new ValuesRepositoryFacade(valuesHolder);

    PageFeedAssetSetServiceOperation removePageFeedAssetSetOperation =
        buildExampleMutateRequest(accountId, valuesRepositoryFacade.getPageFeedAssetSetValuesRepository().getPageFeedAssetSet());

    pageFeedAssetSetService.pageFeedAssetSetServiceRemovePost(removePageFeedAssetSetOperation);
  }

  /**
   * example mutate request.
   */
  public static PageFeedAssetSetServiceOperation buildExampleMutateRequest(long accountId, List<PageFeedAssetSet> operand) {
    PageFeedAssetSetServiceOperation operation = new PageFeedAssetSetServiceOperation();
    operation.setAccountId(accountId);
    operation.setOperand(operand);

    return operation;
  }

  /**
   * example PageFeedAssetSet request.
   *
   * @param accountId long
   * @return PageFeedAssetSet
   */
  private static PageFeedAssetSet createExamplePgeFeedAssetSet(long accountId) {

    PageFeedAssetSet pageFeedAssetSet = new PageFeedAssetSet();
    pageFeedAssetSet.setAccountId(accountId);
    pageFeedAssetSet.setPageFeedAssetSetName("SamplePageFeedAssetSet_" + ApiUtils.getCurrentTimestamp());
    pageFeedAssetSet.setDomain("https://www.yahoo.co.jp");

    return pageFeedAssetSet;
  }

  private static List<PageFeedAssetSet> createExampleSetRequest(List<PageFeedAssetSet> feeds) {

    List<PageFeedAssetSet> operands = new ArrayList<>();

    for (PageFeedAssetSet feed : feeds) {
      // Set Operand
      PageFeedAssetSet operand = new PageFeedAssetSet();
      operand.setAccountId(feed.getAccountId());
      operand.setPageFeedAssetSetId(feed.getPageFeedAssetSetId());
      operand.setPageFeedAssetSetName(feed.getPageFeedAssetSetName());
      operand.setDomain(feed.getDomain());
      operands.add(operand);

    }

    return operands;
  }

  /**
   * example get request.
   *
   * @param accountId     long
   * @param feedIds List<Long>
   * @return PageFeedAssetSetServiceSelector
   */
  public static PageFeedAssetSetServiceSelector buildExampleGetRequest(long accountId, List<Long> feedIds) {
    // Set Selector
    PageFeedAssetSetServiceSelector selector = new PageFeedAssetSetServiceSelector();
    selector.setAccountId(accountId);

    if (feedIds.size() > 0) {
      selector.pageFeedAssetSetIds(feedIds);
    }

    selector.setStartIndex(1);
    selector.setNumberResults(20);

    return selector;
  }
}
