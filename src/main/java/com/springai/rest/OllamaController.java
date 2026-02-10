package com.springai.rest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama/")
public class OllamaController {

    ChatClient chatClient;

    public OllamaController(OllamaChatModel ollamaChatModel) {
        this.chatClient = ChatClient.create(ollamaChatModel);
    }

    @GetMapping("/{message}")
    public ResponseEntity<String> talkToModel(@PathVariable String message) {
        String response = chatClient.prompt(message).call().content();
        return ResponseEntity.ok(response);
    }
}
