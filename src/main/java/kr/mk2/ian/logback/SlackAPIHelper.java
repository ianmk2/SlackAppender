package kr.mk2.ian.logback;

import ch.qos.logback.classic.Level;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SlackAPIHelper {
    private String webHookUrl;
    private String channel;
    private String userName;
    private String iconEmoji;

    public SlackAPIHelper(String webHookUrl, String channel, String userName, String iconEmoji) {
        this.webHookUrl = webHookUrl;
        this.channel = channel;
        this.userName = userName;
        this.iconEmoji = iconEmoji;

    }

    public String makePayload(Level level, String msg) {
        JSONObject payload = new JSONObject();
        JSONArray ary = new JSONArray();
        payload.put("attachments", ary);
        JSONObject attachment = new JSONObject();
        ary.put(attachment);

        if (channel != null && channel.length() != 0)
            payload.put("channel", channel);
        if (userName != null && userName.length() != 0)
            payload.put("username", userName);
        if (iconEmoji != null && iconEmoji.length() != 0) {
            payload.put("icon_emoji", iconEmoji.endsWith(":") ? iconEmoji : iconEmoji + ":");
        }
        String color = null;
        if (level == Level.TRACE) {
            color = "#6f6d6d";
        } else if (level == Level.DEBUG) {
            color = "#b5dae9";
        } else if (level == Level.INFO) {
            color = "good";
        } else if (level == Level.WARN) {
            color = "warning";
        } else if (level == Level.ERROR) {
            color = "danger";
        }
        if (color != null)
            attachment.put("color", color);

        attachment.append("text", msg);
        return payload.toString();

    }

    public void send(Level level, String msg) throws IOException {
        String payload = makePayload(level, msg);

        final URL url = new URL(webHookUrl);

        final StringWriter w = new StringWriter();
        w.append("payload=").append(URLEncoder.encode(payload, "UTF-8"));

        final byte[] bytes = w.toString().getBytes("UTF-8");
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        final OutputStream os = conn.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();

    }

    public void trace(String msg) {
        try {
            send(Level.TRACE, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void debug(String msg) {
        try {
            send(Level.TRACE, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String msg) {
        try {
            send(Level.TRACE, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warn(String msg) {
        try {
            send(Level.TRACE, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void error(String msg) {
        try {
            send(Level.TRACE, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
