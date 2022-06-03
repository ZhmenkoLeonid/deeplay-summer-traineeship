package com.zhmenko.deeplay.config;

import java.io.File;
import java.util.Map;

public interface ConfigurationFileParser {
    Map<String, Map<String, Integer>> parseFile(File configFile);
}
