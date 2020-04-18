package ru.iopump.qa.allure.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.iopump.qa.util.FileUtil;

@Slf4j
public class ZipServiceTest {

    private ZipService zipService;

    @Before
    public void setUp() {
        zipService = new ZipService(FileUtil.getClassPathMainDir().resolve("test").toString());
    }

    @Test
    public void unzipAndStorePositive() throws IOException {
        Resource resource = new ClassPathResource("allure-results.zip");
        Path path = zipService.unzipAndStore(resource.getInputStream());
        log.info("UnZip to: {}", path);

        resource = new ClassPathResource("allure-results-2.zip");
        path = zipService.unzipAndStore(resource.getInputStream());
        log.info("UnZip to: {}", path);

        resource = new ClassPathResource("allure-results-empty-folder.zip");
        path = zipService.unzipAndStore(resource.getInputStream());
        log.info("UnZip to: {}", path);
    }

    @Test
    public void unzipAndStoreNegative() {
        assertThatThrownBy(() -> zipService.unzipAndStore(new ClassPathResource("allure-results.json").getInputStream()))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> zipService.unzipAndStore(new ClassPathResource("allure-results.7z").getInputStream()))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> zipService.unzipAndStore(new ClassPathResource("allure-results-json").getInputStream()))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> zipService.unzipAndStore(new ClassPathResource("allure-results-empty.zip").getInputStream()))
            .isInstanceOf(IllegalArgumentException.class);
    }
}