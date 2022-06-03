package com.zhmenko.deeplay.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

import java.io.File;
import java.util.Map;

public class JsonConfigurationFileParser implements ConfigurationFileParser {
    @Override
    public Map<String, Map<String, Integer>> parseFile(File configFile) {
        Map<String, Map<String, Integer>> config;
        try {
            ObjectMapper mapper = new ObjectMapper();
            config = (Map<String, Map<String, Integer>>) mapper.readValue(configFile, Map.class);
        } catch (Exception e) {
            throw new RuntimeJsonMappingException("Не получилось считать свойства из файла!");
        }
        return config;
    }
}
