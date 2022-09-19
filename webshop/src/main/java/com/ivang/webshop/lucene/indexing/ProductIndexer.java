package com.ivang.webshop.lucene.indexing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ivang.webshop.lucene.indexing.handlers.DocumentHandler;
import com.ivang.webshop.lucene.indexing.handlers.PDFHandler;
import com.ivang.webshop.lucene.indexing.handlers.TextDocHandler;
import com.ivang.webshop.lucene.indexing.handlers.Word2007Handler;
import com.ivang.webshop.lucene.indexing.handlers.WordHandler;
import com.ivang.webshop.lucene.model.shop.ProductEs;
import com.ivang.webshop.lucene.model.shop.dto.ProductRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductIndexer {

	private final ElasticsearchOperations elasticsearchOperations;

	@Value("${files.path}")
    private String dataFilesPath;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

	public String index(ProductEs product) {
		IndexQuery indexQuery = new IndexQueryBuilder()
			.withId(product.getAssociatedId().toString())
			.withObject(product).build();

		String documentId = elasticsearchOperations
			.index(indexQuery, IndexCoordinates.of("products"));

		return documentId;
	}

	public File getResourceFilePath(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            if(url != null) {
                file = new File(url.toURI());
            }
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

	public String saveUploadedFileInFolder(MultipartFile file, Long id) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String newFileName = id.toString() + "-" + file.getOriginalFilename();
            Path path = Paths.get(getResourceFilePath(dataFilesPath).getAbsolutePath() + File.separator + newFileName);
            Files.write(path, bytes);
            Path filepath = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(newFileName)));
            Files.write(filepath, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

	public void indexProduct(ProductRequestDTO productRequestDTO) throws IOException {
		ProductEs productIndexUnit;

		if (productRequestDTO.getDetailedDescription() != null) {
			String fileName = productRequestDTO.getDetailedDescription();
            Path path = Paths.get(getResourceFilePath(dataFilesPath).getAbsolutePath() + File.separator + fileName);
            String pathStr = path.toString();
            productIndexUnit = getHandler(fileName).getIndexUnit(new File(pathStr));
		} else {
			productIndexUnit = new ProductEs();
		}

        productIndexUnit.setAssociatedId(productRequestDTO.getAssociatedId());
		productIndexUnit.setName(productRequestDTO.getName());
		productIndexUnit.setPrice(productRequestDTO.getPrice());
		productIndexUnit.setDescription(productRequestDTO.getDescription());
		index(productIndexUnit);
    }

    public boolean deleteProduct(Long associatedId) {
        String retVal = elasticsearchOperations.delete(associatedId.toString(), IndexCoordinates.of("products"));
		if(associatedId.equals(Long.parseLong(retVal)))
			return true;
		else
			return false;
    }

	public DocumentHandler getHandler(String fileName){
        return getDocumentHandler(fileName);
    }

	public static DocumentHandler getDocumentHandler(String fileName) {
        if(fileName.endsWith(".txt")){
            return new TextDocHandler();
        }else if(fileName.endsWith(".pdf")){
            return new PDFHandler();
        }else if(fileName.endsWith(".doc")){
            return new WordHandler();
        }else if(fileName.endsWith(".docx")){
            return new Word2007Handler();
        }else{
            return null;
        }
    }
}
