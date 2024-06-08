package com.example.sp2.services;

import com.example.sp2.models.Patient;
import com.example.sp2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // đánh dấu đây là một service
public class PatientService{
    @Autowired // tự động tìm kiếm và inject một bean vào một bean khác
    private PatientRepository patientRepository;
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null); // trả về một bệnh nhân theo id, nếu không tìm thấy thì trả về null
    }

    //add a patient
    public void savePatient(Patient patient) {
        patientRepository.save(patient); // thêm một bệnh nhân vào database
    }

    //delete a patient
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id); // xóa một bệnh nhân theo id
    }

    //update a patient
    public void updatePatient(Patient patient) {
        Patient existingPatient = patientRepository.findById(patient.getId()).orElse(null); // tìm kiếm một bệnh nhân theo id, nếu không tìm thấy thì trả về null

        if (existingPatient == null) {
            return;
        }
        existingPatient.setId(patient.getId());
        existingPatient.setName(patient.getName());
        existingPatient.setAge(patient.getAge());
        existingPatient.setGender(patient.getGender());
        existingPatient.setDisease(patient.getDisease());

        patientRepository.save(existingPatient); // cập nhật thông tin của bệnh nhân
    }

    public List<Patient> searchByAgeRange(int minAge, int maxAge) {
        List<Patient> allPatients = patientRepository.findAll();

        return allPatients.stream()
                .filter(patient -> patient.getAge() >= minAge && patient.getAge() <= maxAge)
                .collect(Collectors.toList());
    }
}
