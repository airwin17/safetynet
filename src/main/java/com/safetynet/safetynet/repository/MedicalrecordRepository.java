package com.safetynet.safetynet.repository;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.model.Medicalrecord;

public interface MedicalrecordRepository {
    public void postMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void putMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public Optional<Medicalrecord> getMedicalrecordByNames(String firstName, String lastName);

}
