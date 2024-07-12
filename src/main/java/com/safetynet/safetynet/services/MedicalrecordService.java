package com.safetynet.safetynet.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.repository.MedicalrecordRepository;
@Service
public class MedicalrecordService {
    @Autowired
    MedicalrecordRepository medicalrecordRepositoryImpl;
    public void addMedicalrecord(Medicalrecord medicalrecord) throws IOException{
        
        medicalrecordRepositoryImpl.postMedicalrecord(medicalrecord);
    }
    public void updateMedicalrecord(Medicalrecord medicalrecord) throws IOException{
       
        medicalrecordRepositoryImpl.putMedicalrecord(medicalrecord);
    }
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws IOException{
        medicalrecordRepositoryImpl.deleteMedicalrecord(medicalrecord);
    }
}
