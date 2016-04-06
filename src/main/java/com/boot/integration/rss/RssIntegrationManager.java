package com.boot.integration.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

@Configuration
@EnableAutoConfiguration
public class RssIntegrationManager {

	private static final Logger logger = LoggerFactory.getLogger(RssIntegrationManager.class);

	@Autowired
	private RssUrlManager rssUrlManager;

	@Bean
	@InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedRate = "3600000") )
	public RssUrlManager feedAdapter() throws MalformedURLException {
		return rssUrlManager;
	}

	@MessageEndpoint
	public static class Endpoint {
		@ServiceActivator(inputChannel = "feedChannel")
		public void log(Message<List<SyndFeed>> message) throws IOException {
			List<SyndFeed> syndFeeds = message.getPayload();
			for (SyndFeed syndFeed : syndFeeds) {
				for (SyndEntry syndEntry : syndFeed.getEntries()) {
					logger.info("rss feed infomation. author={}, title={}, link={}", syndEntry.getAuthor(), syndEntry.getTitle(), syndEntry.getLink());
				}
			}
		}

	}

}
