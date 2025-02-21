package com.olkhovskyi.integration.service;

import com.olkhovskyi.entity.NaceDetails;
import com.olkhovskyi.repository.NaceDetailsRepository;
import com.olkhovskyi.service.NaceDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class NaceDetailsServiceIntegrationTest {

    @Autowired
    private NaceDetailsService naceDetailsService;

    @Autowired
    private NaceDetailsRepository naceDetailsRepository;

    @BeforeEach
    public void setUp() {
        naceDetailsRepository.deleteAll();
        NaceDetails naceDetails1 = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        NaceDetails naceDetails2 = new NaceDetails(2, 2, "C", "D", "Description2", "Includes2", "Also Includes2", "Rulings2", "Excludes2", "Reference2");
        naceDetailsRepository.save(naceDetails1);
        naceDetailsRepository.save(naceDetails2);
    }

    @Test
    public void testSaveAndGetNaceDetailsById() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        naceDetailsService.saveNaceDetails(naceDetails);

        Optional<NaceDetails> foundNaceDetails = naceDetailsService.getNaceDetailsById(naceDetails.getId());
        assertThat(foundNaceDetails).isPresent();
        assertThat(foundNaceDetails.get().getCode()).isEqualTo("A");
    }

    @Test
    public void testGetAllNaceDetails() {
        Iterable<NaceDetails> naceDetailsList = naceDetailsService.getAllNaceDetails();
        assertThat(naceDetailsList).hasSize(2);
    }

    @Test
    public void testDeleteNaceDetails() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        naceDetailsService.saveNaceDetails(naceDetails);

        naceDetailsService.deleteNaceDetails(naceDetails.getId());
        Optional<NaceDetails> foundNaceDetails = naceDetailsService.getNaceDetailsById(naceDetails.getId());
        assertThat(foundNaceDetails).isNotPresent();
    }
}