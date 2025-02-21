package com.olkhovskyi.integration.repository;
import com.olkhovskyi.entity.NaceDetails;
import com.olkhovskyi.repository.NaceDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class NaceDetailsRepositoryIntegrationTest {

    @Autowired
    private NaceDetailsRepository naceDetailsRepository;

    @Test
    public void testSaveAndFindById() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        NaceDetails savedNaceDetails = naceDetailsRepository.save(naceDetails);

        Optional<NaceDetails> foundNaceDetails = naceDetailsRepository.findById(savedNaceDetails.getId());
        assertThat(foundNaceDetails).isPresent();
        assertThat(foundNaceDetails.get().getCode()).isEqualTo("A");
    }

    @Test
    public void testFindAll() {
        NaceDetails naceDetails1 = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        NaceDetails naceDetails2 = new NaceDetails(2, 2, "C", "D", "Description2", "Includes2", "Also Includes2", "Rulings2", "Excludes2", "Reference2");
        naceDetailsRepository.save(naceDetails1);
        naceDetailsRepository.save(naceDetails2);

        Iterable<NaceDetails> naceDetailsList = naceDetailsRepository.findAll();
        assertThat(naceDetailsList).hasSize(2);
    }

    @Test
    public void testDeleteById() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        NaceDetails savedNaceDetails = naceDetailsRepository.save(naceDetails);

        naceDetailsRepository.deleteById(savedNaceDetails.getId());
        Optional<NaceDetails> foundNaceDetails = naceDetailsRepository.findById(savedNaceDetails.getId());
        assertThat(foundNaceDetails).isNotPresent();
    }
}