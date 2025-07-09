package com.kaba4cow.difuse.core.config.reader.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.kaba4cow.difuse.core.config.reader.ConfigReader;
import com.kaba4cow.difuse.core.config.source.ConfigSource;
import com.kaba4cow.difuse.core.config.source.impl.MapConfigSource;

public class YamlConfigReader implements ConfigReader {

	@Override
	public ConfigSource read(String name, InputStream input) throws Exception {
		Yaml yaml = new Yaml();
		Object loaded = yaml.load(input);
		if (!(loaded instanceof Map<?, ?>))
			throw new IllegalArgumentException("Yaml root must be a map");
		Map<String, Object> flatMap = new LinkedHashMap<>();
		flattenMap("", (Map<?, ?>) loaded, flatMap);
		return new MapConfigSource(name, flatMap);
	}

	private void flattenMap(String prefix, Map<?, ?> source, Map<String, Object> target) {
		for (Map.Entry<?, ?> entry : source.entrySet()) {
			String key = entry.getKey().toString();
			Object value = entry.getValue();
			String fullKey = prefix.isEmpty() ? key : String.format("%s.%s", prefix, key);
			if (value instanceof Map<?, ?>)
				flattenMap(fullKey, (Map<?, ?>) value, target);
			else
				target.put(fullKey, value);
		}
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("yaml", "yml");
	}

}
