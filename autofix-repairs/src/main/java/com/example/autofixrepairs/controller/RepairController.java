package com.example.autofixrepairs.controller;

import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.service.DetailService;
import com.example.autofixrepairs.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repairs")


public class RepairController {
    @Autowired
    RepairService repairService;
    @Autowired
    DetailService detailService;

    //todos los weas
    @GetMapping("/")
    //este obtiene todos los registros existentes
    public ResponseEntity<List<Repair>> getAllRepair() {
        List<Repair> recordHistory = repairService.getRecordRepository();
        return ResponseEntity.ok(recordHistory);
    }

    //uno solo
    @GetMapping("/repair-patent/{patent}")
    //recibe solo un registro
    public ResponseEntity<Repair> getOneRepairByPatent(@PathVariable String patent) {
        Repair recordHistory = repairService.getOneRecordRespository(patent);
        return ResponseEntity.ok(recordHistory);
    }

    //busca por id para usarla en los detalles
    @GetMapping("/repair-id/{id}")
    //recibe solo un registro
    public ResponseEntity<Repair> getOneRepairById(@PathVariable Long id) {
        Repair recordHistory = repairService.getOneRecordPORID(id);
        return ResponseEntity.ok(recordHistory);
    }

    //busca los que tengan el mismo tipo de reparacion
    @GetMapping("/by-repair/{repairName}")
    public ResponseEntity<List<Repair>> getRepairListByName(@PathVariable String repairName) {
        List<Repair> repair = repairService.getRepairListByName(repairName);
        return ResponseEntity.ok(repair);
    }

    @GetMapping("/Todos/{patent}")
    public ResponseEntity<List<Repair>> getRepairByPatentTodos(@PathVariable String patent) {
        List<Repair> recordHistory = repairService.getTodoslosrepairsDeunaPatente(patent);
        return ResponseEntity.ok(recordHistory);
    }

    @PostMapping("/")
    public ResponseEntity<Repair> saveRecord(@RequestBody Repair recordHistory) {
        Repair recordHistoryNew = repairService.saveRecord(recordHistory);
        return ResponseEntity.ok(recordHistoryNew);
    }

    @DeleteMapping("/repair-id-delete/{id}")
    public ResponseEntity<Boolean> deleteRecordById(@PathVariable Long id) throws Exception {
        var isDeleted = repairService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/newRepair/")
    //este necesita, las fechas los descuentos, las recargas y el iva por separado
    public ResponseEntity<Repair> updateRecord(@RequestBody Repair rec){

        //Creamos una nueva entidad con new
        Repair repairHistory = new Repair();

        //Conseguimos los costos para colocarlo en el auto
        double totalAmount = detailService.getCostbyRepair(rec);

        double precioNormal = detailService.precioSegunReparacionyMotor(rec);

        //IVA
        double iva = detailService.IVASOLO(precioNormal);

        double costonormal= precioNormal + iva;

        //recargos
        double recargos = detailService.getCostRecharges( precioNormal, rec);

        //descuentos
        double descuentos = detailService.getCostDiscounts(precioNormal, rec);

        //Vamos a colocar cada uno de los componentes en el nuevo auto
        repairHistory.setId(rec.getId());
        repairHistory.setPatent(rec.getPatent());

        //FECHAS CLIENTE
        repairHistory.setAdmissionHour(rec.getAdmissionHour());
        repairHistory.setAdmissionDateDayName(rec.getAdmissionDateDayName());
        repairHistory.setAdmissionDateDay(rec.getAdmissionDateDay());
        repairHistory.setAdmissionDateMonth(rec.getAdmissionDateMonth());

        repairHistory.setRepairType(rec.getRepairType());

        //FECHAS TALLER
        repairHistory.setDepartureDateDay(rec.getDepartureDateDay());
        repairHistory.setDepartureDateMonth(rec.getDepartureDateMonth());
        repairHistory.setDepartureHour(rec.getDepartureHour());

        //FECHAS SALIDA CLIENTE
        repairHistory.setClientDateDay(rec.getClientDateDay());
        repairHistory.setClientDateMonth(rec.getClientDateMonth());
        repairHistory.setClientHour(rec.getClientHour());

        repairHistory.setTotalAmount(totalAmount);

        repairHistory.setTotalDiscounts(descuentos);
        repairHistory.setTotalIva(iva);
        repairHistory.setTotalRecharges(recargos);

        Repair repairHistoryNew = repairService.saveRecord(repairHistory);
        return ResponseEntity.ok(repairHistoryNew);
    }

    @GetMapping("/byCar/{id}")
    public ResponseEntity<List<Repair>> getByStudentId(@PathVariable("carId") Long carId) {
        List<Repair> repairs = repairService.byCarId(carId);
        return ResponseEntity.ok(repairs);
    }

    @PutMapping("/updateRepairBONOMARCA/{id}")
    public ResponseEntity<Repair> updateRecordDESCUENTOMARCA(@PathVariable Long id) {
        Repair rep = repairService.getOneRecordPORID(id);
        if (rep == null) {
            return ResponseEntity.notFound().build();
        }
        String patent = rep.getPatent();
        double precioActual = rep.getTotalAmount();
        double descuentoMarca = detailService.DescuentoSegunMarca1(patent, precioActual);

        // Actualizamos datos
        rep.setTotalAmount(rep.getTotalAmount() - descuentoMarca);
        rep.setTotalDiscounts(rep.getTotalDiscounts() + descuentoMarca);
        Repair repairHistoryNew = repairService.saveRecord(rep);

        return ResponseEntity.ok(repairHistoryNew);
    }


    @GetMapping("/marca/{id}")
    public double marca(@PathVariable Long id){
        Repair rep = repairService.getOneRecordPORID(id);
        String patent = rep.getPatent();
        double precioActual= rep.getTotalAmount();
        double descuento = detailService.DescuentoSegunMarca1(patent, precioActual);

        return descuento;
    }

}