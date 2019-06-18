package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.util.Log;

import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RssParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.toptas.rssconverter.RssConverterFactory;
import me.toptas.rssconverter.RssFeed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.Constants.DELAY;

public class GlobesPresenter implements GlobesContract.Presenter {
    private GlobesContract.View mView;
    private RssService service;
    private String mQuery;
    private Timer mTimer;
    private TimerTask timerTask;
    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String mQuery) {
        this.mQuery = mQuery;
    }

    public interface RssService {
        @GET("webservice/rss/rssfeeder.asmx/FeederNode{iID}")
        Call<RssFeed> getRss(@Path("iID") String iID);
    }

    public GlobesPresenter(GlobesContract.View view) {
        mView = view;
        mView.setPresenter(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://globes.co.il")
                .addConverterFactory(RssConverterFactory.Companion.create())
                .build();
        service = retrofit.create(RssService.class);
    }

    public void stopTimer(){
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void getModels(final String query) {
        mQuery = query;
        mTimer = new Timer();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                final Call<RssFeed> repos =  service.getRss(mQuery);
                repos.enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        Log.i("TAG", "SUCCESS");
                        /*TODO:  PARSING IS NOW SECCUESSFULLY PARSING IS NOW SECCUESSFULLY*/
                        RssParser rssParser = new RssParser(response.toString());
                        rssParser.parse();
                        // ArrayList<RssParser.RssItem> rssFeed = rssParser.getFeed().getFeedItems();
                        //Simulation
                        ArrayList<RssParser.RssItem> rssFeed = new ArrayList<>();
                        RssParser.RssItem rssItem = new RssParser.RssItem();
                        rssItem.title = "item 1";
                        rssItem.description = "description first item";
                        rssItem.link = "https://www.globes.co.il/news/article.aspx?did=1001289542#utm_source=RSS";
                        rssFeed.add(rssItem);
                        rssItem = new RssParser.RssItem();
                        rssItem.title = "item 2";
                        rssItem.description = "description second item";
                        rssItem.link = "https://www.globes.co.il/news/article.aspx?did=1001289451#utm_source=RSS";
                        rssFeed.add(rssItem);
                        mView.getSuccess(rssFeed);
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        Log.i("TAG", "FAIL");

                    }
                });
            }
        };
        mTimer.schedule(timerTask, 0, DELAY);

    }

}
