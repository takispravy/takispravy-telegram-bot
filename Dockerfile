FROM openjdk:8

RUN mkdir -p /usr/src/takispravy-telegram-bot
RUN mkdir -p /usr/app

COPY build/distributions/* /usr/src/takispravy-telegram-bot/

RUN unzip /usr/src/takispravy-telegram-bot/takispravy-telegram-bot-*.zip -d /usr/app/
RUN ln -s /usr/app/takispravy-telegram-bot-* /usr/app/takispravy-telegram-bot

WORKDIR /usr/app/takispravy-telegram-bot

ENTRYPOINT ["./bin/takispravy-telegram-bot"]
CMD []
