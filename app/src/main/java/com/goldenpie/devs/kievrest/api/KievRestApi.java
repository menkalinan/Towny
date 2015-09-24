package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import retrofit.http.GET;
import retrofit.http.Path;

public interface KievRestApi {
    @GET(Constants.NEWS_LINK)
    void getNews(BaseCallback<NewsLoadedEvent> callback);

    @GET(Constants.CONCRETE_SELECTION_LINK)
    void getSelection(@Path("id") String id, BaseCallback<SelectionLoadedEvent> callback);

    @GET(Constants.SELECTION_LINK)
    void getSelections(BaseCallback<SelectionsLoadedEvent> callback);
}
