package org.example.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventsApplication {
    /*
        Разработать REST API для управления событиями (мероприятиями) с возможностью:
            - создания новых событий с указанием названия, описания и даты проведения
            - просмотра списка всех событий
            - удаления событий по идентификатору
    */
    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

}
