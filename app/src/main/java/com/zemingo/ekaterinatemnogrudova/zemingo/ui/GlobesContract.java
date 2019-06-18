package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import com.zemingo.ekaterinatemnogrudova.zemingo.utils.BaseView;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RssParser;

import java.util.List;

public class GlobesContract {
    public interface View extends BaseView<Presenter> {
        void getSuccess(List<RssParser.RssItem> result);
    }

    interface Presenter  {
        void getModels(String query);
        void setQuery(String currentID);
        void stopTimer();

    }
}
