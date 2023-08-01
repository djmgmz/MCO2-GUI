package project.ccprog3mco2gui.model;

import project.ccprog3mco2gui.model.*;
import java.util.ArrayList;

public class VendingMachineService {

    private ArrayList<RegularVendingMachine> regularVendingMachines;
    private ArrayList<SpecialVendingMachine> specialVendingMachines;

    // The singleton instance
    private static VendingMachineService instance = null;

    // Make the constructor private to prevent instantiation
    private VendingMachineService() {
        regularVendingMachines = new ArrayList<>();
        specialVendingMachines = new ArrayList<>();
    }

    // Provide a static method to get the singleton instance
    public static VendingMachineService getInstance() {
        if (instance == null) {
            instance = new VendingMachineService();
        }
        return instance;
    }

    public void addRegularMachine(RegularVendingMachine machine) {
        regularVendingMachines.add(machine);
    }

    public void addSpecialMachine(SpecialVendingMachine machine) {
        specialVendingMachines.add(machine);
    }

    public ArrayList<RegularVendingMachine> getRegularVendingMachines() {
        return regularVendingMachines;
    }

    public ArrayList<SpecialVendingMachine> getSpecialVendingMachines() {
        return specialVendingMachines;
    }
}