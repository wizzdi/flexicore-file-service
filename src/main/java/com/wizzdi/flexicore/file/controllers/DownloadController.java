/*******************************************************************************
 *  Copyright (C) FlexiCore, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Avishay Ben Natan And Asaf Ben Natan, October 2015
 ******************************************************************************/
package com.wizzdi.flexicore.file.controllers;

import com.flexicore.annotations.IOperation;
import com.flexicore.annotations.IOperation.Access;
import com.flexicore.annotations.OperationsInside;
import com.flexicore.security.SecurityContextBase;
import com.wizzdi.flexicore.boot.base.interfaces.Plugin;
import com.wizzdi.flexicore.file.model.ZipFile;
import com.wizzdi.flexicore.file.request.ZipAndDownloadRequest;
import com.wizzdi.flexicore.file.service.FileResourceService;
import com.wizzdi.flexicore.file.service.ZipFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fileResource")
@Extension
@Tag(name = "fileResource")
@OperationsInside
public class DownloadController implements Plugin {

    private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @Autowired
    private FileResourceService fileResourceService;

    @Autowired
    private ZipFileService zipFileService;


    /**
     * download a file by its fileResource ID
     *
     * @param authenticationkey authentication key
     * @param id id
     * @param securityContextBase security context
     * @param offset offset to start reading from
     * @param req request context
     * @param size length to read
     * @return binary file data
     */
    @GetMapping("{authenticationkey}/{id}")
    @IOperation(access = Access.allow, Name = "downloadFile", Description = "downloads file by its fileResource ID")
    public ResponseEntity<Resource> download(@PathVariable(value = "authenticationkey",required = false) String authenticationkey,
                                             @Parameter(description = "id of the FileResource Object to Download")
                             @RequestHeader(value = "offset",required = false) long offset,
                                             @RequestHeader(value = "size",required = false) long size,
                                             @PathVariable("id") String id, HttpServletRequest req, @RequestAttribute SecurityContextBase securityContextBase) {
        return fileResourceService.download(offset, size, id, req.getRemoteAddr(), securityContextBase);

    }




    /**
     * zips list of fileResources and sends it
     *
     * @param authenticationkey authentication key
     * @param zipAndDownload zip and download request
     * @param securityContextBase security context
     * @return binary zip data
     */
    @PostMapping("zipAndDownload")
    @Operation(summary = "zipAndDownload", description = "Mass Download")
    public ResponseEntity<Resource> zipAndDownload(@RequestHeader("authenticationKey") String authenticationkey,
                                   ZipAndDownloadRequest zipAndDownload, @RequestAttribute SecurityContextBase securityContextBase) {
        zipFileService.validate(zipAndDownload, securityContextBase);
        ZipFile zipFile = zipFileService.zipAndDownload(zipAndDownload, securityContextBase);

        return fileResourceService.prepareFileResourceForDownload(zipFile, zipAndDownload.getOffset(), 0);


    }

    @PostMapping("getOrCreateZipFile")
    @Operation(summary = "getOrCreateZipFile", description = "getOrCreateZipFile")
    public ZipFile getOrCreateZipFile(@RequestHeader("authenticationKey") String authenticationkey,
                                      ZipAndDownloadRequest zipAndDownload, @RequestAttribute SecurityContextBase securityContextBase) {
        zipFileService.validate(zipAndDownload, securityContextBase);
        return zipFileService.zipAndDownload(zipAndDownload, securityContextBase);
    }






}
