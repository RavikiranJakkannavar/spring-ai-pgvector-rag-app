package com.springai.rest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pdf/")
public class MavericController {

    private ChatClient chatClient;
    private VectorStore vectorStore;

    private String prompt = """
            Answer the question using ONLY the context below.
            If answer not present, say you don't know.
                        
            QUESTION:
            {input}
                        
            DOCUMENTS:
            {documents}
                        
            """;

    public MavericController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.chatClient = ChatClient.create(ollamaChatModel);
        this.vectorStore=vectorStore;
    }

    @GetMapping("/ask")
    public String simplify(@RequestParam (value="question") String question) {

        PromptTemplate template
                = new PromptTemplate(prompt);
        Map<String, Object> promptsParameters = new HashMap<>();
        promptsParameters.put("input", question);
        promptsParameters.put("documents", findSimilarData(question));

        return  chatClient
                .prompt(template.create(promptsParameters))
                .call()
                .content();
    }

    private String findSimilarData(String question) {

        List<Document> documents =
                vectorStore.similaritySearch(SearchRequest.builder()
                                .query(question)
                                .topK(5)
                                .build());

        if (documents.isEmpty()) {
            return "No relevant information found.";
        }

        return documents
                .stream()
                .map(document -> document.getText().toString())
                .collect(Collectors.joining());
    }

}
