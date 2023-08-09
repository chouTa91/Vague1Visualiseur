package fr.polemploi.suivi.migration.controller;

import fr.polemploi.suivi.migration.monitor.PEFileSystemListener;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flux/reactor")
public class EventFluxController {
    private final PEFileSystemListener pEFileSystemListener;

    public EventFluxController(PEFileSystemListener pEFileSystemListenerDl1) {
        this.pEFileSystemListener = pEFileSystemListenerDl1;
    }

    @GetMapping(value = "/changements", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> getLogEvents() {
        return pEFileSystemListener.getEventFlux()
                .map(event -> ServerSentEvent.<String>builder()
                        .data(event)
                        .build()
                );
    }
}
