package com.olshevchenko.service;

import com.olshevchenko.entity.ResultOfTheFileAnalysis;
import com.olshevchenko.utils.PathAndWordValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oleksandr Shevchenko
 */
public class StreamFileAnalyzer implements FileAnalyzer {

    @Override
    public String readContent(String path) {
        String content;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            content = String.join("", stream.collect(Collectors.toList()));
            return content;
        }  catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cant read file by provided path", e);
        }
    }

    @Override
    public List<String> getListFromFileContent(String content) {
        return Stream.of(content.split("(([.?!]))"))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSentencesWithGivenWord(List<String> contentList, String word) {
        return contentList.stream()
                .filter((c) -> c.toLowerCase().contains(word.toLowerCase()))
                .toList();
    }

    @Override
    public int countWordEntry(List<String> sentences, String word) {
        return (int) sentences.stream()
                .map(s -> s.split("\\s+"))
                .flatMap(Arrays::stream)
                .filter(sentence -> sentence.toLowerCase().contains(word.toLowerCase()))
                .count();
    }

    @Override
    public ResultOfTheFileAnalysis analyze(String path, String word) {
        PathAndWordValidation.validate(path, word);

        String content = readContent(path);
        List<String> sentences = getListFromFileContent(content);
        List<String> filteredSentences = getSentencesWithGivenWord(sentences, word);
        int wordCount = countWordEntry(filteredSentences, word);

        return new ResultOfTheFileAnalysis(word, filteredSentences, wordCount);
    }


}
