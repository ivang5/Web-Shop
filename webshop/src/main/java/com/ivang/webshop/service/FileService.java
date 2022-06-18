package com.ivang.webshop.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ivang.webshop.entity.DetailedDescription;
import com.ivang.webshop.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class FileService implements FileServiceInterface {

    private final FileRepository fileRepository;

    @Override
    public DetailedDescription findOne(Long id) {
        log.info("Fetching file {}", id);
        return fileRepository.getById(id);
    }
    @Override
    public List<DetailedDescription> findAll() {
        log.info("Fetching files");
		return fileRepository.findAll();
    }

    @Override
    public DetailedDescription save(MultipartFile file) throws IOException {
        log.info("Saving new file {} to the database", file.getOriginalFilename());
        DetailedDescription dd = new DetailedDescription();
        dd.setName(file.getOriginalFilename());
        dd.setType(file.getContentType());
        dd.setData(file.getBytes());

        return fileRepository.save(dd);
    }

    @Override
    public void remove(Long id) {
        log.info("Removing file {}", id);
        fileRepository.deleteById(id);
    }
    
}
