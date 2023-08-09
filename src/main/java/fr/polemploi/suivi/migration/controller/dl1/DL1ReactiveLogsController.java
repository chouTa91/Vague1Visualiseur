package fr.polemploi.suivi.migration.controller.dl1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.monitor.PEFileSystemListener;
import fr.polemploi.suivi.migration.monitor.event.Event;
import fr.polemploi.suivi.migration.monitor.event.FolderChangeEvent;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/dl1/logs/reactor")
public class DL1ReactiveLogsController implements ApplicationListener<FolderChangeEvent> {

	@Autowired
	private PathDispenser pathDispenser;

	private final PEFileSystemListener pEFileSystemListenerDl1;

	DL1ReactiveLogsController(PEFileSystemListener pEFileSystemListener) {
		this.pEFileSystemListenerDl1 = pEFileSystemListener;
	}

	private final SubscribableChannel subscribableChannel = MessageChannels.publishSubscribe().get();

	@PostConstruct
	void init() {
		//this.pEFileSystemListenerDl1.start(this.pathDispenser.getDL1NasFolder());
	}

	@Override
	public void onApplicationEvent(FolderChangeEvent event) {
		this.subscribableChannel.send(new GenericMessage<>(event));
	}

	@GetMapping(path = "/controlelogs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Event> getLogsFolderWatch() {

		return null;

		// Flux.create(sink -> {
		// MessageHandler handler = message -> sink
		// .next(FolderChangeEvent.class.cast(message.getPayload()).getEvent());
		//
		// sink.onCancel(() -> this.subscribableChannel.unsubscribe(handler));
		// this.subscribableChannel.subscribe(handler);
		//
		// }, FluxSink.OverflowStrategy.LATEST);

	}
}
