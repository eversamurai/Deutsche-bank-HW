package com.olkhovskyi.unit;

import com.olkhovskyi.entity.NaceDetails;
import com.olkhovskyi.repository.NaceDetailsRepository;
import com.olkhovskyi.service.NaceDetailsService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NaceDetailsServiceTest {

    @Mock
    private NaceDetailsRepository naceDetailsRepository;

    @InjectMocks
    private NaceDetailsService naceDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNaceDetailsById_Positive() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        when(naceDetailsRepository.findById(1L)).thenReturn(Optional.of(naceDetails));

        Optional<NaceDetails> foundNaceDetails = naceDetailsService.getNaceDetailsById(1L);

        assertThat(foundNaceDetails).isPresent();
        assertThat(foundNaceDetails.get().getCode()).isEqualTo("A");
    }

    @Test
    public void testGetNaceDetailsById_Negative() {
        when(naceDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<NaceDetails> foundNaceDetails = naceDetailsService.getNaceDetailsById(1L);

        assertThat(foundNaceDetails).isNotPresent();
    }

    @Test
    public void testGetAllNaceDetails_Positive() {
        NaceDetails naceDetails1 = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        NaceDetails naceDetails2 = new NaceDetails(2, 2, "C", "D", "Description2", "Includes2", "Also Includes2", "Rulings2", "Excludes2", "Reference2");
        when(naceDetailsRepository.findAll()).thenReturn(Arrays.asList(naceDetails1, naceDetails2));

        List<NaceDetails> naceDetailsList = naceDetailsService.getAllNaceDetails();

        assertThat(naceDetailsList).hasSize(2);
        assertThat(naceDetailsList).contains(naceDetails1, naceDetails2);
    }

    @Test
    public void testSaveNaceDetails_Positive() {
        NaceDetails naceDetails = new NaceDetails(1, 1, "A", "B", "Description", "Includes", "Also Includes", "Rulings", "Excludes", "Reference");
        when(naceDetailsRepository.save(any(NaceDetails.class))).thenReturn(naceDetails);

        naceDetailsService.saveNaceDetails(naceDetails);

        verify(naceDetailsRepository, times(1)).save(naceDetails);
    }

    @Test
    public void testDeleteNaceDetails_Positive() {
        doNothing().when(naceDetailsRepository).deleteById(1L);

        naceDetailsService.deleteNaceDetails(1L);

        verify(naceDetailsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNaceDetails_Negative() {
        doThrow(new RuntimeException("NaceDetails not found")).when(naceDetailsRepository).deleteById(1L);

        try {
            naceDetailsService.deleteNaceDetails(1L);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("NaceDetails not found");
        }

        verify(naceDetailsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetriveNaceDetails_Positive() throws Exception {
        String csvContent = "Order,Level,Code,Parent,Description,This item includes,This item also includes,Rulings,This item excludes,Reference to ISIC Rev. 4\n" +
                "1,1,A,B,Description,Includes,Also Includes,Rulings,Excludes,Reference\n";
        CSVParser csvParser = new CSVParser(new StringReader(csvContent), CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines());

        List<NaceDetails> naceDetailsList = naceDetailsService.retriveNaceDetails(csvParser);

        assertThat(naceDetailsList).hasSize(1);
        NaceDetails naceDetails = naceDetailsList.get(0);
        assertThat(naceDetails.getOrderNumber()).isEqualTo(1);
        assertThat(naceDetails.getLevel()).isEqualTo(1);
        assertThat(naceDetails.getCode()).isEqualTo("A");
        assertThat(naceDetails.getParent()).isEqualTo("B");
        assertThat(naceDetails.getDescription()).isEqualTo("Description");
        assertThat(naceDetails.getThisItemIncludes()).isEqualTo("Includes");
        assertThat(naceDetails.getThisItemAlsoIncludes()).isEqualTo("Also Includes");
        assertThat(naceDetails.getRulings()).isEqualTo("Rulings");
        assertThat(naceDetails.getThisItemExcludes()).isEqualTo("Excludes");
        assertThat(naceDetails.getReferenceToISICRev4()).isEqualTo("Reference");
    }

    @Test
    public void testRetriveNaceDetails_Negative_InvalidCSVFormat() throws IOException {
        String invalidCsvContent = "Order,Level,Code,Parent,Description,This item includes,This item also includes,Rulings,This item excludes,Reference to ISIC Rev. 4\n" +
                "1,1,A,B,Description,Includes,Also Includes,Rulings,Excludes\n"; // missing one column
        CSVParser csvParser = new CSVParser(new StringReader(invalidCsvContent), CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            naceDetailsService.retriveNaceDetails(csvParser);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid CSV format");
    }
}