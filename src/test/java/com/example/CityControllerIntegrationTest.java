package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/drop-tables.sql", "/db/migration/V1.0.0__add-schema.sql",
		"/db/migration/V1.1.0__add-initial-data.sql" }, config = @SqlConfig(encoding = "UTF-8"))
public class CityControllerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void testPostCities() {
		City toyama = new City();
		toyama.setName("Toyama");
		JsonNode created = restTemplate.postForObject("/cities", toyama, JsonNode.class);
		assertThat(created.get("name").asText()).isEqualTo("Toyama");
		assertThat(created.get("id").asInt()).isEqualTo(4);

		JsonNode cities = restTemplate.getForObject("/cities", JsonNode.class);
		assertThat(cities.size()).isEqualTo(4);
	}

	@Test
	public void testGetCities() {
		JsonNode cities = restTemplate.getForObject("/cities", JsonNode.class);
		assertThat(cities.size()).isEqualTo(3);
		assertThat(cities.get(0).get("name").asText()).isEqualTo("Tokyo");
		assertThat(cities.get(1).get("name").asText()).isEqualTo("Osaka");
		assertThat(cities.get(2).get("name").asText()).isEqualTo("Kyoto");
	}

}