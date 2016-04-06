package com.boot.integration.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.impl.HashMapFeedInfoCache;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

@Component
public class RssUrlManager implements MessageSource<SyndEntry>, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(RssUrlManager.class);

	private FeedFetcher feedFetcher;

	@Override
	public void afterPropertiesSet() throws Exception {
		feedFetcher = new HttpURLFeedFetcher(HashMapFeedInfoCache.getInstance());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Message receive() {
		return MessageBuilder.withPayload(getFeeds()).build();
	}

	private List<SyndFeed> getFeeds() {
		List<SyndFeed> feeds = new ArrayList<>();
		try {
			feeds.add(feedFetcher.retrieveFeed(new URL("http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp")));
			feeds.add(feedFetcher.retrieveFeed(new URL("http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml")));
		} catch (Exception e) {
			logger.error("Problem while retrieving feed. {}", e);
		}
		return feeds;
	}

}
