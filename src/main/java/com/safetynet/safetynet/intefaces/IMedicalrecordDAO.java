package com.safetynet.safetynet.intefaces;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.Model.Medicalrecord;

public interface IMedicalrecordDAO {
    public void postMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void putMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws JsonProcessingException, IOException;

}
