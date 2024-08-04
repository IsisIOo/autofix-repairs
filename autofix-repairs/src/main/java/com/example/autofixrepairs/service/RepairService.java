package com.example.autofixrepairs.service;

import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.model.Car;
import com.example.autofixrepairs.repository.DetailsRepository;
import com.example.autofixrepairs.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    RestTemplate restTemplate;


    public List<Repair> getRecordRepository(){
        return (List<Repair>) repairRepository.findAll();
    }

    public Repair getOneRecordRespository(String patent){
        return repairRepository.findByPatentOne(patent);
    }

    //por id
    public Repair getOneRecordPORID(Long id){
        return repairRepository.findByIdE(id);
    }

    public List<Repair> getTodoslosrepairsDeunaPatente(String patent){
        return repairRepository.findByPatentRepairs(patent);
    }

    public List<Repair> getRepairListByName(String name) {
        return repairRepository.findByRepairName(name);
    }


    public Repair saveRecord(Repair record){
        return repairRepository.save(record);
    }

    public boolean deleteRecord(Long id) throws Exception {
        try{
            repairRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
                    //---------------MICROSERVICIOS-------------------

//REALIZA LA FUNCION OBTENER UN AUTO DE ACUERDO A UNA PATENTE ESPCIFICA, RETORNA SOLO UN AUTO
    public Car getCar(String patent) {
        Car car = restTemplate.getForObject("http://autofix-car/api/car/carpatent/" + patent, Car.class);
        return car;
    }

    //encuentra los repositorios de un auto
    public List<Repair> byCarId(Long carId) {
        return repairRepository.findRepairByCarId(carId);
    }

}

