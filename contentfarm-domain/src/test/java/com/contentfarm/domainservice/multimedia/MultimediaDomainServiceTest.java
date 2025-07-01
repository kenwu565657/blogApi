package com.contentfarm.domainservice.multimedia;

import com.contentfarm.outputport.multimedia.file.IMultimediaFileQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MultimediaDomainServiceTest {
    private final MultimediaDomainService multimediaDomainService = new MultimediaDomainService(new IMultimediaFileQueryServiceSpy());
    private final byte[] defaultByteArray = {69, 121, 101, 45, 62, 118, 101, 114, 61, 101, 98};

    @Test
    void downloadImageAsync() {
        var fileContentByteArrayMono = multimediaDomainService.downloadImageAsync("test.jpg");
        StepVerifier.create(fileContentByteArrayMono)
                .expectNextMatches(fileContentByte -> Arrays.equals(defaultByteArray, fileContentByte))
                .expectComplete()
                .verify();
    }

    private static class IMultimediaFileQueryServiceSpy implements IMultimediaFileQueryService {
        private final byte[] defaultByteArray = {69, 121, 101, 45, 62, 118, 101, 114, 61, 101, 98};

        @Override
        public Mono<byte[]> downloadImageByImageFileNameAsync(String imageName) {
            return Mono.just(defaultByteArray);
        }
    }
}