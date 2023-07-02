package com.example.zpofinal.helpers;

import com.example.zpofinal.models.Wine;

public class CalculationResult {
    private Iterable<Wine> wines;
    private Double reachedAlcoholLevel;

    public CalculationResult(Iterable<Wine> wines, Double reachedAlcoholLevel) {
        this.wines = wines;
        this.reachedAlcoholLevel = reachedAlcoholLevel;
    }

    public Iterable<Wine> getWines() {
        return wines;
    }

    public void setWines(Iterable<Wine> wines) {
        this.wines = wines;
    }

    public Double getReachedAlcoholLevel() {
        return reachedAlcoholLevel;
    }

    public void setReachedAlcoholLevel(Double reachedAlcoholLevel) {
        this.reachedAlcoholLevel = reachedAlcoholLevel;
    }
}
