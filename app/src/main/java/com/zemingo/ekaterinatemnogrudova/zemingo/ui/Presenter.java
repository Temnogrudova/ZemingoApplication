package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.util.Log;

import com.zemingo.ekaterinatemnogrudova.zemingo.api.Service;
import com.zemingo.ekaterinatemnogrudova.zemingo.models.ModelResponse;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.IScheduler;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class Presenter  implements Contract.Presenter {
    private Contract.View mView;
    private Disposable mDisposable;
    private IScheduler mScheduler;

    public Presenter(Contract.View view, IScheduler scheduler) {
        mView = view;
        mView.setPresenter(this);
        mScheduler = scheduler;
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void getModels(String query) {
        mDisposable = Service.getInstance().getModel(query)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui()).subscribeWith(new DisposableObserver<ModelResponse>() {
                    @Override
                    public void onNext(ModelResponse response) {
                        mView.getSuccess(response.getList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("NETWORK ERROR", "getModels() loading error");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
