package com.viettel.nfv.vdashboardx.communication;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

public interface ISSEController {

    /**
     * 1.7. Send Notifications
     *
     * @return
     */
    @GetMapping(value = { "${sse.notifications}" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    default Flux<ServerSentEvent<String>> sendNotificationSseApi() {
        return sendNotificationSse();
    }

    Flux<ServerSentEvent<String>> sendNotificationSse();

}
