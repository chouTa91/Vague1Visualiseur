package fr.polemploi.suivi.migration.controller.db2;

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
@RequestMapping("/db2/reactor")
public class DB2ReactiveController implements ApplicationListener<FolderChangeEvent> {

	@Autowired
	private PathDispenser pathDispenser;

	private final PEFileSystemListener pEFileSystemListenerDl1;

	DB2ReactiveController(PEFileSystemListener pEFileSystemListener) {
		this.pEFileSystemListenerDl1 = pEFileSystemListener;
	}

	private final SubscribableChannel subscribableChannel = MessageChannels.publishSubscribe().get();

	@PostConstruct
	void init() {
		//this.pEFileSystemListenerDl1.start(this.pathDispenser.getDB2RootFolder());
	}

	@GetMapping(path = "/controle", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Event> getFolderWatch() {

		return null;
		// Flux.create(sink -> {
		// MessageHandler handler = message -> sink
		// .next(FolderChangeEvent.class.cast(message.getPayload()).getEvent());
		//
		// sink.onCancel(() -> this.subscribableChannel.unsubscribe(handler));
		// this.subscribableChannel.subscribe(handler);
		//
		// },FluxSink.OverflowStrategy.LATEST);

	}

	@Override
	public void onApplicationEvent(FolderChangeEvent event) {
		this.subscribableChannel.send(new GenericMessage<>(event));
	}

}
