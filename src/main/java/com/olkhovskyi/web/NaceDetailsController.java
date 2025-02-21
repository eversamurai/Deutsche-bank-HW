package com.olkhovskyi.web;

import com.olkhovskyi.entity.NaceDetails;
import com.olkhovskyi.service.NaceDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/nace")
@Tag(name = "NaceDetails", description = "API for NaceDetails operations")
public class NaceDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(NaceDetailsController.class);

    private final NaceDetailsService naceDetailsService;

    public NaceDetailsController(NaceDetailsService naceDetailsService) {
        this.naceDetailsService = naceDetailsService;
    }

    @PostMapping(path = "/putNaceDetails/csv",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save NaceDetails from CSV", description = "Save NaceDetails entities from a CSV file")
    public ResponseEntity<String> putNaceDetailsFromCsv(
            @Parameter(description = "CSV file to upload", required = true) @RequestParam("file") MultipartFile file) {
        logger.info("Received request to save NaceDetails from CSV file");
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines());
            List<NaceDetails> naceDetailsList = naceDetailsService.retriveNaceDetails(csvParser);
            naceDetailsList.forEach(naceDetailsService::saveNaceDetails);
            return ResponseEntity.ok("CSV file processed successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid CSV format", e);
            return ResponseEntity.badRequest().body("Invalid CSV format: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing CSV file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing CSV file: " + e.getMessage());
        }

    }

    @GetMapping("/getNaceDetails/{orderNumber}")
    @Operation(summary = "Get NaceDetails by order number", description = "Retrieve a NaceDetails entity by its order number")
    public Optional<NaceDetails> getNaceDetails(@PathVariable Integer orderNumber) {
        logger.info("Received request to get NaceDetails by order number: {}", orderNumber);
        return naceDetailsService.getAllNaceDetails().stream()
                .filter(naceDetails -> orderNumber.equals(naceDetails.getOrderNumber()))
                .findFirst();
    }

    @GetMapping("/healthCheck")
    @Operation(summary = "Health Check", description = "Check if the NaceDetailsController is up and running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("NaceDetailsController is up and running!");
    }
}