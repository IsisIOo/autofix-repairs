package com.example.autofixrepairs.repository;

import com.example.autofixrepairs.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

        //obtener un solo registro al cual le puedo hacer un get luego
        //@Query(value = "SELECT * FROM repair WHERE repair.patent = :patent", nativeQuery = true)
        //Repair findByPatentOne(@Param("patent") String patent);

        @Query(value = "SELECT * FROM repair WHERE car.id = :id", nativeQuery = true)
        List<Repair> findRepairByCarId(@Param("id") Long id);


        @Query(value = "SELECT * FROM repair WHERE repair.patent = :patent", nativeQuery = true)
        Repair findByPatentOne(@Param("patent") String patent);

        //ENCUENTRA TODAS LAS REPARACIONES HECHAS A UN AUTO
        @Query(value = "SELECT * FROM repair WHERE repair.patent = :patent", nativeQuery = true)
        List<Repair> findByPatentRepairs(@Param("patent") String patent);

        @Query(value = "SELECT * FROM repair WHERE repair.repair_type LIKE %:repairType%", nativeQuery = true)
        List<Repair> findByRepairName(@Param("repairType") String repairType);




}
