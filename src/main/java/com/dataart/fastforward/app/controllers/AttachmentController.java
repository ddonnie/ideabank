package com.dataart.fastforward.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Orlov on 20.12.2016.
 */

@RestController
@RequestMapping(value = "/upload/{attachmentName}.{extension}")
@ResponseStatus(HttpStatus.OK)
public class AttachmentController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping
    public Resource getAttachment(@PathVariable String attachmentName, @PathVariable String extension) {
        System.out.println(attachmentName+"."+extension);
        String uploadsDir = "/upload/";
        System.out.println(attachmentName);
        return resourceLoader.getResource(uploadsDir+attachmentName+"."+extension);
    }

}
