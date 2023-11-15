package com.ssafy.board.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BadWordController {

    @GetMapping("/board/badwords")
    public List<String> getBadWords() {
        List<String> badWords = new ArrayList<>();
        
        try {
            Resource resource = new ClassPathResource("badword.csv");
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            // CSV 파일을 한 줄씩 읽어서 badWords 목록에 추가
            StringBuilder word = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                if (ch == ',') {
                    badWords.add(word.toString().trim());
                    word.setLength(0);
                } else if (ch == '\n') {
                    badWords.add(word.toString().trim());
                } else {
                    word.append((char) ch);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return badWords;
    }
}
