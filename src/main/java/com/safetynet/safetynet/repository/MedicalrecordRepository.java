package com.safetynet.safetynet.repository;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Medicalrecord;
@Component
public interface MedicalrecordRepository {
    public void postMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void putMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public Optional<Medicalrecord> getMedicalrecordByNames(String firstName, String lastName);

}
