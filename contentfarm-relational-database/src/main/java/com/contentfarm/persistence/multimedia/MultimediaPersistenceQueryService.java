package com.contentfarm.persistence.multimedia;

import com.contentfarm.file.operation.springboot.starter.exception.FileOperationException;
import com.contentfarm.file.operation.springboot.starter.service.FileStorageService;
import com.contentfarm.outputport.multimedia.persistence.IMultimediaPersistenceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MultimediaPersistenceQueryService implements IMultimediaPersistenceQueryService {
    private final FileStorageService fileStorageService;

    @Override
    public Mono<byte[]> downloadAsync(String directory, String imageName) {
        if (!isEndWithExpectedFileExtension(imageName)) {
            return Mono.error(FileOperationException.ofFileNameNotExist());
        }
        return Mono.fromFuture(fileStorageService.downloadFileAsync(directory, imageName))
                .handle((asyncOperationResult, sink) -> {
                    if (asyncOperationResult.isSuccess()) {
                        sink.next(asyncOperationResult.getData());
                        return;
                    }
                    try {
                        sink.error(asyncOperationResult.getFailCause());
                    } catch (Throwable e) {
                        sink.error(FileOperationException.ofUnexpectedError());
                    }
                });
    }

    private boolean isEndWithExpectedFileExtension(String fileName) {
        Pattern pattern = Pattern.compile(".*\\.(?:jpg|gif|png)$");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}
