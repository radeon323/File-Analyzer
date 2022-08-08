package com.olshevchenko.service;

import com.olshevchenko.entity.ResultOfTheFileAnalysis;
import com.olshevchenko.utils.PathAndWordValidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Oleksandr Shevchenko
 */
public class DefaultFileAnalyzer implements FileAnalyzer {

    @Override
    public String readContent(String path) {
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file)) {
            int length = (int) file.length();
            byte [] array = new byte[length];
            inputStream.read(array);
            return new String(array);
        }  catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cant read file by provided path", e);
        }
    }

    @Override
    public List<String> getListFromFileContent(String content) {
        Pattern REGEX = Pattern.compile("(([.?!]))");
        String[] sentences = REGEX.split(content);
        List<String> result = new ArrayList<>();
        for (String sentence : sentences) {
            result.add(sentence.trim());
        }
        return result;
    }

    @Override
    public List<String> getSentencesWithGivenWord(List<String> contentList, String word) {
        List<String> result = new ArrayList<>();
        for (String sentence : contentList) {
            if (sentence.contains(word.toLowerCase())) {
                result.add(sentence);
            }
        }
        return result;
    }

    @Override
    public int countWordEntry(List<String> sentences, String word) {
        int count = 0;
        for (String sentence : sentences) {
            String[] words = sentence.split("\\s+");
            for (String w : words) {
                if (w.toLowerCase().contains(word.toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
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
