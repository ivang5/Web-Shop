package com.ivang.webshop.controller;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ivang.webshop.dto.DetailedDescriptionDTO;
import com.ivang.webshop.entity.DetailedDescription;
import com.ivang.webshop.service.FileServiceInterface;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "shop/files")
@RequiredArgsConstructor
public class FileController {
    
    private final FileServiceInterface fileService;

    @GetMapping
    public ResponseEntity<List<DetailedDescriptionDTO>> getAllFiles() {
        return new ResponseEntity<List<DetailedDescriptionDTO>>(fileService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DetailedDescription> getFileById(@PathVariable("id") Long id) {
		DetailedDescription dd = fileService.findOne(id);
		
		if(dd == null) {
			return new ResponseEntity<DetailedDescription>(HttpStatus.NOT_FOUND);
		}

        DetailedDescription newDd = dd;
        
        if(dd.getProduct() == null) {
            newDd = new DetailedDescription(id, dd.getName(), dd.getType(), dd.getData());
        }
		
		return new ResponseEntity<DetailedDescription>(newDd, HttpStatus.OK);
	}

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        DetailedDescription dd = fileService.findOne(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dd.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dd.getName() + "\"")
                .body(new ByteArrayResource(dd.getData()));
    }

    @PostMapping
    public ResponseEntity<DetailedDescription> uploadFile(@RequestParam MultipartFile file) {
        try {
            return new ResponseEntity<DetailedDescription>(fileService.save(file), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<DetailedDescription>(HttpStatus.BAD_REQUEST);
        }
    }
}
