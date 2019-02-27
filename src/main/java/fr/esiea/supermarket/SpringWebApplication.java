package fr.esiea.supermarket;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.ProductUnit;

import org.springframework.boot.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample web application.<br/>
 * Run {@link #main(String[])} to launch.
 */

@SpringBootApplication
@RestController
public class SpringWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringWebApplication.class);
    private final BiMap<Integer, Product> database = HashBiMap.create();
    private final AtomicInteger sequenceGenerator = new AtomicInteger();

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class);
    }

    @RequestMapping("add_product")
    int addProduct(@RequestParam("productName") String productName, @RequestParam("unit") ProductUnit unit) {
        Product c = new Product(productName, unit);

        final int id;
        if (!database.containsValue(c)) {
            id = sequenceGenerator.incrementAndGet();
            c.setId(id);
            database.put(id, c);
            LOGGER.info(c + " crée avec l'ID : " + database.inverse()
                .get(c));
        } else {
            id = database.inverse()
                .get(c);
            LOGGER.info(c + " existe déjà avec l'ID: " + database.inverse()
                .get(c));
        }
        return id;
    }

    @RequestMapping("/list_products")
    List<Product> listProducts() {
        return database.entrySet()
            .stream()
            .map(Entry::getValue)
            .collect(Collectors.toList());
    }
}
