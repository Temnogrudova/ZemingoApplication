package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zemingo.ekaterinatemnogrudova.zemingo.R;
import com.zemingo.ekaterinatemnogrudova.zemingo.models.Model;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.SchedulerProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity   implements Contract.View {
    private Contract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new Presenter(this, new SchedulerProvider());
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getModels("something");
    }

    @Override
    public void getSuccess(List<Model> result) {

    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }
}
