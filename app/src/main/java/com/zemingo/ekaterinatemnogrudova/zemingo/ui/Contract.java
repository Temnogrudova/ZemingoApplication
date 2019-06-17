package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import com.zemingo.ekaterinatemnogrudova.zemingo.models.Model;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.BaseView;

import java.util.List;

public class Contract {
    public interface View extends BaseView<Presenter> {
        void getSuccess(List<Model> result);
    }

    interface Presenter  {
        void getModels(String query);
        void unsubscribe();
    }
}
