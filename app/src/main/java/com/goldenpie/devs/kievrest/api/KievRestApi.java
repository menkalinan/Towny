package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import retrofit.http.GET;

public interface KievRestApi {
    @GET(Constants.NEWS_LINK)
    void getNews(BaseCallback<NewsLoadedEvent> callback);
}
