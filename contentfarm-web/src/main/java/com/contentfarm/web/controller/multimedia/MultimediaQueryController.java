package com.contentfarm.web.controller.multimedia;

import com.contentfarm.inputport.multimedia.web.IMultimediaWebDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/multimedia")
public class MultimediaQueryController {

    private final IMultimediaWebDomainService multimediaWebDomainService;

    @GetMapping(value = "/image/{fileName}", produces = "image/png+jpeg")
    public byte[] getMultimediaImageByFileName(@PathVariable String fileName) {
        return multimediaWebDomainService.downloadImageAsync(fileName).block();
    }
}
