//import com.example.zpofinal.helpers.CalculationResult;
//import com.example.zpofinal.models.Boozed;
//import com.example.zpofinal.models.Wine;
//import com.example.zpofinal.repositories.WineRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//class BoozedCalculatorTest {
//
//    @Mock
//    private WineRepository wineRepository;
//
//    @InjectMocks
//    private BoozedCalculator boozedCalculator;
//
//    public BoozedCalculatorTest() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void calculateBooze_ShouldReturnCalculationResult() {
//        // Arrange
//        Boozed boozed = new Boozed();
//        // Set the properties of the boozed object as needed for the test
//
//        Wine wine1 = new Wine();
//        // Set the properties of wine1 as needed for the test
//
//        Wine wine2 = new Wine();
//        // Set the properties of wine2 as needed for the test
//
//        // Mock the wine repository to return a list of wines
//        when(wineRepository.findAll()).thenReturn(Arrays.asList(wine1, wine2));
//
//        // Act
//        ResponseEntity<CalculationResult> response = boozedCalculator.calculateBooze(boozed);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        CalculationResult calculationResult = response.getBody();
//        // Verify the calculation result and its properties as needed for the test
//    }
//
//    @Test
//    void findBestCombination_ShouldReturnBestCombination() {
//        // Arrange
//        List<Wine> wines = Arrays.asList(new Wine(), new Wine(), new Wine());
//        double targetClearAlcohol = 10.0;
//        List<Wine> currentCombination = new ArrayList<>();
//        List<Wine> bestCombination = new ArrayList<>();
//        double closestDifference = Double.MAX_VALUE;
//
//        // Act
//        List<Wine> result = boozedCalculator.findBestCombination(wines, targetClearAlcohol, currentCombination, bestCombination, closestDifference);
//
//        // Assert
//        // Verify the result and its properties as needed for the test
//    }
//
//    @Test
//    void calculateCombinationCost_ShouldReturnCombinationCost() {
//        // Arrange
//        List<Wine> combination = Arrays.asList(new Wine(), new Wine(), new Wine());
//
//        // Act
//        double result = boozedCalculator.calculateCombinationCost(combination);
//
//        // Assert
//        // Verify the result as needed for the test
//    }
//
//    @Test
//    void calculateTotalClearAlcohol_ShouldReturnTotalClearAlcohol() {
//        // Arrange
//        List<Wine> wines = Arrays.asList(new Wine(), new Wine(), new Wine());
//
//        // Act
//        double result = boozedCalculator.calculateTotalClearAlcohol(wines);
//
//        // Assert
//        // Verify the result as needed for the test
//    }
//}
