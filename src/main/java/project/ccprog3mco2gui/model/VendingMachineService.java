package project.ccprog3mco2gui.model;

import project.ccprog3mco2gui.model.*;
import java.util.ArrayList;
/**
 * A service class to manage instances of RegularVendingMachine and SpecialVendingMachine.
 * This class uses the Singleton design pattern to ensure only a single instance of VendingMachineService exists.
 */
public class VendingMachineService {
    /**
     * An ArrayList storing instances of RegularVendingMachine.
     */
    private ArrayList<RegularVendingMachine> regularVendingMachines;
    /**
     * An ArrayList storing instances of SpecialVendingMachine.
     */
    private ArrayList<SpecialVendingMachine> specialVendingMachines;

    /**
     * The singleton instance of the VendingMachineService.
     */
    private static VendingMachineService instance = null;

    /**
     * Private constructor to prevent instantiation of VendingMachineService.
     * Initializes empty ArrayLists for regularVendingMachines and specialVendingMachines.
     */
    private VendingMachineService() {
        regularVendingMachines = new ArrayList<>();
        specialVendingMachines = new ArrayList<>();
    }

    /**
     * Provides a static method to get the singleton instance of VendingMachineService.
     * If no instance exists, it creates one.
     *
     * @return The singleton instance of VendingMachineService.
     */    public static VendingMachineService getInstance() {
        if (instance == null) {
            instance = new VendingMachineService();
        }
        return instance;
    }
    /**
     * Adds a RegularVendingMachine instance to the list of regular vending machines.
     *
     * @param machine The RegularVendingMachine instance to add.
     */
    public void addRegularMachine(RegularVendingMachine machine) {
        regularVendingMachines.add(machine);
    }
    /**
     * Adds a SpecialVendingMachine instance to the list of special vending machines.
     *
     * @param machine The SpecialVendingMachine instance to add.
     */
    public void addSpecialMachine(SpecialVendingMachine machine) {
        specialVendingMachines.add(machine);
    }

    /**
     * Gets the list of RegularVendingMachine instances.
     *
     * @return The ArrayList of RegularVendingMachine instances.
     */
    public ArrayList<RegularVendingMachine> getRegularVendingMachines() {
        return regularVendingMachines;
    }

    /**
     * Gets the list of SpecialVendingMachine instances.
     *
     * @return The ArrayList of SpecialVendingMachine instances.
     */
    public ArrayList<SpecialVendingMachine> getSpecialVendingMachines() {
        return specialVendingMachines;
    }
}