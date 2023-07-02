package com.example.zpofinal.controllers;

import com.example.zpofinal.helpers.CalculationResult;
import com.example.zpofinal.models.Boozed;
import com.example.zpofinal.models.Wine;
import com.example.zpofinal.services.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wine")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping
    public Iterable<Wine> getAllWines() {
        return wineService.getAllWines();
    }

    @GetMapping("/{id}")
    public Wine getWineById(@PathVariable("id") Long id) {
        return wineService.getWineById(id);
    }

    @PostMapping
    public Wine createWine(@RequestBody Wine wine) {
        return wineService.createWine(wine);
    }

    @PutMapping("/{id}")
    public Wine updateWine(@PathVariable("id") Long id, @RequestBody Wine updatedWine) {
        return wineService.updateWine(id, updatedWine);
    }

    @DeleteMapping("/{id}")
    public void deleteWine(@PathVariable("id") Long id) {
        wineService.deleteWine(id);
    }

    @GetMapping("/bestselection/{budget}")
    public Iterable<Wine> bestSelection(@PathVariable("budget") double budget){
        return wineService.bestSelection(budget);
    }
    @PostMapping("/boozed")
    public ResponseEntity<CalculationResult> boozed(@RequestBody Boozed boozeRequest){
        return wineService.calculateBooze(boozeRequest);
    }
}
