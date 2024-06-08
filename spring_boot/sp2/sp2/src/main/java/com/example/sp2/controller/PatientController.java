package com.example.sp2.controller;

import com.example.sp2.models.Patient;
import com.example.sp2.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public String patientList(Model model) {
        List<Patient> list = patientService.getAllPatient();
        model.addAttribute("list", list);
        return "list";
    }

    //create a new patient
    @GetMapping("/patients/new")
    public String showFormCreatePatient(Model model) {
        Patient patient = new Patient();
        patient.setGender("Male");
        model.addAttribute("patient", patient);
        return "new";
    }

    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    //delete a patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return "redirect:/patients";
    }

    //update a patient
    @GetMapping("/edit/{id}")
    public String showFormEditPatient(@PathVariable(value = "id") Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "update";
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute("patient") Patient patient) {
        patientService.updatePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/search")
    public String searchPatientsByAge(@RequestParam("min") Integer minAge, @RequestParam("max") Integer maxAge, Model model) {

        if (minAge.toString().isEmpty() && maxAge.toString().isEmpty()) {
            List<Patient> patients = patientService.getAllPatient();
            if (patients.isEmpty()) {
                model.addAttribute("errorMessage", "No patients found.");
            }
            model.addAttribute("list", patients);
            return "error";
        }

        if (minAge != null && maxAge != null && maxAge < minAge) {
            model.addAttribute("errorMessage", "Max age cannot be less than Min age.");
            List<Patient> patients = patientService.getAllPatient();
            if (patients.isEmpty()) {
                model.addAttribute("errorMessage", "No patients found.");
            }
            model.addAttribute("list", patients);
            return "list";
        }

        List<Patient> patients = patientService.searchByAgeRange(minAge, maxAge);
        model.addAttribute("list", patients);
        return "list";
    }

    @PostMapping("/patients/deleteSelected")
    public String deleteSelectedPatients(@RequestParam(value = "selectedIds", required = false) List<Long> selectedIds) {
        if (selectedIds != null && !selectedIds.isEmpty()) {
            for (Long id : selectedIds) {
                patientService.deletePatientById(id);
            }
        }
        return "redirect:/patients";
    }
}
