package org.levelup.reflection;

public class Phone{
    private String model;
    public int ram;
    private double cpu;

    private Phone(double cpu) {
        this.cpu = cpu;
    }

    public Phone() {
    }

    public Phone(String model) {
        this.model = model;
    }

    public Phone(String model, int ram, double cpu) {
        this.model = model;
        this.ram = ram;
        this.cpu = cpu;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }
}
