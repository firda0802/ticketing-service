package com.binar.tix.service;

import com.binar.tix.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class SetupData {

    private final ClassSeats economiClass = new ClassSeats("Economi Class", 35000);
    private final ClassSeats businessClass = new ClassSeats("Business Class", 100000);

    private final Pricing economiPrice = new Pricing(650000);

    private final Pricing businessPrice = new Pricing(1300000);

    @Autowired
    InitializeService initService;

    public void generate() {
        DestinationCity jakarta = new DestinationCity("Jakarta");
        DestinationCity surabaya = new DestinationCity("Surabaya");
        DestinationCity medan = new DestinationCity("Medan");
        DestinationCity makassar = new DestinationCity("Makassar");
        DestinationCity yogyakarta = new DestinationCity("Yogyakarta");
        DestinationCity bali = new DestinationCity("Bali");
        DestinationCity palembang = new DestinationCity("Palembang");

        if (initService.getDestinationCity().isEmpty()) {
            //Destination City
            initService.initDestinationCity(jakarta);
            initService.initDestinationCity(surabaya);
            initService.initDestinationCity(medan);
            initService.initDestinationCity(makassar);
            initService.initDestinationCity(yogyakarta);
            initService.initDestinationCity(bali);
            initService.initDestinationCity(palembang);
        }

        Airport airportJakarta = new Airport("Bandar Udara Internasional Soekarno–Hatta", "Kota Tangerang, Banten 19120", jakarta.getDestinationCityId());
        Airport airportSurabaya = new Airport("Bandar Udara Internasional Juanda", "Jl. Ir. H. Juanda, Betro, Kec. Sedati, Kabupaten Sidoarjo, Jawa Timur 61253", surabaya.getDestinationCityId());
        Airport airportMedan = new Airport("Bandar Udara Internasional Kualanamu", "Jl. Bandara Kuala Namu, Ps. Enam Kuala Namu, Kec. Beringin, Kabupaten Deli Serdang, Sumatera Utara 20553", medan.getDestinationCityId());
        Airport airportMakassar = new Airport("Bandar Udara Internasional Sultan Hasanuddin", "Jalan Airport No.1, Kec. Makassar, Kabupaten Maros, Sulawesi Selatan 90552", makassar.getDestinationCityId());
        Airport airportYogyakarta = new Airport("Bandar Udara Internasional Yogyakarta", "Kepek, Palihan, Kec. Temon, Kabupaten Kulon Progo, Daerah Istimewa Yogyakarta 55654", yogyakarta.getDestinationCityId());
        Airport airportBali = new Airport("Bandar Udara Internasional Ngurah Rai", "Jalan Raya Gusti Ngurah Rai, Tuban, Kec. Kuta, Kabupaten Badung, Bali 80362", bali.getDestinationCityId());
        Airport airportPalembang = new Airport("Bandar Udara Internasional Sultan Mahmud Badaruddin II", "Jl. Bandara Sultan Mahmud Badaruddin II, Talang Betutu, Kec. Sukarami, Kota Palembang, Sumatera Selatan 30761", palembang.getDestinationCityId());

        if (initService.getAirport().isEmpty()) {
            //Airport
            initService.initAirport(airportJakarta);
            initService.initAirport(airportSurabaya);
            initService.initAirport(airportMedan);
            initService.initAirport(airportMakassar);
            initService.initAirport(airportYogyakarta);
            initService.initAirport(airportBali);
            initService.initAirport(airportPalembang);
        }

        //Passenge Type
        PassengerType type1 = new PassengerType("Anak - Anak", "Umur 2 - 11 Tahun");
        PassengerType type2 = new PassengerType("Dewasa", "12 Tahun lebih");
        PassengerType type3 = new PassengerType("Bayi", "Dibawah usia 2 tahun");

        if (initService.getPassengerType().isEmpty()) {
            initService.initPassengerType(type1);
            initService.initPassengerType(type2);
            initService.initPassengerType(type3);
        }

        //Airplane
        Airplane jktEconomyBussines = new Airplane();
        Airplane jktEconomyBussines2 = new Airplane();
        Airplane sbyEconomyBussines = new Airplane();
        Airplane sbyEconomyBussines2 = new Airplane();
        Airplane mdnEconomyBussines = new Airplane();
        Airplane mdnEconomyBussines2 = new Airplane();
        Airplane mksEconomyBussines = new Airplane();
        Airplane mksEconomyBussines2 = new Airplane();
        Airplane ygEconomyBussines = new Airplane();
        Airplane ygEconomyBussines2 = new Airplane();
        Airplane baliEconomyBussines = new Airplane();
        Airplane baliEconomyBussines2 = new Airplane();
        Airplane plbEconomyBussines = new Airplane();
        Airplane plbEconomyBussines2 = new Airplane();
        if (initService.getAirplane().isEmpty()) {
            String typeA = "Boeing 737-800NG";
            String typeB = "Airbus A330-200";
            String typeC = "Airbus A330-300";
            String typeD = "Airbus A330-900neo";

            //Class Seats
            initService.initClassSeats(economiClass);
            initService.initClassSeats(businessClass);

            //Pricing
            economiPrice.setClassId(economiClass.getClassId());
            businessPrice.setClassId(businessClass.getClassId());
            initService.initPricing(economiPrice);
            initService.initPricing(businessPrice);

            //Airplane - Jakarta
            jktEconomyBussines.setType(typeA);
            jktEconomyBussines.setLuggageCapacity(20);
            jktEconomyBussines.setAirportId(airportJakarta.getIdAirport());
            jktEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(jktEconomyBussines);

            jktEconomyBussines2.setType(typeB);
            jktEconomyBussines2.setLuggageCapacity(20);
            jktEconomyBussines2.setAirportId(airportJakarta.getIdAirport());
            jktEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(jktEconomyBussines2);

            //Airplane - Surabaya
            sbyEconomyBussines.setType(typeC);
            sbyEconomyBussines.setLuggageCapacity(20);
            sbyEconomyBussines.setAirportId(airportSurabaya.getIdAirport());
            sbyEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(sbyEconomyBussines);

            sbyEconomyBussines2.setType(typeD);
            sbyEconomyBussines2.setLuggageCapacity(20);
            sbyEconomyBussines2.setAirportId(airportSurabaya.getIdAirport());
            sbyEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(sbyEconomyBussines2);

            //Airplane - Medan
            mdnEconomyBussines.setType(typeA);
            mdnEconomyBussines.setLuggageCapacity(20);
            mdnEconomyBussines.setAirportId(airportMedan.getIdAirport());
            mdnEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mdnEconomyBussines);

            mdnEconomyBussines2.setType(typeB);
            mdnEconomyBussines2.setLuggageCapacity(20);
            mdnEconomyBussines2.setAirportId(airportMedan.getIdAirport());
            mdnEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mdnEconomyBussines2);

            //Airplane - Makassar
            mksEconomyBussines.setType(typeC);
            mksEconomyBussines.setLuggageCapacity(20);
            mksEconomyBussines.setAirportId(airportMakassar.getIdAirport());
            mksEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mksEconomyBussines);

            mksEconomyBussines2.setType(typeD);
            mksEconomyBussines2.setLuggageCapacity(20);
            mksEconomyBussines2.setAirportId(airportMakassar.getIdAirport());
            mksEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(mksEconomyBussines2);

            //Airplane - Yogyakarta
            ygEconomyBussines.setType(typeA);
            ygEconomyBussines.setLuggageCapacity(20);
            ygEconomyBussines.setAirportId(airportYogyakarta.getIdAirport());
            ygEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(ygEconomyBussines);

            ygEconomyBussines2.setType(typeB);
            ygEconomyBussines2.setLuggageCapacity(20);
            ygEconomyBussines2.setAirportId(airportYogyakarta.getIdAirport());
            ygEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(ygEconomyBussines2);

            //Airplane - Bali
            baliEconomyBussines.setType(typeC);
            baliEconomyBussines.setLuggageCapacity(20);
            baliEconomyBussines.setAirportId(airportBali.getIdAirport());
            baliEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(baliEconomyBussines);

            baliEconomyBussines2.setType(typeD);
            baliEconomyBussines2.setLuggageCapacity(20);
            baliEconomyBussines2.setAirportId(airportBali.getIdAirport());
            baliEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(baliEconomyBussines2);

            //Airplane - Palembang
            plbEconomyBussines.setType(typeA);
            plbEconomyBussines.setLuggageCapacity(20);
            plbEconomyBussines.setAirportId(airportPalembang.getIdAirport());
            plbEconomyBussines.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(plbEconomyBussines);

            plbEconomyBussines2.setType(typeB);
            plbEconomyBussines2.setLuggageCapacity(20);
            plbEconomyBussines2.setAirportId(airportPalembang.getIdAirport());
            plbEconomyBussines2.setSeats(generateSeats(economiClass.getClassId(), businessClass.getClassId()));
            initService.initAirplanes(plbEconomyBussines2);
        }

        if (initService.getDestination().isEmpty()) {
            //Destination
            //--Jakarta
            //Jakarta - Surabaya
            Destination djkt1 = new Destination(jakarta.getDestinationCityId(), surabaya.getDestinationCityId(), 305000, 90);
            //Jakarta - Medan
            Destination djkt3 = new Destination(jakarta.getDestinationCityId(), medan.getDestinationCityId(), 720000, 140);
            //Jakarta - Makassar
            Destination djkt5 = new Destination(jakarta.getDestinationCityId(), makassar.getDestinationCityId(), 640000, 145);
            //Jakarta - Yogyakarta
            Destination djkt7 = new Destination(jakarta.getDestinationCityId(), yogyakarta.getDestinationCityId(), 224800, 75);
            //Jakarta - Bali
            Destination djkt9 = new Destination(jakarta.getDestinationCityId(), bali.getDestinationCityId(), 472000, 110);
            //Jakarta - Palembang
            Destination djkt11 = new Destination(jakarta.getDestinationCityId(), palembang.getDestinationCityId(), 212800, 65);

            List<Destination> destJkt = Arrays.asList(djkt1, djkt3, djkt5, djkt7, djkt9, djkt11);
            initService.initDestination(destJkt);
            generateSchedule(destJkt, jktEconomyBussines.getAirplaneId(), jktEconomyBussines2.getAirplaneId());

            //--Surabaya
            //Surabaya - Jakarta
            Destination sj1 = new Destination(surabaya.getDestinationCityId(), jakarta.getDestinationCityId(), 305000, 90);
            //Surabaya - Medan
            Destination sj3 = new Destination(surabaya.getDestinationCityId(), medan.getDestinationCityId(), 1062800, 255);
            //Surabaya - Makassar
            Destination sj5 = new Destination(surabaya.getDestinationCityId(), makassar.getDestinationCityId(), 329600, 90);
            // Surabaya - Yogyakarta
            Destination sj7 = new Destination(surabaya.getDestinationCityId(), yogyakarta.getDestinationCityId(), 129600, 175);
            //Surabaya - Bali
            Destination sj9 = new Destination(surabaya.getDestinationCityId(), bali.getDestinationCityId(), 169600, 55);
            //Surabaya - Palembang
            Destination sj11 = new Destination(surabaya.getDestinationCityId(), palembang.getDestinationCityId(), 521600, 150);

            List<Destination> destSby = Arrays.asList(sj1, sj3, sj5, sj7, sj9, sj11);
            initService.initDestination(destSby);
            generateSchedule(destSby, sbyEconomyBussines.getAirplaneId(), sbyEconomyBussines2.getAirplaneId());

            //--Medan
            //Medan - Jakarta
            Destination mj1 = new Destination(medan.getDestinationCityId(), jakarta.getDestinationCityId(), 752400, 140);
            //Medan - Surabaya
            Destination mj3 = new Destination(medan.getDestinationCityId(), surabaya.getDestinationCityId(), 1062800, 260);
            //Medan - Makassar
            Destination mj5 = new Destination(medan.getDestinationCityId(), makassar.getDestinationCityId(), 1391600, 355);
            //Medan - Yogyakarta
            Destination mj7 = new Destination(medan.getDestinationCityId(), yogyakarta.getDestinationCityId(), 974400, 165);
            //Medan - Bali
            Destination mj9 = new Destination(medan.getDestinationCityId(), bali.getDestinationCityId(), 1222000, 275);
            //Medan - Palembang
            Destination mj11 = new Destination(medan.getDestinationCityId(), palembang.getDestinationCityId(), 543600, 180);

            List<Destination> destMdn = Arrays.asList(mj1, mj3, mj5, mj7, mj9, mj11);
            initService.initDestination(destMdn);
            generateSchedule(destMdn, mdnEconomyBussines.getAirplaneId(), mdnEconomyBussines2.getAirplaneId());

            //--Makassar
            //Makassar - Jakarta
            Destination mkj1 = new Destination(makassar.getDestinationCityId(), jakarta.getDestinationCityId(), 640000, 140);
            //Makassar - Surabaya
            Destination mkj3 = new Destination(makassar.getDestinationCityId(), surabaya.getDestinationCityId(), 329600, 95);
            //Makassar - Medan
            Destination mkj5 = new Destination(makassar.getDestinationCityId(), medan.getDestinationCityId(), 1391600, 340);
            //Makassar - Yogyakarta
            Destination mkj7 = new Destination(makassar.getDestinationCityId(), yogyakarta.getDestinationCityId(), 458800, 120);
            //Makassar - Bali
            Destination mkj9 = new Destination(makassar.getDestinationCityId(), bali.getDestinationCityId(), 398400, 85);
            //Makassar - Palembang
            Destination mkj11 = new Destination(makassar.getDestinationCityId(), palembang.getDestinationCityId(), 850800, 285);

            List<Destination> destMks = Arrays.asList(mkj1, mkj3, mkj5, mkj7, mkj9, mkj11);
            initService.initDestination(destMks);
            generateSchedule(destMks, mksEconomyBussines.getAirplaneId(), mksEconomyBussines2.getAirplaneId());

            //--Yogyakarta
            //Yogyakarta - Jakarta
            Destination yj1 = new Destination(yogyakarta.getDestinationCityId(), jakarta.getDestinationCityId(), 224800, 70);
            //Yogyakarta - Surabaya
            Destination yj3 = new Destination(yogyakarta.getDestinationCityId(), surabaya.getDestinationCityId(), 129600, 210);
            //Yogyakarta - Medan
            Destination yj5 = new Destination(yogyakarta.getDestinationCityId(), medan.getDestinationCityId(), 974400, 165);
            //Yogyakarta - Makassar
            Destination yj7 = new Destination(yogyakarta.getDestinationCityId(), makassar.getDestinationCityId(), 458800, 120);
            //Yogyakarta - Bali
            Destination yj9 = new Destination(yogyakarta.getDestinationCityId(), bali.getDestinationCityId(), 289200, 140);
            //Yogyakarta - Palembang
            Destination yj11 = new Destination(yogyakarta.getDestinationCityId(), palembang.getDestinationCityId(), 433600, 165);

            List<Destination> destYg = Arrays.asList(yj1, yj3, yj5, yj7, yj9, yj11);
            initService.initDestination(destYg);
            generateSchedule(destYg, ygEconomyBussines.getAirplaneId(), ygEconomyBussines2.getAirplaneId());

            //--Bali
            //Bali - Jakarta
            Destination destBali1 = new Destination(bali.getDestinationCityId(), jakarta.getDestinationCityId(), 472000, 110);
            //Bali - Surabaya
            Destination destBali3 = new Destination(bali.getDestinationCityId(), surabaya.getDestinationCityId(), 169600, 55);
            //Bali - Medan
            Destination destBali5 = new Destination(bali.getDestinationCityId(), medan.getDestinationCityId(), 1222000, 275);
            //Bali - Makassar
            Destination destBali7 = new Destination(bali.getDestinationCityId(), makassar.getDestinationCityId(), 398400, 140);
            //Bali - Yogyakarta
            Destination destBali9 = new Destination(bali.getDestinationCityId(), yogyakarta.getDestinationCityId(), 289200, 145);
            //Bali - Palembang
            Destination destBali11 = new Destination(bali.getDestinationCityId(), palembang.getDestinationCityId(), 680800, 240);

            List<Destination> destBali = Arrays.asList(destBali1, destBali3, destBali5, destBali7, destBali9, destBali11);
            initService.initDestination(destBali);
            generateSchedule(destBali, baliEconomyBussines.getAirplaneId(), baliEconomyBussines2.getAirplaneId());

            //--Palembang
            //Palembang - Jakarta
            Destination destPlb1 = new Destination(palembang.getDestinationCityId(), jakarta.getDestinationCityId(), 212800, 65);
            //Palembang - Surabaya
            Destination destPlb3 = new Destination(palembang.getDestinationCityId(), surabaya.getDestinationCityId(), 521600, 150);
            //Palembang - Medan
            Destination destPlb5 = new Destination(palembang.getDestinationCityId(), medan.getDestinationCityId(), 543600, 180);
            //Palembang - Makassar
            Destination destPlb7 = new Destination(palembang.getDestinationCityId(), makassar.getDestinationCityId(), 850800, 285);
            //Palembang - Yogyakarta
            Destination destPlb9 = new Destination(palembang.getDestinationCityId(), yogyakarta.getDestinationCityId(), 433600, 165);
            //Palembang - Bali
            Destination destPlb11 = new Destination(palembang.getDestinationCityId(), bali.getDestinationCityId(), 680800, 240);

            List<Destination> destPlb = Arrays.asList(destPlb1, destPlb3, destPlb5, destPlb7, destPlb9, destPlb11);
            initService.initDestination(destPlb);
            generateSchedule(destPlb, plbEconomyBussines.getAirplaneId(), plbEconomyBussines2.getAirplaneId());
        }
    }


    private Set<Seats> generateSeats(int idClass, int idClass2) {
        return new HashSet<>(Arrays.asList(new Seats("A1", 'A', idClass,1), new Seats("A2", 'A', idClass,2), new Seats("A3", 'A', idClass,3), new Seats("A4", 'A', idClass, 4), new Seats("A5", 'A', idClass, 5), new Seats("A6", 'A', idClass, 6), new Seats("A7", 'A', idClass, 7), new Seats("A8", 'A', idClass, 8), new Seats("A9", 'A', idClass, 9), new Seats("A10", 'A', idClass, 10)
                , new Seats("B1", 'B', idClass, 1), new Seats("B2", 'B', idClass, 2), new Seats("B3", 'B', idClass, 3), new Seats("B4", 'B', idClass, 4), new Seats("B5", 'B', idClass, 5), new Seats("B6", 'B', idClass, 6), new Seats("B7", 'B', idClass, 7), new Seats("B8", 'B', idClass, 8), new Seats("B9", 'B', idClass, 9), new Seats("B10", 'B', idClass, 10)
                , new Seats("C1", 'C', idClass, 1), new Seats("C2", 'C', idClass, 2), new Seats("C3", 'C', idClass, 3), new Seats("C4", 'C', idClass, 4), new Seats("C5", 'C', idClass, 5), new Seats("C6", 'C', idClass, 6), new Seats("C7", 'C', idClass, 7), new Seats("C8", 'C', idClass, 8), new Seats("C9", 'C', idClass, 9), new Seats("C10", 'C', idClass, 10)
                , new Seats("D1", 'D', idClass, 1), new Seats("D2", 'D', idClass, 2), new Seats("D3", 'D', idClass, 3), new Seats("D4", 'D', idClass, 4), new Seats("D5", 'D', idClass, 5), new Seats("D6", 'D', idClass, 6), new Seats("D7", 'D', idClass, 7), new Seats("D8", 'D', idClass, 8), new Seats("D9", 'D', idClass, 9), new Seats("D10", 'D', idClass, 10),
                new Seats("A11", 'A', idClass2, 11), new Seats("A12", 'A', idClass2,12), new Seats("A13", 'A', idClass2,13), new Seats("A14", 'A', idClass2,14), new Seats("A15", 'A', idClass2, 15), new Seats("A16", 'A', idClass2, 16), new Seats("A17", 'A', idClass2,17), new Seats("A18", 'A', idClass2,18), new Seats("A19", 'A', idClass2,19), new Seats("A20", 'A', idClass2, 20)
                , new Seats("B11", 'B', idClass2,11), new Seats("B12", 'B', idClass2,12), new Seats("B13", 'B', idClass2,13), new Seats("B14", 'B', idClass2, 14), new Seats("B15", 'B', idClass2,15), new Seats("B16", 'B', idClass2,16), new Seats("B17", 'B', idClass2,17), new Seats("B18", 'B', idClass2,18), new Seats("B19", 'B', idClass2,19), new Seats("B20", 'B', idClass2, 20)
                , new Seats("C11", 'C', idClass2, 11), new Seats("C12", 'C', idClass2, 12), new Seats("C13", 'C', idClass2, 13), new Seats("C14", 'C', idClass2, 14), new Seats("C15", 'C', idClass2,15), new Seats("C16", 'C', idClass2,16), new Seats("C17", 'C', idClass2,17), new Seats("C18", 'C', idClass2,18), new Seats("C19", 'C', idClass2,19), new Seats("C20", 'C', idClass2,20)
                , new Seats("D11", 'D', idClass2,11), new Seats("D12", 'D', idClass2,12), new Seats("D13", 'D', idClass2,13), new Seats("D14", 'D', idClass2,14), new Seats("D15", 'D', idClass2,15), new Seats("D16", 'D', idClass2,16), new Seats("D17", 'D', idClass2,17), new Seats("D18", 'D', idClass2,18), new Seats("D19", 'D', idClass2,19), new Seats("D20", 'D', idClass2,20)));
    }

    private void generateSchedule(List<Destination> destinations, int airplane1, int airplane2) {
        int priceEconomy = economiPrice.getPricingId();
        int priceBusiness = businessPrice.getPricingId();
        for (Destination req : destinations) {
            int destinationId = req.getDestinationId();
            for (long i = 0; i <= 365; i++) {
                LocalDate flighDate = LocalDate.now().plusDays(i);
                //Economy
                Schedule schedule = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(3, 40), LocalTime.of(3, 40 ).plusMinutes(req.getDuration()), flighDate, airplane1);
                Schedule schedule1 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(5, 30), LocalTime.of(5, 30).plusMinutes(req.getDuration()), flighDate, airplane2);
                Schedule schedule2 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(11, 30), LocalTime.of(11, 30).plusMinutes(req.getDuration()), flighDate, airplane1);
                Schedule schedule3 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(15, 0),LocalTime.of(15, 0).plusMinutes(req.getDuration()) , flighDate, airplane2);
                Schedule schedule4 = new Schedule(destinationId, economiClass.getClassId(), priceEconomy, LocalTime.of(16, 45), LocalTime.of(16, 45).plusMinutes(req.getDuration()), flighDate, airplane1);

                //Business
                Schedule schedule5 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(req.getDuration()), flighDate, airplane1);
                Schedule schedule6 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(5, 30),LocalTime.of(5, 30).plusMinutes(req.getDuration()) , flighDate, airplane2);
                Schedule schedule7 = new Schedule(destinationId, businessClass.getClassId(), priceEconomy, LocalTime.of(11, 30), LocalTime.of(11, 30).plusMinutes(req.getDuration()), flighDate, airplane1);
                Schedule schedule8 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(15, 0), LocalTime.of(15, 0).plusMinutes(req.getDuration()), flighDate, airplane2);
                Schedule schedule9 = new Schedule(destinationId, businessClass.getClassId(), priceBusiness, LocalTime.of(16, 45),LocalTime.of(16, 45).plusMinutes(req.getDuration()) , flighDate, airplane1);
                initService.initSchedule(Arrays.asList(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, schedule8, schedule9));
            }
        }
    }
}
