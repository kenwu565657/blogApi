package com.contentfarm.inputport.multimedia.web;

import reactor.core.publisher.Mono;

public interface IMultimediaWebDomainService {
    Mono<byte[]> downloadImageAsync(String imageFileName);
}
