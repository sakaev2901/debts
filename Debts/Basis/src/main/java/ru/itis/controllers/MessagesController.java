package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {

    private static final Map<String, List<MessageDto>> messages= new HashMap<>();

//    @PostMapping("/messages")
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto messageDto) {
//        MessageDto messageDto = new MessageDto();
//        messageDto.setPageId(pageId);
//        messageDto.setText(text);
        if (!messages.containsKey(messageDto.getPageId())) {
            messages.put(messageDto.getPageId(), new ArrayList<>());
        }

        for (List<MessageDto> pageMessages: messages.values())  {
            synchronized (pageMessages) {
                pageMessages.add(messageDto);
                pageMessages.notifyAll();
            }
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessages(@RequestParam("pageId") String pageId) {
        synchronized (messages.get(pageId)) {
            if (messages.get(pageId).isEmpty()) {
                messages.get(pageId).wait();
            }
        }

        List<MessageDto> response = new ArrayList<>(messages.get(pageId));
        messages.get(pageId).clear();
        return ResponseEntity.ok(response);
    }
}
