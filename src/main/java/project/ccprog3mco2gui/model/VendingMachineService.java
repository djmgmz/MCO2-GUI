package project.ccprog3mco2gui.model;

import project.ccprog3mco2gui.model.*;
import java.util.ArrayList;

public class VendingMachineService {

    private ArrayList<RegularVendingMachine> regularVendingMachines;
    private ArrayList<SpecialVendingMachine> specialVendingMachines;

    public VendingMachineService() {
        regularVendingMachines = new ArrayList<>();
        specialVendingMachines = new ArrayList<>();
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