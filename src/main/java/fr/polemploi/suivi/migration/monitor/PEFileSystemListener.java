package fr.polemploi.suivi.migration.monitor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.polemploi.suivi.migration.api.path.PathDispenser;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.monitor.event.FolderChangeEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
@Configuration
public class PEFileSystemListener {

	private static final Logger logger = LoggerFactory.getLogger(PEFileSystemListener.class);

	private Sinks.Many<String> eventSink = Sinks.many().multicast().onBackpressureBuffer();
	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	private PathDispenser pathDispenser;

	PEFileSystemListener(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	// @Bean
	// ApplicationEventMulticaster applicationEventMulticaster() {
	// 	SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
	// 	eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
	// 	eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
	// 	return eventMulticaster;
	// }

	// @SuppressWarnings("unchecked")
	// public void start(String folderName) {
	// 	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	// 	singleThreadExecutor.execute(() -> {
	// 		try {
	// 			PEFileSystemListener.logger.info("Folder watch service started");

	// 			WatchService watchService = FileSystems.getDefault().newWatchService();
	// 			Path folder = Paths.get(folderName);
	// 			folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
	// 					StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

	// 			WatchKey key;
	// 			while ((key = watchService.take()) != null) {
	// 				for (WatchEvent<?> event : key.pollEvents()) {
	// 					WatchEvent.Kind<?> kind = event.kind();

	// 					if (kind == StandardWatchEventKinds.OVERFLOW) {
	// 						continue;
	// 					}

	// 					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
	// 					Path path = folder.resolve(pathEvent.context());

	// 					PEFileSystemListener.logger.info("Folder change event is published: {}", pathEvent);
	// 					this.eventPublisher.publishEvent(new FolderChangeEvent(this, pathEvent, path));
	// 				}

	// 				boolean valid = key.reset();
	// 				if (!valid) {
	// 					break;
	// 				}
	// 			}

	// 			watchService.close();
	// 			PEFileSystemListener.logger.info("Folder watch service finished");
	// 		} catch (Exception e) {
	// 			PEFileSystemListener.logger.error("Folder watch service failed", e);
	// 		}
	// 	});
	// }

	@PostConstruct
	public void init1() throws IOException {
		System.out.println("create watchService of newWatchService");
		PEFileSystemListener.logger.info("create watchService of newWatchService");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		System.out.println("create keymap of wathkey");
		Map<WatchKey,Path> keyMap = new HashMap<>();
		Path logsFolderRoot = Paths.get(this.pathDispenser.getRootFolder()+"\\fichierbouchon\\");
		Path logsFolderDL1 = Paths.get(this.pathDispenser.getDL1NasFolder());
		Path logsFolderDB2 = Paths.get(this.pathDispenser.getDB2RootFolder());
		Path logFolderConversion = Paths.get(this.pathDispenser.getDB2ConvertOracle());
		System.out.println("create registre of wathkey");
		keyMap.put(logsFolderRoot.register(watchService,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE),logsFolderRoot
		);
		keyMap.put(logsFolderDL1.register(watchService,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE),logsFolderDL1
		);
		keyMap.put(logsFolderDB2.register(watchService,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE),logsFolderDB2
		);

		keyMap.put(logFolderConversion.register(watchService,
				StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE),logFolderConversion
		);

		new Thread(() -> {
			WatchKey watchKey;
			try {
				do{
					watchKey = watchService.take();
					System.out.println(" take ");
					Path eventDir  = keyMap.get(watchKey);
					PEFileSystemListener.logger.info("Folder change event is published: {}", eventDir);
					for (WatchEvent<?> event:watchKey.pollEvents()){
						WatchEvent.Kind<?> kind  = event.kind();
						Path eventPath = (Path)event.context();
						System.out.println(eventDir  + " :"+ kind+ " : "+ eventPath);
						eventSink.tryEmitNext(eventDir  + " :"+ kind+ " : "+ eventPath);
					}
				}while(watchKey.reset());
				watchService.close();
				PEFileSystemListener.logger.info("Folder watch service finished");
			} catch (InterruptedException e) {
				PEFileSystemListener.logger.error("Folder watch service failed", e);
				eventSink.tryEmitComplete();
			} catch (IOException e) {
				PEFileSystemListener.logger.error("Folder watch service failed", e);
                throw new RuntimeException(e);
            }
        }).start();
	}
	public Flux<String> getEventFlux() {
		return eventSink.asFlux();
	}
}
