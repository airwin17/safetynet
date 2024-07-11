package com.safetynet.safetynet.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.safetynet.safetynet.model.Medicalrecord;
import com.safetynet.safetynet.repository.MedicalrecordRepository;
import com.safetynet.safetynet.repository.impl.MedicalrecordRepositoryImpl;
@Service
public class MedicalrecordService {
    MedicalrecordRepository medicalrecordRepositoryImpl;
    public void addMedicalrecord(Medicalrecord medicalrecord) throws IOException{
        medicalrecordRepositoryImpl=new MedicalrecordRepositoryImpl();
        medicalrecordRepositoryImpl.postMedicalrecord(medicalrecord);
    }
    public void updateMedicalrecord(Medicalrecord medicalrecord) throws IOException{
        medicalrecordRepositoryImpl=new MedicalrecordRepositoryImpl();
        medicalrecordRepositoryImpl.putMedicalrecord(medicalrecord);
    }
    public void deleteMedicalrecord(Medicalrecord medicalrecord) throws IOException{
        medicalrecordRepositoryImpl=new MedicalrecordRepositoryImpl();
        medicalrecordRepositoryImpl.deleteMedicalrecord(medicalrecord);
    }
}
