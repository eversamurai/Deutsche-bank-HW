package com.olkhovskyi.service;

import com.olkhovskyi.entity.NaceDetails;
import com.olkhovskyi.repository.NaceDetailsRepository;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NaceDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(NaceDetailsService.class);

    @Autowired
    private NaceDetailsRepository naceDetailsRepository;

    public Optional<NaceDetails> getNaceDetailsById(Long id) {
        logger.info("Fetching NaceDetails with id: {}", id);
        return naceDetailsRepository.findById(id);
    }

    public List<NaceDetails> getAllNaceDetails() {
        logger.info("Fetching all NaceDetails");
        return (List<NaceDetails>) naceDetailsRepository.findAll();
    }

    public void saveNaceDetails(NaceDetails naceDetails) {
        logger.info("Saving NaceDetails: {}", naceDetails);
        naceDetailsRepository.save(naceDetails);
    }

    public void deleteNaceDetails(Long id) {
        logger.info("Deleting NaceDetails with id: {}", id);
        naceDetailsRepository.deleteById(id);
    }

    public  List<NaceDetails> retriveNaceDetails(CSVParser csvParser) {
        List<NaceDetails> naceDetailsList = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            if (record.size() < 10) {
                throw new IllegalArgumentException("Invalid CSV format");
            }
            NaceDetails naceDetails = new NaceDetails(
                    Integer.parseInt(record.get(0)), // Order
                    Integer.parseInt(record.get(1)), // Level
                    record.get(2), // Code
                    record.get(3), // Parent
                    record.get(4), // Description
                    record.get(5), // This item includes
                    record.get(6), // This item also includes
                    record.get(7), // Rulings
                    record.get(8), // This item excludes
                    record.get(9)  // Reference to ISIC Rev. 4
            );
            naceDetailsList.add(naceDetails);
        }
        return naceDetailsList;
    }
}