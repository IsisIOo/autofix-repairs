package com.example.autofixrepairs.service;

import com.example.autofixrepairs.entity.Details;
import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.model.Car;
import com.example.autofixrepairs.model.RepairList;
import com.example.autofixrepairs.repository.DetailsRepository;
import com.example.autofixrepairs.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DetailService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    RepairRepository repairRepository;

    public Details saveDetail(Details detalle){
        return detailsRepository.save(detalle);
    }

    public Details getOneDetail(String patent){
        return detailsRepository.findByPatentDetails(patent);
    }



    public Car getCar(String patent) {
        Car car = restTemplate.getForObject("http://autofix-car/api/car/carpatent/" + patent, Car.class);
        return car;
    }

    //public Details getDetailsById(Long id){
    //    return detailsRepository.findDetailsById(id);
    //}

    public RepairList getPriceRepairs(String repair) {
        RepairList price = restTemplate.getForObject("http://autofix-list-repair/api/repair-list/by-repair/" + repair, RepairList.class);
        return price;
    }

    public double precioSegunReparacionyMotor(Repair rec) {
        double total_price = 0;
        String patente_auto = rec.getPatent(); //obtiene el id de un repair. con el id de repair lo
        // busca en details y desde details se recupera la patente y la demas info del auto

        String motor = getCar(patente_auto).getMotorType();
        System.out.println(motor);

        //obtengo el id de repair, a partir de ese id hago la funcion getdetailsbyid y
        // puedo obtener las reparaciones que se le hicieron al auto
        String repairtype = rec.getRepairType();

        //esto es mas que nada para sacar la cantidad de reparaciones, el tamano del for
        String[] repairArray = repairtype.split(",");
        List<String> repairList = Arrays.asList(repairArray);


        System.out.println(repairtype); //entrega raparacines

        //esto esta debido a que no se puede usar la funcion para buscar la reparacion con el string compelto cuando son mas de una reparacion
        for(int i=0; i<repairList.size(); i++){

            if (motor.toLowerCase().equals("gasolina")) {
                if (repairtype.toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                    System.out.println("el precio de las reparaciones de frenos es:" + getPriceRepairs(repairList.get(i)).getGasolineAmount());
                }
                if (repairtype.toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();

                }
                if (repairtype.toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount(); //4
                }
                if (repairtype.toLowerCase().contains("reparación del sistema eléctrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparación de neumáticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones de la suspensión y la dirección")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparación del sistema de aire acondicionado y calefacción")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount(); //9
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairtype.toLowerCase().contains("reparación y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
            }

            if (motor.toLowerCase().equals("diesel")) {
                if (repairtype.toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();
                }
                if (repairtype.toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //2
                }
                if (repairtype.toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //3
                }
                if (repairtype.toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //4
                }
                if (repairtype.toLowerCase().contains("reparación del sistema eléctrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();//5
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //6
                }
                if (repairtype.toLowerCase().contains("reparación de neumáticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //7
                }
                if (repairtype.toLowerCase().contains("reparaciones de la suspensión y la dirección")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //8
                }
                if (repairtype.toLowerCase().contains("reparación del sistema de aire acondicionado y calefacción")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //9
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //10
                }
                if (repairtype.toLowerCase().contains("reparación y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();
                }
            }

            if (motor.toLowerCase().equals("híbrido")) {
                if (repairtype.toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount(); //4
                }
                if (repairtype.toLowerCase().contains("reparación del sistema eléctrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparación de neumáticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones de la suspensión y la dirección")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparación del sistema de aire acondicionado y calefacción")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount(); //9
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairtype.toLowerCase().contains("reparación y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
            }

            if (motor.toLowerCase().equals("eléctrico")) {
                if (repairtype.toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();//4
                }
                if (repairtype.toLowerCase().contains("reparación del sistema eléctrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price +0;
                }
                if (repairtype.toLowerCase().contains("reparación de neumáticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("reparaciones de la suspensión y la dirección")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairtype.toLowerCase().contains("reparación del sistema de aire acondicionado y calefacción")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount(); //9
                }
                if (repairtype.toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price ;
                }
                if (repairtype.toLowerCase().contains("reparación y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
            }
            else{
                total_price= total_price+0;
            }
        }
            System.out.println("el precio total sin nada. " + total_price);
            return total_price;
    }

    //-----------Descuentos-----------------
    public double DescuentosSegunHora(Repair rec, double total_price) {
        // ahora veo si aplica el descuento segun la hora de ingreso

        //agregar dia
        int hour = rec.getAdmissionHour();//hora para determinar si se le aplica descuento por hora de llegada
        String day = rec.getAdmissionDateDayName().toLowerCase();//dia para determinar si se le aplica descuento por dia de llegada
        if (9 < hour && hour < 12 ) {//agregar que se entre lunes y jueves
            if(day.toLowerCase().equals("jueves")  || day.toLowerCase().equals("lunes")) {
                double total_price_hour = total_price * 0.1;
                total_price = total_price - total_price_hour;
                System.out.println("El descuento aplicado por la hora RECORD SERIVE SIN 1 : " + total_price_hour);
            }
        }
        else {
            total_price = total_price;
        }
        System.out.println("Precio total de la reparación con descuento por hora: " + total_price);
        return total_price;
    }

    //descuento segun marca, aun tengo dudas de este y correo blabla
    public double DescuentoSegunMarca(String patent, double total_price) {
        //descuento segun marca
        String brand = getCar(patent).getBrand();
        if (brand.toLowerCase().equals("toyota")) {
            double total_price_brand = total_price * 0.1;
            total_price = total_price - total_price_brand;
            System.out.println("El descuento aplicado por la marca Toyota: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("ford")) {
            double total_price_brand = total_price * 0.15;
            total_price = total_price - total_price_brand;
            System.out.println("El descuento aplicado por la marca Ford: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("hyundai")) {
            double total_price_brand = total_price * 0.2;
            total_price = total_price - total_price_brand;
            System.out.println("El descuento aplicado por la marca Hyundai: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("honda")) {
            double total_price_brand = total_price * 0.25;
            total_price = total_price - total_price_brand;
            System.out.println("El descuento aplicado por la marca Honda: " + total_price_brand);
        }

        else {
            total_price = total_price;
            System.out.println("No se aplicó descuento por marca");
        }
        System.out.println("Precio total de la reparación con descuento por marca: " + total_price);
        return total_price;
    }

    //bototottottoon
    public double DescuentoSegunMarca1(Repair rec, double total_price) {
        //descuento segun marca
        double total_price_brand = 0;
        String brand = getCar(rec.getPatent()).getBrand();
        if (brand.toLowerCase().equals("toyota")) {
            total_price_brand = total_price -70000;
            System.out.println("El descuento aplicado por la marca Toyota: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("ford")) {
             total_price_brand = total_price -50000;
            System.out.println("El descuento aplicado por la marca Ford: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("hyundai")) {
            total_price_brand = total_price - 30000;
            System.out.println("El descuento aplicado por la marca Hyundai: " + total_price_brand);
        }
        if (brand.toLowerCase().equals("honda")) {
            total_price_brand = total_price - 40000;
            System.out.println("El descuento aplicado por la marca Honda: " + total_price_brand);
        }

        else {
            total_price = total_price;
            System.out.println("No se aplicó descuento por marca");
        }
        System.out.println("Precio total de la reparación con descuento por marca: " + total_price_brand);
        return total_price_brand;
    }

    //------------------Recargos-----------------
    public double RecargoPorKilometraje(String patent, double total_price) {
        //recargo por kilometraje
        double total_price_km=0;
        String type1 = getCar(patent).getType();
        int km = getCar(patent).getKilometers();
        if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
            if (km <= 5000) {
                total_price = total_price;
                System.out.println("No se aplicó recargo por kilometraje bajo 5000");
            }
            if (5001 < km && km <= 12000) {
                total_price_km = total_price * 0.03;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a Sedan por kilometraje sobre 5000: " + total_price_km);
            }
            if (12001 < km && km <= 25000) {
                total_price_km = total_price * 0.07;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a Sedan por kilometraje sobre 12000: " + total_price_km);
            }
            if (25001 < km && km <= 40000) {
                total_price_km = total_price * 0.12;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a Sedan por kilometraje sobre 25000: " + total_price_km);
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a Sedan por kilometraje sobre 40000: " + total_price_km);
            }
        }

        if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("furgoneta") || type1.toLowerCase().equals("pickup")) {
            if (km < 5000) {
                total_price = total_price;
                System.out.println("No se aplicó recargo por kilometraje bajo 5000");
            }
            if (5001 < km && km < 12000) {
                total_price_km = total_price * 0.05;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a SUV por kilometraje sobre 5000: " + total_price_km);
            }
            if (12001 < km && km < 25000) {
                total_price_km = total_price * 0.09;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a SUV por kilometraje sobre 12000: " + total_price_km);
            }
            if (25001 < km && km < 40000) {
                total_price_km = total_price * 0.12;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a SUV por kilometraje sobre 25000: " + total_price_km);
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
                total_price = total_price + total_price_km;
                System.out.println("El recargo aplicado a SUV por kilometraje sobre 40000: " + total_price_km);
            }
        }
        System.out.println("Precio total de la reparación con recargo por kilometraje kakaakaka: " + total_price +"y el valor es " + total_price_km);
        System.out.println("los kilometros son:" +km);
        System.out.println("el recargo por kilometraje es:" + total_price_km);
        return total_price;
    }

    public double recargoPorAntiguedad(String patent, double total_price) {
        //recargo por antiguedad
        int year_car = getCar(patent).getProductionYear();
        String type1 = getCar(patent).getType();
        if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
            if ((2024 - year_car) <= 5) {
                total_price = total_price;
            }

            if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
                double total_price_year = total_price * 0.05;
                total_price = total_price + total_price_year;
            }

            if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
                double total_price_year = total_price * 0.09;
                total_price = total_price + total_price_year;
            }

            if ((2024 - year_car) >= 16) {
                double total_price_year = total_price * 0.15;
                total_price = total_price + total_price_year;
            }
        }

        if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("furgoneta") || type1.toLowerCase().equals("pickup") ) {
            if ((2024 - year_car) <= 5) {
                total_price = total_price;

            }

            if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
                double total_price_year = total_price * 0.07;
                total_price = total_price + total_price_year;

            }

            if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
                double total_price_year = total_price * 0.11;
                total_price = total_price + total_price_year;

            }

            if ((2024 - year_car) >= 16) {
                double total_price_year = total_price * 0.2;
                total_price = total_price + total_price_year;

            }
        }

        return total_price;
    }

    public double recargoPorAtraso(Repair rec, double total_price) {
        //fechas de retiro indicadas por el taller
        int dia_retiro_taller = rec.getDepartureDateDay();
        int mes_retiro_taller = rec.getDepartureDateMonth();

        //fecha retirada por el cliente
        int dia_retiro_cliente = rec.getClientDateDay();
        int mes_retiro_cliente = rec.getClientDateMonth();

        //si el retiro del cliente es mayor al retiro del taller es pq está atrasado
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && mes_retiro_cliente == mes_retiro_taller) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;
            System.out.println("el cliente esta retrasado por " + retraso + "DIAS");
            //corresponde a la cantidad de dias de retraso por 5% de cada dia que se demoro
            double recargo_dias = retraso * 0.05 * total_price;
            total_price = total_price + recargo_dias;

        }
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && (mes_retiro_cliente - mes_retiro_taller) > 0) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;
            int retraso_meses = mes_retiro_cliente - mes_retiro_taller;
            System.out.println("el cliente esta retrasado por " + retraso + "dias y " + retraso_meses + "meses");
            //voy a considerar que los meses solo tienen 30 dias

            //retraso de dias simples
            double recargo_dias = retraso * 0.05 * total_price;
            //por ejemplo, si son 3 meses de diferencia, para sacar los dias seria 3 *30 =90 dias y cada dia tiene 0.05 de recargo
            double recargo_meses = retraso_meses * 30 * 0.05 * total_price;

            double recargo_total = recargo_dias + recargo_meses;
            total_price = total_price + recargo_total;
        }

        System.out.println("el total con atraso es:" + total_price);
        return total_price;
    }


    //----------------IVA----------------
    public double IVATOTAL(double total_price){
        double iva = total_price * 0.19;
        total_price = total_price + iva;
        System.out.println("El IVA aplicado a la reparación: " + iva);
        System.out.println("Precio total de la reparación con IVA: " + total_price);
        return total_price;
    }

    //saca solo el iva para hacerle set
    public double IVASOLO(double total_price){
        double iva = total_price * 0.19;
        return iva;
    }


    //costo total

    //funcion donde debe entrar el historial con todo igual, solo cambiando eso
    public double getCostbyRepair(Repair rec) {
        double total_price1 = 0;

        double total_price = precioSegunReparacionyMotor(rec);
        double iva = IVASOLO(total_price); //le saca el iva al costo original
        double descuento1 = DescuentosSegunHora1(rec, total_price);
        //total_price = DescuentoSegunMarca(patent, total_price);
        //comentada el descuento segun marca porque espero usar essa funcion como un boton
        double recargo1 = recargoPorAtraso1(rec, total_price);
        double recargo2 = RecargoPorKilometraje1(rec, total_price);
        double recargo3 = recargoPorAntiguedad1(rec, total_price);
        //el calculo es recargos al precio normal, descuento al precio normal + ivasolo
        total_price1 = (total_price + recargo1 + recargo2 + recargo3 - descuento1) + iva;
        System.out.println("total"+total_price1);
        System.out.println("iva solo:" + iva);
        System.out.println("descuento solo:" + descuento1);
        System.out.println("recargo1 solo:" + recargo1);
        System.out.println("recargo2 solo:" + recargo2);
        System.out.println("recargo3 solo:" + recargo3);
        System.out.println("precio normal:"+ total_price);
        return total_price1;
    }


    //cosas por separado
    public double getCostDiscounts(double totalAmount, Repair rec){
        double discounts = 0;
        double discounts1 = DescuentosSegunHora1(rec, totalAmount);
        System.out.println("descuento por hora de llegada: "+ discounts1);
        double discounts2 = Descuentoporcantidadreparaciones1(rec, totalAmount);
        System.out.println("descuento por cantidad de reparaciones: "+ discounts2);
        discounts = discounts1 + discounts2 ;
        return discounts;
    }

    public double getCostRecharges(double totalAmount, Repair rec){
        //funcion para sacar los recargos por separado, que sera colocado en repair.
        double recharges = 0;
        System.out.println(totalAmount);
        String patent= rec.getPatent();
        double recharges1 = recargoPorAtraso1(rec, totalAmount);
        System.out.println("recargo por atraso  1 es:" + recharges1);
        double recharges2 = RecargoPorKilometraje1(rec, totalAmount)+ recharges;
        System.out.println("recargo por atraso  2 es:" + recharges2);
        double recharges3 = recargoPorAntiguedad1(rec, totalAmount) + recharges;
        System.out.println("recargo por atraso  3 es:" + recharges3);
        recharges = recharges1 + recharges2 + recharges3;
        return recharges;
    }

//---------------COSAS INDIVIDUALES-----------
    //--------------recargos---------------
public double recargoPorAntiguedad1(Repair rec, double total_price) {
    //recargo por antiguedad
    int year_car = getCar(rec.getPatent()).getProductionYear();
    double total_price_year = 0;
    String type1 = getCar(rec.getPatent()).getType();
    if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
        if ((2024 - year_car) <= 5) {
            System.out.println("No se aplicó recargo por antiguedad bajo 5 años");
        }

        if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
            total_price_year = total_price * 0.05;
        }

        if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
            total_price_year = total_price * 0.09;
        }

        if ((2024 - year_car) >= 16) {
            total_price_year = total_price * 0.15;
        }
    }

    if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("pickup") || type1.toLowerCase().equals("furgoneta")) {
        if ((2024 - year_car) <= 5) {
            System.out.println("No se aplicó recargo por antiguedad bajo 5 años");
        }

        if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
            total_price_year = total_price * 0.07;
        }

        if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
            total_price_year = total_price * 0.11;
        }

        if ((2024 - year_car) >= 16) {
            total_price_year = total_price * 0.2;
        }
    }

    System.out.println("el recargo por antiguedad es:" + total_price_year);
    return total_price_year;
}

    public double RecargoPorKilometraje1(Repair rec, double total_price) {
        //recargo por kilometraje
        double total_price_km=0;
        String type1 = getCar(rec.getPatent()).getType();
        int km = getCar(rec.getPatent()).getKilometers();
        if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
            if (km <= 5000) {
                System.out.println("No se aplicó recargo por kilometraje bajo 5000");
            }
            if (5001 < km && km <= 12000) {
                total_price_km = total_price * 0.03;
            }
            if (12001 < km && km <= 25000) {
                total_price_km = total_price * 0.07;
            }
            if (25001 < km && km <= 40000) {
                total_price_km = total_price * 0.12;
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
            }
        }

        if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("pickup") || type1.toLowerCase().equals("furgoneta")) {
            if (km < 5000) {
                System.out.println("No se aplicó recargo por kilometraje bajo 5000 11111");
            }
            if (5001 < km && km < 12000) {
                total_price_km = total_price * 0.05;
            }
            if (12001 < km && km < 25000) {
                total_price_km = total_price * 0.09;
            }
            if (25001 < km && km < 40000) {
                total_price_km = total_price * 0.12;
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
            }
        }

        System.out.println("recargo km eeeesss :" + total_price_km);
        return total_price_km;
    }

    public double recargoPorAtraso1(Repair rec, double total_price) {
        double recargo_total =0;

        //fechas de retiro indicadas por el taller
        int dia_retiro_taller = rec.getDepartureDateDay();
        int mes_retiro_taller = rec.getDepartureDateMonth();

        //fecha retirada por el cliente
        int dia_retiro_cliente = rec.getClientDateDay();
        int mes_retiro_cliente = rec.getClientDateMonth();

        //si el retiro del cliente es mayor al retiro del taller es pq está atrasado por dias
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && mes_retiro_cliente == mes_retiro_taller) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;

            //corresponde a la cantidad de dias de retraso por 5% de cada dia que se demoro
            recargo_total = retraso * 0.05 * total_price;
            System.out.println("el cliente esta retrasado por " + retraso + "DIAS y su recargo es:" + recargo_total );
            return recargo_total;

        }
        //atrasado por meses y dias
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && (mes_retiro_cliente - mes_retiro_taller) > 0) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;
            int retraso_meses = mes_retiro_cliente - mes_retiro_taller;
            System.out.println("el cliente esta retrasado por " + retraso + "dias y " + retraso_meses + "meses");
            //voy a considerar que los meses solo tienen 30 dias

            //retraso de dias simples
            double recargo_dias = retraso * 0.05 * total_price;
            //por ejemplo, si son 3 meses de diferencia, para sacar los dias seria 3 *30 =90 dias y cada dia tiene 0.05 de recargo
            double recargo_meses = retraso_meses * 30 * 0.05 * total_price;

            recargo_total = recargo_dias + recargo_meses;
            return recargo_total;
        }
        return recargo_total;
    }

    //descuentos
    public double DescuentosSegunHora1(Repair rec, double total_price) {
        // ahora veo si aplica el descuento segun la hora de ingreso
        //agregar dia
        double total_price_hour = 0;
        String patent = rec.getPatent();
        int hour = rec.getAdmissionHour();//hora para determinar si se le aplica descuento por hora de llegada
        String day = rec.getAdmissionDateDayName().toLowerCase();//dia para determinar si se le aplica descuento por dia de llegada
        if (9 < hour && hour < 12 ) {//agregar que se entre lunes y jueves
            if(day.equals("jueves")  ||  day.equals("lunes")) {
                total_price_hour = total_price * 0.1;
                System.out.println("El descuento aplicado por la hora: " + total_price_hour);
                return total_price_hour;
            }
        }
        return total_price_hour;
    }

    public double Descuentoporcantidadreparaciones1(Repair rec, double total_price) {
        //recargo por numero de reparaciones
        double total_price_km=0;
        String type1 = getCar(rec.getPatent()).getMotorType();
        int cantidadReparaciones = repairRepository.findByPatentRepairs(rec.getPatent()).size();

        if (type1.toLowerCase().equals("Gasolina")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price = total_price * 0.05;
                System.out.println("se aplico descuento");
            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.1;
            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.15;
            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.2;
            }
        }

        if (type1.toLowerCase().equals("diesel")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price = total_price * 0.07;

            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.12;

            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.17;

            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.22;

            }
        }

        if (type1.toLowerCase().equals("hibrido")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price = total_price*0.1;

            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.15;

            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.2;

            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.25;

            }
        }

        if (type1.toLowerCase().equals("electrico")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price = total_price*0.08;

            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.13;

            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.18;

            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.23;

            }
        }
        System.out.println("el monto que ingreso a la funcion de descuento por reparaciones: " + total_price);
        System.out.println("el descuento por reparaciones es:" + total_price_km);
        return total_price;
    }

}
