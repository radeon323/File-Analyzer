package com.olshevchenko;

import com.olshevchenko.entity.ResultOfTheFileAnalysis;
import com.olshevchenko.service.DefaultFileAnalyzer;
import com.olshevchenko.service.FileAnalyzer;
import com.olshevchenko.service.StreamFileAnalyzer;

import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {

    public static void main(String[] args) {
        System.out.println("Enter 2 arguments through a space: 'keyword' 'path to file' \r\n");
        if (args.length != 2) {
            throw new IllegalArgumentException("Make sure you pass two arguments: 'keyword' and 'path to file'");
        }
        String word = args[0];
        String path = args[1];
        FileAnalyzer defaultFileAnalyzer = new DefaultFileAnalyzer();
        FileAnalyzer streamFileAnalyzer = new StreamFileAnalyzer();

        ResultOfTheFileAnalysis resultOfTheFileAnalysisFromDefaultFileAnalyzer = defaultFileAnalyzer.analyze(path, word);
        ResultOfTheFileAnalysis resultOfTheFileAnalysisFromStreamFileAnalyzer = streamFileAnalyzer.analyze(path, word);

        System.out.println("The result of DefaultFileAnalyzer is:");
        System.out.println(viewFileInfo(resultOfTheFileAnalysisFromDefaultFileAnalyzer));
        System.out.println("The result of StreamFileAnalyzer is:");
        System.out.println(viewFileInfo(resultOfTheFileAnalysisFromStreamFileAnalyzer));

    }


    public static String viewFileInfo(ResultOfTheFileAnalysis analysis) {
        List<String> sentences = analysis.getSentencesWithGivenWord();
        String givenWord = analysis.getGivenWord();
        int wordEntryCount = analysis.getWordEntryCount();
        int sentencesWithGivenWordCount = sentences.size();

        StringBuilder result = new StringBuilder();
        result.append("\u001B[34m");
        result.append("Word '");
        result.append(givenWord);
        result.append("' in this text occurs ");
        result.append(wordEntryCount);
        result.append(" times.\r\n");
        result.append("\u001B[0m");

        result.append("\u001B[33m");
        result.append("The number of sentences with word '");
        result.append(givenWord);
        result.append("' is ");
        result.append(sentencesWithGivenWordCount);
        result.append("\r\n");
        result.append("\u001B[0m");

        result.append("\u001B[35m");
        result.append("The sentences with word '");
        result.append(givenWord);
        result.append("' are:\r\n");
        result.append("\u001B[0m");
        for (String sentence : sentences) {
            result.append("- ").append(sentence).append(";").append("\r\n");
        }

        return result.toString();
    }
}
