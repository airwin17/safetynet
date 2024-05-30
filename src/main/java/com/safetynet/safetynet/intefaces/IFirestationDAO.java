package com.safetynet.safetynet.intefaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynet.Model.Firestation;

public interface IFirestationDAO {
    public void postFirestation(Firestation firestation) throws JsonProcessingException;
    public void putFirestation(Firestation firestation);
    public void deleteFirestation(Firestation firestation);
}
