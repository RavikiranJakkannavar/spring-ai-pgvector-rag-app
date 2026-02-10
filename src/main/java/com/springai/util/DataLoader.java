package com.springai.util;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private VectorStore vectorStore;

    private JdbcClient jdbcClient;

    public DataLoader(VectorStore vectorStore, JdbcClient jdbcClient) {
        this.vectorStore=vectorStore;
        this.jdbcClient=jdbcClient;
    }

    @Value("classpath:/maveric-brochure.pdf")
    Resource pdfResource;

    @PostConstruct
    public  void init() {
        Integer count = jdbcClient.sql("select COUNT(*) from vector_store")
                .query(Integer.class)
                .single();

        if(count == 0) {
            PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                    .withPagesPerDocument(1)
                    .build();

            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource, config);

            var textSplitter = new TokenTextSplitter();
            vectorStore.add(textSplitter.apply(reader.get()));
        }
    }




}
