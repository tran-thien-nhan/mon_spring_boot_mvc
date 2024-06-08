package com.example.sp2.repository;

import com.example.sp2.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> { // jpa repository có nghĩa là interface này sẽ thực hiện các thao tác với database
    // JpaRepository<Patient, Long> có nghĩa là interface này sẽ thực hiện các thao tác với database với kiểu dữ liệu là Patient và kiểu dữ liệu của khóa chính là Long

}
