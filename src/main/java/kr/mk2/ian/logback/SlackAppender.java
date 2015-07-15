package kr.mk2.ian.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class SlackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private static final String DEFAULT_LAYOUT_PATTERN = "%d{\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\",UTC} %-5level [%thread] %logger: %m%n";


    private String webHookUrl;
    private String channel;
    private String userName;
    private String iconEmoji;
    private String pattern = DEFAULT_LAYOUT_PATTERN;
    private PatternLayout patternLayout;
    private SlackAPIHelper apiHelper;

    @Override
    public void start() {
        initPatternLayout();
        if (apiHelper == null) {
            apiHelper = new SlackAPIHelper(webHookUrl, channel, userName, iconEmoji);
        }
        if (!patternLayout.isStarted())
            patternLayout.start();
        if (webHookUrl == null)
            addError("webHookUrl must not be null");
        super.start();
    }

    public void initPatternLayout() {
        if (patternLayout == null) {
            patternLayout = new PatternLayout();
            patternLayout.setPattern(getPattern());
        }
        Context context = patternLayout.getContext();
        if (context == null) {
            patternLayout.setContext(getContext());
        }
    }

    @Override
    public void stop() {
        if (patternLayout != null && patternLayout.isStarted()) {
            patternLayout.stop();
        }
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            if (webHookUrl == null) return;
            apiHelper.send(eventObject.getLevel(), patternLayout.doLayout(eventObject));
        } catch (Exception e) {
            addError("Error logging to slack", e);
        }
    }


    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
