package com.ivang.webshop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ivang.webshop.dto.DetailedDescriptionDTO;
import com.ivang.webshop.entity.DetailedDescription;
import com.ivang.webshop.lucene.indexing.ProductIndexer;
import com.ivang.webshop.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class FileService implements FileServiceInterface {

    private final FileRepository fileRepository;
    private final ProductIndexer productIndexer;

    @Override
    public DetailedDescription findOne(Long id) {
        log.info("Fetching file {}", id);
        return fileRepository.getById(id);
    }
    
    @Override
    public List<DetailedDescriptionDTO> findAll() {
        log.info("Fetching all files");
        List<DetailedDescriptionDTO> ddDTOs = new ArrayList<DetailedDescriptionDTO>();
		
        for(DetailedDescription dd : fileRepository.findAll()) {
            ddDTOs.add(new DetailedDescriptionDTO(dd));
        }

        return ddDTOs;
    }

    @Override
    public DetailedDescription save(MultipartFile file) throws IOException {
        log.info("Saving new file {} to the database", file.getOriginalFilename());
        DetailedDescription dd = new DetailedDescription();
        dd.setName(file.getOriginalFilename());
        dd.setType(file.getContentType());
        dd.setData(file.getBytes());

        DetailedDescription newDD = fileRepository.save(dd);
        productIndexer.saveUploadedFileInFolder(file, newDD.getId());
        return newDD;
    }

    @Override
    public void remove(Long id) {
        log.info("Removing file {}", id);
        fileRepository.deleteById(id);
    }
    
}
