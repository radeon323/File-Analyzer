package com.olshevchenko.service;

import com.olshevchenko.entity.ResultOfTheFileAnalysis;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public interface FileAnalyzer {
    String readContent(String path);
    List<String> getListFromFileContent(String content);
    List<String> getSentencesWithGivenWord(List<String> contentList, String word);
    int countWordEntry(List<String> sentences, String word);
    ResultOfTheFileAnalysis analyze(String path, String word);
}
