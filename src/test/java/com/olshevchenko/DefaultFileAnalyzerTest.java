package com.olshevchenko;

import com.olshevchenko.service.DefaultFileAnalyzer;
import com.olshevchenko.service.FileAnalyzer;

/**
 * @author Oleksandr Shevchenko
 */
class DefaultFileAnalyzerTest extends FileAnalyzerTest{
    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new DefaultFileAnalyzer();
    }
}