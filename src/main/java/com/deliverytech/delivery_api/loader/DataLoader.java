package com.deliverytech.delivery_api.loader;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RestauranteRepository restauranteRepository;

    public DataLoader(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar restaurantes
        Restaurante r1 = new Restaurante(
                "Restaurante A",
                "Italiana",
                BigDecimal.valueOf(10.0),
                true,
                "Rua A, 123",
                "123456789",
                4.5
        );

        Restaurante r2 = new Restaurante(
                "Restaurante B",
                "Japonesa",
                BigDecimal.valueOf(8.5),
                true,
                "Rua B, 456",
                "987654321",
                4.8
        );

        restauranteRepository.save(r1);
        restauranteRepository.save(r2);

        System.out.println("Restaurantes carregados no banco!");
    }
}

