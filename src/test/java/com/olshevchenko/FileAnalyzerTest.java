package com.olshevchenko;

import com.olshevchenko.entity.ResultOfTheFileAnalysis;
import com.olshevchenko.service.FileAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Oleksandr Shevchenko
 */
public abstract class FileAnalyzerTest {
    private static final String word = "Lion";
    private static final String path = "src/test/resources/story.txt";
    private static final String content =
            "A lion was once sleeping in the jungle when a mouse started running up and down his body just for fun." +
                    "This disturbed the lion’s sleep, and he woke up quite angry." +
                    "He was about to eat the mouse when the mouse desperately requested the lion to set him free." +
                    "I promise you, I will be of great help to you someday if you save me." +
                    "The lion laughed at the mouse’s confidence and let him go." +
                    "One day, a few hunters came into the forest and took the lion with them." +
                    "They tied him up against a tree." +
                    "The lion was struggling to get out and started to whimper." +
                    "Soon, the mouse walked past and noticed the lion in trouble." +
                    "Quickly, he ran and gnawed on the ropes to set the lion Lion free." +
                    "Both of them sped off into the jungle.";
    private final List<String> contentList = List.of(content.split("[\\.\\!\\?]"));
    private final List<String> sentences = getListWithSentences(contentList);
    private FileAnalyzer fileAnalyzer;

    @BeforeEach
    public void before() {
        fileAnalyzer = getFileAnalyzer();
    }

    protected abstract FileAnalyzer getFileAnalyzer();

    private List<String> getListWithSentences(List<String> contentList) {
        List<String> expected = new ArrayList<>();
        expected.add(contentList.get(0));
        expected.add(contentList.get(1));
        expected.add(contentList.get(2));
        expected.add(contentList.get(4));
        expected.add(contentList.get(5));
        expected.add(contentList.get(7));
        expected.add(contentList.get(8));
        expected.add(contentList.get(9));
        return expected;
    }

    @Test
    @DisplayName("Test method should return content when reading the file by the providing path")
    void testReadContent() {
        String actual = fileAnalyzer.readContent(path);
        assertEquals(content, actual.replaceAll("\r\n", ""));
    }

    @Test
    @DisplayName("Test method should return a list of sentences from given content")
    void testGetListFromFileContent() {
        List<String> actual = fileAnalyzer.getListFromFileContent(content);
        assertEquals(contentList, actual);
    }

    @Test
    @DisplayName("Test method should return a list of filtered sentences each contain given word from a list of sentences")
    void testGetSentencesWithGivenWord() {
        List<String> expected = getListWithSentences(contentList);
        List<String> actual = fileAnalyzer.getSentencesWithGivenWord(contentList, word);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test method should return a number of given word entry in the provided list of sentences")
    void testCountWordEntry() {
        int expected = 9;
        int actual = fileAnalyzer.countWordEntry(sentences, word);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test method should return an entity 'ResultOfTheFileAnalysis' as a result of file analysis")
    void testAnalyze() {
        List<String> filteredSentences = getListWithSentences(contentList);
        int wordCount = 9;

        ResultOfTheFileAnalysis expected = new ResultOfTheFileAnalysis(word, filteredSentences, wordCount);
        ResultOfTheFileAnalysis actual = fileAnalyzer.analyze(path, word);

        assertEquals(expected.getGivenWord(), actual.getGivenWord());
        assertEquals(expected.getSentencesWithGivenWord(), actual.getSentencesWithGivenWord());
        assertEquals(expected.getWordEntryCount(), actual.getWordEntryCount());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test method should throw IllegalArgumentException when file does not exist")
    void testAnalyzeThrowIllegalArgumentException() {
        String path = "src/test/resources/story1.txt";
        Assertions.assertThrows(IllegalArgumentException.class, () -> fileAnalyzer.analyze(path, word));
    }

    @Test
    @DisplayName("Test method should throw NullPointerException when word is empty")
    void testAnalyzeThrowNullPointerException() {
        String word = null;
        Assertions.assertThrows(NullPointerException.class, () -> fileAnalyzer.analyze(path, word));
    }


}
