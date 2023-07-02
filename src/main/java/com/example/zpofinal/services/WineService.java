package com.example.zpofinal.services;

import com.example.zpofinal.helpers.CalculationResult;
import com.example.zpofinal.models.Boozed;
import com.example.zpofinal.models.Wine;
import com.example.zpofinal.repositories.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class WineService {

    @Autowired
    private WineRepository wineRepository;

    public Iterable<Wine> getAllWines() {
        try {
            return wineRepository.findAll();
        }
        catch (Exception ex){
            return null;
        }
    }

    public Wine getWineById(Long id) {
        return wineRepository.findById(id).orElse(null);
    }

    public Wine createWine(Wine wine) {
        try {
            wineRepository.save(wine);
            return wine;
        }
        catch (Exception ex){
            System.out.println("Problem with adding Wine");
            return null;
        }
    }

    public Wine updateWine(Long id, Wine updatedWine) {
        try{
            Wine foundWine = wineRepository.findById(id).orElse(null);
            if (foundWine != null) {
                foundWine.setName(updatedWine.getName());
                foundWine.setBrand(updatedWine.getBrand());
                foundWine.setType(updatedWine.getType());
                foundWine.setVolume(updatedWine.getVolume());
                foundWine.setPrice(updatedWine.getPrice());
                foundWine.setVoltage(updatedWine.getVoltage());
                wineRepository.save(foundWine);
                return foundWine;
            }
            else return null;
        }
        catch (Exception ex){
            return null;
        }
    }

    public void deleteWine(Long id) {
        try{
            wineRepository.deleteById(id);
        }
        catch (Exception ex){
            System.out.println("Problem with deleting Wine");
        }
    }

    public Iterable<Wine> bestSelection(double budget) {
        return StreamSupport.stream(wineRepository.findAll().spliterator(), false)
                .filter(wine -> wine.getPrice() <= budget)
                .sorted(Comparator.comparingDouble(Wine::getVoltagePerVolume).reversed()
                        .thenComparingDouble(Wine::getPrice))
                .collect(Collectors.collectingAndThen(Collectors.toList(), wines -> {
                    List<Wine> selectedWines = new ArrayList<>();
                    double totalPrice = 0.0;
                    double totalClearAlcohol = 0.0;
                    for (Wine wine : wines) {
                        if (totalPrice + wine.getPrice() <= budget) {
                            selectedWines.add(wine);
                            totalPrice += wine.getPrice();
                            totalClearAlcohol += wine.getClearAlcohol();
                        } else {
                            break;
                        }
                    }
                    System.out.println(totalPrice);
                    System.out.println(totalClearAlcohol);
                    return selectedWines;
                }));
    }
    //region Booze
    public ResponseEntity<CalculationResult> calculateBooze(Boozed boozed) {
        double maxbloodAlcoholLevel;
        double genderConverter;

        if (boozed.isMale()) {
            maxbloodAlcoholLevel = boozed.getTimeTillClasses() * 0.15 + boozed.getDestinatedBloodAlcoholLevel();
            genderConverter = 0.7;
        } else {
            maxbloodAlcoholLevel = boozed.getTimeTillClasses() * 0.1 + boozed.getDestinatedBloodAlcoholLevel();
            genderConverter = 0.6;
        }

        double maxClearAlcohol = maxbloodAlcoholLevel * genderConverter * boozed.getWeight();

        Iterable<Wine> allWines = wineRepository.findAll();
        List<Wine> availableWines = new ArrayList<>();
        allWines.forEach(availableWines::add);

        double closestDifference = Double.MAX_VALUE;

        List<Wine> bestCombination = findBestCombination(new ArrayList<>(availableWines), maxClearAlcohol, new ArrayList<>(), new ArrayList<>(), closestDifference);
        double totalClearAlcohol = calculateTotalClearAlcohol(bestCombination);
        double reachedAlcoholLevel = totalClearAlcohol / (genderConverter * boozed.getWeight());

        double elapsedHours = boozed.getTimeTillClasses();
        double reductionRate = (reachedAlcoholLevel - boozed.getDestinatedBloodAlcoholLevel()) / elapsedHours;
        reachedAlcoholLevel -= reductionRate * elapsedHours;

        CalculationResult calculationResult = new CalculationResult(bestCombination, reachedAlcoholLevel);
        return new ResponseEntity<>(calculationResult, HttpStatus.OK);
    }

    private List<Wine> findBestCombination(List<Wine> wines, double targetClearAlcohol, List<Wine> currentCombination, List<Wine> bestCombination, double closestDifference) {
        return wines.stream()
                .flatMap(wine -> {
                    List<Wine> newWines = new ArrayList<>(wines);
                    List<Wine> newCurrentCombination = new ArrayList<>(currentCombination);
                    newCurrentCombination.add(wine);
                    newWines.remove(wine);
                    double currentClearAlcohol = calculateTotalClearAlcohol(newCurrentCombination);
                    double difference = Math.abs(currentClearAlcohol - targetClearAlcohol);

                    if (difference < closestDifference) {
                        List<Wine> newBestCombination = new ArrayList<>(newCurrentCombination);
                        return Stream.of(findBestCombination(new ArrayList<>(newWines), targetClearAlcohol, newCurrentCombination, newBestCombination, difference));
                    }

                    if (currentClearAlcohol >= targetClearAlcohol || newWines.isEmpty()) {
                        return Stream.of(bestCombination);
                    }

                    return Stream.of(findBestCombination(new ArrayList<>(newWines), targetClearAlcohol, newCurrentCombination, bestCombination, closestDifference));
                })
                .min(Comparator.comparingDouble(this::calculateCombinationCost))
                .orElse(bestCombination);
    }

    private double calculateCombinationCost(List<Wine> combination) {
        System.out.println(combination.stream()
                .mapToDouble(Wine::getPrice)
                .sum());
        return combination.stream()
                .mapToDouble(Wine::getPrice)
                .sum();
    }


    private double calculateTotalClearAlcohol(List<Wine> wines) {
        return wines.stream()
                .mapToDouble(Wine::getClearAlcohol)
                .sum();
    }
//endregion
}
