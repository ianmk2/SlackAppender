Slack logback appender
======================

slack appender for logback


## Sample logback.xml configuration:
```
<configuration>
    <appender name="slack" class="kr.mk2.ian.logback.SlackAppender">
        <webHookUrl>
            https://hooks.slack.com/services/T0ASD3EE/B0ASDD2BZ/mmnINASDAASDA31Af1g
        </webHookUrl>
        <iconEmoji><!--optional-->
            :ghost:
        </iconEmoji>
        <userName><!--optional-->
            Bot
        </userName>
        <channel><!--optional-->
            #CIC
        </channel>
        <pattern><!--optional-->
            @%-5level %logger{40} - %msg%n
        </pattern>
    </appender>
    ...
</configuration>
```

## License

The MIT License (MIT)