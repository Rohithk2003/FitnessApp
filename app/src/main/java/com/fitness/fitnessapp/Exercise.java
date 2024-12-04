package com.fitness.fitnessapp;

import java.util.List;

public class Exercise {
    private String name;
    private String force;
    private String level;
    private String mechanic;
    private String equipment;
    private List<String> primaryMuscles;
    private List<String> secondaryMuscles;
    private List<String> instructions;
    private String category;
    private List<String> images;
    private String id;

    // Constructor
    public Exercise(String name, String force, String level, String mechanic, String equipment,
                    List<String> primaryMuscles, List<String> secondaryMuscles, List<String> instructions,
                    String category, List<String> images, String id) {
        this.name = name;
        this.force = force;
        this.level = level;
        this.mechanic = mechanic;
        this.equipment = equipment;
        this.primaryMuscles = primaryMuscles;
        this.secondaryMuscles = secondaryMuscles;
        this.instructions = instructions;
        this.category = category;
        this.images = images;
        this.id = id;
    }

    // Getters
    public String getName() { return name; }
    public String getForce() { return force; }
    public String getLevel() { return level; }
    public String getMechanic() { return mechanic; }
    public String getEquipment() { return equipment; }
    public List<String> getPrimaryMuscles() { return primaryMuscles; }
    public List<String> getSecondaryMuscles() { return secondaryMuscles; }
    public List<String> getInstructions() { return instructions; }
    public String getCategory() { return category; }
    public List<String> getImages() { return images; }
    public String getId() { return id; }

    // Setters (if needed)
    public void setName(String name) { this.name = name; }
    public void setForce(String force) { this.force = force; }
    public void setLevel(String level) { this.level = level; }
    public void setMechanic(String mechanic) { this.mechanic = mechanic; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
    public void setPrimaryMuscles(List<String> primaryMuscles) { this.primaryMuscles = primaryMuscles; }
    public void setSecondaryMuscles(List<String> secondaryMuscles) { this.secondaryMuscles = secondaryMuscles; }
    public void setInstructions(List<String> instructions) { this.instructions = instructions; }
    public void setCategory(String category) { this.category = category; }
    public void setImages(List<String> images) { this.images = images; }
    public void setId(String id) { this.id = id; }
}
