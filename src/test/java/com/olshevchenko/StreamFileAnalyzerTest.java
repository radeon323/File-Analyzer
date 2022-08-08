package com.olshevchenko;

import com.olshevchenko.service.FileAnalyzer;
import com.olshevchenko.service.StreamFileAnalyzer;

/**
 * @author Oleksandr Shevchenko
 */
public class StreamFileAnalyzerTest extends FileAnalyzerTest{
    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new StreamFileAnalyzer();
    }
}
