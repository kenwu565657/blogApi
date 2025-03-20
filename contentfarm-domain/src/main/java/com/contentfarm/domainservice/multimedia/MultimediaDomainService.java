package com.contentfarm.domainservice.multimedia;

import com.contentfarm.inputport.multimedia.web.IMultimediaWebDomainService;
import com.contentfarm.outputport.multimedia.file.IMultimediaFileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MultimediaDomainService implements IMultimediaWebDomainService {
    private final IMultimediaFileQueryService multimediaFileQueryService;

    @Override
    public Mono<byte[]> downloadImageAsync(String imageFileName) {
        return multimediaFileQueryService.downloadImageByImageFileNameAsync(imageFileName);
    }
}
