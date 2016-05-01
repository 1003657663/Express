package extrace.user.search;

/**
 * Created by songchao on 16/5/1.
 */
public interface SearchExpressView {
    void getBundle();
    void init();
    void onError(String errorMessage);
    void getSearchInfo();
    void onRequestSuccess();
}
