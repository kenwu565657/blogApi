package com.contentfarm.outputport.multimedia.file;

import reactor.core.publisher.Mono;

public interface IMultimediaFileQueryService {
    Mono<byte[]> downloadImageByImageFileNameAsync(String imageName);
}
